package com.peakyhell.skytrack.utils;

import com.peakyhell.skytrack.SkyTrack;
import net.hypixel.modapi.HypixelModAPI;
import net.hypixel.modapi.packet.impl.clientbound.ClientboundHelloPacket;
import net.hypixel.modapi.packet.impl.clientbound.ClientboundPartyInfoPacket;
import net.hypixel.modapi.packet.impl.clientbound.ClientboundPingPacket;
import net.hypixel.modapi.packet.impl.clientbound.ClientboundPlayerInfoPacket;
import net.hypixel.modapi.packet.impl.clientbound.event.ClientboundLocationPacket;
import net.hypixel.modapi.packet.impl.serverbound.ServerboundPartyInfoPacket;
import net.hypixel.modapi.packet.impl.serverbound.ServerboundPingPacket;
import net.hypixel.modapi.packet.impl.serverbound.ServerboundPlayerInfoPacket;

public class HypixelPacketHandler {

    public static void init() {
        HypixelModAPI instance = SkyTrack.HYPIXELMODAPI;

        // Subscribe to Packets
        instance.subscribeToEventPacket(ClientboundLocationPacket.class);

        // Create handler for packets
        instance.createHandler(ClientboundHelloPacket.class, HypixelPacketHandler::handleHello);
        instance.createHandler(ClientboundPingPacket.class, HypixelPacketHandler::handlePing);
        instance.createHandler(ClientboundPartyInfoPacket.class, HypixelPacketHandler::handlePartyInfo);
        instance.createHandler(ClientboundPlayerInfoPacket.class, HypixelPacketHandler::handlePlayerInfo);
        instance.createHandler(ClientboundLocationPacket.class, HypixelPacketHandler::handleLocation);
    }

    private static void handleHello(ClientboundHelloPacket packet) {
        SkyTrack.LOGGER.info(packet.toString());
    }

    private static void handlePing(ClientboundPingPacket packet) {
        SkyTrack.LOGGER.info(packet.toString());
    }

    private static void handlePartyInfo(ClientboundPartyInfoPacket packet) {
        SkyTrack.LOGGER.info(packet.toString());
    }

    private static void handlePlayerInfo(ClientboundPlayerInfoPacket packet) {
        SkyTrack.LOGGER.info(packet.toString());
    }

    private static void handleLocation(ClientboundLocationPacket packet) {
        SkyTrack.PLAYER_INFO.setLOCATION(packet.getMode().toString());
        SkyTrack.LOGGER.info(packet.toString());
    }

    public static void sendPing() {
        SkyTrack.HYPIXELMODAPI.sendPacket(new ServerboundPingPacket());
    }

    public static void sendPartyInfo() {
        SkyTrack.HYPIXELMODAPI.sendPacket(new ServerboundPartyInfoPacket());
    }

    public static void sendPlayerInfo() {
        SkyTrack.HYPIXELMODAPI.sendPacket(new ServerboundPlayerInfoPacket());
    }
}
