package com.algorithmlx.dimore.mixin;

import com.algorithmlx.dimore.init.resource.DimOreResourcePack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.RepositorySource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(PackRepository.class)
public class PackRepositoryMixin {
    @ModifyVariable(
            at = @At("HEAD"),
            method = "<init>*",
            argsOnly = true
    )
    private static RepositorySource[] onInit(RepositorySource[] value) {
        final var l = new ArrayList<>(Arrays.asList(value));
        l.add(src -> {
            final var pack = DimOreResourcePack.getAsPack();
            if (pack != null) src.accept(pack);
        });
        return l.toArray(new RepositorySource[0]);
    }
}
