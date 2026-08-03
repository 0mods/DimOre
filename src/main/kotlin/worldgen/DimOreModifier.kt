package com.algorithmlx.dimore.worldgen

//? if neoforge {
/*import com.algorithmlx.dimore.LOGGER
import com.algorithmlx.dimore.init.Registry
import com.algorithmlx.dimore.init.config.DimOreConfigManager
import com.algorithmlx.dimore.util.OreCatalog
import com.algorithmlx.dimore.util.OreDimensionType
import com.algorithmlx.dimore.util.OreGeneratorFactory
import com.algorithmlx.dimore.util.OrePlacementConfig
import com.algorithmlx.dimore.util.ResLoc
import com.mojang.serialization.MapCodec
import net.minecraft.core.Holder
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.tags.TagKey
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.levelgen.GenerationStep
import net.minecraft.world.level.levelgen.placement.PlacedFeature
import net.neoforged.neoforge.common.world.BiomeModifier
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo

class DimOreModifier : BiomeModifier {
    private val features = mutableMapOf<String, Holder<PlacedFeature>>()
    private val invalidSelectors = mutableSetOf<String>()

    override fun modify(
        biome: Holder<Biome>,
        phase: BiomeModifier.Phase,
        builder: ModifiableBiomeInfo.BiomeInfo.Builder
    ) {
        if (phase != BiomeModifier.Phase.ADD) return

        OreCatalog.generation(DimOreConfigManager.config).forEach { ore ->
            if (!matches(biome, ore.settings.target)) return@forEach

            addFeature(builder, ore.id, ore.dimensionType, ore.settings)
        }

        Registry.getPostBlocks().forEach { (id, block) ->
            val generation = block.generationSettings

            if (!matches(biome, generation.target)) return@forEach

            addFeature(builder, id, generation.asDimensionType(), generation.config)
        }
    }

    override fun codec(): MapCodec<out BiomeModifier> = codec

    private fun matches(biome: Holder<Biome>, selector: String): Boolean {
        if (selector.isBlank()) return false

        val isTag = selector.startsWith('#')
        val rawLocation = if (isTag) selector.substring(1) else selector
        val location = try {
            ResLoc.parse(rawLocation)
        } catch (exception: Exception) {
            if (invalidSelectors.add(selector)) {
                LOGGER.error("Invalid biome selector '{}'; generation skipped", selector, exception)
            }
            return false
        }

        return if (isTag) {
            biome.`is`(TagKey.create(Registries.BIOME, location))
        } else {
            biome.`is`(ResourceKey.create(Registries.BIOME, location))
        }
    }

    private fun addFeature(
        builder: ModifiableBiomeInfo.BiomeInfo.Builder,
        id: String,
        dimensionType: OreDimensionType,
        config: OrePlacementConfig
    ) {
        val feature = features.getOrPut(id) {
            val block = Registry.blockHolders[id]?.value() ?: return
            val configured = OreGeneratorFactory.createConfigured(dimensionType, block, config.size)
            val placed = OreGeneratorFactory.createPlaced(
                Holder.direct(configured),
                config.count,
                config.minHeight,
                config.maxHeight
            )

            Holder.direct(placed)
        }

        builder.generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, feature)
    }

    companion object {
        val codec: MapCodec<DimOreModifier> = MapCodec.unit(DimOreModifier())
    }
}
*///?}
