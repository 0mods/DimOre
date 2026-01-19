package com.algorithmlx.dimore.block

import com.algorithmlx.dimore.util.OreDimensionType
import net.minecraft.network.chat.Component
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.RedStoneOreBlock

class DimensionalRedstoneOre(private val oreDimensionType: OreDimensionType) : RedStoneOreBlock(
    Properties.ofFullCopy(Blocks.STONE)
) {
    // no more translations
    // todo: but have problems in some languages like arabic, or where it is incorrect...
    // but i'm too stupid (stupid russian ha-ha-hah) to make it normally
    override fun getDescriptionId(): String = createAppend(oreDimensionType.dimensionBlock).string

    private fun createAppend(material: Block) = Component.translatable(material.descriptionId)
        .append("-")
        .append(Component.translatable(Blocks.REDSTONE_ORE.descriptionId))
}
