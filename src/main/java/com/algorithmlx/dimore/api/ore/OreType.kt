package com.algorithmlx.dimore.api.ore

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks

enum class OreType(private val ore: Block): IOreType {
    QUARTZ(Blocks.NETHER_QUARTZ_ORE),
    COAL(Blocks.COAL_ORE),
    COPPER(Blocks.COPPER_ORE),
    IRON(Blocks.IRON_ORE),
    GOLD(Blocks.GOLD_ORE),
    LAPIS(Blocks.LAPIS_ORE),
    DIAMOND(Blocks.DIAMOND_ORE),
    EMERALD(Blocks.EMERALD_ORE);

    override fun getParentOreBlock(): Block = this.ore
}