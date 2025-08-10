package com.peakyhell.skytrack;

import com.peakyhell.skytrack.commands.CommandsConfig;

import com.peakyhell.skytrack.mining.EfficientMinerOverlay;
import com.peakyhell.skytrack.render.waypoints.Waypoint;
import com.peakyhell.skytrack.render.waypoints.WaypointManager;
import com.peakyhell.skytrack.utils.Scheduler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkyTrack implements ClientModInitializer {
    public static final String MOD_ID = "skytrack";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final WaypointManager WAYPOINT_MANAGER = new WaypointManager();
    public static final Scheduler SCHEDULER = new Scheduler();

    @Override
    public void onInitializeClient() {

        // Register commands
        CommandsConfig.init();

        // Updates EfficientMinerOverlay every tick
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            SCHEDULER.tick();
            WAYPOINT_MANAGER.clear();
        });

        // Render waypoints
        WorldRenderEvents.AFTER_TRANSLUCENT.register(context -> {
            for (Waypoint wp : WAYPOINT_MANAGER.getWaypoints()) {
                wp.renderFilled(context);
            }
        });

        LOGGER.info("[SkyTrack] Mod Loaded Successfully");
    }
}
