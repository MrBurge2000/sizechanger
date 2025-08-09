package com.example.sizechanger;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SizechangerMod implements ModInitializer {
    public static final String MOD_ID = "sizechanger";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Sizechanger mod");
    }
}
