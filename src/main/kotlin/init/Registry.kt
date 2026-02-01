package com.algorithmlx.dimore.init

import com.algorithmlx.dimore.ModId
import com.algorithmlx.dimore.block.DimensionalOreBlock
import com.algorithmlx.dimore.block.DimensionalRedstoneOre
import com.algorithmlx.dimore.init.config.ConfigManager
import com.algorithmlx.dimore.item.NamedBlockItem
import com.algorithmlx.dimore.util.DimensionOreConfig
import com.algorithmlx.dimore.util.OreDimensionType
import com.algorithmlx.dimore.util.OreDimensionTypes
import com.algorithmlx.dimore.util.OreGeneratorFactory
import com.algorithmlx.dimore.util.OreType
import com.algorithmlx.dimore.util.OreTypes
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
//? if neoforge {
/*import com.algorithmlx.dimore.worldgen.DimOreModifier
import net.minecraft.core.Holder
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredBlock
import net.neoforged.neoforge.registries.DeferredRegister
import net.neoforged.neoforge.registries.NeoForgeRegistries
import java.util.function.Supplier
*///?}
//? if >1.21.10 {
import com.algorithmlx.dimore.util.ResourceLocation
//?} else {
/*import net.minecraft.resources.ResourceLocation
*///?}
//? if fabric {
import net.fabricmc.fabric.api.event.registry.DynamicRegistrySetupCallback
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature
import net.minecraft.world.level.levelgen.placement.PlacedFeature
//?}

object Registry {
    //? if neoforge {
    /*private val blockRegistry = DeferredRegister.createBlocks(ModId)
    private val itemRegistry = DeferredRegister.createItems(ModId)
    private val biomeModifierSerializers = DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, ModId)
    val blockHolders = mutableMapOf<String, Holder<Block>>()

    fun init(bus: IEventBus) {
        blockRegistry.register(bus)
        itemRegistry.register(bus)
        biomeModifierSerializers.register(bus)

        registerOres()

        biomeModifierSerializers.register("dimore_modifier", Supplier { DimOreModifier.codec }) }
    *///?} else {
    fun init() {
        registerOres()

        DynamicRegistrySetupCallback.EVENT.register { regMgr ->
            val confReg = regMgr.getOptional(Registries.CONFIGURED_FEATURE)
            val placedReg = regMgr.getOptional(Registries.PLACED_FEATURE)

            if (!confReg.isPresent && !placedReg.isPresent) return@register

            registerFeatures(confReg.get(), placedReg.get())
        }
    }
    //?}

    private fun registerOres() {
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

    private fun registerOre(id: String, oreType: OreType, oreDimensionType: OreDimensionType) = registerBlock(
        id,
        { p -> DimensionalOreBlock(oreType, oreDimensionType, p) },
        BlockBehaviour.Properties.ofFullCopy(Blocks.STONE),
        true
    )

    private fun registerRedstone(id: String, oreDimensionType: OreDimensionType) = registerBlock(
        id,
        { p -> DimensionalRedstoneOre(oreDimensionType, p) },
        BlockBehaviour.Properties.ofFullCopy(Blocks.STONE),
        true
    )

    //? if neoforge {
    /*private fun <B: Block> registerBlock(
        id: String,
        block: (BlockBehaviour.Properties) -> B,
        properties: BlockBehaviour.Properties,
        shouldRegisterItem: Boolean
    ): DeferredBlock<B> {
        //? if >1.21.1 {
        val blockKey = { it: ResourceLocation -> ResourceKey.create(Registries.BLOCK, it) }
        val bl = blockRegistry.register(id) { rk ->
            block(properties.setId(blockKey(rk)))
        }
        //?} else
        /*val bl = blockRegistry.register(id, Supplier { block(properties) })*/

        if (shouldRegisterItem) {
            //? if >1.21.1 {
            itemRegistry.register(id) { rk ->
                NamedBlockItem(
                    bl.get(),
                    Item.Properties()
                        .setId(ResourceKey.create(Registries.ITEM, rk))
                        .useBlockDescriptionPrefix()
                )
            }
            //?} else
            /*itemRegistry.register(id, Supplier { NamedBlockItem(bl.get(), Item.Properties()) })*/
        }

        blockHolders[id] = bl

        return bl
    }
    *///?} else {

    private fun registerFeatures(
        cfReg: Registry<ConfiguredFeature<*, *>>,
        pfReg: Registry<PlacedFeature>
    ) {
        if (ConfigManager.config.netherOres.generateOres) {
            OreTypes.netherOres.forEach { type ->
                val id = "nether_${type.name.lowercase()}_ore"
                val config = OreTypes.configByTypeNether[type] ?: return@forEach
                //? if >1.21.1 {
                val block = BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath(ModId, id))
                    .orElseThrow()
                    .value()
                //?} else {
                /*val block = BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath(ModId, id))
                *///?}

                createFeature(cfReg, pfReg, id, block, OreDimensionTypes.NETHER, config)
            }
        }

        if (ConfigManager.config.overworldOres.generateOres) {
            OreTypes.overworldOres.forEach { type ->
                val stoneId = "stone_${type.name.lowercase()}_ore"
                val deepslateId = "deepslate_${type.name.lowercase()}_ore"
                val config = OreTypes.configByTypeOverworld[type] ?: return@forEach

                //? if >1.21.1 {
                val stoneBlock = BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath(ModId, stoneId))
                    .orElseThrow()
                    .value()
                val deepslateBlock =
                    BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath(ModId, deepslateId))
                        .orElseThrow()
                        .value()
                //?} else {
                /*val stoneBlock = BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath(ModId, stoneId))
                val deepslateBlock = BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath(ModId, deepslateId))
                *///?}

                createFeature(cfReg, pfReg, stoneId, stoneBlock, OreDimensionTypes.OVERWORLD, config)
                createFeature(cfReg, pfReg, deepslateId, deepslateBlock, OreDimensionTypes.OVERWORLD_DEEPSLATE, config)
            }
        }

        if (ConfigManager.config.endOres.generateOres) {
            OreTypes.endOres.forEach { type ->
                val id = "end_${type.name.lowercase()}_ore"
                val config = OreTypes.configByTypeEnd[type] ?: return@forEach
                //? if >1.21.1 {
                val block = BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath(ModId, id))
                    .orElseThrow()
                    .value()
                //?} else {
                /*val block = BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath(ModId, id))
                *///?}

                createFeature(cfReg, pfReg, id, block, OreDimensionTypes.END, config)
            }
        }
    }

    private fun createFeature(
        cfReg: Registry<ConfiguredFeature<*, *>>,
        pfReg: Registry<PlacedFeature>,
        id: String,
        block: Block,
        dimType: OreDimensionType,
        settings: DimensionOreConfig
    ) {
        val location = ResourceLocation.fromNamespaceAndPath(ModId, id)

        val configured = OreGeneratorFactory.createConfigured(dimType, block, settings.size)
        Registry.register(cfReg, location, configured)

        val cfKey = ResourceKey.create(Registries.CONFIGURED_FEATURE, location)
        //? if >1.21.1 {
        val entry = cfReg.get(cfKey).orElseThrow()
        //?} else {
        /*val entry = cfReg.getHolder(cfKey).orElseThrow()
        *///?}

        val placed = OreGeneratorFactory.createPlaced(entry, settings.count, settings.minHeight, settings.maxHeight)
        Registry.register(pfReg, location, placed)
    }

    private fun registerBlock(id: String, factory: (BlockBehaviour.Properties) -> Block, properties: BlockBehaviour.Properties, shouldRegisterItem: Boolean): Block {
        val blockKey = ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(ModId, id))
        //? if >1.21.1 {
        val b = factory(properties.setId(blockKey))
        //?} else {
        /*val b = factory(properties)
        *///?}

        if (shouldRegisterItem) {
            val props = Item.Properties()
            val itemKey = ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ModId, id))
            //? if >1.21.1
            props.setId(itemKey).useBlockDescriptionPrefix()
            val blockItem = NamedBlockItem(b, props)
            Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem)
        }

        return Registry.register(BuiltInRegistries.BLOCK, blockKey, b)
    }
    //?}
}