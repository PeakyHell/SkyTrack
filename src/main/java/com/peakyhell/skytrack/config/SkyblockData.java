package com.peakyhell.skytrack.config;

import com.google.gson.JsonObject;
import com.peakyhell.skytrack.utils.HypixelAPI;

public class SkyblockData {
    public static final JsonObject COLLECTIONS = HypixelAPI.getCollections();
    public static final JsonObject SKILLS = HypixelAPI.getSkills();
    public static final JsonObject ITEMS = HypixelAPI.getItems();
    public static final JsonObject ELECTIONS = HypixelAPI.getElection();
    public static final JsonObject BINGO = HypixelAPI.getBingo();
    public static final JsonObject NEWS = HypixelAPI.getNews();
    public static final JsonObject FIRE_SALES = HypixelAPI.getFireSales();
}
