package com.peakyhell.skytrack.commands;

import com.peakyhell.skytrack.SkyTrack;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;


public class ConfigScreen {

    public static void init() {

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("skytrack")
                    .executes(context -> {
                        Screen screen = new com.peakyhell.skytrack.render.ConfigScreen(
                                Text.literal("SkyTrack Config Screen")
                        );
                        SkyTrack.SCHEDULER.schedule(() -> MinecraftClient.getInstance().setScreen(screen), 1);

                        return 1;
                    })
            );
        });
    }
}
