package com.prunus.client;

import com.prunus.PrunusMod;
import com.prunus.data.PlayerDataManager;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = PrunusMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class KeyBindings {
    private static KeyMapping togglePocketKey;

    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        togglePocketKey = new KeyMapping(
            "Toggle Prunus",
            GLFW.GLFW_KEY_G,
            "Prunus"
        );
        event.register(togglePocketKey);
    }

    @Mod.EventBusSubscriber(modid = PrunusMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class TickHandler {
        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            if (event.phase != TickEvent.Phase.END) return;

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
