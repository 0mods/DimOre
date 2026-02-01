package com.algorithmlx.dimore

import com.algorithmlx.dimore.init.config.ConfigManager
import com.algorithmlx.dimore.init.Registry
//? if neoforge {
/*import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.common.Mod
*///?} else if fabric
import com.algorithmlx.dimore.worldgen.OreConfig

const val ModId: String = "dimore"

//? if neoforge {
/*@Mod(ModId)
class Mod(bus: IEventBus) {
    init {
        ConfigManager.load()
        Registry.init(bus)
    }
}
*///?} else {
object Mod {
    fun onInitialize() {
        ConfigManager.load()
        Registry.init()
        OreConfig.init()
    }
}
//?}
