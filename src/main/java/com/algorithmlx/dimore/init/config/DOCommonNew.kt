package com.algorithmlx.dimore.init.config

import com.algorithmlx.dimore.api.config.CfgFile
import com.algorithmlx.dimore.api.config.ConfigField
import com.algorithmlx.dimore.api.config.ICfg

@CfgFile
object DOCommonNew: ICfg {
    @JvmField
    @ConfigField("test", "omg", "How it works?", "I idk...")
    var testValue = false

    override fun getName(): String = "dimensional_ores/common"
}