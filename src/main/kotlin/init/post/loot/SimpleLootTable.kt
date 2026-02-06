package com.algorithmlx.dimore.init.post.loot

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SimpleLootTable(
    val target: String,
    val replaceExists: String? = null,
    val drops: List<SimpleDrop>
)

@Serializable
sealed class SimpleDrop {
    abstract val type: String
}

@Serializable
sealed class SimpleRequire {
    abstract val type: String
}

@SerialName("selfblock")
@Serializable
data class SelfDrop(
    override val type: String = "selfblock",
    val require: List<SimpleRequire>? = null
): SimpleDrop()

@SerialName("item")
@Serializable
data class ItemDrop(
    override val type: String = "item",
    val id: String,
    val functions: List<String> = emptyList(),
    val uniformValues: UniformValues? = null
): SimpleDrop() {
    @Serializable
    data class UniformValues(
        val min: Float,
        val max: Float
    )
}

@SerialName("enchantment")
@Serializable
data class EnchantmentRequire(
    override val type: String = "enchantment",
    val enchantment: String,
    val minLevel: Int,
    val maxLevel: Int? = null
): SimpleRequire()
