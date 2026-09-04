package com.prunus.events;

import com.prunus.PrunusMod;
import com.prunus.data.PlayerDataManager;
import com.prunus.dimension.PocketDimensionManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerEvents {
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            PlayerDataManager.getOrCreatePlayerData(player.getUUID(), player.getName().getString());
            PrunusMod.LOGGER.info("Player " + player.getName().getString() + " logged in");
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.player instanceof ServerPlayer player)) return;
        if (!PlayerDataManager.isInPocketDimension(player)) return;
        if (player.getY() >= 0) return;

        player.fallDistance = 0;
        PocketDimensionManager.teleportToPocket(player);
    }
}
