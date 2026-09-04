package com.prunus;

import com.prunus.events.CommandEvents;
import com.prunus.events.PlayerEvents;
import com.prunus.events.DataEvents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

@Mod(PrunusMod.MODID)
public class PrunusMod {
    public static final String MODID = "prunus";
    public static final Logger LOGGER = LogUtils.getLogger();

    public PrunusMod(IEventBus modEventBus) {
        NeoForge.EVENT_BUS.register(CommandEvents.class);
        NeoForge.EVENT_BUS.register(PlayerEvents.class);
        NeoForge.EVENT_BUS.register(DataEvents.class);
        if (FMLEnvironment.dist.isClient()) {
            modEventBus.register(com.prunus.client.KeyBindings.class);
            com.prunus.client.KeyBindings.registerForgeEvents();
        }
        LOGGER.info("Prunus mod loaded");
    }
}
