package com.algorithmlx.dimore.worldgen

//? if fabric {
import com.algorithmlx.dimore.LOGGER
import com.algorithmlx.dimore.ModId
import com.algorithmlx.dimore.init.Registry
import com.algorithmlx.dimore.init.config.DimOreConfigManager
import com.algorithmlx.dimore.util.OreCatalog
import com.algorithmlx.dimore.util.ResLoc
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.dimension.LevelStem
import net.minecraft.world.level.levelgen.GenerationStep
import java.util.function.Predicate

object OreConfig {
    fun init() {
        OreCatalog.generation(DimOreConfigManager.config).forEach { ore ->
            generateInDimension(ore.settings.target, ore.id)
        }

        Registry.getPostBlocks().forEach { (id, block) ->
            generateInDimension(block.generationSettings.target, id)
        }
    }

    private fun generateInDimension(dimension: String, id: String) {
        val location = try {
            ResLoc.parse(dimension)
        } catch (exception: Exception) {
            LOGGER.error("Invalid dimension '{}' for ore '{}'; generation skipped", dimension, id, exception)
            return
        }

        val dimensionKey = ResourceKey.create(Registries.LEVEL_STEM, location)

        BiomeModifications.addFeature(
            foundInDimension(dimensionKey),
            GenerationStep.Decoration.UNDERGROUND_ORES,
            ResourceKey.create(Registries.PLACED_FEATURE, ResLoc.fromNamespaceAndPath(ModId, id))
        )
    }

    private fun foundInDimension(key: ResourceKey<LevelStem>): Predicate<BiomeSelectionContext> =
        Predicate { it.canGenerateIn(key) }
}
//?}
