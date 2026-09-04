package com.prunus.data;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerDataManager {
    private static final ResourceLocation POCKET_DIMENSION = new ResourceLocation("prunus", "pocket");
    private static final Map<UUID, PlayerData> playerDataMap = new ConcurrentHashMap<>();

    public static class PlayerData {
        public UUID playerUUID;
        public String playerName;
        public double lastOverworldX;
        public double lastOverworldY;
        public double lastOverworldZ;
        public String lastOverworldDimension;

        public PlayerData(UUID uuid, String name) {
            this.playerUUID = uuid;
            this.playerName = name;
        }
    }

    public static PlayerData getOrCreatePlayerData(UUID uuid, String name) {
        return playerDataMap.computeIfAbsent(uuid, k -> new PlayerData(uuid, name));
    }

    public static PlayerData getPlayerData(UUID uuid) {
        return playerDataMap.get(uuid);
    }

    public static void removePlayerData(UUID uuid) {
        playerDataMap.remove(uuid);
    }

    public static void saveExitPoint(ServerPlayer player) {
        PlayerData data = getOrCreatePlayerData(player.getUUID(), player.getName().getString());
        data.lastOverworldX = player.getX();
        data.lastOverworldY = player.getY();
        data.lastOverworldZ = player.getZ();
        data.lastOverworldDimension = player.level().dimension().location().toString();
    }

    public static boolean isInPocketDimension(Player player) {
        return player.level().dimension().location().equals(POCKET_DIMENSION);
    }
}
