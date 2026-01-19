package com.algorithmlx.dimore

import com.algorithmlx.dimore.init.BlockRegistry
import com.algorithmlx.dimore.init.config.ConfigManager
//? if fabric
import com.algorithmlx.dimore.worldgen.OreConfig

//? if neoforge {
/*import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.common.Mod
*///?}

const val ModId: String = "dimore"

//? if neoforge {
/*@Mod(ModId)
class Mod(bus: IEventBus) {
    init {
        ConfigManager.load()
        BlockRegistry.init(bus)
    }
}
*///?} else {
object Mod {
    @JvmStatic
    fun onInitialize() {
        ConfigManager.load()
        BlockRegistry.init()
        OreConfig.init()
    }
}
//?}
