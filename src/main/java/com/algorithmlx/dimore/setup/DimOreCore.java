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
import com.algorithmlx.dimore.world.gen.EndOreGen;
import com.algorithmlx.dimore.world.gen.NetherOreGen;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = DimOre.ModId, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DimOreCore {
    public static final ItemGroup DimOreNetherTab = new ItemGroup(DimOre.ModId + ".dimore_nether_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Registration.NETHER_IRON_ORE.get());
        }
    };
    public static final ItemGroup DimOreEndTab = new ItemGroup(DimOre.ModId + ".dimore_end_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Registration.END_IRON_ORE.get());
        }
    };

    public static void init(final FMLCommonSetupEvent event) {
        NetherOreGen.registerOres();
        EndOreGen.registerOres();
        DimOre.LOGGER.info("DimOreCore is started!");
    }

    @SubscribeEvent
    public static void serverLoad(RegisterCommandsEvent event) {
    }
    public DimOreCore() {
        DimOre.LOGGER.info("Mod DimOre Module loading Finished!");
    }
}
