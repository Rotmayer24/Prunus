package com.prunus.events;

import com.prunus.data.PlayerDataManager;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class DataEvents {
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            ServerPlayer oldPlayer = (ServerPlayer) event.getOriginal();
            ServerPlayer newPlayer = (ServerPlayer) event.getEntity();

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
}
