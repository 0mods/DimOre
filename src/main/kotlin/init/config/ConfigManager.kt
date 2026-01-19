package com.algorithmlx.dimore.init.config

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.encodeToStream
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.io.File

@OptIn(ExperimentalSerializationApi::class)
object ConfigManager {
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

    private var configCache: Map<String, Boolean> = emptyMap()

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
        json.encodeToStream(config, configFile.outputStream())
        updateCache()
    }

    private fun updateCache() {
        val jsonObject = json.encodeToJsonElement(config).jsonObject

        configCache = jsonObject.mapValues { entry ->
            entry.value.jsonPrimitive.booleanOrNull ?: false
        }
    }

    fun isEnable(key: String): Boolean = configCache[key] ?: true
}
