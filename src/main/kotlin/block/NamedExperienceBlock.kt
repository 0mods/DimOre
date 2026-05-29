package com.algorithmlx.dimore.block

import net.minecraft.network.chat.MutableComponent
import net.minecraft.util.valueproviders.IntProvider
import net.minecraft.world.level.block.DropExperienceBlock

open class NamedExperienceBlock(
    experience: IntProvider,
    properties: Properties,
    val component: MutableComponent? = null
): DropExperienceBlock(experience, properties) {
    override fun getName(): MutableComponent = component ?: super.getName()
}