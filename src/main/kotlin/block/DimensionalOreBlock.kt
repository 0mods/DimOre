package com.algorithmlx.dimore.block

import com.algorithmlx.dimore.util.OreDimensionType
import com.algorithmlx.dimore.util.OreType
import net.minecraft.network.chat.Component
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.DropExperienceBlock

class DimensionalOreBlock(private val oreType: OreType, private val oreDimensionType: OreDimensionType): DropExperienceBlock(
    oreType.experienceDrop,
    Properties.ofFullCopy(Blocks.STONE)
        .requiresCorrectToolForDrops()
        .strength(3f, 3f)
        .noOcclusion()
) {
    // no more translations
    // todo: but have problems in some languages like arabic, or where it is incorrect...
    // but i'm too stupid (stupid russian ha-ha-hah) to make it normally
    override fun getDescriptionId(): String = createAppend(oreDimensionType.dimensionBlock, oreType.parentOreBlock).string

    private fun createAppend(material: Block, ore: Block) = Component.translatable(material.descriptionId)
        .append("-")
        .append(Component.translatable(ore.descriptionId))
}
