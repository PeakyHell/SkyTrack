package com.peakyhell.skytrack.config;

import com.peakyhell.skytrack.SkyTrack;
import com.peakyhell.skytrack.commands.ConfigScreen;
import com.peakyhell.skytrack.commands.Hello;
import com.peakyhell.skytrack.mining.EfficientMinerOverlay;
import com.peakyhell.skytrack.render.waypoints.Waypoint;
import com.peakyhell.skytrack.render.waypoints.WaypointManager;
import com.peakyhell.skytrack.utils.scheduler.Scheduler;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;

import org.slf4j.LoggerFactory;

public class ModConfig {

    public static void init() {

        SkyTrack.LOGGER = LoggerFactory.getLogger(SkyTrack.MOD_ID);
        SkyTrack.PLAYER_INFO = new PlayerInfo();
        SkyTrack.SKYBLOCK_DATA = new SkyblockData();
        SkyTrack.WAYPOINT_MANAGER = new WaypointManager();
        SkyTrack.SCHEDULER = new Scheduler();

        // Initialize Commands
        Hello.init();
        ConfigScreen.init();

        // Initialize Features
        EfficientMinerOverlay.init();

        // Starts Scheduler
        ClientTickEvents.END_CLIENT_TICK.register(client -> SkyTrack.SCHEDULER.tick());

        // Render waypoints
        WorldRenderEvents.AFTER_TRANSLUCENT.register(context -> {
            for (Waypoint wp : SkyTrack.WAYPOINT_MANAGER.getWaypoints()) {
                wp.renderFilled(context);
            }
        });
    }
}
