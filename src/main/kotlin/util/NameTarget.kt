package com.algorithmlx.dimore.util

import net.minecraft.world.level.block.Block

interface NameTarget {
    val dimensionBlock: () -> Block
}