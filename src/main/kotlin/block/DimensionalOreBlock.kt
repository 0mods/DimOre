package com.algorithmlx.dimore.block

import com.algorithmlx.dimore.util.DimensionOre
import com.algorithmlx.dimore.util.NameTarget
import com.algorithmlx.dimore.util.OreDimensionType
import com.algorithmlx.dimore.util.OreType
import net.minecraft.network.chat.MutableComponent
import net.minecraft.world.level.block.DropExperienceBlock

class DimensionalOreBlock(
    override val oreType: OreType,
    override val oreDimensionType: OreDimensionType,
    properties: Properties
): DropExperienceBlock(
    oreType.experienceDrop, properties,
), DimensionOre {
    override fun getName(): MutableComponent = if (oreDimensionType is NameTarget) buildName(oreDimensionType) else super.getName()
}
