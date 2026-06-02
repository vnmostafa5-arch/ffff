package net.example.timer;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimerMod implements ModInitializer {
    public static final String MOD_ID = "worldtimer";
    public static final Logger LOGGER = LoggerFactory.LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("World Timer Mod has been successfully initialized!");
    }
}
