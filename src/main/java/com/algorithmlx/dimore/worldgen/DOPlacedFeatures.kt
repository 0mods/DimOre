package com.algorithmlx.dimore.worldgen

import com.algorithmlx.dimore.LOGGER
import com.algorithmlx.dimore.ModId
import com.algorithmlx.dimore.worldgen.DOConfFeatures.endCoalFeature
import com.algorithmlx.dimore.worldgen.DOConfFeatures.endCopperFeature
import com.algorithmlx.dimore.worldgen.DOConfFeatures.endDiamondFeature
import com.algorithmlx.dimore.worldgen.DOConfFeatures.endEmeraldFeature
import com.algorithmlx.dimore.worldgen.DOConfFeatures.endGoldFeature
import com.algorithmlx.dimore.worldgen.DOConfFeatures.endIronFeature
import com.algorithmlx.dimore.worldgen.DOConfFeatures.endLapisFeature
import com.algorithmlx.dimore.worldgen.DOConfFeatures.endQuartzFeature
import com.algorithmlx.dimore.worldgen.DOConfFeatures.endRedStoneFeature
import com.algorithmlx.dimore.worldgen.DOConfFeatures.netherCoalFeature
import com.algorithmlx.dimore.worldgen.DOConfFeatures.netherCopperFeature
import com.algorithmlx.dimore.worldgen.DOConfFeatures.netherDiamondFeature
import com.algorithmlx.dimore.worldgen.DOConfFeatures.netherEmeraldFeature
import com.algorithmlx.dimore.worldgen.DOConfFeatures.netherIronFeature
import com.algorithmlx.dimore.worldgen.DOConfFeatures.netherLapisFeature
import com.algorithmlx.dimore.worldgen.DOConfFeatures.netherRedStoneFeature
import net.minecraft.core.Registry
import net.minecraft.world.level.levelgen.VerticalAnchor
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature
import net.minecraft.world.level.levelgen.placement.*
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.RegistryObject

object DOPlacedFeatures {
    private val placed = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, ModId)

    fun init(bus: IEventBus) {
        placed.register(bus)
        LOGGER.info("Placed Features initialized")
    }
    /*
    * Quartz - 16
    * Coal - 20
    * Iron - 10
    * Gold - 4
    * Redstone - 8
    * Diamond - 7
    * Lapis Lazuli - 4
    * Emerald - 4
    * Copper - 16
    */

    val netherCoalPlace = place("nether_coal", netherCoalFeature, 20)
    val netherIronPlace = place("nether_iron", netherIronFeature, 10)
    val netherLapisPlace = place("nether_lapis", netherLapisFeature, 4)
    val netherRedstonePlace = place("nether_redstone", netherRedStoneFeature, 8)
    val netherCopperPlace = place("nether_copper", netherCopperFeature, 16)
    val netherDiamondPlace = place("nether_diamond", netherDiamondFeature, 7)
    val netherEmeraldPlace = place("nether_emerald", netherEmeraldFeature, 4)

    val endCoalPlace = place("end_coal", endCoalFeature, 7)
    val endIronPlace = place("end_iron", endIronFeature, 5)
    val endLapisPlace = place("end_lapis", endLapisFeature, 4)
    val endRedstonePlace = place("end_redstone", endRedStoneFeature, 8)
    val endCopperPlace = place("end_copper", endCopperFeature, 8)
    val endDiamondPlace = place("end_diamond", endDiamondFeature, 7)
    val endEmeraldPlace = place("end_emerald", endEmeraldFeature, 4)
    val endGoldPlace = place("end_gold", endGoldFeature, 4)
    val endQuartzPlace = place("end_quartz", endQuartzFeature, 8)

    fun <T: RegistryObject<ConfiguredFeature<*, *>>> place(id: String, holder: T, size: Int) =
        placed.register(id) {
            PlacedFeature(
                holder.holder.get(),
                commonOrePlacement(
                    size,
                    HeightRangePlacement.uniform(
                        VerticalAnchor.absolute(-16),
                        VerticalAnchor.absolute(480)
                    )
                )
            )
        }

    fun <T: RegistryObject<ConfiguredFeature<*, *>>> rarePlace(id: String, holder: T, size: Int) =
        placed.register(id) {
            PlacedFeature(
                holder.holder.get(),
                rareOrePlacement(
                    size,
                    HeightRangePlacement.uniform(
                        VerticalAnchor.absolute(-16),
                        VerticalAnchor.absolute(480)
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