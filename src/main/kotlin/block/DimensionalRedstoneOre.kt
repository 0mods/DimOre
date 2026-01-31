package com.algorithmlx.dimore.block

import com.algorithmlx.dimore.util.DimensionOre
import com.algorithmlx.dimore.util.OreDimensionType
import com.algorithmlx.dimore.util.OreType
import com.algorithmlx.dimore.util.OreTypes
import net.minecraft.network.chat.MutableComponent
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.RedStoneOreBlock
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument
import net.minecraft.world.level.material.MapColor

class DimensionalRedstoneOre(
    override val oreDimensionType: OreDimensionType,
    properties: Properties
): RedStoneOreBlock(properties), DimensionOre {
    override fun getName(): MutableComponent = buildName()
    override val oreType: OreType = OreTypes.REDSTONE
}
