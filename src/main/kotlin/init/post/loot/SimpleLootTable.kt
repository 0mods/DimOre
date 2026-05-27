package com.algorithmlx.dimore.init.post.loot

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.minecraft.core.HolderLookup
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition

val ExampleLootTable = SimpleLootTable(
    target = "dimore:deepslate_quartz_ore",
    drops = listOf(
        SelfDrop(
            requires = listOf(EnchantmentRequire(enchantment = "minecraft:silk_touch", minLevel = 1)),
            functions = listOf(ExplosionDecay())
        ),
        ItemDrop(
            id = "cobblestone",
            functions = listOf(
                ExplosionDecay(),
                SimpleApplyOreBonus(withEnchantment = "minecraft:fortune"),
                SetCountUniform(min = 1F, max = 5F)
            )
        )
    )
)

@Serializable
data class SimpleLootTable(
    val target: String = "",
    val drops: List<SimpleDrop>,
    val rolls: Float = 1F,
    @SerialName("bonus_rolls")
    val bonusRolls: Float = 0F
)

@Serializable
sealed class SimpleDrop {
    abstract val requires: List<SimpleRequire>
    abstract val functions: List<SimpleFunction>
}

@Serializable
sealed class SimpleRequire {
    abstract fun asMC(lookupProvider: HolderLookup.Provider): LootItemCondition.Builder
}

@Serializable
sealed class SimpleFunction {
    abstract fun asMC(lookupProvider: HolderLookup.Provider): LootItemConditionalFunction.Builder<*>
}
