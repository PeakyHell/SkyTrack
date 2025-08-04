package com.peakyhell.skytrack.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HypixelAPI {
    private static final String HYPIXEL_API_URL = "https://api.hypixel.net/v2/";
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public String getRequest(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();

        HttpResponse<String>response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("HTTP Request Error: " + response.statusCode());
        }

        return response.body();
    }

    public JsonObject getCollections() {
        JsonObject collections;
        try {
            collections = JsonParser.parseString(getRequest(HYPIXEL_API_URL + "resources/skyblock/collections")).getAsJsonObject().get("collections").getAsJsonObject();
        }
        catch (Exception e) {
            return null;
        }

        JsonObject farming_collections = collections.get("FARMING").getAsJsonObject().get("items").getAsJsonObject();
        JsonObject mining_collections = collections.get("MINING").getAsJsonObject().get("items").getAsJsonObject();
        JsonObject combat_collections = collections.get("COMBAT").getAsJsonObject().get("items").getAsJsonObject();
        JsonObject foraging_collections = collections.get("FORAGING").getAsJsonObject().get("items").getAsJsonObject();
        JsonObject fishing_collections = collections.get("FISHING").getAsJsonObject().get("items").getAsJsonObject();
        JsonObject rift_collections = collections.get("rift").getAsJsonObject().get("items").getAsJsonObject();

        return collections;
    }

    public JsonObject getSkills() {
        JsonObject skills;
        try {
            skills = JsonParser.parseString(getRequest(HYPIXEL_API_URL + "resources/skyblock/skills")).getAsJsonObject().get("skills").getAsJsonObject();
        }
        catch (Exception e) {
            return null;
        }

        JsonObject farming = skills.get("FARMING").getAsJsonObject();
        JsonObject mining = skills.get("MINING").getAsJsonObject();
        JsonObject combat = skills.get("COMBAT").getAsJsonObject();
        JsonObject foraging = skills.get("FORAGING").getAsJsonObject();
        JsonObject fishing = skills.get("FISHING").getAsJsonObject();
        JsonObject enchanting = skills.get("ENCHANTING").getAsJsonObject();
        JsonObject alchemy = skills.get("ALCHEMY").getAsJsonObject();
        JsonObject carpentry = skills.get("CARPENTRY").getAsJsonObject();
        JsonObject runecrafting = skills.get("RUNECRAFTING").getAsJsonObject();
        JsonObject social = skills.get("SOCIAL").getAsJsonObject();
        JsonObject taming = skills.get("TAMING").getAsJsonObject();
        JsonObject hunting = skills.get("HUNTING").getAsJsonObject();

        return skills;
    }

    public JsonArray getItems() {
        JsonArray items;
        try {
            items = JsonParser.parseString(getRequest(HYPIXEL_API_URL + "resources/skyblock/items")).getAsJsonObject().get("items").getAsJsonArray();
        }
        catch (Exception e) {
            return null;
        }

        return items;
    }

    public JsonObject getElection() {
        JsonObject election;
        try {
            election = JsonParser.parseString(getRequest(HYPIXEL_API_URL + "resources/skyblock/election")).getAsJsonObject();
        }
        catch (Exception e) {
            return null;
        }

        return election;
    }

    public JsonObject getBingo() {
        JsonObject bingo;
        try {
            bingo = JsonParser.parseString(getRequest(HYPIXEL_API_URL + "resources/skyblock/bingo")).getAsJsonObject();
        } catch (Exception e) {
            return null;
        }

        return bingo;
    }
}
