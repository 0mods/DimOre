package com.algorithmlx.dimore.init

import com.algorithmlx.dimore.LOGGER
import com.algorithmlx.dimore.ModId
import com.algorithmlx.dimore.api.dimension.DimensionOreType
import com.algorithmlx.dimore.api.ore.OreType
import com.algorithmlx.dimore.block.RedstoneWorldedOreBlock
import com.algorithmlx.dimore.block.WorldedOreBlock
import net.minecraft.network.chat.Component
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.Block
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject
import java.util.function.Supplier

object DORegistry {
    val dimoreTab: CreativeModeTab = object : CreativeModeTab(ModId) {
        override fun makeIcon(): ItemStack = ItemStack(netherDiamond.get())

        override fun getDisplayName(): Component = Component.literal("Dimensional Ores")
    }

    private val items = DeferredRegister.create(ForgeRegistries.ITEMS, ModId)
    private val block = DeferredRegister.create(ForgeRegistries.BLOCKS, ModId)

    fun init(bus: IEventBus) {
        LOGGER.info("Registry initialized")
        items.register(bus)
        block.register(bus)
    }

    val netherCoal = block("nether_coal_ore") { WorldedOreBlock(OreType.COAL, DimensionOreType.NETHER) }
    val netherIron = block("nether_iron_ore") { WorldedOreBlock(OreType.IRON, DimensionOreType.NETHER) }
    val netherLapis = block("nether_lapis_ore") { WorldedOreBlock(OreType.LAPIS, DimensionOreType.NETHER) }
    val netherRedstone = block("nether_redstone_ore") { RedstoneWorldedOreBlock(DimensionOreType.NETHER) }
    val netherDiamond = block("nether_diamond_ore") { WorldedOreBlock(OreType.DIAMOND, DimensionOreType.NETHER) }
    val netherEmerald = block("nether_emerald_ore") { WorldedOreBlock(OreType.EMERALD, DimensionOreType.NETHER) }
    val netherCopper = block("nether_copper_ore") { WorldedOreBlock(OreType.COPPER, DimensionOreType.NETHER) }

    val endQuartz = block("end_quartz_ore") { WorldedOreBlock(OreType.QUARTZ, DimensionOreType.END) }
    val endCoal = block("end_coal_ore") { WorldedOreBlock(OreType.COAL, DimensionOreType.END) }
    val endIron = block("end_iron_ore") { WorldedOreBlock(OreType.IRON, DimensionOreType.END) }
    val endGold = block("end_gold_ore") { WorldedOreBlock(OreType.GOLD, DimensionOreType.END) }
    val endLapis = block("end_lapis_ore") { WorldedOreBlock(OreType.LAPIS, DimensionOreType.END) }
    val endRedstone = block("end_redstone_ore") { RedstoneWorldedOreBlock(DimensionOreType.END) }
    val endDiamond = block("end_diamond_ore") { WorldedOreBlock(OreType.DIAMOND, DimensionOreType.END) }
    val endEmerald = block("end_emerald_ore") { WorldedOreBlock(OreType.EMERALD, DimensionOreType.END) }
    val endCopper = block("end_copper_ore") { WorldedOreBlock(OreType.COPPER, DimensionOreType.END) }

    val overworldQuartz = block("overworld_quartz_ore") { WorldedOreBlock(OreType.QUARTZ, DimensionOreType.OVERWORLD) }

    private fun <T: Block> block(id: String, b: Supplier<T>): RegistryObject<T> {
        val block = this.block.register(id, b)
        items.register(id) { BlockItem(block.get(), Item.Properties().tab(dimoreTab)) }
        return block
    }
}