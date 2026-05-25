package com.algorithmlx.dimore.mixin;

import com.algorithmlx.dimore.ModKt;
import com.algorithmlx.dimore.init.resource.DimOreResourcePack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackCompatibility;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.flag.FeatureFlagSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mixin(PackRepository.class)
public class PackRepositoryMixin {
    @ModifyVariable(method = "discoverAvailable", at = @At("RETURN"), name = "discovered")
    private Map<String, Pack> externalPack(Map<String, Pack> discovered) {
        var mutable = new HashMap<>(discovered);

        var locationInfo = new PackLocationInfo(
                ModKt.ModId + "_generated_resources",
                Component.literal(ModKt.ModId + " generated"),
                PackSource.BUILT_IN,
                Optional.empty()
        );

        Pack.ResourcesSupplier resolver = new Pack.ResourcesSupplier() {
            @Override
            public PackResources openPrimary(PackLocationInfo location) {
                return new DimOreResourcePack(location);
            }

            @Override
            public PackResources openFull(PackLocationInfo location, Pack.Metadata metadata) {
                return openPrimary(location);
            }
        };

        var meta = new Pack.Metadata(
                Component.literal(ModKt.ModId + " generated"),
                PackCompatibility.COMPATIBLE,
                FeatureFlagSet.of(),
                List.of()
        );

        var selectionConfig = new PackSelectionConfig(true, Pack.Position.TOP, false);
        var customPack = new Pack(locationInfo, resolver, meta, selectionConfig);

        mutable.put(locationInfo.id(), customPack);

        return discovered;
    }
}
