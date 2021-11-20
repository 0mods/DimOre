package com.algorithmlx.dimore.block;

import net.minecraft.block.OreBlock;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class OreBase extends OreBlock {
    public OreBase() {
        super(Properties.of(Material.STONE).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().strength(2f, 2f));
    }
}
