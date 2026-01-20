package com.algorithmlx.dimore.util

import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

interface DimensionOre {
    val oreType: OreType
    val oreDimensionType: OreDimensionType

    // no more translations
    // todo: but have problems in some languages like arabic, or where it is incorrect...
    // but i'm too stupid (stupid russian ha-ha-hah) to make it normally
    fun buildName(): MutableComponent =
        Component.translatable(oreDimensionType.dimensionBlock.descriptionId, oreType.parentOreBlock.descriptionId)
}