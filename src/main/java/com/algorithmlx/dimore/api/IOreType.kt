package com.algorithmlx.dimore.api

import net.minecraft.world.level.block.Block

interface IOreType {
    fun getParentOreBlock(): Block
}