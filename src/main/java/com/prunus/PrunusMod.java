package com.prunus;

import com.prunus.events.CommandEvents;
import com.prunus.events.PlayerEvents;
import com.prunus.events.DataEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

@Mod(PrunusMod.MODID)
public class PrunusMod {
    public static final String MODID = "prunus";
    public static final Logger LOGGER = LogUtils.getLogger();

    public PrunusMod() {
        MinecraftForge.EVENT_BUS.register(CommandEvents.class);
        MinecraftForge.EVENT_BUS.register(PlayerEvents.class);
        MinecraftForge.EVENT_BUS.register(DataEvents.class);
        LOGGER.info("Prunus mod loaded");
    }
}
