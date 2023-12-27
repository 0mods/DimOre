package com.algorithmlx.dimore

import com.algorithmlx.dimore.init.DORegistry
import com.algorithmlx.dimore.init.config.DimOreCommon
import com.algorithmlx.dimore.worldgen.DOConfFeatures
import com.algorithmlx.dimore.worldgen.DOPlacedFeatures
import net.minecraftforge.fml.LoadingFailedException
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.config.ModConfig
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.Marker
import org.slf4j.MarkerFactory
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import kotlin.random.Random

const val ModId = "dimore"
@JvmField
val LOGGER: Logger = LoggerFactory.getLogger("Dimensional Ores")
val FATAL_MARKER = MarkerFactory.getMarker("FATAL")

@Mod(ModId)
class DimOre {
    private val crashMessages = listOf(
        "Stupid AlgorithmLX, can't make normal mod",
        "Nooooooooooooo, i'm anstablea",
        "I'm error.",
        "Hello HollowHorizon!",
        "Fix Me, please",
        "TODO: Don't worry! or wori?",
        "Hello LGameStudio! I love you! But<comma/><space/>I'mer01110010<space/>01101111<space/>0111010"
    )

    init {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DimOreCommon.spec, "dimensional_ores/common.toml")
        LOGGER.info("Starting the mod!")
        try {
            DORegistry.init(MOD_BUS)
            DOConfFeatures.init(MOD_BUS)
            DOPlacedFeatures.init(MOD_BUS)
        } catch (e: LoadingFailedException) {
            val rand = Random(crashMessages.size)
            val mess = crashMessages[rand.nextInt() - 1]
            LOGGER.info(FATAL_MARKER, "Failed to start mod...\n${mess}", e)
        }
    }
}