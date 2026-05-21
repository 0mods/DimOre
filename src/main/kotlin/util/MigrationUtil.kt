package com.algorithmlx.dimore.util

//$ if >1.21.10 'import net.minecraft.resources.Identifier' else 'import net.minecraft.resources.ResourceLocation'
import net.minecraft.resources.Identifier

typealias ResLoc =
    //$ if >1.21.10 'Identifier' else 'ResourceLocation'
    Identifier
