package com.peakyhell.skytrack;

import com.peakyhell.skytrack.commands.CommandsConfig;

import com.peakyhell.skytrack.render.Waypoint;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkyTrack implements ClientModInitializer {
    public static final String MOD_ID = "skytrack";

    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        LOGGER.info("[SkyTrack] Mod Loaded Successfully");

        // Register commands
        CommandsConfig.init();

        Waypoint wpOutlined = new Waypoint(100, 100, 100);
        Waypoint wpFilled = new Waypoint(105, 105, 105);

        WorldRenderEvents.AFTER_TRANSLUCENT.register(context -> {
            wpOutlined.renderOutlined(context);
            wpFilled.renderFilled(context);
        });

    }
}
