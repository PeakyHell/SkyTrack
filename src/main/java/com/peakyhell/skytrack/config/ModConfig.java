package com.peakyhell.skytrack.config;

import com.peakyhell.skytrack.SkyTrack;
import com.peakyhell.skytrack.commands.ConfigScreen;
import com.peakyhell.skytrack.commands.Hello;
import com.peakyhell.skytrack.mining.EfficientMinerOverlay;
import com.peakyhell.skytrack.render.waypoints.Waypoint;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;

public class ModConfig {

    public static void init() {

        // Initialize Commands
        Hello.init();
        ConfigScreen.init();

        // Initialize Features
        EfficientMinerOverlay.init();

        // Starts Scheduler
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            SkyTrack.SCHEDULER.tick();
        });

        // Render waypoints
        WorldRenderEvents.AFTER_TRANSLUCENT.register(context -> {
            for (Waypoint wp : SkyTrack.WAYPOINT_MANAGER.getWaypoints()) {
                wp.renderFilled(context);
            }
        });
    }
}
