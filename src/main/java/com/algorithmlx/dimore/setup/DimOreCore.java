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
package com.algorithmlx.dimore.setup;

import com.algorithmlx.dimore.DimOre;
import com.algorithmlx.dimore.world.gen.OreConfigured;
import com.algorithmlx.dimore.world.gen.OrePlacement;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = DimOre.ModId, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DimOreCore {
    public static final CreativeModeTab DIMORE_TAB = new CreativeModeTab(DimOre.ModId + ".dimore_tab") {
        @Override @Nonnull
        public ItemStack makeIcon() {
            return new ItemStack(DimOreReg.NETHER_DIAMOND_ORE.get());
        }
    };

    public static void commonInit(final FMLCommonSetupEvent event) {
        OrePlacement.register();
        OreConfigured.register();
    }

    @SubscribeEvent
    public static void serverLoad(RegisterCommandsEvent event) {

    }
}
