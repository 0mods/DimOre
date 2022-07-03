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

import com.algorithmlx.dimore.setup.DimOreReg;
import com.algorithmlx.dimore.world.gen.feature.DimOreFeature;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber
public class OreConfigured {
    public static Holder<ConfiguredFeature<OreConfiguration,?>> END_ORES;
    public static Holder<ConfiguredFeature<OreConfiguration,?>> NETHER_ORES;

    public static void register() {
        END_ORES = FeatureUtils.register("crystalline_cluster",
               Feature.ORE,
               new OreConfiguration(List.of(
                       OreConfiguration.target(
                            DimOreFeature.BlockFiller.END_STONE,
                            DimOreReg.END_COPPER_ORE.get().defaultBlockState()
                       ),
                       OreConfiguration.target(
                            DimOreFeature.BlockFiller.END_STONE,
                            DimOreReg.END_COAL_ORE.get().defaultBlockState()
                       ),
                       OreConfiguration.target(
                            DimOreFeature.BlockFiller.END_STONE,
                            DimOreReg.END_DIAMOND_ORE.get().defaultBlockState()
                       ),
                       OreConfiguration.target(
                            DimOreFeature.BlockFiller.END_STONE,
                            DimOreReg.END_EMERALD_ORE.get().defaultBlockState()
                       ),
                       OreConfiguration.target(
                            DimOreFeature.BlockFiller.END_STONE,
                            DimOreReg.END_GOLDEN_ORE.get().defaultBlockState()
                       ),
                       OreConfiguration.target(
                            DimOreFeature.BlockFiller.END_STONE,
                            DimOreReg.END_IRON_ORE.get().defaultBlockState()
                       ),
                       OreConfiguration.target(
                            DimOreFeature.BlockFiller.END_STONE,
                            DimOreReg.END_LAPIS_LAZULI_ORE.get().defaultBlockState()
                       ),
                       OreConfiguration.target(
                            DimOreFeature.BlockFiller.END_STONE,
                            DimOreReg.END_QUARTZ_ORE.get().defaultBlockState()
                       ),
                       OreConfiguration.target(
                            DimOreFeature.BlockFiller.END_STONE,
                            DimOreReg.END_REDSTONE_ORE.get().defaultBlockState()
                       ),
                       OreConfiguration.target(
                            DimOreFeature.BlockFiller.END_STONE,
                            DimOreReg.END_TIN_ORE.get().defaultBlockState()
                       )
               ), 4)
       );

       NETHER_ORES = FeatureUtils.register(
               "crystalline_cluster_dim",
               Feature.ORE,
               new OreConfiguration(
                       List.of(
                        OreConfiguration.target(
                            DimOreFeature.BlockFiller.END_STONE,
                            DimOreReg.NETHER_COPPER_ORE.get().defaultBlockState()
                       ),
                       OreConfiguration.target(
                            DimOreFeature.BlockFiller.END_STONE,
                            DimOreReg.NETHER_COAL_ORE.get().defaultBlockState()
                       ),
                       OreConfiguration.target(
                            DimOreFeature.BlockFiller.END_STONE,
                            DimOreReg.NETHER_DIAMOND_ORE.get().defaultBlockState()
                       ),
                       OreConfiguration.target(
                            DimOreFeature.BlockFiller.END_STONE,
                            DimOreReg.NETHER_EMERALD_ORE.get().defaultBlockState()
                       ), 
                       OreConfiguration.target(
                            DimOreFeature.BlockFiller.END_STONE,
                            DimOreReg.NETHER_IRON_ORE.get().defaultBlockState()
                       ),
                       OreConfiguration.target(
                            DimOreFeature.BlockFiller.END_STONE,
                            DimOreReg.NETHER_LAPIS_LAZULI_ORE.get().defaultBlockState()
                       ),
                       OreConfiguration.target(
                            DimOreFeature.BlockFiller.END_STONE,
                            DimOreReg.NETHER_REDSTONE_ORE.get().defaultBlockState()
                       ),
                       OreConfiguration.target(
                            DimOreFeature.BlockFiller.END_STONE,
                            DimOreReg.NETHER_TIN_ORE.get().defaultBlockState()
                       )
                    ), 4)
       );
    }
}
