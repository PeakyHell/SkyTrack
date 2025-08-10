package com.peakyhell.skytrack;

import com.peakyhell.skytrack.commands.CommandsConfig;

import com.peakyhell.skytrack.mining.EfficientMinerOverlay;
import com.peakyhell.skytrack.render.waypoints.Waypoint;
import com.peakyhell.skytrack.render.waypoints.WaypointManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
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

        // Get waypoints every tick
        WaypointManager waypointManager = new WaypointManager();
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            waypointManager.clear();
            EfficientMinerOverlay.getBLocksAroundPlayer(waypointManager);
        });

        // Render waypoints
        WorldRenderEvents.AFTER_TRANSLUCENT.register(context -> {
            for (Waypoint wp : waypointManager.getWaypoints()) {
                wp.renderFilled(context);
            }
        });

    }
}
