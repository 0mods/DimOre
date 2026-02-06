package com.algorithmlx.dimore.worldgen

//? if fabric {
import com.algorithmlx.dimore.ModId
import com.algorithmlx.dimore.init.config.ConfigManager
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import com.algorithmlx.dimore.util.ResLoc
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
        generateOverworld(config.overworldOres.quartzSettings.generate, "stone_quartz_ore")
        generateOverworld(config.overworldOres.quartzSettings.generate, "deepslate_quartz_ore")
    }

    private fun generateNetherOres() {
        generateNether(config.netherOres.coalSettings.generate, "nether_coal_ore")
        generateNether(config.netherOres.copperSettings.generate, "nether_copper_ore")
        generateNether(config.netherOres.diamondSettings.generate, "nether_diamond_ore")
        generateNether(config.netherOres.emeraldSettings.generate, "nether_emerald_ore")
        generateNether(config.netherOres.ironSettings.generate, "nether_iron_ore")
        generateNether(config.netherOres.lapisSettings.generate, "nether_lapis_ore")
        generateNether(config.netherOres.redstoneSettings.generate, "nether_redstone_ore")
    }

    private fun generateEndOres() {
        generateEnd(config.endOres.coalSettings.generate, "end_coal_ore")
        generateEnd(config.endOres.copperSettings.generate, "end_copper_ore")
        generateEnd(config.endOres.diamondSettings.generate, "end_diamond_ore")
        generateEnd(config.endOres.emeraldSettings.generate, "end_emerald_ore")
        generateEnd(config.endOres.goldSettings.generate, "end_gold_ore")
        generateEnd(config.endOres.ironSettings.generate, "end_iron_ore")
        generateEnd(config.endOres.lapisSettings.generate, "end_lapis_ore")
        generateEnd(config.endOres.quartzSettings.generate, "end_quartz_ore")
        generateEnd(config.endOres.redstoneSettings.generate, "end_redstone_ore")
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
        ResLoc.fromNamespaceAndPath(ModId, id)
    )
}
//?}
