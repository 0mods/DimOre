package com.algorithmlx.dimore.util

import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration

interface OreDimensionType {
    fun replacementSettings(block: BlockState): OreConfiguration.TargetBlockState
}
