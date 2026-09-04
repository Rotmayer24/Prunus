package com.prunus.client;

import com.prunus.data.PlayerDataManager;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    private static KeyMapping togglePocketKey;

    public static void registerForgeEvents() {
        NeoForge.EVENT_BUS.register(new TickHandler());
    }

    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        togglePocketKey = new KeyMapping(
            "Toggle Prunus",
            GLFW.GLFW_KEY_G,
            "Prunus"
        );
        event.register(togglePocketKey);
    }

    public static class TickHandler {
        @SubscribeEvent
        public void onClientTick(ClientTickEvent.Post event) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null || mc.level == null) return;
            if (togglePocketKey == null) return;

            while (togglePocketKey.consumeClick()) {
                if (PlayerDataManager.isInPocketDimension(mc.player)) {
                    mc.player.connection.sendUnsignedCommand("malus");
                } else {
                    mc.player.connection.sendUnsignedCommand("prunus");
                }
            }
        }
    }
}
