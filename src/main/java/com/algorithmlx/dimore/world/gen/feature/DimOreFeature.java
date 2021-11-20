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
package com.algorithmlx.dimore.world.gen.feature;

import com.algorithmlx.dimore.DimOre;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;

public class DimOreFeature implements IFeatureConfig {
    public static final Codec<DimOreFeature> CODEC = RecordCodecBuilder.create((p_236568_0_)
            -> p_236568_0_.group(RuleTest.CODEC.fieldOf("target").forGetter((p_236570_0_)
            -> p_236570_0_.target), BlockState.CODEC.fieldOf("state").forGetter((p_236569_0_)
            -> p_236569_0_.state), Codec.intRange(0, 64).fieldOf("size").forGetter((p_236567_0_)
            -> p_236567_0_.size)).apply(p_236568_0_, DimOreFeature::new)
    );
    public final RuleTest target;
    public final int size;
    public final BlockState state;

    public DimOreFeature(RuleTest test, BlockState state, int size) {
        this.size = size;
        this.state = state;
        this.target = test;
        DimOre.LOGGER.debug("DimOreFeature is Loaded!");
    }

    public static final class BlockFiller {
        public static final RuleTest END_STONE = new BlockMatchRuleTest(Blocks.END_STONE);
    }
}