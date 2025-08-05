package com.peakyhell.skytrack.commands;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ConfigScreen {
    public static void init() {

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                dispatcher.register(ClientCommandManager.literal("skytrack").executes(context -> {
                    MinecraftClient.getInstance().setScreen(
                            new com.peakyhell.skytrack.render.ConfigScreen(Text.empty())
                    );
                    return 1;
                }))
        );
    }
}
