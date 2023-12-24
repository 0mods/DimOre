package com.algorithmlx.dimore.worldgen

import com.algorithmlx.dimore.ModId
import com.algorithmlx.dimore.worldgen.DOConfFeatures.endFeature
import com.algorithmlx.dimore.worldgen.DOConfFeatures.endRareFeature
import com.algorithmlx.dimore.worldgen.DOConfFeatures.netherFeature
import com.algorithmlx.dimore.worldgen.DOConfFeatures.netherRareFeature
import net.minecraft.core.Registry
import net.minecraft.world.level.levelgen.VerticalAnchor
import net.minecraft.world.level.levelgen.placement.*
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister

object DOPlacedFeatures {
    private val placed = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, ModId)

    fun init(bus: IEventBus) {
        placed.register(bus)
    }

    val netherPlaced = placed.register("nether_placed") {
        PlacedFeature(
            netherFeature.holder.get(),
            commonOrePlacement(
                4,
                HeightRangePlacement.uniform(
                    VerticalAnchor.aboveBottom(-64),
                    VerticalAnchor.aboveBottom(319)
                )
            )
        )
    }

    val netherRarePlaced = placed.register("nether_rare_placed") {
        PlacedFeature(
            netherRareFeature.holder.get(),
            rareOrePlacement(
                2,
                HeightRangePlacement.uniform(
                    VerticalAnchor.aboveBottom(-64),
                    VerticalAnchor.aboveBottom(319)
                )
            )
        )
    }

    val endPlaced = placed.register("end_placed") {
        PlacedFeature(
            endFeature.holder.get(),
            commonOrePlacement(
                4,
                HeightRangePlacement.uniform(
                    VerticalAnchor.aboveBottom(-64),
                    VerticalAnchor.aboveBottom(319)
                )
            )
        )
    }

    val endRarePlaced = placed.register("end_rare_placed") {
        PlacedFeature(
            endRareFeature.holder.get(),
            rareOrePlacement(
                2,
                HeightRangePlacement.uniform(
                    VerticalAnchor.aboveBottom(-64),
                    VerticalAnchor.aboveBottom(319)
                )
            )
        )
    }

    fun orePlacement(sizeModifier: PlacementModifier, heightPlacement: PlacementModifier) = listOf(
        sizeModifier, InSquarePlacement.spread(), heightPlacement, BiomeFilter.biome()
    )

    fun commonOrePlacement(size: Int, height: PlacementModifier) = orePlacement(CountPlacement.of(size), height)

    fun rareOrePlacement(size: Int, height: PlacementModifier) = orePlacement(RarityFilter.onAverageOnceEvery(size), height)
}