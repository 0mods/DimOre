package com.algorithmlx.dimore

import com.algorithmlx.dimore.init.DORegistry
import com.algorithmlx.dimore.init.config.DOCommonConfig
import com.algorithmlx.dimore.worldgen.DOConfFeatures
import com.algorithmlx.dimore.worldgen.DOPlacedFeatures
import net.minecraftforge.fml.ModLoadingException
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.config.ModConfig
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MarkerFactory
import thedarkcolour.kotlinforforge.forge.FORGE_BUS
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.forge.registerConfig

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
        "Hello LGameStudio! I love you! But<comma/><space/>I'm\\ner01110010<space/>01101111<space/>0111010",
        """
            failed to load method: 
            \"fun `findInAZaz0-9`(errorMessage: String) {
                val pattern = Pattern.compile("[a-zA-Z0-9]")
                val matcher = pattern.matcher(errorMessage)
                if (matcher.matches()) {
                    LOGGER.info(errorMessage)
                }
            }\"
            
            Reason: HAHAHHHHAHAHAHAHHAAHHHAHAHHAHAHAHAHAHAHAHAHAHAHHAAHHHAHHA YOU HAVE BEEN TROLLED XD
            
            but.. crash is real...
            
        """.trimIndent()
    )

    init {
        registerConfig(ModConfig.Type.COMMON, DOCommonConfig.commonConfig, "dimore/common.toml")
        LOGGER.info("Starting the mod!")
        try {
            FORGE_BUS.register(this)
            DORegistry.init(MOD_BUS)
            DOConfFeatures.init(MOD_BUS)
            DOPlacedFeatures.init(MOD_BUS)
        } catch (e: ModLoadingException) {
            LOGGER.error(FATAL_MARKER, "Failed to start mod...\n$setupMessage", e)
        }
    }

    private val setupMessage: String
        get() {
            var intFull = 0
            for (i in crashMessages.indices) {
                intFull++
            }
            return crashMessages[intFull - 1]
        }
}