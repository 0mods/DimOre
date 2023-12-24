package com.algorithmlx.dimore

import com.algorithmlx.dimore.init.DORegistry
import com.algorithmlx.dimore.init.config.DimOreCommon
import com.algorithmlx.dimore.worldgen.DOConfFeatures
import com.algorithmlx.dimore.worldgen.DOPlacedFeatures
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.config.ModConfig
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory

const val ModId = "dimore"
@JvmField
val LOGGER: Logger = LoggerFactory.getLogger("Dimensional Ores")

@Mod(ModId)
class DimOre {
    init {
        val bus = FMLJavaModLoadingContext.get().modEventBus
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DimOreCommon.spec, "dimensional_ores/common.toml")
        DORegistry.init(bus)
        DOConfFeatures.init(bus)
        DOPlacedFeatures.init(bus)
    }
}