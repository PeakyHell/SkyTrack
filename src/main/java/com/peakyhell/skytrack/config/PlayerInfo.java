package com.peakyhell.skytrack.config;

import net.minecraft.client.MinecraftClient;

public class PlayerInfo {
    public static final String USERNAME = MinecraftClient.getInstance().getSession().getUsername();
    public static final String UUID = MinecraftClient.getInstance().getSession().getUuidOrNull().toString();
    //private static final ArrayList<String> PROFILES;
    //private static String ACTIVE_PROFILE;
}
