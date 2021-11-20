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
package com.algorithmlx.dimore.world.gen;

import com.algorithmlx.dimore.DimOre;
import com.algorithmlx.dimore.setup.DimOreConfig;
import com.algorithmlx.dimore.setup.Registration;
import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Objects;

import static com.algorithmlx.dimore.DimOre.ModId;


@Mod.EventBusSubscriber
public class NetherOreGen {
    private static final ArrayList<ConfiguredFeature<?, ?>> netherOres = new ArrayList<>();

    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String name, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, ModId + ":" + name, configuredFeature);
    }

    public static void registerOres() {
        if(DimOreConfig.genNetherOres.get().equals(true)) {
            if (DimOreConfig.genNetherCoalOre.get().equals(true)) {
                netherOres.add(genNetherOre(Registration.NETHER_COAL_ORE.get(), 0, 128, 7, 40));
            }
            if (DimOreConfig.genNetherIronOre.get().equals(true)) {
                netherOres.add(genNetherOre(Registration.NETHER_IRON_ORE.get(), 0, 128, 7, 35));
            }
            if (DimOreConfig.genNetherRedstoneOre.get().equals(true)) {
                netherOres.add(genNetherOre(Registration.NETHER_REDSTONE_ORE.get(), 0, 128, 7, 20));
            }
            if (DimOreConfig.genNetherDiamondOre.get().equals(true)) {
                netherOres.add(genNetherOre(Registration.NETHER_DIAMOND_ORE.get(), 0, 128, 7, 8));
            }
            if (DimOreConfig.getNetherEmeraldOre.get().equals(true)) {
                netherOres.add(genNetherOre(Registration.NETHER_EMERALD_ORE.get(), 0, 128, 7, 5));
            }
        }
        if(DimOreConfig.genNetherOres.get().equals(true) && DimOreConfig.allowIntegration.get().equals(true) && ModList.get().isLoaded("thermal_foundation") || ModList.get().isLoaded("mekanism") || ModList.get().isLoaded("immersiveengineering")) {
            if (DimOreConfig.genNetherCopperOre.get().equals(true)) {
                netherOres.add(genNetherOre(Registration.NETHER_COPPER_ORE.get(), 0, 128, 10, 25));
            }
            if (DimOreConfig.genNetherTinOre.get().equals(true)) {
                netherOres.add(genNetherOre(Registration.NETHER_TIN_ORE.get(), 0, 128, 10, 25));
            }
        }
    }

    public static ConfiguredFeature<?, ?> genNetherOre(Block block, int minHeight, int maxHeight, int veinSize, int amountOfVeinInChunk) {
        DimOre.LOGGER.info("Registered nether ore: " + Objects.requireNonNull(block.getRegistryName()).getPath());
        return register(Objects.requireNonNull(block.getRegistryName()).getPath(), Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, block.defaultBlockState(), veinSize)).decorated(Placement.RANGE.configured(new TopSolidRangeConfig(minHeight, 0, maxHeight))).squared().count(amountOfVeinInChunk));
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void generateOres(BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder generation = event.getGeneration();
        if (event.getCategory().equals(Biome.Category.NETHER)) {
            for (ConfiguredFeature<?, ?> ore : netherOres) {
                if (ore != null) generation.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ore);
            }
        }
    }
}
