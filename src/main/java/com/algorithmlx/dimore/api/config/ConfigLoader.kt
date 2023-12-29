package com.algorithmlx.dimore.api.config

import com.algorithmlx.dimore.FATAL_MARKER
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import net.minecraftforge.fml.loading.FMLPaths
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.lang.reflect.Field
import java.lang.reflect.Modifier

object ConfigLoader {
    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()
    private val configs = ConfigFinder.getConfigList()
    private val logger = ConfigFinder.logger

    @JvmStatic
    fun startMagic() {
        logger.info("starting a magic")
        val dir = FMLPaths.CONFIGDIR.get().toFile()
        val files = dir.listFiles()

        if (configs.isEmpty()) logger.warn("Configs is empty! Skipping initialization...")

        configs.forEach {
            logger.info("founded ${it.getName()}")
            files?.forEach { file ->
                val configs: MutableMap<String, JsonObject> = hashMapOf()
                val name = file.name.substring(0, file.name.length - (".json".length))
                try {
                    val fileContents = FileUtils.readFileToString(file, Charsets.UTF_8)
                    val jsonObject = gson.fromJson(fileContents, JsonObject::class.java)
                    if (it.getName() == name)
                        configs[name] = jsonObject
                } catch (e: IOException) {
                    logger.error("Failed to load config file: ${file.name}")
                    e.printStackTrace()
                }
            }

            for (entry in saveJson(it).entries) {
                val configFile = File(dir, entry.key + ".json")
                val jsonStr = gson.toJson(entry.value)
                try {
                    FileUtils.writeStringToFile(configFile, jsonStr, Charsets.UTF_8)
                } catch (e: IOException) {
                    logger.error("Failed to save config file: ${configFile.absolutePath}")
                    throw RuntimeException("Failed to save config file: ${configFile.absolutePath}")
                }
            }
        }
    }

    private fun <T: ICfg> getConfigFields(t: T): MutableMap<Field, ConfigField> {
        val fieldMap: MutableMap<Field, ConfigField> = hashMapOf()
        if (configs.isEmpty())
            logger.warn("Failed to load config fields, because configs is not loaded! Skipping fields search...")
        else {
            for (field in t.javaClass.declaredFields) {
                if (!field.isAnnotationPresent(ConfigField::class.java)) continue

                if (!Modifier.isStatic(field.modifiers)) {
                    logger.error(FATAL_MARKER, "Field ${field.name} is not static!")
                    break
                }

                val annotation = field.getAnnotation(ConfigField::class.java)
                fieldMap[field] = annotation
            }
        }
        return fieldMap
    }

    private fun <T: ICfg> saveJson(t: T): MutableMap<String, JsonObject?> {
        val configs: MutableMap<String, JsonObject?> = hashMapOf()

        if (this.configs.isEmpty())
            logger.warn("Failed to saving config files, because config is not loaded!")
        else {
            val fieldMap = this.getConfigFields(t)
            for (entry in fieldMap) {
                val field = entry.key
                val annotation = entry.value

                val config: JsonObject = configs.computeIfAbsent(t.getName()) { JsonObject() }!!

                val categoryObject: JsonObject
                if (config.has(annotation.category)) {
                    categoryObject = config.getAsJsonObject(annotation.category)
                } else {
                    categoryObject = JsonObject()
                    config.add(annotation.category, categoryObject)
                }

                val key = annotation.paramName.ifEmpty { field.name }

                if (categoryObject.has(key))
                    throw UnsupportedOperationException("Some bad news... Duplicate key found: $key! I idk why.. but you know?")

                val fieldObj = JsonObject()
                if (annotation.desc.isNotEmpty()) fieldObj.addProperty("description", annotation.desc)
                if (annotation.comment.isNotEmpty()) fieldObj.addProperty("comment", annotation.comment)

                val value: Any = try {
                    field.get(null)
                } catch (e: IllegalAccessError) {
                    throw RuntimeException(e)
                }

                val element = gson.toJsonTree(value)
                fieldObj.add("value", element)

                categoryObject.add(key, fieldObj)
            }
        }

        return configs
    }

    private fun <T: ICfg> readJson(configs: HashMap<String, JsonObject?>, t: T) {
        if (this.configs.isEmpty()) {
            logger.warn("Failed to load config files, because config is not loaded!")
            return
        }

        val fields = this.getConfigFields(t)

        for (entry in fields.entries) {
            val field = entry.key
            val annot = entry.value

            val config = configs[t.getName()] ?: continue
            val categoryObject = config.getAsJsonObject(annot.category) ?: continue

            val key = field.name; if (!categoryObject.has(key)) continue

            val fieldObj = categoryObject.get(key).asJsonObject; if (!fieldObj.has("value")) continue

            val jsonValue = fieldObj.get("value")
            val fieldType = field.type

            val fieldValue: Any = gson.fromJson(jsonValue, fieldType)

            try {
                field.set(null, fieldValue)
            } catch (e: IllegalAccessError) {
                logger.error("Failed to set field value. Check stacktrace for more info.")
                throw RuntimeException("Failed to set field value for ${field.name}, in ${t.javaClass.simpleName}! Maybe it is final?", e)
            }
        }
    }
}