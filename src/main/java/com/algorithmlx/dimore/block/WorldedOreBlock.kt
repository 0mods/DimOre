package com.algorithmlx.dimore.block

import com.algorithmlx.dimore.api.IDimensionOreType
import com.algorithmlx.dimore.api.IOreType
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.util.valueproviders.UniformInt
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.DropExperienceBlock
import net.minecraft.world.level.material.Material

class WorldedOreBlock(val oreType: IOreType, val dimensionType: IDimensionOreType): DropExperienceBlock(
    Properties.of(Material.STONE)
        .requiresCorrectToolForDrops()
        .strength(3F, 3F)
        .noOcclusion(),
    UniformInt.of(0, 2)
) {

    override fun getDescriptionId(): String = createAppend(dimensionType.getDimBlock(), oreType.getParentOreBlock()).string

    private fun createAppend(material: Block, ore: Block) =
        Component.translatable(material.descriptionId)
            .append("-")
            .append(Component.translatable(ore.descriptionId))
}
