package com.algorithmlx.dimore.init.config

import net.minecraftforge.common.ForgeConfigSpec

object DimOreCommon {
    val dimoreConfig = ForgeConfigSpec.Builder()
    val spec: ForgeConfigSpec

    init {
        dimoreConfig.push("Dimensional Ores Config")
    }

    val generateNetherOres = dimoreConfig.comment("Generate ores in Nether").define("gno", true)
    val generateEndOres = dimoreConfig.comment("Generate ores in End").define("gne", true)

    init {
        dimoreConfig.pop()
        spec = dimoreConfig.build()
    }
}