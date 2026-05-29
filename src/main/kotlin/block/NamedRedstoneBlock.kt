package com.algorithmlx.dimore.block

import net.minecraft.network.chat.MutableComponent
import net.minecraft.world.level.block.RedStoneOreBlock

class NamedRedstoneBlock(
    properties: Properties,
    val component: MutableComponent? = null
): RedStoneOreBlock(properties) {
    override fun getName(): MutableComponent = component ?: super.getName()
}