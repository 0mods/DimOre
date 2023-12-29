package com.algorithmlx.dimore.api.ore

import net.minecraft.world.level.block.Block

interface IOreType {
    fun getParentOreBlock(): Block
}