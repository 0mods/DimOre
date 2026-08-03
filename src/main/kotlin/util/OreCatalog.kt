package com.algorithmlx.dimore.util

import com.algorithmlx.dimore.init.config.DimensionalOresConfig

data class OreDefinition(
    val id: String,
    val oreType: OreTypes,
    val dimensionType: OreDimensionTypes,
    val settings: DimensionalOresConfig.OreGenerationSettings,
    val generationEnabled: Boolean
)

object OreCatalog {
    fun all(config: DimensionalOresConfig): List<OreDefinition> = buildList {
        with(config.netherOres) {
            add("nether_coal_ore", OreTypes.COAL, OreDimensionTypes.NETHER, coal, generateOres)
            add("nether_copper_ore", OreTypes.COPPER, OreDimensionTypes.NETHER, copper, generateOres)
            add("nether_iron_ore", OreTypes.IRON, OreDimensionTypes.NETHER, iron, generateOres)
            add("nether_lapis_ore", OreTypes.LAPIS, OreDimensionTypes.NETHER, lapis, generateOres)
            add("nether_diamond_ore", OreTypes.DIAMOND, OreDimensionTypes.NETHER, diamond, generateOres)
            add("nether_emerald_ore", OreTypes.EMERALD, OreDimensionTypes.NETHER, emerald, generateOres)
            add("nether_redstone_ore", OreTypes.REDSTONE, OreDimensionTypes.NETHER, redstone, generateOres)
        }

        with(config.overworldOres) {
            add("stone_quartz_ore", OreTypes.QUARTZ, OreDimensionTypes.OVERWORLD, quartz, generateOres)
            add(
                "deepslate_quartz_ore",
                OreTypes.QUARTZ,
                OreDimensionTypes.OVERWORLD_DEEPSLATE,
                quartz,
                generateOres
            )
        }

        with(config.endOres) {
            add("end_quartz_ore", OreTypes.QUARTZ, OreDimensionTypes.END, quartz, generateOres)
            add("end_coal_ore", OreTypes.COAL, OreDimensionTypes.END, coal, generateOres)
            add("end_copper_ore", OreTypes.COPPER, OreDimensionTypes.END, copper, generateOres)
            add("end_iron_ore", OreTypes.IRON, OreDimensionTypes.END, iron, generateOres)
            add("end_gold_ore", OreTypes.GOLD, OreDimensionTypes.END, gold, generateOres)
            add("end_lapis_ore", OreTypes.LAPIS, OreDimensionTypes.END, lapis, generateOres)
            add("end_diamond_ore", OreTypes.DIAMOND, OreDimensionTypes.END, diamond, generateOres)
            add("end_emerald_ore", OreTypes.EMERALD, OreDimensionTypes.END, emerald, generateOres)
            add("end_redstone_ore", OreTypes.REDSTONE, OreDimensionTypes.END, redstone, generateOres)
        }
    }

    fun generation(config: DimensionalOresConfig): List<OreDefinition> =
        all(config).filter { it.generationEnabled && it.settings.target.isNotBlank() }

    private fun MutableList<OreDefinition>.add(
        id: String,
        oreType: OreTypes,
        dimensionType: OreDimensionTypes,
        settings: DimensionalOresConfig.OreGenerationSettings,
        generationEnabled: Boolean
    ) {
        add(OreDefinition(id, oreType, dimensionType, settings, generationEnabled))
    }
}
