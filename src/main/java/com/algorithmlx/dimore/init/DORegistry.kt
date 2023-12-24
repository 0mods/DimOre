package com.algorithmlx.dimore.init

import com.algorithmlx.dimore.ModId
import com.algorithmlx.dimore.init.config.DimOreCommon
import com.google.common.base.Suppliers
import net.minecraft.core.Registry
import net.minecraft.data.worldgen.features.OreFeatures
import net.minecraft.util.valueproviders.UniformInt
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.DropExperienceBlock
import net.minecraft.world.level.block.RedStoneOreBlock
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.minecraft.world.level.levelgen.VerticalAnchor
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration
import net.minecraft.world.level.levelgen.placement.BiomeFilter
import net.minecraft.world.level.levelgen.placement.CountPlacement
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement
import net.minecraft.world.level.levelgen.placement.InSquarePlacement
import net.minecraft.world.level.levelgen.placement.PlacedFeature
import net.minecraft.world.level.levelgen.placement.PlacementModifier
import net.minecraft.world.level.levelgen.placement.RarityFilter
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest
import net.minecraft.world.level.material.Material
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject

object DORegistry {
    val dimoreTab: CreativeModeTab = object : CreativeModeTab(ModId) {
        override fun makeIcon(): ItemStack = ItemStack(netherDiamond.get())
    }

    private val items = DeferredRegister.create(ForgeRegistries.ITEMS, ModId)
    private val block = DeferredRegister.create(ForgeRegistries.BLOCKS, ModId)

    fun init(bus: IEventBus) {
        items.register(bus)
        block.register(bus)
    }

    val netherCoal = block("nether_coal_ore", DropExperienceBlock(
        Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F),
        UniformInt.of(0, 2)
    ), Item.Properties())
    val netherIron = block("nether_iron_ore", DropExperienceBlock(
        Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F),
        UniformInt.of(0, 2)
    ), Item.Properties())
    val netherLapis = block("nether_lapis_ore", DropExperienceBlock(
        Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F),
        UniformInt.of(0, 2)
    ), Item.Properties())
    val netherRedstone = block("nether_redstone_ore", RedStoneOreBlock(Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F)),
        Item.Properties()
    )
    val netherDiamond = block("nether_diamond_ore", DropExperienceBlock(
        Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F),
        UniformInt.of(0, 2)
    ), Item.Properties())
    val netherEmerald = block("nether_emerald_ore", DropExperienceBlock(
        Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F),
        UniformInt.of(0, 2)
    ), Item.Properties())

    val endQuartz = block("end_quartz_ore", DropExperienceBlock(
        Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F),
        UniformInt.of(0, 2)
    ), Item.Properties())
    val endCoal = block("end_coal_ore", DropExperienceBlock(
        Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F),
        UniformInt.of(0, 2)
    ), Item.Properties())
    val endIron = block("end_iron_ore", DropExperienceBlock(
        Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F),
        UniformInt.of(0, 2)
    ), Item.Properties())
    val endGold = block("end_gold_ore", DropExperienceBlock(
        Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F),
        UniformInt.of(0, 2)
    ), Item.Properties())
    val endLapis = block("end_lapis_ore", DropExperienceBlock(
        Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F),
        UniformInt.of(0, 2)
    ), Item.Properties())
    val endRedstone = block("end_redstone_ore", RedStoneOreBlock(Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F)),
        Item.Properties()
    )
    val endDiamond = block("end_diamond_ore", DropExperienceBlock(
        Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F),
        UniformInt.of(0, 2)
    ), Item.Properties())
    val endEmerald = block("end_emerald_ore", DropExperienceBlock(
        Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F),
        UniformInt.of(0, 2)
    ), Item.Properties())

    private fun <T: Block> block(id: String, b: T, props: Item.Properties): RegistryObject<T> {
        val block = this.block.register(id) { b }
        items.register(id) { BlockItem(block.get(), props.tab(dimoreTab)) }
        return block
    }
}