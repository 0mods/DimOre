package com.algorithmlx.dimore.worldgen

import com.algorithmlx.dimore.LOGGER
import com.algorithmlx.dimore.ModId
import com.algorithmlx.dimore.init.config.DOCommonConfig
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
import thedarkcolour.kotlinforforge.forge.ObjectHolderDelegate
import thedarkcolour.kotlinforforge.forge.registerObject

object DOPlacedFeatures {
    private val placed = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, ModId)

    fun init(bus: IEventBus) {
        placed.register(bus)
        LOGGER.info("Placed Features initialized")
    }

    val netherCoalPlace: ObjectHolderDelegate<PlacedFeature> = nether("nether_coal", netherCoalFeature, 20)
    val netherIronPlace: ObjectHolderDelegate<PlacedFeature> = nether("nether_iron", netherIronFeature, 10)
    val netherLapisPlace: ObjectHolderDelegate<PlacedFeature> = nether("nether_lapis", netherLapisFeature, 4)
    val netherRedStonePlace: ObjectHolderDelegate<PlacedFeature> = nether("nether_redstone", netherRedStoneFeature, 8)
    val netherCopperPlace: ObjectHolderDelegate<PlacedFeature> = nether("nether_copper", netherCopperFeature, 16)
    val netherDiamondPlace: ObjectHolderDelegate<PlacedFeature> = nether("nether_diamond", netherDiamondFeature, 7)
    val netherEmeraldPlace: ObjectHolderDelegate<PlacedFeature> = nether("nether_emerald", netherEmeraldFeature, 4)

    val endCoalPlace: ObjectHolderDelegate<PlacedFeature> = end("end_coal", endCoalFeature, 7)
    val endIronPlace: ObjectHolderDelegate<PlacedFeature> = end("end_iron", endIronFeature, 5)
    val endLapisPlace: ObjectHolderDelegate<PlacedFeature> = end("end_lapis", endLapisFeature, 4)
    val endRedStonePlace: ObjectHolderDelegate<PlacedFeature> = end("end_redstone", endRedStoneFeature, 8)
    val endCopperPlace: ObjectHolderDelegate<PlacedFeature> = end("end_copper", endCopperFeature, 8)
    val endDiamondPlace: ObjectHolderDelegate<PlacedFeature> = end("end_diamond", endDiamondFeature, 7)
    val endEmeraldPlace: ObjectHolderDelegate<PlacedFeature> = end("end_emerald", endEmeraldFeature, 4)
    val endGoldPlace: ObjectHolderDelegate<PlacedFeature> = end("end_gold", endGoldFeature, 4)
    val endQuartzPlace: ObjectHolderDelegate<PlacedFeature> = end("end_quartz", endQuartzFeature, 8)

    private fun <T: ObjectHolderDelegate<ConfiguredFeature<*, *>>> nether(id: String, holder: T, size: Int) =
        place(id, holder, size)

    private fun <T: ObjectHolderDelegate<ConfiguredFeature<*, *>>> end(id: String, holder: T, size: Int) =
        place(id, holder, size)

    private fun <T: ObjectHolderDelegate<ConfiguredFeature<*, *>>> place(id: String, holder: T, size: Int) =
        placed.registerObject(id) {
            PlacedFeature(
                holder.registryObject.holder.get(),
                commonOrePlacement(
                    size,
                    HeightRangePlacement.uniform(
                        VerticalAnchor.absolute(-16),
                        VerticalAnchor.absolute(480)
                    )
                )
            )
        }
    private fun orePlacement(sizeModifier: PlacementModifier, heightPlacement: PlacementModifier) = listOf(
        sizeModifier, InSquarePlacement.spread(), heightPlacement, BiomeFilter.biome()
    )

    private fun commonOrePlacement(size: Int, height: PlacementModifier) = orePlacement(CountPlacement.of(size), height)

    private fun rareOrePlacement(size: Int, height: PlacementModifier) = orePlacement(RarityFilter.onAverageOnceEvery(size), height)
}