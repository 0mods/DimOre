package com.algorithmlx.dimore.api.tab

import net.minecraft.world.item.Item
import net.minecraftforge.event.CreativeModeTabEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraft.world.item.CreativeModeTab as Tab

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
object CreativeModeTabHelper {
    private val tabMap: MutableMap<Item, Tab> = hashMapOf()

    fun <T: Item> T.addItemToTab(tab: Tab): T {
        tabMap[this] = tab
        return this
    }

    @SubscribeEvent
    fun addTabsItem(evt: CreativeModeTabEvent.BuildContents) {
        for (entry in tabMap) {
            if (evt.tab.equals(entry.value))
                evt.accept(entry.key)
        }
    }
}