package com.peakyhell.skytrack.commands;

import com.peakyhell.skytrack.SkyTrack;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.Text;


public class Hello {

    public static void init() {

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                dispatcher.register(ClientCommandManager.literal("hello").executes(context -> {
                    context.getSource().sendFeedback(Text.literal("[SkyTrack] Hello, " + SkyTrack.PLAYER_INFO.getUSERNAME() + "(" + SkyTrack.PLAYER_INFO.getUUID() + ") !"));
                    return 1;
                }))
        );
    }

}
