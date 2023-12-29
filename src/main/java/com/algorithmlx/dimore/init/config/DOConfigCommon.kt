package com.algorithmlx.dimore.init.config

import net.minecraftforge.common.ForgeConfigSpec

object DOConfigCommon {
    @JvmField
    val spec: ForgeConfigSpec
    @JvmField
    val dimoreConfig = ForgeConfigSpec.Builder()

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