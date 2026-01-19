package com.algorithmlx.dimore.init

import com.algorithmlx.dimore.ModId
import com.google.common.base.Supplier
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.levelgen.placement.PlacedFeature
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredRegister
import net.neoforged.neoforge.registries.NeoForgeRegistries
import com.algorithmlx.dimore.worldgen.ConfigurableOreModifier
import com.mojang.serialization.MapCodec
import net.neoforged.neoforge.common.world.BiomeModifier
import net.neoforged.neoforge.registries.DeferredHolder

object CodecRegistry {
    private val biomeModifiers = DeferredRegister.create(
        NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS,
        ModId
    )

    val codec: DeferredHolder<MapCodec<out BiomeModifier>, MapCodec<ConfigurableOreModifier>> = biomeModifiers.register(
        "conditional_ore",
        Supplier { coderBuilder }
    )

    private val coderBuilder = RecordCodecBuilder.mapCodec { instance ->
        instance.group(
            Biome.LIST_CODEC.fieldOf("biomes").forGetter(ConfigurableOreModifier::biomes),
            PlacedFeature.CODEC.fieldOf("features").forGetter(ConfigurableOreModifier::feature),
            Codec.list(Codec.STRING).fieldOf("required_values").forGetter(ConfigurableOreModifier::requiredValues)
        ).apply(instance, ::ConfigurableOreModifier)
    }

    fun init(bus: IEventBus) {
        biomeModifiers.register(bus)
    }
}
