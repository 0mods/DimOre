package com.algorithmlx.dimore.util

import net.minecraft.util.valueproviders.ConstantInt
import net.minecraft.util.valueproviders.IntProvider
import net.minecraft.util.valueproviders.UniformInt
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks

private val noExperience = ConstantInt.of(0)

enum class OreTypes(
    override val parentOreBlock: () -> Block,
    override val experienceDrop: IntProvider = noExperience
) : OreType {
    QUARTZ({ Blocks.NETHER_QUARTZ_ORE }, UniformInt.of(2, 5)),
    COAL({ Blocks.COAL_ORE }, UniformInt.of(0, 2)),
    COPPER({ Blocks.COPPER_ORE }),
    IRON({ Blocks.IRON_ORE }),
    GOLD({ Blocks.GOLD_ORE }),
    LAPIS({ Blocks.LAPIS_ORE }, UniformInt.of(2, 5)),
    DIAMOND({ Blocks.DIAMOND_ORE }, UniformInt.of(3, 7)),
    EMERALD({ Blocks.EMERALD_ORE }, UniformInt.of(3, 7)),
    REDSTONE({ Blocks.REDSTONE_ORE })
}
