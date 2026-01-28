package com.algorithmlx.dimore.init.config

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DimensionalOresConfig(
    // Nether Ores
    @JsonComment([
        "Configures ore generation in The Nether"
    ])
    @SerialName("nether_ores")
    val netherOres: NetherOres = NetherOres(),

    // Overworld Ores
    @JsonComment([
        "Configures ore generation in Overworld"
    ])
    @SerialName("overworld_ores")
    val overworldOres: OverworldOres = OverworldOres(),

    // End Ores
    @JsonComment([
        "Configures ore generation in The End"
    ])
    @SerialName("end_ores")
    val endOres: EndOres = EndOres()
) {
    @Serializable
    data class NetherOres(
        @JsonComment([
            "Enables/disables ore generation in The Nether.",
            "If false, no ore will be generated."
        ], multiline = true)
        @SerialName("enabled")
        val generateOres: Boolean = true,

        @JsonComment(["Enables/disables Coal generation in The Nether."])
        @SerialName("coal")
        val generateCoal: Boolean = true,

        @JsonComment(["Enables/disables Copper generation in The Nether."])
        @SerialName("copper")
        val generateCopper: Boolean = true,

        @JsonComment(["Enables/disables Iron generation in The Nether."])
        @SerialName("iron")
        val generateIron: Boolean = true,

        @JsonComment(["Enables/disables Lapis Lazuli generation in The Nether."])
        @SerialName("lapis")
        val generateLapis: Boolean = true,

        @JsonComment(["Enables/disables Diamond generation in The Nether."])
        @SerialName("diamond")
        val generateDiamond: Boolean = true,

        @JsonComment(["Enables/disables Emerald generation in The Nether."])
        @SerialName("emerald")
        val generateEmerald: Boolean = true,

        @JsonComment(["Enables/disables Redstone generation in The Nether."])
        @SerialName("redstone")
        val generateRedstone: Boolean = true
    )

    @Serializable
    data class OverworldOres(
        @JsonComment([
            "Enables/disables ore generation in Overworld.",
            "If false, no ore will be generated."
        ], multiline = true)
        @SerialName("enabled")
        val generateOres: Boolean = true,

        @JsonComment(["Enables/disables Quartz generation in Overworld."])
        @SerialName("quartz")
        val generateQuartz: Boolean = true
    )

    @Serializable
    data class EndOres(
        @JsonComment([
            "Enables/disables ore generation in The End.",
            "If false, no ore will be generated."
        ], multiline = true)
        @SerialName("enabled")
        val generateOres: Boolean = true,

        @JsonComment(["Enables/disables Quartz generation in The End."])
        @SerialName("quartz")
        val generateQuartz: Boolean = true,

        @JsonComment(["Enables/disables Coal generation in The End."])
        @SerialName("coal")
        val generateCoal: Boolean = true,

        @JsonComment(["Enables/disables Copper generation in The End."])
        @SerialName("copper")
        val generateCopper: Boolean = true,

        @JsonComment(["Enables/disables Iron generation in The End."])
        @SerialName("iron")
        val generateIron: Boolean = true,

        @JsonComment(["Enables/disables Gold generation in The End."])
        @SerialName("gold")
        val generateGold: Boolean = true,

        @JsonComment(["Enables/disables Lapis Lazuli generation in The End."])
        @SerialName("lapis")
        val generateLapis: Boolean = true,

        @JsonComment(["Enables/disables Diamond generation in The End."])
        @SerialName("diamond")
        val generateDiamond: Boolean = true,

        @JsonComment(["Enables/disables Emerald generation in The End."])
        @SerialName("emerald")
        val generateEmerald: Boolean = true,

        @JsonComment(["Enables/disables Redstone generation in The End."])
        @SerialName("redstone")
        val generateRedstone: Boolean = true
    )
}
