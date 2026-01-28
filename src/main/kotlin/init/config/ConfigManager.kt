package com.algorithmlx.dimore.init.config

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.File
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.memberProperties

@OptIn(ExperimentalSerializationApi::class)
object ConfigManager {
    private const val COMMENT_MULTILINE_START = "/*"
    private const val COMMENT_MULTILINE_END = "*/"
    private const val COMMENT_STAR = "*"
    private const val COMMENT_SINGLETON = "//"

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        prettyPrintIndent = "  "
        allowComments = true
        encodeDefaults = true
    }
    private val configFile = File("config/dimore.json")

    var config = DimensionalOresConfig()
        private set

    private var configCache: Map<String, Any> = emptyMap()

    fun load() {
        if (!configFile.exists()) {
            configFile.parentFile.mkdirs()
            save()
        } else {
            try {
                config = json.decodeFromStream(configFile.inputStream())
            } catch (e: Exception) {
                e.printStackTrace()
                save()
            }
        }

        updateCache()
    }

    fun save() {
        val jsonStr = json.encodeToString(config)
        val commentedStr = injectComments(jsonStr, DimensionalOresConfig::class)
        configFile.writeText(commentedStr)
        updateCache()
    }

    private fun updateCache() {
        val jsonObject = json.encodeToJsonElement(config).jsonObject
        configCache = flatten(jsonObject)
    }

    @JvmStatic
    fun isEnable(key: String): Boolean = configCache[key] as? Boolean ?: true

    @JvmStatic
    fun getInt(key: String): Int = configCache[key] as? Int ?: 0

    private fun flatten(el: JsonElement, pr: String = ""): Map<String, Any> {
        val m = mutableMapOf<String, Any>()
        when (el) {
            is JsonObject -> {
                el.forEach { (k, v) ->
                    val nk = if (pr.isEmpty()) k else "$pr.$k"
                    m.putAll(flatten(v, nk))
                }
            }
            is JsonPrimitive -> {
                if (el.isString) m[pr] = el.content
                else if (el.booleanOrNull != null) m[pr] = el.boolean
                else if (el.intOrNull != null) m[pr] = el.int
                else if (el.doubleOrNull != null) m[pr] = el.double
            }
            else -> {}
        }

        return m
    }

    private fun injectComments(jsonDecoded: String, configObject: KClass<*>): String {
        var r = jsonDecoded
        val commMap = buildCommentMap(configObject)
        commMap.forEach { (k, c) ->
            val regex = Regex("""^(\s*)"${Regex.escape(k)}"\s*:""", RegexOption.MULTILINE)
            r = regex.replace(r) { mr ->
                val ind = mr.groupValues[1]
                val intdC = c.lines().joinToString("\n") { "$ind$it" }
                "$intdC\n${mr.value}"
            }
        }

        return r
    }

    private fun buildCommentMap(kClass: KClass<*>, visited: MutableSet<KClass<*>> = mutableSetOf()): Map<String, String> {
        if (!visited.add(kClass)) return emptyMap()
        val m = mutableMapOf<String, String>()

        kClass.memberProperties.forEach { p ->
            val ann = p.findAnnotation<JsonComment>()
            val serial = p.findAnnotation<SerialName>()?.value ?: p.name

            if (ann != null)
                m[serial] = formatComment(ann)

            val rtc = p.returnType.classifier as? KClass<*>
            if (rtc != null) {
                val isSerializable = rtc.hasAnnotation<Serializable>()
                val isNotPrimitive = !rtc.qualifiedName!!.startsWith("kotlin")

                if (isSerializable && isNotPrimitive)
                    m.putAll(buildCommentMap(rtc, visited))
            }
        }

        return m
    }

    private fun formatComment(ann: JsonComment): String = if (ann.comments.size > 1 && ann.multiline)
        "${COMMENT_MULTILINE_START}\n${ann.comments.joinToString("\n") { " $COMMENT_STAR $it" } }\n $COMMENT_MULTILINE_END"
    else ann.comments.joinToString("\n") { "$COMMENT_SINGLETON $it" }
}
