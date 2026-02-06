package com.algorithmlx.dimore.init.post.loot

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MCLootTable(
    val type: String = "minecraft:block",
    val pools: List<MCPool>,
    @SerialName("random_sequence")
    val randomSequence: String? = null
)

@Serializable
data class MCPool(
    val rolls: Float = 1F,
    val bonusRolls: Float = 0F,
    val entries: List<MCEntry>
)

@Serializable
data class MCEntry(
    val type: String,
    val name: String? = null,
    val children: List<MCEntry>? = null,
    val conditions: List<MCCondition>? = null,
    val functions: List<MCFunction>? = null
)

@Serializable
data class MCCondition(
    val condition: String,
    val predicate: MCPredicate? = null
)

@Serializable
data class MCPredicate(
    val enchantments: List<MCEnchantmentPredicate>? = null
)

@Serializable
data class MCEnchantmentPredicate(
    val enchantment: String,
    val levels: MCLevelBounds
) {
    @Serializable
    data class MCLevelBounds(
        val min: Int,
        val max: Int?
    )
}

@Serializable
sealed class MCFunction {
    abstract val function: String

    @SerialName("singleton")
    data class SingletonFunction(override val function: String): MCFunction()
}

@SerialName("set_count")
@Serializable
data class MCSetCountFunction(
    override val function: String = "minecraft:set_count",
    val add: Boolean,
    val count: MCCount
): MCFunction() {
    @Serializable
    data class MCCount(
        val type: String = "minecraft:uniform",
        val min: Float,
        val max: Float
    )
}

@SerialName("apply_bonus")
@Serializable
data class MCApplyBonus(
    override val function: String = "minecraft:apply_bonus",
    val enchantment: String,
    val formula: String
): MCFunction()
