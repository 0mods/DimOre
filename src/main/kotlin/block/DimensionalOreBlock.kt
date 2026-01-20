package com.algorithmlx.dimore.block

import com.algorithmlx.dimore.util.DimensionOre
import com.algorithmlx.dimore.util.OreDimensionType
import com.algorithmlx.dimore.util.OreType
import net.minecraft.network.chat.MutableComponent
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.DropExperienceBlock

class DimensionalOreBlock(override val oreType: OreType, override val oreDimensionType: OreDimensionType): DropExperienceBlock(
    oreType.experienceDrop,
    Properties.ofFullCopy(Blocks.STONE)
        .requiresCorrectToolForDrops()
        .strength(3f, 3f)
        .noOcclusion()
), DimensionOre {
    override fun getName(): MutableComponent = buildName()
}
