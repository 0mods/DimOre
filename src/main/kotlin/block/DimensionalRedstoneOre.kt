package com.algorithmlx.dimore.block

import com.algorithmlx.dimore.util.DimensionOre
import com.algorithmlx.dimore.util.NameTarget
import com.algorithmlx.dimore.util.OreDimensionType
import com.algorithmlx.dimore.util.OreType
import com.algorithmlx.dimore.util.OreTypes
import net.minecraft.network.chat.MutableComponent
import net.minecraft.world.level.block.RedStoneOreBlock

class DimensionalRedstoneOre(
    override val oreDimensionType: OreDimensionType,
    properties: Properties
): RedStoneOreBlock(properties), DimensionOre {
    override fun getName(): MutableComponent = if (oreDimensionType is NameTarget) buildName(oreDimensionType) else super.getName()
    override val oreType: OreType = OreTypes.REDSTONE
}
