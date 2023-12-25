package com.algorithmlx.dimore

import com.algorithmlx.dimore.init.DORegistry
import com.algorithmlx.dimore.init.config.DimOreCommon
import com.algorithmlx.dimore.worldgen.DOConfFeatures
import com.algorithmlx.dimore.worldgen.DOPlacedFeatures
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.config.ModConfig
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import thedarkcolour.kotlinforforge.forge.MOD_BUS

const val ModId = "dimore"
@JvmField
val LOGGER: Logger = LoggerFactory.getLogger("Dimensional Ores")

@Mod(ModId)
class DimOre {
    init {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DimOreCommon.spec, "dimensional_ores/common.toml")
        LOGGER.info("Starting the mod!")
        DORegistry.init(MOD_BUS)
        DOConfFeatures.init(MOD_BUS)
        DOPlacedFeatures.init(MOD_BUS)
    }
}