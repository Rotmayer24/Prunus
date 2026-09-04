package com.prunus.events;

import com.prunus.data.PlayerDataManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DataEvents {
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) return;
        if (!(event.getOriginal() instanceof ServerPlayer oldPlayer)) return;
        if (!(event.getEntity() instanceof ServerPlayer newPlayer)) return;

        PlayerDataManager.PlayerData oldData = PlayerDataManager.getPlayerData(oldPlayer.getUUID());
        if (oldData != null) {
            PlayerDataManager.PlayerData newData = PlayerDataManager.getOrCreatePlayerData(newPlayer.getUUID(), newPlayer.getName().getString());
            newData.lastOverworldX = oldData.lastOverworldX;
            newData.lastOverworldY = oldData.lastOverworldY;
            newData.lastOverworldZ = oldData.lastOverworldZ;
            newData.lastOverworldDimension = oldData.lastOverworldDimension;
        }
    }
}
