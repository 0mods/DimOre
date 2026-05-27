package com.algorithmlx.dimore.init.post.loot

import com.algorithmlx.dimore.util.ResLoc
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.minecraft.advancements.criterion.DataComponentMatchers
import net.minecraft.advancements.criterion.ItemPredicate
import net.minecraft.core.HolderLookup
import net.minecraft.core.component.DataComponentExactPredicate
import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.enchantment.ItemEnchantments
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition
import net.minecraft.world.level.storage.loot.predicates.MatchTool

@SerialName("enchantment")
@Serializable
data class EnchantmentRequire(
    val enchantment: String,
    @SerialName("min_level")
    val minLevel: Int
): SimpleRequire() {
    override fun asMC(lookupProvider: HolderLookup.Provider): LootItemCondition.Builder =
        MatchTool.toolMatches(ItemPredicate.Builder.item().withComponents(
            DataComponentMatchers.Builder.components().exact(
                DataComponentExactPredicate.builder().expect(
                    DataComponents.ENCHANTMENTS,
                    ItemEnchantments.Mutable(ItemEnchantments.EMPTY).apply {
                        this.set(lookupProvider.getOrThrow(
                            ResourceKey.create(Registries.ENCHANTMENT, ResLoc.parse(enchantment))
                        ), 1)
                    }.toImmutable()
                ).build()
            ).build()
        ))
}
