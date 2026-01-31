package com.algorithmlx.dimore.util

import net.minecraft.core.Holder
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.levelgen.VerticalAnchor
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration
import net.minecraft.world.level.levelgen.placement.BiomeFilter
import net.minecraft.world.level.levelgen.placement.CountPlacement
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement
import net.minecraft.world.level.levelgen.placement.InSquarePlacement
import net.minecraft.world.level.levelgen.placement.PlacedFeature

object OreGeneratorFactory {
    @JvmStatic
    fun createConfigured(
        oreDimensionType: OreDimensionType,
        target: Block,
        size: Int
    ): ConfiguredFeature<*, *> {
        val targ = oreDimensionType.replacementSettings(target.defaultBlockState())
        return ConfiguredFeature(
            Feature.ORE,
            OreConfiguration(listOf(targ), size)
        )
    }

    @JvmStatic
    fun createPlaced(
        feature: Holder<ConfiguredFeature<*, *>>,
        count: Int,
        minHeight: Int,
        maxHeight: Int
    ): PlacedFeature {
        val placed = listOf(
            CountPlacement.of(count),
            InSquarePlacement.spread(),
            HeightRangePlacement.uniform(
                VerticalAnchor.absolute(minHeight),
                VerticalAnchor.absolute(maxHeight)
            ),
            BiomeFilter.biome()
        )

        return PlacedFeature(feature, placed)
    }
}
