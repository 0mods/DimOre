package com.algorithmlx.dimore.mixin;

import com.algorithmlx.dimore.ModKt;
import com.algorithmlx.dimore.init.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.ReloadableServerRegistries;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ReloadableServerRegistries.Holder.class)
public class LootDataManagerMixin {
    @Inject(method = "getLootTable", at = @At("HEAD"), cancellable = true)
    private void injectLootTable(ResourceKey<LootTable> id, CallbackInfoReturnable<LootTable> cir) {
        var key =
                //$ if >=1.21.11 'id.identifier();' else 'id.location();'
                id.identifier();
        if (key.getNamespace().equals(ModKt.ModId)) {
            var table = Registry.spawnCustomLoot().get(id);
            if (table != null)
                cir.setReturnValue(table);
        }
    }
}
