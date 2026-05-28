package com.algorithmlx.dimore.init.post.loot

import com.algorithmlx.dimore.util.ResLoc
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
//$ if >1.21.1 'import net.minecraft.core.HolderLookup' else 'import net.minecraft.core.HolderGetter'
import net.minecraft.core.HolderLookup
//? if >1.21.1 {
import net.minecraft.advancements.criterion.DataComponentMatchers
import net.minecraft.advancements.criterion.ItemPredicate
import net.minecraft.core.component.DataComponentExactPredicate
//?} else {
/*import net.minecraft.advancements.critereon.ItemPredicate
import net.minecraft.core.component.DataComponentPredicate
*///?}
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
    //$ if >1.21.1 'override fun asMC(lookupProvider: HolderLookup.Provider): LootItemCondition.Builder =' else 'override fun asMC(lookupProvider: HolderGetter.Provider): LootItemCondition.Builder ='
    override fun asMC(lookupProvider: HolderLookup.Provider): LootItemCondition.Builder =
        //? if >1.21.1 {
        MatchTool.toolMatches(ItemPredicate.Builder.item().withComponents(
            DataComponentMatchers.Builder.components().exact(
                DataComponentExactPredicate.builder().expect(
                    DataComponents.ENCHANTMENTS,
                    ItemEnchantments.Mutable(ItemEnchantments.EMPTY).apply {
                        this.set(lookupProvider.getOrThrow(
                            ResourceKey.create(Registries.ENCHANTMENT, ResLoc.parse(enchantment))
                        ), minLevel)
                    }.toImmutable()
                ).build()
            ).build()
        ))
        //?} else {
        /*MatchTool.toolMatches(ItemPredicate.Builder.item().hasComponents(
            DataComponentPredicate.builder()
                .expect(
                    DataComponents.ENCHANTMENTS,
                    ItemEnchantments.Mutable(ItemEnchantments.EMPTY).apply {
                        this.set(lookupProvider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(
                            ResourceKey.create(Registries.ENCHANTMENT, ResLoc.parse(enchantment))
                        ), minLevel)
                    }.toImmutable()
                ).build()
        ))
        *///?}
}
