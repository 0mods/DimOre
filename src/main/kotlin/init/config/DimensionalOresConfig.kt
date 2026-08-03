package com.algorithmlx.dimore.init.config

import com.algorithmlx.dimore.ModId
import com.algorithmlx.dimore.util.DimensionOreConfig
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

const val GENERATION_TARGET_FIELD: String =
    //$ if fabric '"dimension"' else '"biome"'
    "dimension"

const val DEFAULT_NETHER_TARGET: String =
    //$ if fabric '"minecraft:the_nether"' else '"#minecraft:is_nether"'
    "minecraft:the_nether"

const val DEFAULT_OVERWORLD_TARGET: String =
    //$ if fabric '"minecraft:overworld"' else '"#minecraft:is_overworld"'
    "minecraft:overworld"

const val DEFAULT_END_TARGET: String =
    //$ if fabric '"minecraft:the_end"' else '"#minecraft:is_end"'
    "minecraft:the_end"

@JsonComment([
    "Hello! This is $ModId's config.",
    "A broken config is backed up with the current time before defaults are restored.",
    "Comments and values are preserved across restarts."
], multiline = true)
@JsonDefaults(recursive = true)
@Serializable
data class DimensionalOresConfig(
    @JsonComment(["Enables custom blocks from config/$ModId/custom."])
    @SerialName("custom_blocks")
    val enableCustomBlocks: Boolean = false,

    @JsonComment(["Enables custom loot tables from config/$ModId/loot."])
    @SerialName("custom_loot_tables")
    val enableLootTables: Boolean = false,

    @JsonComment(["Configures ore generation in The Nether."])
    @SerialName("nether_ores")
    val netherOres: NetherOres = NetherOres(),

    @JsonComment(["Configures ore generation in the Overworld."])
    @SerialName("overworld_ores")
    val overworldOres: OverworldOres = OverworldOres(),

    @JsonComment(["Configures ore generation in The End."])
    @SerialName("end_ores")
    val endOres: EndOres = EndOres()
) {
    @JsonDefaults(recursive = true)
    @Serializable
    data class NetherOres(
        @JsonComment(["Enables all DimOre generation in The Nether."])
        @SerialName("enabled")
        val generateOres: Boolean = true,

        val coal: OreGenerationSettings = ore(DEFAULT_NETHER_TARGET, 32, 20, MiningLevel.DIAMOND),
        val copper: OreGenerationSettings = ore(DEFAULT_NETHER_TARGET, 20, 16, MiningLevel.DIAMOND),
        val iron: OreGenerationSettings = ore(DEFAULT_NETHER_TARGET, 18, 10, MiningLevel.DIAMOND),
        val lapis: OreGenerationSettings = ore(DEFAULT_NETHER_TARGET, 14, 4, MiningLevel.DIAMOND),
        val diamond: OreGenerationSettings = ore(DEFAULT_NETHER_TARGET, 16, 7, MiningLevel.DIAMOND),
        val emerald: OreGenerationSettings = ore(DEFAULT_NETHER_TARGET, 12, 4, MiningLevel.DIAMOND),
        val redstone: OreGenerationSettings = ore(DEFAULT_NETHER_TARGET, 16, 8, MiningLevel.DIAMOND)
    )

    @JsonDefaults(recursive = true)
    @Serializable
    data class OverworldOres(
        @JsonComment(["Enables all DimOre generation in the Overworld."])
        @SerialName("enabled")
        val generateOres: Boolean = true,

        val quartz: OreGenerationSettings = ore(DEFAULT_OVERWORLD_TARGET, 14, 16, MiningLevel.IRON)
    )

    @JsonDefaults(recursive = true)
    @Serializable
    data class EndOres(
        @JsonComment(["Enables all DimOre generation in The End."])
        @SerialName("enabled")
        val generateOres: Boolean = true,

        val quartz: OreGenerationSettings = ore(DEFAULT_END_TARGET, 28, 8, MiningLevel.DIAMOND),
        val coal: OreGenerationSettings = ore(DEFAULT_END_TARGET, 32, 7, MiningLevel.DIAMOND),
        val copper: OreGenerationSettings = ore(DEFAULT_END_TARGET, 20, 8, MiningLevel.DIAMOND),
        val iron: OreGenerationSettings = ore(DEFAULT_END_TARGET, 18, 5, MiningLevel.DIAMOND),
        val gold: OreGenerationSettings = ore(DEFAULT_END_TARGET, 18, 4, MiningLevel.DIAMOND),
        val lapis: OreGenerationSettings = ore(DEFAULT_END_TARGET, 14, 4, MiningLevel.DIAMOND),
        val diamond: OreGenerationSettings = ore(DEFAULT_END_TARGET, 16, 7, MiningLevel.DIAMOND),
        val emerald: OreGenerationSettings = ore(DEFAULT_END_TARGET, 12, 4, MiningLevel.DIAMOND),
        val redstone: OreGenerationSettings = ore(DEFAULT_END_TARGET, 16, 8, MiningLevel.DIAMOND)
    )

    @JsonDefaults
    @Serializable
    data class OreGenerationSettings(
        //? if fabric {
        @JsonComment(["Dimension id where this ore generates. An empty value disables this ore."])
        @SerialName("dimension")
        //?} else {
        /*@JsonComment(["Biome id or #biome_tag where this ore generates. An empty value disables this ore."])
        @SerialName("biome")
        *///?}
        override val target: String = "",

        @JsonComment(["The size of a single ore vein."])
        override val size: Int = 1,

        @JsonComment(["Number of ore veins per chunk."])
        override val count: Int = 1,

        @JsonComment(["Minimum generation height."])
        @SerialName("min_height")
        override val minHeight: Int = -64,

        @JsonComment(["Maximum generation height."])
        @SerialName("max_height")
        override val maxHeight: Int = 480,

        @JsonComment(["Mining tool: pickaxe, axe, shovel or hoe."])
        override val tool: MiningTool = MiningTool.PICKAXE,

        @JsonComment(["Minimum tool level: wood, stone, iron or diamond."])
        @SerialName("tool_level")
        override val toolLevel: MiningLevel = MiningLevel.DIAMOND
    ) : DimensionOreConfig, MiningConfiguration

    companion object {
        private fun ore(
            target: String,
            size: Int,
            count: Int,
            toolLevel: MiningLevel
        ) = OreGenerationSettings(
            target = target,
            size = size,
            count = count,
            minHeight = -64,
            maxHeight = 480,
            tool = MiningTool.PICKAXE,
            toolLevel = toolLevel
        )
    }
}
