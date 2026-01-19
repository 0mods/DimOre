package com.algorithmlx.dimore.worldgen

import com.algorithmlx.dimore.ModId
import com.algorithmlx.dimore.init.config.ConfigManager
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.levelgen.GenerationStep
import java.util.function.Predicate

object OreConfig {
    private val config = ConfigManager.config
    
    fun init() {
        if (config.generateOverworldOres) generateOverworldOres()
        if (config.generateNetherOres) generateNetherOres()
        if (config.generateEndOres) generateEndOres()
    }
    
    private fun generateOverworldOres() {
        generateOverworld(config.generateQuartz, "overworld_quartz_ore")
    }
    
    private fun generateNetherOres() {
        generateNether(config.generateNetherCoal, "nether_coal_ore")
        generateNether(config.generateNetherCopper, "nether_copper_ore")
        generateNether(config.generateNetherDiamond, "nether_diamond_ore")
        generateNether(config.generateNetherEmerald, "nether_emerald_ore")
        generateNether(config.generateNetherIron, "nether_iron_ore")
        generateNether(config.generateNetherLapis, "nether_lapis_ore")
        generateNether(config.generateNetherRedstone, "nether_redstone_ore")
    }

    private fun generateEndOres() {
        generateEnd(config.generateEndCoal, "end_coal_ore")
        generateEnd(config.generateEndCopper, "end_copper_ore")
        generateEnd(config.generateEndDiamond, "end_diamond_ore")
        generateEnd(config.generateEndEmerald, "end_emerald_ore")
        generateEnd(config.generateEndGold, "end_gold_ore")
        generateEnd(config.generateEndIron, "end_iron_ore")
        generateEnd(config.generateEndLapis, "end_lapis_ore")
        generateEnd(config.generateEndQuartz, "end_quartz_ore")
        generateEnd(config.generateEndRedstone, "end_redstone_ore")
    }

    private fun generateOverworld(shouldGenerate: Boolean, id: String) =
        shouldGenerateOre(shouldGenerate, BiomeSelectors.foundInOverworld(), id)

    private fun generateNether(shouldGenerate: Boolean, id: String) = 
        shouldGenerateOre(shouldGenerate, BiomeSelectors.foundInTheNether(), id)

    private fun generateEnd(shouldGenerate: Boolean, id: String) =
        shouldGenerateOre(shouldGenerate, BiomeSelectors.foundInTheEnd(), id)
    
    private fun shouldGenerateOre(shouldGenerate: Boolean, selectors: Predicate<BiomeSelectionContext>, id: String) {
        if (shouldGenerate)
            generateOre(selectors, id)
    }

    private fun generateOre(selectors: Predicate<BiomeSelectionContext>, id: String) = BiomeModifications.addFeature(
        selectors,
        GenerationStep.Decoration.UNDERGROUND_ORES,
        key(id)
    )

    private fun key(id: String) = ResourceKey.create(
        Registries.PLACED_FEATURE,
        ResourceLocation.fromNamespaceAndPath(ModId, id)
    )
}
