package com.algorithmlx.dimore.worldgen

import com.algorithmlx.dimore.LOGGER
import com.algorithmlx.dimore.ModId
import com.algorithmlx.dimore.api.dimension.DimensionOreType
import com.algorithmlx.dimore.api.dimension.IDimensionOreType
import com.algorithmlx.dimore.init.DORegistry
import com.algorithmlx.dimore.init.config.DOCommonConfig
import com.google.common.base.Suppliers
import net.minecraft.core.Registry
import net.minecraft.data.worldgen.features.OreFeatures
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.RegistryObject
import thedarkcolour.kotlinforforge.forge.ObjectHolderDelegate
import thedarkcolour.kotlinforforge.forge.registerObject

object DOConfFeatures {
    private val configured = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, ModId)
    
    fun init(bus: IEventBus) {
        configured.register(bus)
        LOGGER.info("Configuration Features initialized")
    }

    val netherCoalFeature: ObjectHolderDelegate<ConfiguredFeature<*, *>>
    val netherIronFeature: ObjectHolderDelegate<ConfiguredFeature<*, *>>
    val netherLapisFeature: ObjectHolderDelegate<ConfiguredFeature<*, *>>
    val netherRedStoneFeature: ObjectHolderDelegate<ConfiguredFeature<*, *>>
    val netherCopperFeature: ObjectHolderDelegate<ConfiguredFeature<*, *>>
    val netherDiamondFeature: ObjectHolderDelegate<ConfiguredFeature<*, *>>
    val netherEmeraldFeature: ObjectHolderDelegate<ConfiguredFeature<*, *>>

    val endCoalFeature: ObjectHolderDelegate<ConfiguredFeature<*, *>>
    val endIronFeature: ObjectHolderDelegate<ConfiguredFeature<*, *>>
    val endLapisFeature: ObjectHolderDelegate<ConfiguredFeature<*, *>>
    val endRedStoneFeature: ObjectHolderDelegate<ConfiguredFeature<*, *>>
    val endCopperFeature: ObjectHolderDelegate<ConfiguredFeature<*, *>>
    val endDiamondFeature: ObjectHolderDelegate<ConfiguredFeature<*, *>>
    val endEmeraldFeature: ObjectHolderDelegate<ConfiguredFeature<*, *>>
    val endGoldFeature: ObjectHolderDelegate<ConfiguredFeature<*, *>>
    val endQuartzFeature: ObjectHolderDelegate<ConfiguredFeature<*, *>>

    init {
        netherCoalFeature = nether("nether_coal", DORegistry.netherCoal, 32)
        netherIronFeature = nether("nether_iron", DORegistry.netherIron, 18)
        netherLapisFeature = nether("nether_lapis", DORegistry.netherLapis, 14)
        netherRedStoneFeature = nether("nether_redstone", DORegistry.netherRedstone, 16)
        netherCopperFeature = nether("nether_copper", DORegistry.netherCopper, 20)
        netherDiamondFeature = nether("nether_diamond", DORegistry.netherDiamond, 16)
        netherEmeraldFeature= nether("nether_emerald", DORegistry.netherEmerald, 12)

        endCoalFeature = end("end_coal", DORegistry.endCoal, 32)
        endIronFeature = end("end_iron", DORegistry.endIron, 18)
        endLapisFeature = end("end_lapis", DORegistry.endLapis, 14)
        endRedStoneFeature = end("end_redstone", DORegistry.endRedstone, 16)
        endCopperFeature = end("end_copper", DORegistry.endCopper, 20)
        endDiamondFeature = end("end_diamond", DORegistry.endDiamond, 16)
        endEmeraldFeature = end("end_emerald", DORegistry.endEmerald, 12)
        endGoldFeature = end("end_gold", DORegistry.endGold, 18)
        endQuartzFeature = end("end_quartz", DORegistry.endQuartz, 28)
    }

    private fun <T: Block> end(id: String, t: ObjectHolderDelegate<T>, size: Int) =
        feature(id, t, size, DimensionOreType.END)

    private fun <T: Block> nether(id: String, t: ObjectHolderDelegate<T>, size: Int) =
        feature(id, t, size, DimensionOreType.NETHER)

    private fun <T: Block> feature(id: String, t: ObjectHolderDelegate<T>, size: Int, dim: IDimensionOreType = DimensionOreType.OVERWORLD):
            ObjectHolderDelegate<ConfiguredFeature<*, *>> =
        configured.registerObject(id) {
            ConfiguredFeature(
                Feature.ORE,
                OreConfiguration(
                    Suppliers.memoize { listOf(dim.getReplacementSettings(t.get().defaultBlockState())) }.get(),
                    size
                )
            )
        }
}