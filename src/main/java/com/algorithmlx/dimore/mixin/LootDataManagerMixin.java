package com.algorithmlx.dimore.mixin;

import com.algorithmlx.dimore.init.Registry;
//$ if >1.21.1 'import net.minecraft.core.HolderLookup;' else 'import net.minecraft.core.HolderGetter;'
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
    //$ if >1.21.1 'public abstract HolderLookup.Provider lookup();' else 'public abstract HolderGetter.Provider lookup();'
    public abstract HolderLookup.Provider lookup();

    @Inject(method = "getLootTable", at = @At("HEAD"), cancellable = true)
    private void injectLootTable(ResourceKey<LootTable> id, CallbackInfoReturnable<LootTable> cir) {
        var table = Registry.getLoot(id, this.lookup());
        if (table != null)
            cir.setReturnValue(table);
    }
}
