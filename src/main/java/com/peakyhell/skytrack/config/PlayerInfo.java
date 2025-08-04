package com.peakyhell.skytrack.config;

import com.google.gson.JsonObject;
import com.peakyhell.skytrack.utils.HypixelAPI;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;

public class PlayerInfo {
    public static final String USERNAME = MinecraftClient.getInstance().getSession().getUsername();
    public static final String UUID = MinecraftClient.getInstance().getSession().getUuidOrNull().toString();
    public static ArrayList<String> PROFILES = getPlayerProfiles();
    public static String ACTIVE_PROFILE;

    /**
     * Requests the profiles list of the player and creates an ArrayList with their UUIDs
     * @return An ArrayList of the UUIDs.
     */
    static ArrayList<String> getPlayerProfiles() {
        JsonArray data = HypixelAPI.getPlayerProfiles(UUID).get("profiles").getAsJsonArray();
        ArrayList<String> profiles_uuids = new ArrayList<>();

        for (JsonElement profile : data) {
            JsonObject profile_data = profile.getAsJsonObject();
            String profile_uuid = profile_data.get("profile_id").getAsString();

            profiles_uuids.add(profile_uuid);
            if (profile_data.get("selected").getAsBoolean()) {
                ACTIVE_PROFILE = profile_uuid;
            }
        }

        return profiles_uuids;
    }
}
