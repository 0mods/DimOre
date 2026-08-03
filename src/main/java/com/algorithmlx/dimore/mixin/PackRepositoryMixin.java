package com.algorithmlx.dimore.mixin;

//? if fabric {
import com.algorithmlx.dimore.init.resource.DimOrePackSource;
import com.google.common.collect.Lists;
import net.minecraft.server.packs.repository.RepositorySource;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
//?}
import net.minecraft.server.packs.repository.PackRepository;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PackRepository.class)
public class PackRepositoryMixin {
    //? if fabric {
    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true)
    private static RepositorySource[] dimore$init(RepositorySource[] sources) {
        var result = Lists.newArrayList(sources);
        result.add(DimOrePackSource.INSTANCE);
        return result.toArray(new RepositorySource[0]);
    }
    //?}
}
