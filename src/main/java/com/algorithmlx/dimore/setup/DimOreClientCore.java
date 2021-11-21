package com.algorithmlx.dimore.setup;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class DimOreClientCore {
    public DimOreClientCore() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::init);
    }
    public void init(FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(EventHandler.INSTANCE);
    }
}
