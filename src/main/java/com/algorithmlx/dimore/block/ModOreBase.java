package com.algorithmlx.dimore.block;

import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.material.Material;

public class ModOreBase extends OreBlock {
    public ModOreBase() {
        super(Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2f, 2f));
    }
}
