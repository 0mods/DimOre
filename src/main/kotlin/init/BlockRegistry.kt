package com.algorithmlx.dimore.init

import com.algorithmlx.dimore.ModId
import com.algorithmlx.dimore.block.DimensionalOreBlock
import com.algorithmlx.dimore.block.DimensionalRedstoneOre
import com.algorithmlx.dimore.util.OreDimensionType
import com.algorithmlx.dimore.util.OreDimensionTypes
import com.algorithmlx.dimore.util.OreType
import com.algorithmlx.dimore.util.OreTypes
//? if fabric {
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
//?}
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
//? if neoforge {
/*import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredBlock
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier
*///?}

//? if fabric && >1.21.10 {
import com.algorithmlx.dimore.util.ResourceLocation
//?} else if fabric {
/*import net.minecraft.resources.ResourceLocation
*///?}

object BlockRegistry {
    //? if neoforge {
    /*fun init(bus: IEventBus) {
        blockRegistry.register(bus)
        itemRegistry.register(bus)
    *///?} else {
    fun init() {
    //?}
        // Nether Ores
        OreTypes.netherOres.forEach {
            val id = "nether_${it.name.lowercase()}_ore"
            if (it == OreTypes.REDSTONE) {
                registerRedstone(id, OreDimensionTypes.NETHER)
                return@forEach
            }
            registerOre(id, it, OreDimensionTypes.NETHER)
        }

        // Overworld ores
        OreTypes.overworldOres.forEach {
            val id = "stone_${it.name.lowercase()}_ore"
            val deepSlateId = "deepslate_${it.name.lowercase()}_ore"

            registerOre(id, it, OreDimensionTypes.OVERWORLD)
            registerOre(deepSlateId, it, OreDimensionTypes.OVERWORLD_DEEPSLATE)
        }

        // End ores
        OreTypes.endOres.forEach {
            val id = "end_${it.name.lowercase()}_ore"
            if (it == OreTypes.REDSTONE) {
                registerRedstone(id, OreDimensionTypes.END)
                return@forEach
            }

            registerOre(id, it, OreDimensionTypes.END)
        }
    }

    //? if neoforge {
    /*private val blockRegistry = DeferredRegister.createBlocks(ModId)
    private val itemRegistry = DeferredRegister.createItems(ModId)

    private fun registerOre(id: String, oreType: OreType, oreDimensionType: OreDimensionType) = registerBlock(
        id, { DimensionalOreBlock(oreType, oreDimensionType) }
    ) { b -> BlockItem(b, Item.Properties()) }

    private fun registerRedstone(id: String, oreDimensionType: OreDimensionType) = registerBlock(
        id, { DimensionalRedstoneOre(oreDimensionType) }
    ) { b -> BlockItem(b, Item.Properties()) }

    private fun <B: Block, I: Item> registerBlock(id: String, block: () -> B, item: (B) -> I): DeferredBlock<B> {
        val bl = blockRegistry.register(id, block)
        itemRegistry.register(id, Supplier { item(bl.get()) })
        return bl
    }
    *///?} else {
    private fun registerOre(id: String, oreType: OreType, oreDimensionType: OreDimensionType) = registerBlock(
        id, DimensionalOreBlock(oreType, oreDimensionType)
    ) { b -> BlockItem(b, Item.Properties()) }

    private fun registerRedstone(id: String, oreDimensionType: OreDimensionType) = registerBlock(
        id, DimensionalRedstoneOre(oreDimensionType)
    ) { b -> BlockItem(b, Item.Properties()) }

    private fun <T: Block> registerBlock(id: String, register: T, item: ((T) -> Item)? = { BlockItem(it, Item.Properties()) }): T {
        val key = ResourceKey.create(
            Registries.BLOCK,
            ResourceLocation.fromNamespaceAndPath(ModId, id)
        )
        val b = Registry.register(BuiltInRegistries.BLOCK, key, register)
        if (item != null) registerItem(id, item(b))
        return b
    }

    private fun <T: Item> registerItem(id: String, register: T): T {
        val key = ResourceKey.create(
            Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(ModId, id)
        )
        return Registry.register(BuiltInRegistries.ITEM, key, register)
    }

    //?}
}