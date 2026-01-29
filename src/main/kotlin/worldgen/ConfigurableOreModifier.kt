package com.algorithmlx.dimore.worldgen

//? if neoforge {
/*import com.algorithmlx.dimore.init.CodecRegistry
import com.algorithmlx.dimore.init.config.ConfigManager
import com.mojang.serialization.MapCodec
import net.minecraft.core.Holder
import net.minecraft.core.HolderSet
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.levelgen.GenerationStep
import net.minecraft.world.level.levelgen.placement.PlacedFeature
import net.neoforged.neoforge.common.world.BiomeModifier
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo

@JvmRecord
data class ConfigurableOreModifier(
    val biomes: HolderSet<Biome>,
    val feature: Holder<PlacedFeature>,
    val requiredValues: List<String>
): BiomeModifier {
    override fun modify(
        biome: Holder<Biome>,
        phase: BiomeModifier.Phase,
        builder: ModifiableBiomeInfo.BiomeInfo.Builder
    ) {
        if (!requiredValues.all { ConfigManager.isEnable(it) }) return

        if (phase == BiomeModifier.Phase.ADD && this.biomes.contains(biome))
            builder.generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, this.feature)
    }

    override fun codec(): MapCodec<out BiomeModifier> = CodecRegistry.codec.get()
}
*///?}
