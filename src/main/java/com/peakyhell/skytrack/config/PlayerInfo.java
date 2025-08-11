package com.peakyhell.skytrack.config;

import com.google.gson.JsonObject;
import com.peakyhell.skytrack.SkyTrack;
import com.peakyhell.skytrack.utils.HypixelAPI;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import com.peakyhell.skytrack.utils.ScoreboardUtils;
import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlayerInfo {
    public String USERNAME;
    public String UUID;
    public ArrayList<String> PROFILES;
    public String ACTIVE_PROFILE;
    public String LOCATION;
    public Date LAST_UPDATED;

    public PlayerInfo() {
        this.USERNAME = MinecraftClient.getInstance().getSession().getUsername();
        this.UUID = MinecraftClient.getInstance().getSession().getUuidOrNull().toString();
        this.PROFILES = getPlayerProfiles(this.UUID);
        this.ACTIVE_PROFILE = "";
        this.LOCATION = getLocation();
        this.LAST_UPDATED = new Date();

        SkyTrack.SCHEDULER.scheduleRecurring(this::getLocation, 2, 10);
    }

    /**
     * Requests the profiles list of the player and creates an ArrayList with their UUIDs
     * @return An ArrayList of the UUIDs.
     */
    public ArrayList<String> getPlayerProfiles(String UUID) {
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
                this.ACTIVE_PROFILE = profile_uuid;
            }
        }

        return profiles_uuids;
    }

    public String getLocation() {
        List<String> scoreboardLines = ScoreboardUtils.getLines();
        if (scoreboardLines == null) return null;

        for (String line : scoreboardLines) {
            if (line.contains("⏣")) {
                return line;
            }
        }

        return null;
    }
}
