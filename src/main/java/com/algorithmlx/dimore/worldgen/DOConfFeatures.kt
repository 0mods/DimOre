package com.algorithmlx.dimore.worldgen

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
    }

    val netherFeature: RegistryObject<ConfiguredFeature<*, *>> = configured.register("nether_ores") {
        ConfiguredFeature(Feature.ORE, OreConfiguration(Suppliers.memoize {
            if (DimOreCommon.generateNetherOres.get()) listOf(
                netherReplaces(DORegistry.netherCoal),
                netherReplaces(DORegistry.netherIron),
                netherReplaces(DORegistry.netherLapis),
                netherReplaces(DORegistry.netherRedstone)
            ) else listOf()
        }.get(), 4))
    }
    val netherRareFeature: RegistryObject<ConfiguredFeature<*, *>> = configured.register("nether_rare_ores") {
        ConfiguredFeature(Feature.ORE, OreConfiguration(Suppliers.memoize {
            if (DimOreCommon.generateNetherOres.get()) listOf(
                netherReplaces(DORegistry.netherDiamond),
                netherReplaces(DORegistry.netherEmerald)
            ) else listOf()
        }.get(), 4))
    }
    val endFeature: RegistryObject<ConfiguredFeature<*, *>> = configured.register("nether_iron_ore") {
        ConfiguredFeature(Feature.ORE, OreConfiguration(Suppliers.memoize {
            if (DimOreCommon.generateEndOres.get()) listOf(
                endReplaces(DORegistry.endQuartz),
                endReplaces(DORegistry.endCoal),
                endReplaces(DORegistry.endIron),
                endReplaces(DORegistry.endLapis),
                endReplaces(DORegistry.endRedstone),
            ) else listOf()
        }.get(), 4))
    }

    val endRareFeature: RegistryObject<ConfiguredFeature<*, *>> = configured.register("end_rare_ore") {
        ConfiguredFeature(Feature.ORE, OreConfiguration(Suppliers.memoize {
            if (DimOreCommon.generateEndOres.get()) listOf(
                endReplaces(DORegistry.endGold),
                endReplaces(DORegistry.endDiamond),
                endReplaces(DORegistry.endEmerald)
            ) else listOf()
        }.get(), 4))
    }

    private fun <T: Block> netherReplaces(block: RegistryObject<T>) =
        OreConfiguration.target(OreFeatures.NETHER_ORE_REPLACEABLES, block.get().defaultBlockState())

    private fun <T: Block> endReplaces(block: RegistryObject<T>) =
        OreConfiguration.target(BlockMatchTest(Blocks.END_STONE), block.get().defaultBlockState())
}