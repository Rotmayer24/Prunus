package com.prunus.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.prunus.data.PlayerDataManager;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public class MalusCommand {
    private static final ResourceKey<Registry<Level>> LEVEL_REGISTRY =
        ResourceKey.createRegistryKey(ResourceLocation.parse("dimension"));

    public static void registerCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("malus")
            .executes(context -> {
                ServerPlayer player = context.getSource().getPlayerOrException();
                handleMalusCommand(player);
                return 1;
            })
        );
    }

    private static void handleMalusCommand(ServerPlayer player) {
        if (!PlayerDataManager.isInPocketDimension(player)) {
            player.sendSystemMessage(Component.literal("You are not in a pocket dimension!").withStyle(ChatFormatting.RED));
            return;
        }

        PlayerDataManager.PlayerData data = PlayerDataManager.getPlayerData(player.getUUID());
        if (data == null || data.lastOverworldDimension == null) {
            player.sendSystemMessage(Component.literal("No saved position found!").withStyle(ChatFormatting.RED));
            return;
        }

        ResourceLocation dimLoc;
        try {
            dimLoc = ResourceLocation.parse(data.lastOverworldDimension);
        } catch (Exception e) {
            player.sendSystemMessage(Component.literal("Invalid dimension data!").withStyle(ChatFormatting.RED));
            return;
        }
        ResourceKey<Level> dimKey = ResourceKey.create(LEVEL_REGISTRY, dimLoc);
        var targetLevel = player.getServer().getLevel(dimKey);
        if (targetLevel == null) {
            targetLevel = player.getServer().overworld();
        }

        player.teleportTo(targetLevel, data.lastOverworldX, data.lastOverworldY, data.lastOverworldZ, player.getYRot(), player.getXRot());
        player.sendSystemMessage(Component.literal("Teleported back!").withStyle(ChatFormatting.GREEN));
    }
}
