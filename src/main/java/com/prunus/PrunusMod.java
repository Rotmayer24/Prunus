package com.prunus;

import com.prunus.events.CommandEvents;
import com.prunus.events.PlayerEvents;
import com.prunus.events.DataEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
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
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
            com.prunus.client.KeyBindings.registerForgeEvents());
        LOGGER.info("Prunus mod loaded");
    }
}
