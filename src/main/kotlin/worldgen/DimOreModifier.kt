package com.algorithmlx.dimore.worldgen

//? if neoforge {
/*import com.algorithmlx.dimore.init.Registry
import com.algorithmlx.dimore.init.config.ConfigManager
import com.algorithmlx.dimore.util.OreDimensionTypes
import com.algorithmlx.dimore.util.OreGeneratorFactory
import com.algorithmlx.dimore.util.OreTypes
import com.mojang.serialization.MapCodec
import net.minecraft.core.Holder
import net.minecraft.tags.BiomeTags
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.levelgen.GenerationStep
import net.minecraft.world.level.levelgen.placement.PlacedFeature
import net.neoforged.neoforge.common.world.BiomeModifier
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo

class DimOreModifier: BiomeModifier {
    private val featuresCache = mutableMapOf<String, Holder<PlacedFeature>>()

    override fun modify(
        biome: Holder<Biome>,
        phase: BiomeModifier.Phase,
        builder: ModifiableBiomeInfo.BiomeInfo.Builder
    ) {
        if (phase != BiomeModifier.Phase.ADD) return

        if (biome.`is`(BiomeTags.IS_NETHER) && ConfigManager.config.netherOres.generateOres) {
            OreTypes.netherOres.forEach {
                val cfg = OreTypes.configByTypeNether[it] ?: return@forEach
                if (!cfg.generate) return@forEach

                val id = "nether_${it.name.lowercase()}_ore"
                val feature = this.getFeature(id) {
                    val block = Registry.blockHolders[id]?.value() ?: return@getFeature null
                    val configured = OreGeneratorFactory.createConfigured(OreDimensionTypes.NETHER, block, cfg.size)
                    val placed = OreGeneratorFactory.createPlaced(Holder.direct(configured), cfg.count, cfg.minHeight, cfg.maxHeight)

                    Holder.direct(placed)
                }

                if (feature != null) builder.generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, feature)
            }
        } else if (biome.`is`(BiomeTags.IS_END) && ConfigManager.config.endOres.generateOres) {
            OreTypes.endOres.forEach {
                val cfg = OreTypes.configByTypeEnd[it] ?: return@forEach
                if (!cfg.generate) return@forEach

                val id = "end_${it.name.lowercase()}_ore"
                val feature = this.getFeature(id) {
                    val block = Registry.blockHolders[id]?.value() ?: return@getFeature null
                    val configured = OreGeneratorFactory.createConfigured(OreDimensionTypes.END, block, cfg.size)
                    val placed = OreGeneratorFactory.createPlaced(Holder.direct(configured), cfg.count, cfg.minHeight, cfg.maxHeight)

                    Holder.direct(placed)
                }

                if (feature != null) builder.generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, feature)
            }
        } else if (ConfigManager.config.overworldOres.generateOres) {
            OreTypes.overworldOres.forEach {
                val cfg = OreTypes.configByTypeOverworld[it] ?: return@forEach
                if (!cfg.generate) return@forEach

                val stoneId = "stone_${it.name.lowercase()}_ore"
                val feature = this.getFeature(stoneId) {
                    val block = Registry.blockHolders[stoneId]?.value() ?: return@getFeature null
                    val configured = OreGeneratorFactory.createConfigured(OreDimensionTypes.OVERWORLD, block, cfg.size)
                    val placed = OreGeneratorFactory.createPlaced(Holder.direct(configured), cfg.count, cfg.minHeight, cfg.maxHeight)

                    Holder.direct(placed)
                }

                if (feature != null) builder.generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, feature)

                val deepslateId = "deepslate_${it.name.lowercase()}_ore"
                val deepslateFeature = this.getFeature(deepslateId) {
                    val block = Registry.blockHolders[deepslateId]?.value() ?: return@getFeature null
                    val configured = OreGeneratorFactory.createConfigured(OreDimensionTypes.OVERWORLD_DEEPSLATE, block, cfg.size)
                    val placed = OreGeneratorFactory.createPlaced(Holder.direct(configured), cfg.count, cfg.minHeight, cfg.maxHeight)

                    Holder.direct(placed)
                }

                if (deepslateFeature != null) builder.generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, deepslateFeature)
            }
        }

    }

    override fun codec(): MapCodec<out BiomeModifier> = codec

    private fun getFeature(id: String, factory: () -> Holder<PlacedFeature>?): Holder<PlacedFeature>? = featuresCache.getOrPut(id) { factory() ?: return null }

    companion object {
        val codec: MapCodec<DimOreModifier> = MapCodec.unit(DimOreModifier())
    }
}
*///?}
