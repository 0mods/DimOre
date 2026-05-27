package com.algorithmlx.dimore.init.post.loot

import com.algorithmlx.dimore.ModId
import com.algorithmlx.dimore.init.config.JsonComment
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.minecraft.core.HolderLookup
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition

val ExampleLootTable = SimpleLootTable(
    target = "dimore:deepslate_quartz_ore",
    entries = listOf(
        SelfEntry(
            requires = listOf(EnchantmentRequire(enchantment = "minecraft:silk_touch", minLevel = 1)),
            functions = listOf(ExplosionDecay())
        ),
        ItemEntry(
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
    @JsonComment([
        "LootTable injection ID. If empty, the default is to: \"$ModId:blocks/custom.json_name\" without \".json\".",
        "Can be none"
    ], multiline = true)
    val target: String = "",
    @JsonComment([
        "LootTable entries.",
        "Required"
    ], multiline = true)
    val entries: List<SimpleEntry>,
    @JsonComment([
        "Rolls. By default 1. Can be none"
    ])
    val rolls: Float = 1F,
    @JsonComment([
        "Rolls. By default 0. Can be none"
    ])
    @SerialName("bonus_rolls")
    val bonusRolls: Float = 0F
)

@Serializable
sealed class SimpleEntry {
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
