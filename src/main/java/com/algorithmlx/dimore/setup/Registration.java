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
import com.algorithmlx.dimore.block.ModOreBase;
import com.algorithmlx.dimore.block.OreBase;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Registration {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DimOre.ModId);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, DimOre.ModId);

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<OreBase> NETHER_COAL_ORE = BLOCKS.register("nether_coal_ore", OreBase::new);
    public static final RegistryObject<OreBase> NETHER_IRON_ORE = BLOCKS.register("nether_iron_ore", OreBase::new);
    public static final RegistryObject<OreBase> NETHER_REDSTONE_ORE = BLOCKS.register("nether_redstone_ore", OreBase::new);
    public static final RegistryObject<OreBase> NETHER_DIAMOND_ORE = BLOCKS.register("nether_diamond_ore", OreBase::new);
    public static final RegistryObject<OreBase> NETHER_EMERALD_ORE = BLOCKS.register("nether_emerald_ore", OreBase::new);

    public static final RegistryObject<OreBase> END_COAL_ORE = BLOCKS.register("end_coal_ore", OreBase::new);
    public static final RegistryObject<OreBase> END_IRON_ORE = BLOCKS.register("end_iron_ore", OreBase::new);
    public static final RegistryObject<OreBase> END_GOLDEN_ORE = BLOCKS.register("end_golden_ore", OreBase::new);
    public static final RegistryObject<OreBase> END_REDSTONE_ORE = BLOCKS.register("end_redstone_ore", OreBase::new);
    public static final RegistryObject<OreBase> END_DIAMOND_ORE = BLOCKS.register("end_diamond_ore", OreBase::new);
    public static final RegistryObject<OreBase> END_EMERALD_ORE = BLOCKS.register("end_emerald_ore", OreBase::new);

    public static final RegistryObject<Item> NETHER_COAL_ORE_ITEM = ITEMS.register("nether_coal_ore", ()-> new BlockItem(NETHER_COAL_ORE.get(), new Item.Properties().tab(DimOreCore.DimOreNetherTab).fireResistant()));
    public static final RegistryObject<Item> NETHER_IRON_ORE_ITEM = ITEMS.register("nether_iron_ore", ()-> new BlockItem(NETHER_IRON_ORE.get(), new Item.Properties().tab(DimOreCore.DimOreNetherTab).fireResistant()));
    public static final RegistryObject<Item> NETHER_REDSTONE_ORE_ITEM = ITEMS.register("nether_redstone_ore", ()-> new BlockItem(NETHER_REDSTONE_ORE.get(), new Item.Properties().tab(DimOreCore.DimOreNetherTab).fireResistant()));
    public static final RegistryObject<Item> NETHER_DIAMOND_ORE_ITEM = ITEMS.register("nether_diamond_ore", ()-> new BlockItem(NETHER_DIAMOND_ORE.get(), new Item.Properties().tab(DimOreCore.DimOreNetherTab).fireResistant()));
    public static final RegistryObject<Item> NETHER_EMERALD_ORE_ITEM = ITEMS.register("nether_emerald_ore", ()-> new BlockItem(NETHER_EMERALD_ORE.get(), new Item.Properties().tab(DimOreCore.DimOreNetherTab).fireResistant()));

    public static final RegistryObject<Item> END_COAL_ORE_ITEM = ITEMS.register("end_coal_ore", ()-> new BlockItem(END_COAL_ORE.get(), new Item.Properties().tab(DimOreCore.DimOreEndTab)));
    public static final RegistryObject<Item> END_IRON_ORE_ITEM = ITEMS.register("end_iron_ore", ()-> new BlockItem(END_IRON_ORE.get(), new Item.Properties().tab(DimOreCore.DimOreEndTab)));
    public static final RegistryObject<Item> END_GOLDEN_ORE_ITEM = ITEMS.register("end_golden_ore", ()-> new BlockItem(END_GOLDEN_ORE.get(), new Item.Properties().tab(DimOreCore.DimOreEndTab)));
    public static final RegistryObject<Item> END_REDSTONE_ORE_ITEM = ITEMS.register("end_redstone_ore", ()-> new BlockItem(END_REDSTONE_ORE.get(), new Item.Properties().tab(DimOreCore.DimOreEndTab)));
    public static final RegistryObject<Item> END_DIAMOND_ORE_ITEM = ITEMS.register("end_diamond_ore", ()-> new BlockItem(END_DIAMOND_ORE.get(), new Item.Properties().tab(DimOreCore.DimOreEndTab)));
    public static final RegistryObject<Item> END_EMERALD_ORE_ITEM = ITEMS.register("end_emerald_ore", ()-> new BlockItem(END_EMERALD_ORE.get(), new Item.Properties().tab(DimOreCore.DimOreEndTab)));

    //integration
    
    public static final RegistryObject<ModOreBase> NETHER_COPPER_ORE = DimOreConfig.allowIntegration.get().equals(true) && ModList.get().isLoaded("thermal_foundation") || ModList.get().isLoaded("mekanism") || ModList.get().isLoaded("immersiveengineering") ? BLOCKS.register("nether_copper_ore", ModOreBase::new) : null;
    public static final RegistryObject<ModOreBase> NETHER_TIN_ORE = DimOreConfig.allowIntegration.get().equals(true) && ModList.get().isLoaded("thermal_foundation") || ModList.get().isLoaded("mekanism") || ModList.get().isLoaded("immersiveengineering") ? BLOCKS.register("nether_tin_ore", ModOreBase::new) : null;

    public static final RegistryObject<ModOreBase> END_COPPER_ORE = DimOreConfig.allowIntegration.get().equals(true) && ModList.get().isLoaded("thermal_foundation") || ModList.get().isLoaded("mekanism") || ModList.get().isLoaded("immersiveengineering") ? BLOCKS.register("end_copper_ore", ModOreBase::new) : null;
    public static final RegistryObject<ModOreBase> END_TIN_ORE = DimOreConfig.allowIntegration.get().equals(true) && ModList.get().isLoaded("thermal_foundation") || ModList.get().isLoaded("mekanism") || ModList.get().isLoaded("immersiveengineering") ? BLOCKS.register("end_tin_ore", ModOreBase::new) : null;

    public static final RegistryObject<Item> NETHER_COPPER_ORE_ITEM = DimOreConfig.allowIntegration.get().equals(true) && ModList.get().isLoaded("thermal_foundation") || ModList.get().isLoaded("mekanism") || ModList.get().isLoaded("immersiveengineering") ? ITEMS.register("nether_copper_ore", ()-> { assert NETHER_COPPER_ORE != null;   return new BlockItem(NETHER_COPPER_ORE.get(), new Item.Properties().tab(DimOreCore.DimOreNetherTab));  }) : null;
    public static final RegistryObject<Item> NETHER_TIN_ORE_ITEM = DimOreConfig.allowIntegration.get().equals(true) && ModList.get().isLoaded("thermal_foundation") || ModList.get().isLoaded("mekanism") || ModList.get().isLoaded("immersiveengineering") ? ITEMS.register("nether_tin_ore", ()-> { assert NETHER_TIN_ORE != null;  return new BlockItem(NETHER_TIN_ORE.get(), new Item.Properties().fireResistant().tab(DimOreCore.DimOreNetherTab));  }) : null;

    public static final RegistryObject<Item> END_COPPER_ORE_ITEM = DimOreConfig.allowIntegration.get().equals(true) && ModList.get().isLoaded("thermal_foundation") || ModList.get().isLoaded("mekanism") || ModList.get().isLoaded("immersiveengineering") ? ITEMS.register("end_copper_ore", ()-> { assert END_COPPER_ORE != null;  return new BlockItem(END_COPPER_ORE.get(), new Item.Properties().tab(DimOreCore.DimOreEndTab));}) : null;
    public static final RegistryObject<Item> END_TIN_ORE_ITEM = DimOreConfig.allowIntegration.get().equals(true) && ModList.get().isLoaded("thermal_foundation") || ModList.get().isLoaded("mekanism") || ModList.get().isLoaded("immersiveengineering") ? ITEMS.register("end_tin_ore", ()-> { assert END_TIN_ORE != null;  return new BlockItem(END_TIN_ORE.get(), new Item.Properties().tab(DimOreCore.DimOreEndTab));  }) : null;
}
