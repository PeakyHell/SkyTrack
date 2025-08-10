package com.peakyhell.skytrack;

import com.peakyhell.skytrack.config.ModConfig;
import com.peakyhell.skytrack.render.waypoints.WaypointManager;
import com.peakyhell.skytrack.utils.Scheduler;

import net.fabricmc.api.ClientModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkyTrack implements ClientModInitializer {
    public static final String MOD_ID = "skytrack";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final WaypointManager WAYPOINT_MANAGER = new WaypointManager();
    public static final Scheduler SCHEDULER = new Scheduler();

    @Override
    public void onInitializeClient() {

        ModConfig.init();

        LOGGER.info("[SkyTrack] Mod Loaded Successfully");
    }
}
