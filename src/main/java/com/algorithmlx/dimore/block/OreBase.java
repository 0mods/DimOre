package com.algorithmlx.dimore.block;

import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.material.Material;

public class OreBase extends OreBlock {
    public OreBase() {
        super(Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3f, 3f));
    }
}
