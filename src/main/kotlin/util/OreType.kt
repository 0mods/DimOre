package com.algorithmlx.dimore.util

import net.minecraft.util.valueproviders.IntProvider
import net.minecraft.world.level.block.Block

interface OreType {
    val parentOreBlock: Block
    val experienceDrop: IntProvider
}