package com.algorithmlx.dimore

import com.algorithmlx.dimore.init.config.ConfigManager
import com.algorithmlx.dimore.init.Registry
import com.algorithmlx.dimore.init.post.loot.SimpleGenerator
//? if neoforge {
/*import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.ModList
import net.neoforged.fml.common.Mod
*///?} else if fabric {
import com.algorithmlx.dimore.worldgen.OreConfig
import net.fabricmc.loader.api.FabricLoader
//?}

const val ModId: String = "dimore"

//? if neoforge {
/*@Mod(ModId)
class Mod(bus: IEventBus) {
    init {
        SimpleGenerator.generateLootTables(ModList.get().isLoaded("kubeJs"))
        ConfigManager.load()
        Registry.init(bus)
    }
}
*///?} else {
object Mod {
    fun onInitialize() {
        SimpleGenerator.generateLootTables(FabricLoader.getInstance().isModLoaded("kubejs"))

        ConfigManager.load()
        Registry.init()
        OreConfig.init()
    }
}
//?}


//Interpretator