package com.algorithmlx.dimore.worldgen

//? if fabric {
/*import com.algorithmlx.dimore.ModId
import com.algorithmlx.dimore.init.config.ConfigManager
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
//? if <1.21.11 {
import net.minecraft.resources.ResourceLocation
//?} else {
/*net.minecraft.resources.Identifier
*///?}
import net.minecraft.world.level.levelgen.GenerationStep
import java.util.function.Predicate

object OreConfig {
    private val config = ConfigManager.config

    fun init() {
        if (config.overworldOres.generateOres) generateOverworldOres()
        if (config.netherOres.generateOres) generateNetherOres()
        if (config.endOres.generateOres) generateEndOres()
    }

    private fun generateOverworldOres() {
        generateOverworld(config.overworldOres.generateQuartz, "overworld_quartz_ore")
    }

    private fun generateNetherOres() {
        generateNether(config.netherOres.generateCoal, "nether_coal_ore")
        generateNether(config.netherOres.generateCopper, "nether_copper_ore")
        generateNether(config.netherOres.generateDiamond, "nether_diamond_ore")
        generateNether(config.netherOres.generateEmerald, "nether_emerald_ore")
        generateNether(config.netherOres.generateIron, "nether_iron_ore")
        generateNether(config.netherOres.generateLapis, "nether_lapis_ore")
        generateNether(config.netherOres.generateRedstone, "nether_redstone_ore")
    }

    private fun generateEndOres() {
        generateEnd(config.endOres.generateCoal, "end_coal_ore")
        generateEnd(config.endOres.generateCopper, "end_copper_ore")
        generateEnd(config.endOres.generateDiamond, "end_diamond_ore")
        generateEnd(config.endOres.generateEmerald, "end_emerald_ore")
        generateEnd(config.endOres.generateGold, "end_gold_ore")
        generateEnd(config.endOres.generateIron, "end_iron_ore")
        generateEnd(config.endOres.generateLapis, "end_lapis_ore")
        generateEnd(config.endOres.generateQuartz, "end_quartz_ore")
        generateEnd(config.endOres.generateRedstone, "end_redstone_ore")
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
*///?}
