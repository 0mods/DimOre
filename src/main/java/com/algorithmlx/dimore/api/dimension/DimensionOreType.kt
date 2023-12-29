package com.algorithmlx.dimore.api.dimension

import net.minecraft.data.worldgen.features.OreFeatures
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest

enum class DimensionOreType(private val assertBlock: Block, private val rule: RuleTest): IDimensionOreType {
    OVERWORLD(Blocks.STONE, OreFeatures.STONE_ORE_REPLACEABLES),
    OVERWORLD_DEEPSLATE(Blocks.DEEPSLATE, OreFeatures.DEEPSLATE_ORE_REPLACEABLES),
    NETHER(Blocks.NETHERRACK, OreFeatures.NETHER_ORE_REPLACEABLES),
    END(Blocks.END_STONE, BlockMatchTest(Blocks.END_STONE));

    override fun getDimBlock(): Block = assertBlock

    override fun getReplacementSettings(block: BlockState): OreConfiguration.TargetBlockState =
        OreConfiguration.target(rule, block)
}