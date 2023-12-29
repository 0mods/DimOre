package com.algorithmlx.dimore.worldgen

import com.algorithmlx.dimore.LOGGER
import com.algorithmlx.dimore.ModId
import com.algorithmlx.dimore.api.dimension.DimensionOreType
import com.algorithmlx.dimore.api.dimension.IDimensionOreType
import com.algorithmlx.dimore.init.DORegistry
import com.algorithmlx.dimore.init.config.DOConfigCommon
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

object DOConfFeatures {
    private val configured = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, ModId)
    
    fun init(bus: IEventBus) {
        configured.register(bus)
        LOGGER.info("Configuration Features initialized")
    }

    /*
    * Quartz - 14
    * Coal - 17
    * Iron - 9
    * Gold - 9
    * Redstone - 8
    * Diamond - 8
    * Lapis Lazuli - 7
    * Emerald - 3
    * Copper - 10
    */

    val netherCoalFeature = nether("nether_coal_ores", DORegistry.netherCoal, 17)
    val netherIronFeature = nether("nether_iron_ores", DORegistry.netherIron, 9)
    val netherLapisFeature = nether("nether_lapis_ores", DORegistry.netherLapis, 7)
    val netherRedStoneFeature = nether("nether_redstone_ores", DORegistry.netherRedstone, 8)
    val netherCopperFeature = nether("nether_copper_ores", DORegistry.netherCopper, 10)
    val netherDiamondFeature = nether("nether_diamond_ores", DORegistry.netherDiamond, 8)
    val netherEmeraldFeature = nether("nether_emerald_ores", DORegistry.netherEmerald, 3)

    val endCoalFeature = end("end_coal_ores", DORegistry.endCoal, 17)
    val endIronFeature = end("end_iron_ores", DORegistry.endIron, 9)
    val endLapisFeature = end("end_lapis_ores", DORegistry.endLapis, 7)
    val endRedStoneFeature = end("end_redstone_ores", DORegistry.endRedstone, 8)
    val endCopperFeature = end("end_copper_ores", DORegistry.endCopper, 10)
    val endDiamondFeature = end("end_diamond_ores", DORegistry.endDiamond, 8)
    val endEmeraldFeature = end("end_emerald_ores", DORegistry.endEmerald, 3)
    val endGoldFeature = end("end_quartz_ores", DORegistry.endGold, 9)
    val endQuartzFeature = end("end_quartz_ores", DORegistry.endQuartz, 14)

    private fun <T: Block> end(id: String, t: RegistryObject<T>, size: Int, bool: Boolean = true) =
        feature(id, t, size, bool && DOConfigCommon.generateEndOres.get(), DimensionOreType.END)

    private fun <T: Block> nether(id: String, t: RegistryObject<T>, size: Int, bool: Boolean = true) =
        feature(id, t, size, bool && DOConfigCommon.generateNetherOres.get(), DimensionOreType.NETHER)

    private fun <T: Block> feature(id: String, t: RegistryObject<T>, size: Int, bool: Boolean = true, dim: IDimensionOreType = DimensionOreType.OVERWORLD):
            RegistryObject<ConfiguredFeature<*, *>> =
        configured.register(id) {
            ConfiguredFeature(
                Feature.ORE,
                OreConfiguration(
                    Suppliers.memoize { listOf(dim.getReplacementSettings(t.get().defaultBlockState())) }.get(),
                    if (bool) size else 0
                )
            )
        }

    private fun <T: Block> netherReplaces(block: RegistryObject<T>) =
        OreConfiguration.target(OreFeatures.NETHER_ORE_REPLACEABLES, block.get().defaultBlockState())

    private fun <T: Block> endReplaces(block: RegistryObject<T>) =
        OreConfiguration.target(BlockMatchTest(Blocks.END_STONE), block.get().defaultBlockState())

    private fun <T: Block> overReplaces(block: RegistryObject<T>) =
        OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, block.get().defaultBlockState())

    private fun <T: Block> overDeepReplaces(block: RegistryObject<T>) =
        OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, block.get().defaultBlockState())
}