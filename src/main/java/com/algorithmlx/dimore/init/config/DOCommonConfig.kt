package com.algorithmlx.dimore.init.config

import net.minecraftforge.common.ForgeConfigSpec
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber
object DOCommonConfig {
    @JvmField
    val commonConfig: ForgeConfigSpec

    @JvmField
    val generateNetherOres: ForgeConfigSpec.BooleanValue
    @JvmField
    val generateEndOres: ForgeConfigSpec.BooleanValue

    init {
        val builder = ForgeConfigSpec.Builder()
        generateNetherOres = builder.comment("Allows to generation nether ores on \"nether\" dimension")
            .define("generateNetherOres", true)
        generateEndOres = builder.comment("Allows to generation end ores on \"end\" dimension")
            .define("generateEndOres", true)
        commonConfig = builder.build()
    }
}