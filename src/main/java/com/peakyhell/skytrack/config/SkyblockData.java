package com.peakyhell.skytrack.config;

import com.google.gson.JsonObject;
import com.peakyhell.skytrack.utils.HypixelAPI;

public class SkyblockData {
    public JsonObject COLLECTIONS;
    public JsonObject SKILLS;
    public JsonObject ITEMS;
    public JsonObject ELECTIONS;
    public JsonObject BINGO;
    public JsonObject NEWS;
    public JsonObject FIRE_SALES;

    public SkyblockData() {
        this.COLLECTIONS = HypixelAPI.getCollections();
        this.SKILLS = HypixelAPI.getSkills();
        this.ITEMS = HypixelAPI.getItems();
        this.ELECTIONS = HypixelAPI.getElection();
        this.BINGO = HypixelAPI.getBingo();
        this.NEWS = HypixelAPI.getNews();
        this.FIRE_SALES = HypixelAPI.getFireSales();
    }
}
