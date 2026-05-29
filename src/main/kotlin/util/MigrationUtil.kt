package com.algorithmlx.dimore.util

//$ if fabric 'import net.fabricmc.loader.api.FabricLoader' else 'import net.neoforged.fml.ModList'
import net.fabricmc.loader.api.FabricLoader

//$ if >1.21.10 'import net.minecraft.resources.Identifier' else 'import net.minecraft.resources.ResourceLocation'
import net.minecraft.resources.Identifier

typealias ResLoc =
    //$ if >1.21.10 'Identifier' else 'ResourceLocation'
    Identifier

fun isModLoaded(modId: String) =
    //$ if fabric 'FabricLoader.getInstance().isModLoaded(modId)' else 'ModList.get().isLoaded(modId)'
    FabricLoader.getInstance().isModLoaded(modId)
