package com.peakyhell.skytrack.commands;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class Hello {

    public static void init() {
        String username = MinecraftClient.getInstance().getSession().getUsername();

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                dispatcher.register(ClientCommandManager.literal("hello").executes(context -> {
                    context.getSource().sendFeedback(Text.literal("[SkyTrack] Hello, " + username + "!"));
                    return 1;
                }))
        );
    }

}
