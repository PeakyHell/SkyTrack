package com.peakyhell.skytrack.config;

import com.google.gson.JsonObject;
import com.peakyhell.skytrack.utils.HypixelAPI;

public class SkyblockData {
    private JsonObject COLLECTIONS;
    private JsonObject SKILLS;
    private JsonObject ITEMS;
    private JsonObject ELECTIONS;
    private JsonObject BINGO;
    private JsonObject NEWS;
    private JsonObject FIRE_SALES;

    public SkyblockData() {
        this.COLLECTIONS = HypixelAPI.getCollections();
        this.SKILLS = HypixelAPI.getSkills();
        this.ITEMS = HypixelAPI.getItems();
        this.ELECTIONS = HypixelAPI.getElection();
        this.BINGO = HypixelAPI.getBingo();
        this.NEWS = HypixelAPI.getNews();
        this.FIRE_SALES = HypixelAPI.getFireSales();
    }

    // Getters
    public JsonObject getCOLLECTIONS() { return COLLECTIONS; }

    public JsonObject getSKILLS() { return SKILLS; }

    public JsonObject getITEMS() { return ITEMS; }

    public JsonObject getELECTIONS() { return ELECTIONS; }

    public JsonObject getBINGO() { return BINGO; }

    public JsonObject getNEWS() { return NEWS; }

    public JsonObject getFIRE_SALES() { return FIRE_SALES; }

    // Setters
    public void setCOLLECTIONS(JsonObject COLLECTIONS) { this.COLLECTIONS = COLLECTIONS; }

    public void setSKILLS(JsonObject SKILLS) { this.SKILLS = SKILLS; }

    public void setITEMS(JsonObject ITEMS) { this.ITEMS = ITEMS; }

    public void setELECTIONS(JsonObject ELECTIONS) { this.ELECTIONS = ELECTIONS; }

    public void setBINGO(JsonObject BINGO) { this.BINGO = BINGO; }

    public void setNEWS(JsonObject NEWS) { this.NEWS = NEWS; }

    public void setFIRE_SALES(JsonObject FIRE_SALES) { this.FIRE_SALES = FIRE_SALES; }
}
