package com.algorithmlx.dimore.init.config

import com.algorithmlx.dimore.util.DimensionOreConfig
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

        @JsonComment([
            "Settings of the Coal generation in The Nether.",
            "Default values:",
            "size = 32",
            "count = 20",
            "min_height = -64",
            "max_height = 480"
        ], multiline = true)
        @SerialName("coal")
        val coalSettings: OreGenerationSettings = OreGenerationSettings(
            true, 32, 20, -64, 480
        ),

        @JsonComment([
            "Settings of the Copper generation in The Nether.",
            "Default values:",
            "size = 20",
            "count = 16",
            "min_height = -64",
            "max_height = 480"
        ], multiline = true)
        @SerialName("copper")
        val copperSettings: OreGenerationSettings = OreGenerationSettings(
            true, 20, 16, -64, 480
        ),

        @JsonComment([
            "Settings of the Iron generation in The Nether.",
            "Default values:",
            "size = 18",
            "count = 10",
            "min_height = -64",
            "max_height = 480"
        ], multiline = true)
        @SerialName("iron")
        val ironSettings: OreGenerationSettings = OreGenerationSettings(
            true, 18, 10, -64, 480
        ),

        @JsonComment([
            "Settings of the Lapis Lazuli generation in The Nether.",
            "Default values:",
            "size = 14",
            "count = 4",
            "min_height = -64",
            "max_height = 480"
        ], multiline = true)
        @SerialName("lapis")
        val lapisSettings: OreGenerationSettings = OreGenerationSettings(
            true, 14, 4, -64, 480
        ),

        @JsonComment([
            "Settings of the Diamond generation in The Nether.",
            "Default values:",
            "size = 16",
            "count = 7",
            "min_height = -64",
            "max_height = 480"
        ], multiline = true)
        @SerialName("diamond")
        val diamondSettings: OreGenerationSettings = OreGenerationSettings(
            true, 16, 7, -64, 480
        ),

        @JsonComment([
            "Settings of the Emerald generation in The Nether.",
            "Default values:",
            "size = 12",
            "count = 4",
            "min_height = -64",
            "max_height = 480"
        ], multiline = true)
        @SerialName("emerald")
        val emeraldSettings: OreGenerationSettings = OreGenerationSettings(
            true, 12, 4, -64, 480
        ),

        @JsonComment([
            "Settings of the Redstone generation in The Nether.",
            "Default values:",
            "size = 16",
            "count = 8",
            "min_height = -64",
            "max_height = 480"
        ], multiline = true)
        @SerialName("redstone")
        val redstoneSettings: OreGenerationSettings = OreGenerationSettings(
            true, 16, 8, -64, 480
        )
    )

    @Serializable
    data class OverworldOres(
        @JsonComment([
            "Enables/disables ore generation in Overworld.",
            "If false, no ore will be generated."
        ], multiline = true)
        @SerialName("enabled")
        val generateOres: Boolean = true,

        @JsonComment([
            "Settings of the Quartz generation in Overworld.",
            "Default values:",
            "size = 16",
            "count = 8",
            "min_height = -64",
            "max_height = 480"
        ], multiline = true)
        @SerialName("quartz")
        val quartzSettings: OreGenerationSettings = OreGenerationSettings(
            true, 14, 16, -64, 480
        ),
    )

    @Serializable
    data class EndOres(
        @JsonComment([
            "Enables/disables ore generation in The End.",
            "If false, no ore will be generated."
        ], multiline = true)
        @SerialName("enabled")
        val generateOres: Boolean = true,

        @JsonComment([
            "Settings of the Quartz generation in The End.",
            "Default values:",
            "size = 28",
            "count = 8",
            "min_height = -64",
            "max_height = 480"
        ], multiline = true)
        @SerialName("quartz")
        val quartzSettings: OreGenerationSettings = OreGenerationSettings(
            true, 28, 8, -64, 480
        ),

        @JsonComment([
            "Settings of the Coal generation in The End.",
            "Default values:",
            "size = 32",
            "count = 7",
            "min_height = -64",
            "max_height = 480"
        ], multiline = true)
        @SerialName("coal")
        val coalSettings: OreGenerationSettings = OreGenerationSettings(
            true, 32, 7, -64, 480
        ),

        @JsonComment([
            "Settings of the Copper generation in The End.",
            "Default values:",
            "size = 20",
            "count = 8",
            "min_height = -64",
            "max_height = 480"
        ], multiline = true)
        @SerialName("copper")
        val copperSettings: OreGenerationSettings = OreGenerationSettings(
            true, 20, 8, -64, 480
        ),

        @JsonComment([
            "Settings of the Iron generation in The End.",
            "Default values:",
            "size = 18",
            "count = 5",
            "min_height = -64",
            "max_height = 480"
        ], multiline = true)
        @SerialName("iron")
        val ironSettings: OreGenerationSettings = OreGenerationSettings(
            true, 18, 5, -64, 480
        ),

        @JsonComment([
            "Settings of the Gold generation in The End.",
            "Default values:",
            "size = 18",
            "count = 4",
            "min_height = -64",
            "max_height = 480"
        ], multiline = true)
        @SerialName("gold")
        val goldSettings: OreGenerationSettings = OreGenerationSettings(
            true, 18, 4, -64, 480
        ),

        @JsonComment([
            "Settings of the Lapis Lazuli generation in The End.",
            "Default values:",
            "size = 14",
            "count = 4",
            "min_height = -64",
            "max_height = 480"
        ], multiline = true)
        @SerialName("lapis")
        val lapisSettings: OreGenerationSettings = OreGenerationSettings(
            true, 14, 4, -64, 480
        ),

        @JsonComment([
            "Settings of the Diamond generation in The End.",
            "Default values:",
            "size = 16",
            "count = 7",
            "min_height = -64",
            "max_height = 480"
        ], multiline = true)
        @SerialName("diamond")
        val diamondSettings: OreGenerationSettings = OreGenerationSettings(
            true, 16, 7, -64, 480
        ),

        @JsonComment([
            "Settings of the Emerald generation in The End.",
            "Default values:",
            "size = 12",
            "count = 4",
            "min_height = -64",
            "max_height = 480"
        ], multiline = true)
        @SerialName("emerald")
        val emeraldSettings: OreGenerationSettings = OreGenerationSettings(
            true, 12, 4, -64, 480
        ),

        @JsonComment([
            "Settings of the Redstone generation in The End.",
            "Default values:",
            "size = 16",
            "count = 8",
            "min_height = -64",
            "max_height = 480"
        ], multiline = true)
        @SerialName("redstone")
        val redstoneSettings: OreGenerationSettings = OreGenerationSettings(
            true, 16, 8, -64, 480
        )
    )

    @Serializable
    data class OreGenerationSettings(
        @SerialName("enabled")
        override val generate: Boolean,
        override val size: Int,
        override val count: Int,
        @SerialName("min_height")
        override val minHeight: Int,
        @SerialName("max_height")
        override val maxHeight: Int
    ) : DimensionOreConfig
}
