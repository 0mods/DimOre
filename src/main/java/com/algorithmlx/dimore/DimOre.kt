package com.algorithmlx.dimore

import com.algorithmlx.dimore.init.DORegistry
import com.algorithmlx.dimore.init.config.DOCommonConfig
import com.algorithmlx.dimore.worldgen.DOConfFeatures
import com.algorithmlx.dimore.worldgen.DOPlacedFeatures
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraftforge.event.CreativeModeTabEvent
import net.minecraftforge.fml.ModLoadingException
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.config.ModConfig
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MarkerFactory
import thedarkcolour.kotlinforforge.forge.FORGE_BUS
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.forge.registerConfig

const val ModId = "dimore"
@JvmField
val LOGGER: Logger = LoggerFactory.getLogger("Dimensional Ores")

@Mod(ModId)
class DimOre {
    init {
        registerConfig(ModConfig.Type.COMMON, DOCommonConfig.commonConfig, "dimore/common.toml")
        LOGGER.info("Starting the mod!")
        try {
            FORGE_BUS.register(this)
            DORegistry.init(MOD_BUS)
            DOConfFeatures.init(MOD_BUS)
            DOPlacedFeatures.init(MOD_BUS)

            MOD_BUS.addListener(this::registerTabs)
        } catch (e: ModLoadingException) {
            e.printStackTrace()
        }
    }

    fun registerTabs(evt: CreativeModeTabEvent.Register) {
        evt.registerCreativeModeTab(ResourceLocation(ModId, "dimore")) {
            it.icon { ItemStack(DORegistry.netherDiamond.get()) }
                .noScrollBar()
                .title(Component.literal("Dimensional Ores"))
                .displayItems { _, pOutput, _ ->
                    pOutput.accept(DORegistry.netherCoal.get())
                    pOutput.accept(DORegistry.netherIron.get())
                    pOutput.accept(DORegistry.netherLapis.get())
                    pOutput.accept(DORegistry.netherRedstone.get())
                    pOutput.accept(DORegistry.netherDiamond.get())
                    pOutput.accept(DORegistry.netherEmerald.get())
                    pOutput.accept(DORegistry.netherCopper.get())

                    pOutput.accept(DORegistry.endQuartz.get())
                    pOutput.accept(DORegistry.endCoal.get())
                    pOutput.accept(DORegistry.endIron.get())
                    pOutput.accept(DORegistry.endGold.get())
                    pOutput.accept(DORegistry.endLapis.get())
                    pOutput.accept(DORegistry.endRedstone.get())
                    pOutput.accept(DORegistry.endDiamond.get())
                    pOutput.accept(DORegistry.endEmerald.get())
                    pOutput.accept(DORegistry.endCopper.get())
                    pOutput.accept(DORegistry.stoneQuartz.get())
                    pOutput.accept(DORegistry.deepslateQuartz.get())
                }
        }
    }
}