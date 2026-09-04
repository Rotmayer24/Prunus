package com.prunus.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.prunus.data.PlayerDataManager;
import com.prunus.dimension.PocketDimensionManager;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class PrunusCommand {
    public static void registerCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("prunus")
            .executes(context -> {
                ServerPlayer player = context.getSource().getPlayerOrException();
                handlePrunusCommand(player);
                return 1;
            })
        );
    }

    private static void handlePrunusCommand(ServerPlayer player) {
        if (PlayerDataManager.isInPocketDimension(player)) {
            player.sendSystemMessage(Component.literal("You are already in a pocket dimension!").withStyle(ChatFormatting.RED));
            return;
        }

        PlayerDataManager.saveExitPoint(player);
        if (PocketDimensionManager.teleportToPocket(player)) {
            player.sendSystemMessage(Component.literal("Teleported to your pocket dimension!").withStyle(ChatFormatting.GREEN));
        }
    }
}
