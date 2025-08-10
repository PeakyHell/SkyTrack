package com.peakyhell.skytrack.config;

import com.google.gson.JsonObject;
import com.peakyhell.skytrack.SkyTrack;
import com.peakyhell.skytrack.utils.HypixelAPI;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardDisplaySlot;
import net.minecraft.scoreboard.ScoreboardEntry;
import net.minecraft.scoreboard.ScoreboardObjective;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class PlayerInfo {
    public static final String USERNAME = MinecraftClient.getInstance().getSession().getUsername();
    public static final String UUID = MinecraftClient.getInstance().getSession().getUuidOrNull().toString();
    public static ArrayList<String> PROFILES = getPlayerProfiles();
    public static String ACTIVE_PROFILE;
    public static String Location = getLocation();
    public static Date lastUpdated = new Date();

    /**
     * Requests the profiles list of the player and creates an ArrayList with their UUIDs
     * @return An ArrayList of the UUIDs.
     */
    static ArrayList<String> getPlayerProfiles() {
        JsonObject data = HypixelAPI.getPlayerProfiles(UUID);
        if (data == null) {
            return null;
        }

        JsonArray array = data.get("profiles").getAsJsonArray();
        ArrayList<String> profiles_uuids = new ArrayList<>();

        for (JsonElement profile : array) {
            JsonObject profile_data = profile.getAsJsonObject();
            String profile_uuid = profile_data.get("profile_id").getAsString();

            profiles_uuids.add(profile_uuid);
            if (profile_data.get("selected").getAsBoolean()) {
                ACTIVE_PROFILE = profile_uuid;
            }
        }

        return profiles_uuids;
    }

    public static String getLocation() {
        if (lastUpdated.getTime() + 10000 > new Date().getTime()) return null; // If last updated less than 10 seconds ago

        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) {
            return null;
        }

        Scoreboard scoreboard = player.getScoreboard();
        ScoreboardObjective sidebar = scoreboard.getObjectiveForSlot(ScoreboardDisplaySlot.SIDEBAR);
        Collection<ScoreboardEntry> entries = scoreboard.getScoreboardEntries(sidebar);
        for (ScoreboardEntry entry : entries) {
            SkyTrack.LOGGER.info(entry.owner());
        }

        return null;
    }
}
