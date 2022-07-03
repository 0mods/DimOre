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

import java.util.List;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class OrePlacement {
   public static Holder<PlacedFeature> END_ORES;
   public static Holder<PlacedFeature> NETHER_ORES;

   public static void register() {
        END_ORES = PlacementUtils.register(
               "end_ore_list",
               OreConfigured.END_ORES,
               orePlacement(1,
                       HeightRangePlacement.uniform(
                               VerticalAnchor.absolute(0),
                               VerticalAnchor.absolute(256)
                       )
               )
       );

       NETHER_ORES = PlacementUtils.register(
               "crystallite_dim",
               OreConfigured.NETHER_ORES,
               orePlacement(1,
                       HeightRangePlacement.uniform(
                               VerticalAnchor.absolute(0),
                               VerticalAnchor.absolute(256)
                       )
               )
       );
   }
   @SubscribeEvent
   public static void biomeModification(final BiomeLoadingEvent loadingEvent) {
       loadingEvent.getGeneration().getFeatures(Decoration.UNDERGROUND_ORES).add(NETHER_ORES);
       loadingEvent.getGeneration().getFeatures(Decoration.UNDERGROUND_ORES).add(END_ORES);
   }

   private static List<PlacementModifier> orePlace(PlacementModifier placementModifier, PlacementModifier modifier) {
       return List.of(placementModifier, InSquarePlacement.spread(), modifier, BiomeFilter.biome());
   }

   private static List<PlacementModifier> orePlacement(int size, PlacementModifier placementModifier) {
       return orePlace(CountPlacement.of(size), placementModifier);
   }
}
