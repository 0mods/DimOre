package com.algorithmlx.dimore.init.post.loot

import com.algorithmlx.dimore.ModId
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import java.io.File
import kotlin.String
import kotlin.io.endsWith
import kotlin.io.inputStream
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.Path
import kotlin.io.path.deleteRecursively
import kotlin.io.path.exists
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.moveTo
import kotlin.io.startsWith

@OptIn(ExperimentalSerializationApi::class)
object SimpleGenerator {
    @OptIn(ExperimentalSerializationApi::class, DelicateCoroutinesApi::class, ExperimentalPathApi::class)
    @JvmStatic
    fun generateLootTables(hasKubeJS: Boolean) {
        val json = Json {
            prettyPrint = true
            prettyPrintIndent = "  "
            ignoreUnknownKeys = true
            allowComments = true
            allowTrailingComma = true
        }

        val generativeConfigPath = Path("config/$ModId/drops")
        val generativeConfigFile = generativeConfigPath.toFile()

        if (!generativeConfigPath.exists()) {
            generativeConfigFile.parentFile.mkdirs()
            generativeConfigFile.mkdirs()
            return // no needed to procedure
        }

        GlobalScope.launch(Dispatchers.Default) {
            generativeConfigFile.listFiles().forEach {
                if (it.startsWith("_") || !it.endsWith(".json")) return@forEach
                val decoded = json.decodeFromStream<SimpleLootTable>(it.inputStream())
                val replaces = decoded.replaceExists != null

                val encodeModId = if (replaces) decoded.replaceExists.split(":") else ModId

                val exitPath = if (hasKubeJS)
                    Path("kubejs/data/$encodeModId/loot_tables/blocks/")
                else Path("${ModId}_generated/data/$encodeModId/loot_tables/blocks/")

                val exitAsFile = exitPath.toFile()
                if (!exitPath.exists()) {
                    exitAsFile.parentFile.mkdirs()
                    exitAsFile.mkdirs()
                }

                this@SimpleGenerator.startConvert(json, decoded, exitAsFile)
            }
        }

        if (!hasKubeJS) return // skipping moving
        val generatedPath = Path("${ModId}_generated/")
        if (!generatedPath.exists()) return
        val target = Path("kubejs/data/")

        generatedPath.listDirectoryEntries().forEach {
            it.moveTo(target)
        }

        generatedPath.deleteRecursively()
    }

    @JvmStatic
    private fun startConvert(json: Json, input: SimpleLootTable, outputDirectory: File) {
        val output = mcConvert(input)
        val fileName = "${(input.replaceExists ?: input.target).split(":").last()}.json"

        if (!outputDirectory.exists()) {
            outputDirectory.parentFile.mkdirs()
            outputDirectory.mkdirs()
        }

        val file = File(outputDirectory, fileName)
        json.encodeToStream(output, file.outputStream())
    }

    @JvmStatic
    private fun mcConvert(input: SimpleLootTable): MCLootTable {
        val childrenEntries = input.drops.map {
            when (it) {
                is SelfDrop -> processSelfDrop(input.target, it)
                is ItemDrop -> processItemDrop(it)
            }
        }

        val mainEntry = MCEntry(
            type = "minecraft:alternative",
            children = childrenEntries
        )

        return MCLootTable(pools = listOf(MCPool(entries = listOf(mainEntry))), randomSequence = input.target.replace(":", ":blocks/"))
    }

    @JvmStatic
    private fun processSelfDrop(target: String, drop: SelfDrop): MCEntry {
        val conditions = mutableListOf<MCCondition>()
        drop.require?.forEach {
            if (it.type != "enchantment") return@forEach
            val require = it as EnchantmentRequire
            conditions += MCCondition(
                condition = "minecraft:match_tool",
                predicate = MCPredicate(
                    enchantments = listOf(
                        MCEnchantmentPredicate(
                            enchantment = "minecraft:${require.enchantment}",
                            levels = MCEnchantmentPredicate.MCLevelBounds(min = require.minLevel, max = require.maxLevel)
                        )
                    )
                )
            )
        }

        return MCEntry(
            type = "minecraft:item",
            name = target,
            conditions = conditions.ifEmpty { null }
        )
    }

    private fun processItemDrop(itemDrop: ItemDrop): MCEntry {
        val functions = mutableListOf<MCFunction>()
        itemDrop.functions.forEach { when(it) {
            "uniform" -> itemDrop.uniformValues?.let { v ->
                functions += MCSetCountFunction(
                    add = false,
                    count = MCSetCountFunction.MCCount(min = v.min, max = v.max)
                )
            }
            "fortune" -> functions += MCApplyBonus(enchantment = "minecraft:fortune", formula = "minecraft:ore_drops")
            "explosion_decay" -> functions += MCFunction.SingletonFunction("minecraft:explosion_decay")
        } }

        return MCEntry(
            type = "minecraft:item",
            name = itemDrop.id,
            functions = functions.ifEmpty { null }
        )
    }
}
