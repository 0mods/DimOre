package com.algorithmlx.dimore

import com.algorithmlx.dimore.init.config.DimOreConfigManager
import com.algorithmlx.dimore.init.Registry
//? if neoforge {
/*import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.common.Mod
*///?} else if fabric {
import com.algorithmlx.dimore.worldgen.OreConfig
//?}
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@JvmField
val LOGGER: Logger = LoggerFactory.getLogger("DimOre")
const val ModId: String = "dimore"

//? if neoforge {
/*@Mod(ModId)
class Mod(bus: IEventBus) {
    init {
        DimOreConfigManager.load()
        Registry.init(bus)
    }
}
*///?} else {
object Mod {
    fun onInitialize() {
        DimOreConfigManager.load()
        Registry.init()
        OreConfig.init()
    }

    fun onInitializeClient() = initClient()
}
//?}
