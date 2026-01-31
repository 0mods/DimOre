package com.algorithmlx.dimore.util

import com.algorithmlx.dimore.init.config.ConfigManager
import net.minecraft.util.valueproviders.ConstantInt
import net.minecraft.util.valueproviders.IntProvider
import net.minecraft.util.valueproviders.UniformInt
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks

private val noExperience = ConstantInt.of(0)
enum class OreTypes(override val parentOreBlock: () -> Block, override val experienceDrop: IntProvider = noExperience): OreType {
    QUARTZ({ Blocks.NETHER_QUARTZ_ORE }, UniformInt.of(2, 5)),
    COAL({ Blocks.COAL_ORE }, UniformInt.of(0, 2)),
    COPPER({ Blocks.COPPER_ORE }),
    IRON({ Blocks.IRON_ORE }),
    GOLD({ Blocks.GOLD_ORE }),
    LAPIS({ Blocks.LAPIS_ORE }, UniformInt.of(2, 5)),
    DIAMOND({ Blocks.DIAMOND_ORE }, UniformInt.of(3, 7)),
    EMERALD({ Blocks.EMERALD_ORE }, UniformInt.of(3, 7)),
    REDSTONE({ Blocks.REDSTONE_ORE });

    companion object {
        private val netherOresConfig = ConfigManager.config.netherOres
        private val overOresConfig = ConfigManager.config.overworldOres
        private val endOresConfig = ConfigManager.config.endOres

        val netherOres = arrayOf(COAL, COPPER, IRON, LAPIS, DIAMOND, EMERALD, REDSTONE)
        val overworldOres = arrayOf(QUARTZ)
        val endOres = entries.toTypedArray()

        val configByTypeNether = mapOf(
            COAL to netherOresConfig.coalSettings,
            COPPER to netherOresConfig.copperSettings,
            IRON to netherOresConfig.ironSettings,
            LAPIS to netherOresConfig.lapisSettings,
            DIAMOND to netherOresConfig.diamondSettings,
            EMERALD to netherOresConfig.emeraldSettings,
            REDSTONE to netherOresConfig.redstoneSettings
        )

        val configByTypeOverworld = mapOf(
            QUARTZ to overOresConfig.quartzSettings
        )

        val configByTypeEnd = mapOf(
            QUARTZ to endOresConfig.quartzSettings,
            COAL to endOresConfig.coalSettings,
            COPPER to endOresConfig.copperSettings,
            IRON to endOresConfig.ironSettings,
            GOLD to endOresConfig.goldSettings,
            LAPIS to endOresConfig.lapisSettings,
            DIAMOND to endOresConfig.diamondSettings,
            EMERALD to endOresConfig.emeraldSettings,
            REDSTONE to endOresConfig.redstoneSettings
        )
    }
}
