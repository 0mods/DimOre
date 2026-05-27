package com.algorithmlx.dimore.init.post.loot

import com.algorithmlx.dimore.util.ResLoc
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator

@Serializable
@SerialName("ore_bonus")
data class SimpleApplyOreBonus(
    @SerialName("with_enchant")
    val withEnchantment: String
): SimpleFunction() {
    override fun asMC(lookupProvider: HolderLookup.Provider): LootItemConditionalFunction.Builder<*> =
        ApplyBonusCount.addOreBonusCount(
            lookupProvider.getOrThrow(ResourceKey.create(Registries.ENCHANTMENT, ResLoc.parse(withEnchantment)))
        )
}

@Serializable
@SerialName("explosion_decay")
class ExplosionDecay: SimpleFunction() {
    override fun asMC(lookupProvider: HolderLookup.Provider): LootItemConditionalFunction.Builder<*> =
        ApplyExplosionDecay.explosionDecay()
}

@Serializable
@SerialName("count_uniform")
data class SetCountUniform(
    val min: Float,
    val max: Float,
    val add: Boolean = false
): SimpleFunction() {
    override fun asMC(lookupProvider: HolderLookup.Provider): LootItemConditionalFunction.Builder<*> =
        SetItemCountFunction.setCount(UniformGenerator.between(min, max), add)
}
