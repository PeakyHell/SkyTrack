package com.peakyhell.skytrack.utils;

import com.peakyhell.skytrack.SkyTrack;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HypixelAPI {
    private static final String HYPIXEL_API_URL = "https://api.hypixel.net/v2";
    private static final String HYPIXEL_API_KEY = "";
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    /**
     * Makes a GET request to the given url with some basic headers.
     * @param url The url the request must be sent to
     * @return The body of the response
     * @throws Exception If the request fails or if the status code isn't 200
     */
    public static String getRequest(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("API-Key", HYPIXEL_API_KEY)
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();

        HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("HTTP Request Error: " + response.statusCode());
        }

        return response.body();
    }

    /**
     * Makes a GET request to the Hypixel API with the given endpoint.
     * @param endpoint The endpoint the request must be sent to
     * @param error_message The error message that must be logged if an Exception is thrown
     * @return - A JSON object containing the requested data
     *         - null if the request or the parsing failed.
     */
    public static JsonObject HypixelAPIRequest(String endpoint, String error_message) {
        String request;
        try {
            request = getRequest(HYPIXEL_API_URL + endpoint);
        } catch (Exception e) {
            SkyTrack.LOGGER.error("[SkyTrack] Hypixel API Error - {}", error_message, e);
            return null;
        }

        JsonObject data;
        try {
            data = JsonParser.parseString(request).getAsJsonObject();
        } catch (Exception e) {
            SkyTrack.LOGGER.error("[SkyTrack] Hypixel API Error - {}", error_message, e);
            return null;
        }

        return data;
    }

    /**
     * Requests the Skyblock collections data.
     * @return - A JSON object containing the collections data
     *         - null if the request failed
     */
    public static JsonObject getCollections() {
        return HypixelAPIRequest("/resources/skyblock/collections", "Failed to fetch collections data.");
    }

    /**
     * Requests the Skyblock skills data.
     * @return - A JSON object containing the skills data
     *         - null if the request failed
     */
    public static JsonObject getSkills() {
        return HypixelAPIRequest("/resources/skyblock/skills", "Failed to fetch skills data.");
    }

    /**
     * Requests the Skyblock items data.
     * @return - A JSON object containing the items data
     *         - null if the request failed
     */
    public static JsonObject getItems() {
        return HypixelAPIRequest("/resources/skyblock/items", "Failed to fetch items data.");
    }

    /**
     * Requests the Skyblock mayor and election data.
     * @return - A JSON object containing the mayor and election data
     *         - null if the request failed
     */
    public static JsonObject getElection() {
        return HypixelAPIRequest("/resources/skyblock/election", "Failed to fetch election data.");
    }

    /**
     * Requests the Skyblock bingo data.
     * @return - A JSON object containing the bingo data
     *         - null if the request failed
     */
    public static JsonObject getBingo() {
        return HypixelAPIRequest("/resources/skyblock/bingo", "Failed to fetch bingo data.");
    }

    /**
     * Requests the Skyblock news data.
     * @return - A JSON object containing the news data
     *         - null if the request failed
     */
    public static JsonObject getNews() {
        return HypixelAPIRequest("/skyblock/news", "Failed to fetch news data.");
    }

    /**
     * Requests auction(s) data according to the given uuid. If multiple parameters are given, the first one will be used.
     * @param auction_uuid - The specific auction uuid
     *                     - null if not needed
     * @param player_uuid - The player uuid
     *                    - null if not needed
     * @param profile_uuid - The profile uuid
     *                     - null if not needed
     * @return - A JSON object containing the auction(s) data
     *         - null if the request failed or no uuid was given
     */
    public static JsonObject getAuctionByUUID(String auction_uuid, String player_uuid, String profile_uuid) {
        String endpoint;

        if (auction_uuid != null) {
            endpoint = "/skyblock/auction?uuid=" + auction_uuid;
            return HypixelAPIRequest(endpoint, "Failed to fetch auction data.");
        }
        else if (player_uuid != null) {
            endpoint = "/skyblock/auction?player=" + player_uuid;
            return HypixelAPIRequest(endpoint, "Failed to fetch player auctions data.");
        }
        else if (profile_uuid != null) {
            endpoint = "/skyblock/auction?profile=" + profile_uuid;
            return HypixelAPIRequest(endpoint, "Failed to fetch profile auctions data.");
        }
        else {
            return null;
        }
    }

    /**
     * Requests the lastly updated active auctions.
     * @return A JSON object containing the auctions data
     */
    public static JsonObject getActiveAuctions() {
        return HypixelAPIRequest("/skyblock/auctions", "Failed to fetch active auctions.");
    }

    /**
     * Requests the lastly updated active auctions of a given page.
     * @param page The page to request the auctions from
     * @return A JSON object containing the auctions data
     */
    public static JsonObject getActiveAuctions(int page) {
        String endpoint = "/skyblock/auctions?page=" + page;
        return HypixelAPIRequest(endpoint, "Failed to fetch active auctions.");
    }

    /**
     * Requests the recently ended auctions.
     * @return A JSON object containing the auctions data
     */
    public static JsonObject getRecentlyEndedAuctions() {
        return HypixelAPIRequest("/skyblock/auctions/ended", "Failed to fetch recently ended auctions.");
    }

    /**
     * Requests the Bazaar items prices data.
     * @return A JSON object containing the Bazaar data
     */
    public static JsonObject getBazaar() {
        return HypixelAPIRequest("/skyblock/bazaar", "Failed to fetch Bazaar data.");
    }

    /**
     * Requests the data of a Skyblock profile.
     * @param uuid The UUID of the profile
     * @return A JSON object containing the profile data
     */
    public static JsonObject getProfileByUUID(String uuid) {
        String endpoint = "/skyblock/profile?profile=" + uuid;
        return HypixelAPIRequest("/skyblock/profile", "Failed to fetch profile data.");
    }

    /**
     * Requests the list of a player's profiles.
     * @param uuid The player's UUID
     * @return A JSON object containing the profiles data
     */
    public static JsonObject getPlayerProfiles(String uuid) {
        String endpoint = "/skyblock/profiles?uuid=" + uuid;
        return HypixelAPIRequest(endpoint, "Failed to fetch player profiles.");
    }

    /**
     * Requests the museum data of a player's profile.
     * @param uuid The profile UUID
     * @return A JSON object containing the museum data.
     */
    public static JsonObject getMuseumByProfileUUID(String uuid) {
        String endpoint = "/skyblock/museum?profile=" + uuid;
        return HypixelAPIRequest(endpoint, "Failed to fetch profile museum data.");
    }

    /**
     * Requests the garden data of a player's profile.
     * @param uuid The profile UUID
     * @return A JSON object containing the garden data.
     */
    public static JsonObject getGardenByProfileUUID(String uuid) {
        String endpoint = "/skyblock/garden?profile=" + uuid;
        return HypixelAPIRequest(endpoint, "Failed to fetch profile garden data.");
    }

    /**
     * Requests the Bingo data of a player.
     * @param uuid The player UUID
     * @return A JSON object containing the Bingo data.
     */
    public static JsonObject getBingoByPlayerUUID(String uuid) {
        String endpoint = "/skyblock/bingo?uuid=" + uuid;
        return HypixelAPIRequest(endpoint, "Failed to fetch player bingo data.");
    }

    /**
     * Requests the active and upcoming fire sales.
     * @return the fire sales data.
     */
    public static JsonObject getFireSales() {
        return HypixelAPIRequest("/skyblock/firesales", "Failed to fetch fire sales data.");
    }
}
