package com.peakyhell.skytrack;

import com.peakyhell.skytrack.config.ModConfig;
import com.peakyhell.skytrack.config.PlayerInfo;
import com.peakyhell.skytrack.config.SkyblockData;
import com.peakyhell.skytrack.render.waypoints.WaypointManager;
import com.peakyhell.skytrack.utils.scheduler.Scheduler;

import net.fabricmc.api.ClientModInitializer;

import net.hypixel.modapi.HypixelModAPI;
import org.slf4j.Logger;

public class SkyTrack implements ClientModInitializer {
    public static final String MOD_ID = "skytrack";
    public static Logger LOGGER;
    public static Scheduler SCHEDULER;
    public static HypixelModAPI HYPIXELMODAPI;
    public static PlayerInfo PLAYER_INFO;
    public static SkyblockData SKYBLOCK_DATA;
    public static WaypointManager WAYPOINT_MANAGER;

    @Override
    public void onInitializeClient() {

        ModConfig.init();

        LOGGER.info("[SkyTrack] Mod Loaded Successfully");
    }
}
