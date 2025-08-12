package com.peakyhell.skytrack.config;

import com.peakyhell.skytrack.utils.HypixelAPI;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;
import java.util.Date;

public class PlayerInfo {
    private String USERNAME;
    private String UUID;
    private ArrayList<String> PROFILES;
    private String ACTIVE_PROFILE;
    private String LOCATION;
    private Date LAST_UPDATED;

    public PlayerInfo() {
        this.USERNAME = MinecraftClient.getInstance().getSession().getUsername();
        this.UUID = MinecraftClient.getInstance().getSession().getUuidOrNull().toString();
        this.PROFILES = getPlayerProfiles(this.UUID);
        this.ACTIVE_PROFILE = "";
        this.LOCATION = "";
        this.LAST_UPDATED = new Date();
    }

    // Getters
    public String getUSERNAME() { return this.USERNAME; }

    public String getUUID() { return this.UUID; }

    public ArrayList<String> getPROFILES() { return this.PROFILES; }

    public String getACTIVE_PROFILE() { return this.ACTIVE_PROFILE; }

    public String getLOCATION() { return this.LOCATION; }

    public Date getLAST_UPDATED() { return this.LAST_UPDATED; }

    // Setters
    public void setUSERNAME(String USERNAME) { this.USERNAME = USERNAME; }

    public void setUUID(String UUID) { this.UUID = UUID; }

    public void setPROFILES(ArrayList<String> PROFILES) { this.PROFILES = PROFILES; }

    public void setACTIVE_PROFILE(String ACTIVE_PROFILE) { this.ACTIVE_PROFILE = ACTIVE_PROFILE; }

    public void setLOCATION(String LOCATION) { this.LOCATION = LOCATION; }

    public void setLAST_UPDATED(Date LAST_UPDATED) { this.LAST_UPDATED = LAST_UPDATED; }

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
                this.setACTIVE_PROFILE(profile_uuid);
            }
        }

        return profiles_uuids;
    }
}
