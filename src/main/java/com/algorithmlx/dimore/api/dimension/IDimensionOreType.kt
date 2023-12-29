package com.algorithmlx.dimore.api.dimension

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration

interface IDimensionOreType {
    fun getDimBlock(): Block

    fun getReplacementSettings(block: BlockState): OreConfiguration.TargetBlockState
}