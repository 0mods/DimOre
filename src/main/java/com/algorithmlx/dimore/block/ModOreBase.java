package com.algorithmlx.dimore.block;

import net.minecraft.block.OreBlock;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class ModOreBase extends OreBlock {
    public ModOreBase() {
        super(Properties.of(Material.STONE).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().strength(2f, 2f));
    }
}
