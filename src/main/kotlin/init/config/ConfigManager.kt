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

    val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        prettyPrintIndent = "  "
        allowComments = true
        encodeDefaults = true
    }

    var config = DimensionalOresConfig()
        private set

    fun load() {
        val configCache: MutableMap<String, Any> = mutableMapOf()

        val configFile = File("config/dimore/dimore.json")
        if (!configFile.exists()) {
            configFile.parentFile.mkdirs()
            save(configCache, configFile, config, DimensionalOresConfig::class)
        } else {
            try {
                config = json.decodeFromStream(configFile.inputStream())
            } catch (e: Exception) {
                e.printStackTrace()
                save(configCache, configFile, config, DimensionalOresConfig::class)
            }
        }

        updateCache(configCache, config)
    }

    private inline fun <reified T : Any> save(cache: MutableMap<String, Any>, configFile: File, toSave: T, clazz: KClass<T>) {
        val jsonStr = json.encodeToString(toSave)
        val commentedStr = injectComments(jsonStr, clazz)
        configFile.writeText(commentedStr)
        updateCache(cache, toSave)
    }

    private inline fun <reified T> updateCache(cache: MutableMap<String, Any>, config: T) {
        val jsonObject = json.encodeToJsonElement(config).jsonObject
        val flat = flatten(jsonObject)
        cache.putAll(flat)
    }

    @JvmStatic
    fun isEnable(cache: Map<String, Any>, key: String): Boolean = cache[key] as? Boolean ?: true

    @JvmStatic
    fun getInt(cache: Map<String, Any>, key: String): Int = cache[key] as? Int ?: 0

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

    fun injectComments(jsonString: String, rootClass: KClass<*>): String {
        val comMap = buildCommentMap(rootClass)

        val lines = jsonString.lines()
        val pathStack = arrayListOf<String>()

        val keyRegex = Regex("""^(\s*)"(.+?)"\s*:""")

        val sb = buildString {
            lines.forEach {
                val match = keyRegex.find(it)
                if (match != null) {
                    val indent = match.groupValues[1]
                    val key = match.groupValues[2]
                    val indentL = indent.length / 2

                    while (pathStack.size >= indentL) {
                        pathStack.removeAt(pathStack.lastIndex)
                    }

                    val curr = if (pathStack.isEmpty()) key else "${pathStack.joinToString(".")}.$key"

                    val comment = comMap[curr]
                    if (comment != null)
                        append(comment.lines().joinToString("\n") { jts -> "$indent$jts" }).append("\n")

                    if (it.trimEnd().endsWith("{"))
                        pathStack.add(key)
                }

                append(it).append("\n")
            }
        }

        return sb.trimEnd()
    }

    private fun buildCommentMap(kClass: KClass<*>, prefix: String = "", visited: MutableSet<KClass<*>> = mutableSetOf()): Map<String, String> {
        if (!visited.add(kClass)) return emptyMap()
        val m = mutableMapOf<String, String>()

        kClass.memberProperties.forEach { p ->
            val ann = p.findAnnotation<JsonComment>()
            val serial = p.findAnnotation<SerialName>()?.value ?: p.name

            val fullKey = if (prefix.isEmpty()) serial else "$prefix.$serial"

            if (ann != null)
                m[fullKey] = formatComment(ann)

            val rtc = p.returnType.classifier as? KClass<*>
            if (rtc != null) {
                val isSerializable = rtc.hasAnnotation<Serializable>()
                val isNotPrimitive = !rtc.qualifiedName!!.startsWith("kotlin.")

                if (isSerializable && isNotPrimitive)
                    m.putAll(buildCommentMap(rtc, fullKey, visited))
            }
        }

        return m
    }

    private fun formatComment(ann: JsonComment): String = if (ann.comments.size > 1 && ann.multiline)
        "${COMMENT_MULTILINE_START}\n${ann.comments.joinToString("\n") { " $COMMENT_STAR $it" } }\n $COMMENT_MULTILINE_END"
    else ann.comments.joinToString("\n") { "$COMMENT_SINGLETON $it" }
}
