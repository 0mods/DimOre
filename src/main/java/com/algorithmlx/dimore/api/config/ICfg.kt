package com.algorithmlx.dimore.api.config

import net.minecraftforge.fml.ModLoadingContext

interface ICfg {
    fun getName(): String = ModLoadingContext.get().activeContainer.modId
}