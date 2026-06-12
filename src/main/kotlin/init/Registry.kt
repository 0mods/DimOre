package com.algorithmlx.dimore.init

import com.algorithmlx.dimore.LOGGER
import com.algorithmlx.dimore.ModId
import com.algorithmlx.dimore.block.DimensionalOreBlock
import com.algorithmlx.dimore.block.DimensionalRedstoneOre
import com.algorithmlx.dimore.block.NamedExperienceBlock
import com.algorithmlx.dimore.block.NamedRedstoneBlock
import com.algorithmlx.dimore.init.post.PostBlock
import com.algorithmlx.dimore.init.config.CommentedJSONManager
import com.algorithmlx.dimore.init.post.loot.ItemEntry
import com.algorithmlx.dimore.init.post.loot.SimpleLootTable
import com.algorithmlx.dimore.item.NamedBlockItem
import com.algorithmlx.dimore.util.OreDimensionType
import com.algorithmlx.dimore.util.OreDimensionTypes
import com.algorithmlx.dimore.util.OreType
import com.algorithmlx.dimore.util.OreTypes
import com.algorithmlx.dimore.util.ResLoc
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.decodeFromStream
import net.minecraft.world.level.block.state.BlockBehaviour
//$ if >1.21.1 'import net.minecraft.core.HolderLookup' else 'import net.minecraft.core.HolderGetter'
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.server.packs.resources.SimplePreparableReloadListener
import net.minecraft.util.profiling.ProfilerFiller
//? if neoforge {
/*import com.algorithmlx.dimore.worldgen.DimOreModifier
import net.minecraft.core.Holder
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.common.NeoForge
//? if >1.21.1 {
import net.neoforged.neoforge.event.AddServerReloadListenersEvent as AddReloadListenerEvent
//?} else {
/*import net.neoforged.neoforge.event.AddReloadListenerEvent
*///?}
import net.neoforged.neoforge.registries.DeferredBlock
import net.neoforged.neoforge.registries.DeferredRegister
import net.neoforged.neoforge.registries.NeoForgeRegistries
import java.util.function.Supplier
*///?} elif fabric {
import com.algorithmlx.dimore.init.config.DimensionalOresConfig
import com.algorithmlx.dimore.util.DimensionOreConfig
import com.algorithmlx.dimore.util.OreGeneratorFactory
import net.fabricmc.fabric.api.event.registry.DynamicRegistrySetupCallback
//$ if >1.21.1 'import net.fabricmc.fabric.api.resource.v1.ResourceLoader' else 'import net.fabricmc.fabric.api.resource.ResourceManagerHelper as ResourceLoader'
import net.fabricmc.fabric.api.resource.v1.ResourceLoader
import net.minecraft.core.Registry
import net.minecraft.server.packs.PackType
//? if <=1.21.1 {
/*import net.minecraft.server.packs.resources.PreparableReloadListener
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor
*///?}
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature
import net.minecraft.world.level.levelgen.placement.PlacedFeature
//?}
import java.io.File

@OptIn(ExperimentalSerializationApi::class)
object Registry {
    private val postBlocks = mutableMapOf<String, PostBlock>()
    private val simpleLootTables = mutableMapOf<String, SimpleLootTable>()
    private val json = CommentedJSONManager.json
    //? if neoforge {
    /*private val blockRegistry = DeferredRegister.createBlocks(ModId)
    private val itemRegistry = DeferredRegister.createItems(ModId)
    private val biomeModifierSerializers = DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, ModId)
    val blockHolders = mutableMapOf<String, Holder<Block>>()
    *///?}

    //$ if forgeLike 'fun init(bus: IEventBus) {' else 'fun init() {'
    fun init() {
        //? if forgeLike {
        /*blockRegistry.register(bus)
        itemRegistry.register(bus)
        biomeModifierSerializers.register(bus)
        *///?}
        
        registerOres()
        if (CommentedJSONManager.config.enableCustomBlocks) initOresFromJSON()
        if (CommentedJSONManager.config.enableLootTables) registerLootTables()

        val lootTableReload = object: SimplePreparableReloadListener<Unit>() {
            override fun prepare(manager: ResourceManager, profiler: ProfilerFiller) {}
            override fun apply(preparations: Unit, manager: ResourceManager, profiler: ProfilerFiller) = registerLootTables()
        }

        //? if >1.21.1 || fabricLike
        val lootTableReloadId = ResLoc.parse("$ModId:config_loot_table")

        //? if forgeLike {
        /*biomeModifierSerializers.register("dimore_modifier", Supplier { DimOreModifier.codec })
        NeoForge.EVENT_BUS.addListener { event: AddReloadListenerEvent ->
            //? if >1.21.1 {
            event.addListener(lootTableReloadId, lootTableReload)
            //?} else {
            /*event.addListener(lootTableReload)
            *///?}
        }
        *///?} else {
        DynamicRegistrySetupCallback.EVENT.register { regMgr ->
            val confReg = regMgr.getOptional(Registries.CONFIGURED_FEATURE)
            val placedReg = regMgr.getOptional(Registries.PLACED_FEATURE)

            if (!confReg.isPresent && !placedReg.isPresent) return@register

            registerFeatures(confReg.get(), placedReg.get())
        }

        ResourceLoader.get(PackType.SERVER_DATA)
            //? if >1.21.1 {
            //$ if >1.21.11 '.registerReloadListener(lootTableReloadId, lootTableReload)' else '.registerReloader(lootTableReloadId, lootTableReload)'
            .registerReloadListener(lootTableReloadId, lootTableReload)
            //?} else {
            /*.registerReloadListener(object : IdentifiableResourceReloadListener {
                override fun getFabricId(): ResLoc? = lootTableReloadId
                override fun reload(
                    preparationBarrier: PreparableReloadListener.PreparationBarrier, resourceManager: ResourceManager,
                    profilerFiller: ProfilerFiller, profilerFiller2: ProfilerFiller, executor: Executor, executor2: Executor
                ): CompletableFuture<Void?>? = lootTableReload.reload(preparationBarrier, resourceManager, profilerFiller, profilerFiller2, executor, executor2)
            })
            *///? }
        //?}
    }

    private fun initOresFromJSON() {
        val configFiles = File("config/$ModId/custom/")
        if (!configFiles.exists()) {
            configFiles.parentFile.mkdirs()
            configFiles.mkdirs()
            LOGGER.info("Custom block config is not exists. Skipping loading.")
        }

        configFiles.listFiles()
            .filter { it.name.endsWith(".json") }
            .filter { !it.name.startsWith("_") }
            .filter { !it.isDirectory }
            .forEach {
                val id = "custom.${it.name.removeSuffix(".json")}"
                val config: PostBlock = json.decodeFromStream(it.inputStream())

                postBlocks[id] = config

                if (!config.isRedstone) this.registerBlock(
                    id, { properties ->
                        NamedExperienceBlock(
                            config.experienceDrop.asMC(),
                            properties,
                            if (config.displayName.isNotEmpty()) Component.translatable(config.displayName) else null
                        )
                    },
                    config.properties.asBlockBehaviourProperties(), true
                ) else this.registerBlock(
                    id, { properties ->
                        NamedRedstoneBlock(
                            properties,
                            if (config.displayName.isNotEmpty()) Component.translatable(config.displayName) else null
                        )
                    },
                    config.properties.asBlockBehaviourProperties(), true
                )
            }
    }

    fun getPostBlocks(): Map<String, PostBlock> = mapOf(*postBlocks.entries.map { it.toPair() }.toTypedArray())

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

    @JvmStatic
    //$ if >1.21.1 'fun getLoot(requestedId: ResourceKey<LootTable>, lookup: HolderLookup.Provider): LootTable? {' else 'fun getLoot(requestedId: ResourceKey<LootTable>, lookup: HolderGetter.Provider): LootTable? {'
    fun getLoot(requestedId: ResourceKey<LootTable>, lookup: HolderLookup.Provider): LootTable? {
        val resourceId =
            //$ if >1.21.1 'requestedId.identifier()' else 'requestedId.location()'
            requestedId.identifier()

        val table = simpleLootTables[resourceId.toString()] ?: return null

        val lootItemsConditioned = table.entries.filter { it.requires.isNotEmpty() }.map {
            val itemId = if (it is ItemEntry) it.id else "${resourceId.namespace}:${resourceId.path.split('/').last()}"
            val item = BuiltInRegistries.ITEM
                //$ if >1.21.1 '.getValue(ResLoc.parse(itemId))' else '.get(ResLoc.parse(itemId))'
                .getValue(ResLoc.parse(itemId))
            var lootItem = LootItem.lootTableItem(item)

            it.requires.map { req -> req.asMC(lookup) }.forEach { req ->
                lootItem = lootItem.`when` { req.build() }
            }

            it.functions.map { func -> func.asMC(lookup) }.forEach { func ->
                lootItem = lootItem.apply { func.build() }
            }

            lootItem
        }

        val lootItemsNoCondition = table.entries.filter { it.requires.isEmpty() }.map {
            val itemId = if (it is ItemEntry) it.id else "${resourceId.namespace}:${resourceId.path.split('/').last()}"
            val item = BuiltInRegistries.ITEM
                //$ if >1.21.1 '.getValue(ResLoc.parse(itemId))' else '.get(ResLoc.parse(itemId))'
                .getValue(ResLoc.parse(itemId))
            var lootItem = LootItem.lootTableItem(item)

            it.functions.map { func -> func.asMC(lookup) }.forEach { func ->
                lootItem = lootItem.apply { func.build() }
            }

            lootItem
        }

        return LootTable.lootTable().withPool(LootPool.lootPool()
            .setRolls(ConstantValue.exactly(table.rolls))
            .setBonusRolls(ConstantValue.exactly(table.bonusRolls))
            .add(AlternativesEntry.alternatives(
                *lootItemsConditioned.toTypedArray(),
                *lootItemsNoCondition.toTypedArray()
            ))).build()
    }

    private fun registerLootTables() {
        val lootFiles = File("config/$ModId/loot/")
        if (!lootFiles.exists()) {
            lootFiles.parentFile.mkdirs()
            lootFiles.mkdirs()
            LOGGER.info("Simple loot table is not found. Skipping loading.")
        }

        if (simpleLootTables.isNotEmpty()) simpleLootTables.clear()

        lootFiles.listFiles()
            .filter { it.name.endsWith(".json") }
            .filter { !it.name.startsWith("_") }
            .filter { !it.isDirectory }
            .forEach {
                val parsed: SimpleLootTable = json.decodeFromStream(it.inputStream())
                val id = parsed.target.ifEmpty { "$ModId:block/custom.${it.name.removeSuffix(".json")}" }

                simpleLootTables[id] = parsed
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
        val blockKey = { it: ResLoc -> ResourceKey.create(Registries.BLOCK, it) }
        val bl = blockRegistry.register(id) { rk ->
            block(properties.setId(blockKey(rk)))
        }
        //?} else
        //val bl = blockRegistry.register(id, Supplier { block(properties) })

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
            //itemRegistry.register(id, Supplier { NamedBlockItem(bl.get(), Item.Properties()) })
        }

        blockHolders[id] = bl

        return bl
    }
    *///?} else {
    private fun registerFeatures(
        cfReg: Registry<ConfiguredFeature<*, *>>,
        pfReg: Registry<PlacedFeature>
    ) {
        if (CommentedJSONManager.config.netherOres.generateOres) {
            OreTypes.netherOres.forEach { type ->
                val id = "nether_${type.name.lowercase()}_ore"
                val config = OreTypes.configByTypeNether[type] ?: return@forEach

                val block = BuiltInRegistries.BLOCK.get(ResLoc.fromNamespaceAndPath(ModId, id))
                    //$ if >1.21.1 '.orElseThrow().value()' else '//.orElseThrow().value()'
                    .orElseThrow().value()
                createFeature(cfReg, pfReg, id, block, OreDimensionTypes.NETHER, config)
            }
        }

        if (CommentedJSONManager.config.overworldOres.generateOres) {
            OreTypes.overworldOres.forEach { type ->
                val stoneId = "stone_${type.name.lowercase()}_ore"
                val deepslateId = "deepslate_${type.name.lowercase()}_ore"
                val config = OreTypes.configByTypeOverworld[type] ?: return@forEach

                val stoneBlock = BuiltInRegistries.BLOCK.get(ResLoc.fromNamespaceAndPath(ModId, stoneId))
                    //$ if >1.21.1 '.orElseThrow().value()' else '//.orElseThrow().value()'
                    .orElseThrow().value()
                val deepslateBlock = BuiltInRegistries.BLOCK.get(ResLoc.fromNamespaceAndPath(ModId, deepslateId))
                    //$ if >1.21.1 '.orElseThrow().value()' else '//.orElseThrow().value()'
                    .orElseThrow().value()
                createFeature(cfReg, pfReg, stoneId, stoneBlock, OreDimensionTypes.OVERWORLD, config)
                createFeature(cfReg, pfReg, deepslateId, deepslateBlock, OreDimensionTypes.OVERWORLD_DEEPSLATE, config)
            }
        }

        if (CommentedJSONManager.config.endOres.generateOres) {
            OreTypes.endOres.forEach { type ->
                val id = "end_${type.name.lowercase()}_ore"
                val config = OreTypes.configByTypeEnd[type] ?: return@forEach
                val block = BuiltInRegistries.BLOCK.get(ResLoc.fromNamespaceAndPath(ModId, id))
                    //$ if >1.21.1 '.orElseThrow().value()' else '//.orElseThrow().value()'
                    .orElseThrow().value()
                createFeature(cfReg, pfReg, id, block, OreDimensionTypes.END, config)
            }
        }

        this.getPostBlocks().forEach { (id, block) ->
            val settings = block.generationSettings
            val generationConfig = settings.config
            val convertedConfig = DimensionalOresConfig.OreGenerationSettings(
                true, generationConfig.size, generationConfig.count,
                generationConfig.minHeight, generationConfig.maxHeight
            )

            val mcBlock = BuiltInRegistries.BLOCK.get(ResLoc.fromNamespaceAndPath(ModId, id))
                //$ if >1.21.1 '.orElseThrow().value()' else '//.orElseThrow().value()'
                .orElseThrow().value()

            createFeature(cfReg, pfReg, id, mcBlock, settings.asDimensionType(), convertedConfig)
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
        val location = ResLoc.fromNamespaceAndPath(ModId, id)

        val configured = OreGeneratorFactory.createConfigured(dimType, block, settings.size)
        Registry.register(cfReg, location, configured)

        val cfKey = ResourceKey.create(Registries.CONFIGURED_FEATURE, location)
        val entry =
            //$ if >1.21.1 'cfReg.get(cfKey).orElseThrow()' else 'cfReg.getHolder(cfKey).orElseThrow()'
            cfReg.get(cfKey).orElseThrow()

        val placed = OreGeneratorFactory.createPlaced(entry, settings.count, settings.minHeight, settings.maxHeight)
        Registry.register(pfReg, location, placed)
    }

    private fun registerBlock(id: String, factory: (BlockBehaviour.Properties) -> Block, properties: BlockBehaviour.Properties, shouldRegisterItem: Boolean): Block {
        val blockKey = ResourceKey.create(Registries.BLOCK, ResLoc.fromNamespaceAndPath(ModId, id))
        val b = factory(
            properties
                //$ if >1.21.1 '.setId(blockKey)' else '//.setId(blockKey)'
                .setId(blockKey)
        )

        if (shouldRegisterItem) {
            val props = Item.Properties()
            val itemKey = ResourceKey.create(Registries.ITEM, ResLoc.fromNamespaceAndPath(ModId, id))
            //? if >1.21.1
            props.setId(itemKey).useBlockDescriptionPrefix()
            val blockItem = NamedBlockItem(b, props)
            Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem)
        }

        return Registry.register(BuiltInRegistries.BLOCK, blockKey, b)
    }
    //?}
}