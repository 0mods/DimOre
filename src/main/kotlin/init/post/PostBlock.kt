package com.algorithmlx.dimore.init.post

import com.algorithmlx.dimore.util.OreDimensionType
import com.algorithmlx.dimore.util.ResLoc
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.tags.TagKey
import net.minecraft.util.valueproviders.ConstantInt
import net.minecraft.util.valueproviders.UniformInt
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest
import java.util.Optional

@Serializable
data class PostBlock(
    val isRedstone: Boolean = false,
    val properties: SimplyProperties = SimplyProperties(),
    val generationSettings: GenerationSettings
) {
    @Serializable
    data class GenerationSettings(
        val size: Int,
        val count: Int,
        @SerialName("min_height")
        val minHeight: Int,
        @SerialName("max_height")
        val maxHeight: Int,
        @SerialName("ore_replacement_rule")
        val oreDimensionType: OreReplacementRule,
        @Serializable
        val dropExperience: ExperienceRange = ExperienceRange.EMPTY
    )

    @Serializable
    abstract class OreReplacementRule(
        val replacement: String,
        val target: TargetType
    ): OreDimensionType {
        @Transient
        override val dimensionBlock: () -> Block = { BuiltInRegistries.BLOCK.getValue(ResLoc.parse(replacement)) }

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
                TagMatchTest(TagKey.create(Registries.BLOCK, ResLoc.parse(into)))
            else {
                val blockId = ResLoc.parse(into)
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

    @Serializable
    data class SimplyProperties(
        @SerialName("no_collision")
        val noCollision: Boolean = false,
        @SerialName("no_occlusion")
        val noOcclusion: Boolean = false,
        val friction: Float = 0F,
        @SerialName("speed_factor")
        val speedFactor: Float = 0F,
        @SerialName("jump_factor")
        val jumpFactor: Float = 0F,
        @SerialName("light_level")
        val lightLevel: Int = 0, // it's too simple to JSON encode
        @SerialName("destroy_time")
        val destroyTime: Float = 0F,
        @SerialName("explosion_resistance")
        val explosionResistance: Float = 0F,
        val instabreak: Boolean = false,
        @SerialName("random_ticks")
        val randomTicks: Boolean = false,
        @SerialName("dynamic_shape")
        val dynamicShape: Boolean = false,
        @SerialName("no_loot_table")
        val noLootTable: Boolean = false,
        @SerialName("override_loot_table")
        val overrideLootTable: String = "",
        @SerialName("ignited_by_lava")
        val ignitedByLava: Boolean = false,
        val liquid: Boolean = false,
        @SerialName("force_solid_on")
        val forceSolidOn: Boolean = false,
        @SerialName("require_tool")
        val requiresCorrectToolForDrops: Boolean = false,
        @SerialName("no_terrain_particles")
        val noTerrainParticles: Boolean = false,
        val replaceable: Boolean = false,
        @SerialName("display_name")
        val displayName: String = ""
    ) {
        fun asBlockBehaviourProperties(): BlockBehaviour.Properties = BlockBehaviour.Properties.of().apply {
            if (noCollision) this.noCollision()
            if (noOcclusion) this.noOcclusion()
            if (friction != 0F) this.friction(friction)
            if (speedFactor != 0F) this.speedFactor(speedFactor)
            if (jumpFactor != 0F) this.jumpFactor(0F)
            if (lightLevel != 0) this.lightLevel { lightLevel }
            if (destroyTime != 0F) this.destroyTime(destroyTime)
            if (explosionResistance != 0F) this.explosionResistance(explosionResistance)
            if (instabreak) this.instabreak()
            if (randomTicks) this.randomTicks()
            if (dynamicShape) this.dynamicShape()
            if (noLootTable) this.noLootTable()
            if (overrideLootTable.isNotEmpty()) this.overrideLootTable(Optional.of(ResourceKey.create(
                Registries.LOOT_TABLE, ResLoc.parse(overrideLootTable)
            )))
            if (ignitedByLava) this.ignitedByLava()
            if (liquid) this.liquid()
            if (forceSolidOn) this.forceSolidOn()
            if (requiresCorrectToolForDrops) this.requiresCorrectToolForDrops()
            if (noTerrainParticles) this.noTerrainParticles()
            if (replaceable) this.replaceable()
        }
    }
}
