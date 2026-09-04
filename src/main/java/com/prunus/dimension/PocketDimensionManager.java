package com.prunus.dimension;

import com.prunus.PrunusMod;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import java.util.UUID;

public class PocketDimensionManager {
    private static final ResourceKey<Registry<Level>> LEVEL_REGISTRY =
        ResourceKey.createRegistryKey(ResourceLocation.parse("dimension"));

    public static final ResourceKey<Level> POCKET_KEY =
        ResourceKey.create(LEVEL_REGISTRY, ResourceLocation.fromNamespaceAndPath(PrunusMod.MODID, "pocket"));

    private static BlockPos getZoneOrigin(UUID uuid) {
        int x = (int) (uuid.getMostSignificantBits() % 10000);
        int z = (int) (uuid.getLeastSignificantBits() % 10000);
        return new BlockPos(x, 64, z);
    }

    public static boolean teleportToPocket(ServerPlayer player) {
        ServerLevel level = player.getServer().getLevel(POCKET_KEY);
        if (level == null) {
            player.sendSystemMessage(Component.literal("Pocket dimension not found!").withStyle(ChatFormatting.RED));
            return false;
        }
        BlockPos origin = getZoneOrigin(player.getUUID());
        if (level.getBlockState(origin).isAir()) {
            level.setBlockAndUpdate(origin, Blocks.BLACK_CONCRETE.defaultBlockState());
        }
        player.fallDistance = 0;
        player.teleportTo(level, origin.getX() + 0.5, origin.getY() + 1, origin.getZ() + 0.5, player.getYRot(), player.getXRot());
        return true;
    }
}
