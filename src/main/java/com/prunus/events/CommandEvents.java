package com.prunus.events;

import com.prunus.PrunusMod;
import com.prunus.commands.PrunusCommand;
import com.prunus.commands.MalusCommand;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegisterCommandsEvent;

public class CommandEvents {
    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        PrunusCommand.registerCommand(event.getDispatcher());
        MalusCommand.registerCommand(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onChat(ServerChatEvent event) {
        String msg = event.getMessage().getString().trim();
        String lower = msg.toLowerCase();
        if (lower.equals("prunus") || lower.equals("malus")) {
            event.setCanceled(true);
            try {
                CommandSourceStack source = event.getPlayer().createCommandSourceStack();
                event.getPlayer().getServer().getCommands().getDispatcher().execute(msg, source);
            } catch (Exception e) {
                PrunusMod.LOGGER.error("Command execution failed: {}", msg, e);
            }
        }
    }
}
