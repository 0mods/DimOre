package com.algorithmlx.dimore.api

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks

enum class DimensionOreType(private val assertBlock: Block): IDimensionOreType {
    OVERWORLD(Blocks.STONE),
    NETHER(Blocks.NETHERRACK),
    END(Blocks.END_STONE);

    override fun getDimBlock(): Block = assertBlock
}