package com.algorithmlx.dimore.init.post.loot

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlinx.serialization.json.JsonElement

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
    @SerialName("bonus_rolls")
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
    val enchantments: List<MCEnchantmentPredicate>? = null,
    val components: Map<String, JsonElement>? = null
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

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("function")
sealed class MCFunction

@Serializable
@SerialName("minecraft:explosion_decay")
object MCExplosionDecayFunction : MCFunction()

@Serializable
@SerialName("minecraft:set_count")
data class MCSetCountFunction(
    val add: Boolean,
    val count: MCCount
) : MCFunction() {
    @Serializable
    data class MCCount(
        val type: String = "minecraft:uniform",
        val min: Float,
        val max: Float
    )
}

@Serializable
@SerialName("minecraft:apply_bonus")
data class MCApplyBonus(
    val enchantment: String,
    val formula: String
) : MCFunction()
