package com.algorithmlx.dimore.mixin;

import com.algorithmlx.dimore.ModKt;
import com.algorithmlx.dimore.init.resource.DimOreResourcePack;
import com.google.common.collect.Lists;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.repository.*;
import net.minecraft.world.flag.FeatureFlagSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;
import java.util.Optional;

@Mixin(PackRepository.class)
public class PackRepositoryMixin {
    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true)
    private static RepositorySource[] dimore$init(RepositorySource[] sources) {
        var asList = Lists.newArrayList(sources);
        asList.add((packConsumer) -> {
            var supp = new Pack.ResourcesSupplier() {
                @Override
                public PackResources openPrimary(PackLocationInfo location) {
                    return new DimOreResourcePack(location);
                }

                @Override
                public PackResources openFull(PackLocationInfo location, Pack.Metadata metadata) {
                    return openPrimary(location);
                }
            };

            var pack = dimore$getPack(supp);
            packConsumer.accept(pack);
        });
        return asList.toArray(new RepositorySource[0]);
    }

    @Unique
    private static Pack dimore$getPack(Pack.ResourcesSupplier supp) {
        var display = Component.literal(ModKt.ModId + "Generated");

        var location = new PackLocationInfo(
                ModKt.ModId + "_generated_resources",
                display,
                PackSource.BUILT_IN,
                Optional.empty()
        );
        var packMeta = new Pack.Metadata(
                display,
                PackCompatibility.COMPATIBLE,
                FeatureFlagSet.of(),
                List.of()
        );
        var selection = new PackSelectionConfig(true, Pack.Position.TOP, true);
        return new Pack(location, supp, packMeta, selection);
    }
}
