package com.algorithmlx.dimore.worldgen

import com.algorithmlx.dimore.LOGGER
import com.algorithmlx.dimore.ModId
import com.algorithmlx.dimore.init.DORegistry
import com.algorithmlx.dimore.init.config.DimOreCommon
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

    val netherCoalFeature: RegistryObject<ConfiguredFeature<*, *>> = configured.register("nether_coal_ores") {
        ConfiguredFeature(Feature.ORE, OreConfiguration(Suppliers.memoize {
            listOf(
                netherReplaces(DORegistry.netherCoal)
            )
        }.get(), 17))
    }
    val netherIronFeature: RegistryObject<ConfiguredFeature<*, *>> = configured.register("nether_iron_ores") {
        ConfiguredFeature(Feature.ORE, OreConfiguration(Suppliers.memoize {
            listOf(
                netherReplaces(DORegistry.netherIron),
            )
        }.get(), 9))
    }
    val netherLapisFeature: RegistryObject<ConfiguredFeature<*, *>> = configured.register("nether_lapis_ores") {
        ConfiguredFeature(Feature.ORE, OreConfiguration(Suppliers.memoize {
            listOf(
                netherReplaces(DORegistry.netherLapis),
            )
        }.get(), 7))
    }
    val netherRedStoneFeature: RegistryObject<ConfiguredFeature<*, *>> = configured.register("nether_redstone_ores") {
        ConfiguredFeature(Feature.ORE, OreConfiguration(Suppliers.memoize {
            listOf(
                netherReplaces(DORegistry.netherRedstone)
            )
        }.get(), 8))
    }
    val netherCopperFeature: RegistryObject<ConfiguredFeature<*, *>> = configured.register("nether_copper_ores") {
        ConfiguredFeature(Feature.ORE, OreConfiguration(Suppliers.memoize {
            listOf(
                netherReplaces(DORegistry.netherCopper)
            )
        }.get(), 10))
    }
    val netherDiamondFeature: RegistryObject<ConfiguredFeature<*, *>> = configured.register("nether_diamond_ores") {
        ConfiguredFeature(Feature.ORE, OreConfiguration(Suppliers.memoize {
            listOf(
                netherReplaces(DORegistry.netherDiamond)
            )
        }.get(), 8))
    }
    val netherEmeraldFeature: RegistryObject<ConfiguredFeature<*, *>> = configured.register("nether_emerald_ores") {
        ConfiguredFeature(Feature.ORE, OreConfiguration(Suppliers.memoize {
            listOf(
                netherReplaces(DORegistry.netherEmerald)
            )
        }.get(), 3))
    }

    val endCoalFeature: RegistryObject<ConfiguredFeature<*, *>> = configured.register("end_coal_ores") {
        ConfiguredFeature(Feature.ORE, OreConfiguration(Suppliers.memoize {
            listOf(
                endReplaces(DORegistry.endCoal)
            )
        }.get(), 17))
    }
    val endIronFeature: RegistryObject<ConfiguredFeature<*, *>> = configured.register("end_iron_ores") {
        ConfiguredFeature(Feature.ORE, OreConfiguration(Suppliers.memoize {
            listOf(
                endReplaces(DORegistry.endIron),
            )
        }.get(), 9))
    }
    val endLapisFeature: RegistryObject<ConfiguredFeature<*, *>> = configured.register("end_lapis_ores") {
        ConfiguredFeature(Feature.ORE, OreConfiguration(Suppliers.memoize {
            listOf(
                endReplaces(DORegistry.endLapis),
            )
        }.get(), 7))
    }
    val endRedStoneFeature: RegistryObject<ConfiguredFeature<*, *>> = configured.register("end_redstone_ores") {
        ConfiguredFeature(Feature.ORE, OreConfiguration(Suppliers.memoize {
            listOf(
                endReplaces(DORegistry.endRedstone)
            )
        }.get(), 8))
    }
    val endCopperFeature: RegistryObject<ConfiguredFeature<*, *>> = configured.register("end_copper_ores") {
        ConfiguredFeature(Feature.ORE, OreConfiguration(Suppliers.memoize {
            listOf(
                endReplaces(DORegistry.endCopper)
            )
        }.get(), 10))
    }
    val endDiamondFeature: RegistryObject<ConfiguredFeature<*, *>> = configured.register("end_diamond_ores") {
        ConfiguredFeature(Feature.ORE, OreConfiguration(Suppliers.memoize {
            listOf(
                endReplaces(DORegistry.endDiamond)
            )
        }.get(), 8))
    }
    val endEmeraldFeature: RegistryObject<ConfiguredFeature<*, *>> = configured.register("end_emerald_ores") {
        ConfiguredFeature(Feature.ORE, OreConfiguration(Suppliers.memoize {
            listOf(
                endReplaces(DORegistry.endEmerald)
            )
        }.get(), 3))
    }
    val endGoldFeature: RegistryObject<ConfiguredFeature<*, *>> = configured.register("end_quartz_ores") {
        ConfiguredFeature(Feature.ORE, OreConfiguration(Suppliers.memoize {
            listOf(
                endReplaces(DORegistry.endGold)
            )
        }.get(), 9))
    }
    val endQuartzFeature: RegistryObject<ConfiguredFeature<*, *>> = configured.register("end_gold_ores") {
        ConfiguredFeature(Feature.ORE, OreConfiguration(Suppliers.memoize {
            listOf(
                endReplaces(DORegistry.endQuartz)
            )
        }.get(), 14))
    }

    private fun <T: Block> netherReplaces(block: RegistryObject<T>) =
        OreConfiguration.target(OreFeatures.NETHER_ORE_REPLACEABLES, block.get().defaultBlockState())

    private fun <T: Block> endReplaces(block: RegistryObject<T>) =
        OreConfiguration.target(BlockMatchTest(Blocks.END_STONE), block.get().defaultBlockState())
}