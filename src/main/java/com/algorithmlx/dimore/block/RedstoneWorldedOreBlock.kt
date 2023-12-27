package com.algorithmlx.dimore.block

import com.algorithmlx.dimore.api.IDimensionOreType
import net.minecraft.network.chat.Component
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.RedStoneOreBlock
import net.minecraft.world.level.material.Material

class RedstoneWorldedOreBlock(val dim: IDimensionOreType): RedStoneOreBlock(
    Properties.of(Material.STONE)
        .requiresCorrectToolForDrops()
        .strength(3F, 3F)
        .noOcclusion()
) {
    override fun getDescriptionId(): String = createAppend(dim.getDimBlock()).string

    private fun createAppend(material: Block) =
        Component.translatable(material.descriptionId)
            .append("-")
            .append(Component.translatable(Blocks.REDSTONE_ORE.descriptionId))
}