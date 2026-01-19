package com.algorithmlx.dimore.init.config

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DimensionalOresConfig(
    // Nether Ores
    @SerialName("generate_nether_ores")
    val generateNetherOres: Boolean = true,

    @SerialName("generate_nether_coal")
    val generateNetherCoal: Boolean = true,
    @SerialName("generate_nether_copper")
    val generateNetherCopper: Boolean = true,
    @SerialName("generate_nether_iron")
    val generateNetherIron: Boolean = true,
    @SerialName("generate_nether_lapis")
    val generateNetherLapis: Boolean = true,
    @SerialName("generate_nether_diamond")
    val generateNetherDiamond: Boolean = true,
    @SerialName("generate_nether_emerald")
    val generateNetherEmerald: Boolean = true,
    @SerialName("generate_nether_redstone")
    val generateNetherRedstone: Boolean = true,
    
    // Overworld Ores
    @SerialName("generate_overworld_ores")
    val generateOverworldOres: Boolean = true,

    @SerialName("generate_quartz")
    val generateQuartz: Boolean = true,
    
    // End Ores
    @SerialName("generate_end_ores")
    val generateEndOres: Boolean = true,

    @SerialName("generate_end_quartz")
    val generateEndQuartz: Boolean = true,
    @SerialName("generate_end_coal")
    val generateEndCoal: Boolean = true,
    @SerialName("generate_end_copper")
    val generateEndCopper: Boolean = true,
    @SerialName("generate_end_iron")
    val generateEndIron: Boolean = true,
    @SerialName("generate_end_gold")
    val generateEndGold: Boolean = true,
    @SerialName("generate_end_lapis")
    val generateEndLapis: Boolean = true,
    @SerialName("generate_end_diamond")
    val generateEndDiamond: Boolean = true,
    @SerialName("generate_end_emerald")
    val generateEndEmerald: Boolean = true,
    @SerialName("generate_end_redstone")
    val generateEndRedstone: Boolean = true
)
