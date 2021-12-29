/*MIT License
 *
 *Copyright (c) 2021 AlgoTeam
 *
 *Permission is hereby granted, free of charge, to any person obtaining a copy
 *of this software and associated documentation files (the "Software"), to deal
 *in the Software without restriction, including without limitation the rights
 *to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *copies of the Software, and to permit persons to whom the Software is
 *furnished to do so, subject to the following conditions:
 *
 *The above copyright notice and this permission notice shall be included in all
 *copies or substantial portions of the Software.
 *
 *THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *SOFTWARE.
 *
 */
package com.algorithmlx.dimore;

import com.algorithmlx.dimore.setup.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(DimOre.ModId)
@Mod.EventBusSubscriber(modid = DimOre.ModId, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DimOre {
    public static final String ModId = "dimore";

    public static final Logger LOGGER = LogManager.getLogger();

    public DimOre() {
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> DimOreClientCore::new);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DimOreConfig.GEN, "dimore_config.toml");
        FMLJavaModLoadingContext.get().getModEventBus().addListener(DimOreCore::commonInit);
        DimOreReg.init();
    }
    @SubscribeEvent
    public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
        DimOreReg.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
            event.getRegistry()
                    .register(new BlockItem(block, new Item.Properties().tab(DimOreCore.DIMORE_TAB))
                            .setRegistryName(block.getRegistryName()));
        });

    }
}
