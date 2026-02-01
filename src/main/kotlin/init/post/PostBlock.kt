package com.algorithmlx.dimore.init.post

import com.algorithmlx.dimore.util.DimensionOreConfig
import com.algorithmlx.dimore.util.OreDimensionType
import com.algorithmlx.dimore.util.ResourceLocation
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.json.Json
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey
import net.minecraft.util.valueproviders.ConstantInt
import net.minecraft.util.valueproviders.UniformInt
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest

@Serializable
data class PostBlock(
    override val generate: Boolean,
    override val size: Int,
    override val count: Int,
    @SerialName("min_height")
    override val minHeight: Int,
    @SerialName("max_height")
    override val maxHeight: Int,
    @SerialName("ore_replacement_rule")
    val oreDimensionType: OreReplacementRule,
    @Serializable
    val dropExperience: ExperienceRange = ExperienceRange.EMPTY
): DimensionOreConfig {
    @Serializable
    abstract class OreReplacementRule(
        val replacement: String,
        val target: TargetType
    ): OreDimensionType {
        @Transient
        override val dimensionBlock: () -> Block = { BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(replacement)) }

        override fun replacementSettings(block: BlockState): OreConfiguration.TargetBlockState =
            OreConfiguration.target(target.asRuleTest(), block)
    }

    @Serializable
    data class TargetType(
        val type: String = "block",
        val into: String
    ) {
        @Transient val isBlock = this.type == "block"
        @Transient val isTag = !isBlock

        fun asRuleTest(): RuleTest {
            return if (isTag)
                TagMatchTest(TagKey.create(Registries.BLOCK, ResourceLocation.parse(into)))
            else {
                val blockId = ResourceLocation.parse(into)
                val block = BuiltInRegistries.BLOCK.getValue(blockId)
                BlockMatchTest(block)
            }
        }
    }

    @Serializable
    data class ExperienceRange(
        @SerialName("min")
        val minExp: Int,
        @SerialName("max")
        val maxExp: Int
    ) {
        companion object {
            @JvmField
            val EMPTY = ExperienceRange(0, 0)
        }

        fun asIntProvider() = if (minExp == maxExp && minExp == 0) ConstantInt.of(0) else UniformInt.of(minExp, maxExp)
    }
}
