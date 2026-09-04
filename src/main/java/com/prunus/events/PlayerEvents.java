package com.prunus.events;

import com.prunus.PrunusMod;
import com.prunus.data.PlayerDataManager;
import com.prunus.dimension.PocketDimensionManager;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class PlayerEvents {
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            PlayerDataManager.getOrCreatePlayerData(player.getUUID(), player.getName().getString());
            PrunusMod.LOGGER.info("Player " + player.getName().getString() + " logged in");
        }
    }

    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            PlayerDataManager.removePlayerData(player.getUUID());
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (!PlayerDataManager.isInPocketDimension(player)) return;
        if (player.getY() >= 0) return;

        player.fallDistance = 0;
        PocketDimensionManager.teleportToPocket(player);
    }
}
