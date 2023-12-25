package com.algorithmlx.dimore.init

import com.algorithmlx.dimore.LOGGER
import com.algorithmlx.dimore.ModId
import net.minecraft.core.BlockPos
import net.minecraft.util.valueproviders.UniformInt
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.DropExperienceBlock
import net.minecraft.world.level.block.RedStoneOreBlock
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.material.Material
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject
import java.util.function.Supplier

object DORegistry {
    val dimoreTab: CreativeModeTab = object : CreativeModeTab(ModId) {
        override fun makeIcon(): ItemStack = ItemStack(netherDiamond.get())
    }

    private val items = DeferredRegister.create(ForgeRegistries.ITEMS, ModId)
    private val block = DeferredRegister.create(ForgeRegistries.BLOCKS, ModId)

    fun init(bus: IEventBus) {
        LOGGER.info("Registry initialized")
        items.register(bus)
        block.register(bus)
    }

    val netherCoal = block("nether_coal_ore", { DropExperienceBlock(
        Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F).noOcclusion(),
        UniformInt.of(0, 2)
    ) }, Item.Properties())
    val netherIron = block("nether_iron_ore", { DropExperienceBlock(
        Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F).noOcclusion(),
        UniformInt.of(0, 2)
    ) }, Item.Properties())
    val netherLapis = block("nether_lapis_ore", { DropExperienceBlock(
        Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F).noOcclusion(),
        UniformInt.of(0, 2)
    ) }, Item.Properties())
    val netherRedstone = block(
        "nether_redstone_ore", {
            RedStoneOreBlock(
                Properties.of(Material.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(3F, 3F)
                    .noOcclusion()
                    .lightLevel { if (it.getValue(BlockStateProperties.LIT)) 9 else 0 }
                    .randomTicks()
            )
        }, Item.Properties()
    )
    val netherDiamond = block("nether_diamond_ore", { DropExperienceBlock(
        Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F).noOcclusion(),
        UniformInt.of(0, 2)
    ) }, Item.Properties())
    val netherEmerald = block("nether_emerald_ore", { DropExperienceBlock(
        Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F).noOcclusion(),
        UniformInt.of(0, 2)
    ) }, Item.Properties())
    val netherCopper = block("nether_copper_ore", { DropExperienceBlock(
        Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F).noOcclusion(),
        UniformInt.of(0, 2)
    ) }, Item.Properties())

    val endQuartz = block("end_quartz_ore", { DropExperienceBlock(
        Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F).noOcclusion(),
        UniformInt.of(0, 2)
    ) }, Item.Properties())
    val endCoal = block("end_coal_ore", { DropExperienceBlock(
        Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F).noOcclusion(),
        UniformInt.of(0, 2)
    ) }, Item.Properties())
    val endIron = block("end_iron_ore", { DropExperienceBlock(
        Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F).noOcclusion(),
        UniformInt.of(0, 2)
    ) }, Item.Properties())
    val endGold = block("end_gold_ore", { DropExperienceBlock(
        Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F).noOcclusion(),
        UniformInt.of(0, 2)
    ) }, Item.Properties())
    val endLapis = block("end_lapis_ore", { DropExperienceBlock(
        Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F).noOcclusion(),
        UniformInt.of(0, 2)
    ) }, Item.Properties())
    val endRedstone = block(
        "end_redstone_ore",
        {
            RedStoneOreBlock(
                Properties.of(Material.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(3F, 3F)
                    .noOcclusion()
                    .lightLevel { if (it.getValue(BlockStateProperties.LIT)) 9 else 0 }
                    .randomTicks()
            )
        },
        Item.Properties()
    )
    val endDiamond = block("end_diamond_ore", { DropExperienceBlock(
        Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F).noOcclusion(),
        UniformInt.of(0, 2)
    ) }, Item.Properties())
    val endEmerald = block("end_emerald_ore", { DropExperienceBlock(
        Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F).noOcclusion(),
        UniformInt.of(0, 2)
    ) }, Item.Properties())
    val endCopper = block("end_copper_ore", { DropExperienceBlock(
        Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F).noOcclusion(),
        UniformInt.of(0, 2)
    ) }, Item.Properties())

    private fun <T: Block> block(id: String, b: Supplier<T>, props: Item.Properties): RegistryObject<T> {
        val block = this.block.register(id, b)
        items.register(id) { BlockItem(block.get(), props.tab(dimoreTab)) }
        return block
    }

    private fun never(state: BlockState, getter: BlockGetter, pos: BlockPos): Boolean = false
}