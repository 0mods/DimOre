package com.algorithmlx.dimore.init.config

import com.algorithmlx.dimore.LOGGER
import com.algorithmlx.dimore.ModId
import com.algorithmlx.dimore.init.post.ExampleBlock
import com.algorithmlx.dimore.init.post.loot.ExampleLootTable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.io.File
import java.io.IOException

object DimOreConfigManager {
    var config = DimensionalOresConfig()
        private set

    fun load() {
        val configFile = File("config/$ModId/common.json")
        val defaultBlockFile = File("config/$ModId/custom/_example_block.json")
        val defaultLootFile = File("config/$ModId/loot/_example_loot.json")

        migrateCommonConfig(configFile)
        config = ConfigManager.saveOrLoad(configFile, DimensionalOresConfig())

        if (config.enableCustomBlocks && !defaultBlockFile.exists()) {
            ConfigManager.saveOrLoad(defaultBlockFile, ExampleBlock)
        }

        if (config.enableLootTables && !defaultLootFile.exists()) {
            ConfigManager.saveOrLoad(defaultLootFile, ExampleLootTable)
        }
    }

    private fun migrateCommonConfig(file: File) {
        if (!file.exists()) return

        try {
            val original = ConfigManager.json.parseToJsonElement(file.readText()).jsonObject
            val migrated = migrateOreSettings(original)

            if (migrated != original) {
                val defaults = DimensionalOresConfig()
                val decoded = ConfigManager.json.decodeFromJsonElement<DimensionalOresConfig>(migrated)

                ConfigManager.regenerate(file, decoded, defaults)
                LOGGER.info("Migrated DimOre config to the current schema")
            }
        } catch (_: SerializationException) {
            // ConfigManager will back up and recover malformed JSON.
        } catch (_: IOException) {
            // ConfigManager will back up and recover unreadable JSON.
        }
    }

    private fun migrateOreSettings(root: JsonObject): JsonObject {
        var changed = false
        val alternateTargetField = if (GENERATION_TARGET_FIELD == "dimension") "biome" else "dimension"
        val groups = mapOf(
            "nether_ores" to MigrationDefaults(DEFAULT_NETHER_TARGET, MiningLevel.DIAMOND),
            "overworld_ores" to MigrationDefaults(DEFAULT_OVERWORLD_TARGET, MiningLevel.IRON),
            "end_ores" to MigrationDefaults(DEFAULT_END_TARGET, MiningLevel.DIAMOND)
        )

        val migratedRoot = root.toMutableMap()

        groups.forEach { (groupName, defaults) ->
            val group = root[groupName] as? JsonObject ?: return@forEach
            val migratedGroup = group.toMutableMap()

            group.forEach { (oreName, element) ->
                if (oreName == "enabled" || element !is JsonObject) return@forEach

                val migratedSettings = element.toMutableMap()
                val oldEnabled = element["enabled"]?.jsonPrimitive?.booleanOrNull
                val alternateTarget = element[alternateTargetField]?.jsonPrimitive?.contentOrNull

                if (!element.containsKey(GENERATION_TARGET_FIELD)) {
                    migratedSettings[GENERATION_TARGET_FIELD] = JsonPrimitive(
                        if (oldEnabled == false || alternateTarget?.isBlank() == true) "" else defaults.target
                    )
                    changed = true
                }

                if (migratedSettings.remove("enabled") != null) changed = true
                if (migratedSettings.remove(alternateTargetField) != null) changed = true

                if (!element.containsKey("tool")) {
                    migratedSettings["tool"] = JsonPrimitive("pickaxe")
                    changed = true
                }

                if (!element.containsKey("tool_level")) {
                    migratedSettings["tool_level"] = JsonPrimitive(
                        defaults.toolLevel.name.lowercase()
                    )
                    changed = true
                }

                migratedGroup[oreName] = JsonObject(migratedSettings)
            }

            migratedRoot[groupName] = JsonObject(migratedGroup)
        }

        return if (changed) JsonObject(migratedRoot) else root
    }

    private data class MigrationDefaults(val target: String, val toolLevel: MiningLevel)
}
