package com.algorithmlx.dimore.init.config

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface MiningConfiguration {
    val tool: MiningTool
    val toolLevel: MiningLevel
}

@JsonDefaults
@Serializable
data class MiningSettings(
    @JsonComment(["Mining tool: pickaxe, axe, shovel or hoe."])
    override val tool: MiningTool = MiningTool.PICKAXE,
    @JsonComment(["Minimum tool level: wood, stone, iron or diamond."])
    @SerialName("tool_level")
    override val toolLevel: MiningLevel = MiningLevel.WOOD
) : MiningConfiguration

@Serializable
enum class MiningTool(val tagPath: String) {
    @SerialName("pickaxe")
    PICKAXE("mineable/pickaxe"),

    @SerialName("axe")
    AXE("mineable/axe"),

    @SerialName("shovel")
    SHOVEL("mineable/shovel"),

    @SerialName("hoe")
    HOE("mineable/hoe")
}

@Serializable
enum class MiningLevel(val tagPath: String?) {
    @SerialName("wood")
    WOOD(null),

    @SerialName("stone")
    STONE("needs_stone_tool"),

    @SerialName("iron")
    IRON("needs_iron_tool"),

    @SerialName("diamond")
    DIAMOND("needs_diamond_tool")
}
