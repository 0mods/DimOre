package com.algorithmlx.dimore.mixin;

import com.algorithmlx.dimore.init.Registry;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.ReloadableServerRegistries;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ReloadableServerRegistries.Holder.class)
public abstract class LootDataManagerMixin {
    @Shadow
    public abstract HolderLookup.Provider lookup();

    @Inject(method = "getLootTable", at = @At("HEAD"), cancellable = true)
    private void injectLootTable(ResourceKey<LootTable> id, CallbackInfoReturnable<LootTable> cir) {
        var table = Registry.getLoot(id, this.lookup());
        if (table != null)
            cir.setReturnValue(table);
    }
}
