package com.algorithmlx.dimore.setup;

import com.algorithmlx.dimore.DimOre;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent.LoggedInEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.VersionChecker;
import net.minecraftforge.fml.VersionChecker.CheckResult;
import net.minecraftforge.fml.VersionChecker.Status;

public class EventHandler {
    public static final EventHandler INSTANCE = new EventHandler();

    @SubscribeEvent
    public void handlePlayerLoggedInEvent(LoggedInEvent event) {
        if(DimOreConfig.checkUpdates.get().equals(true)) {
            CheckResult versionRAW = VersionChecker.getResult(ModList.get().getModFileById(DimOre.ModId).getMods().get(0));
            Status result = versionRAW.status;

            if (!(result.equals(Status.UP_TO_DATE) || result.equals(Status.PENDING) || result.equals(Status.AHEAD))) {
                event.getPlayer().displayClientMessage(new TranslationTextComponent("update." + DimOre.ModId + ".update_message", versionRAW.target), false);
                event.getPlayer().displayClientMessage(new TranslationTextComponent("update." + DimOre.ModId + ".changelog"), false);

                String changes = versionRAW.changes.get(versionRAW.target);
                if (changes != null) {
                    String[] changesFormat = changes.split("\n");

                    for (String change : changesFormat) {
                        event.getPlayer().displayClientMessage(new TranslationTextComponent("update." + DimOre.ModId + ".change", change), false);
                    }
                    if (versionRAW.changes.size() > 1) {
                        event.getPlayer().displayClientMessage(new TranslationTextComponent("update." + DimOre.ModId + ".update_message2"), false);
                    }
                }
            }
            if (result.equals(Status.AHEAD)) {
                event.getPlayer().displayClientMessage(new TranslationTextComponent("update." + DimOre.ModId + ".not_released"), false);
            }
        }
    }
}
