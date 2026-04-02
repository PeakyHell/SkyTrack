package com.peakyhell.skytrack.render;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;


public class ConfigScreen extends Screen {

    public ConfigScreen(Component title) {
        super(title);
    }

    @Override
    protected void init() {
        Button configurationButton = Button.builder(Component.literal("SkyTrack Configuration"), button -> {
            // TODO Button action
        })
                .pos(width/2 - 205, 40)
                .size(200, 20)
                .tooltip(Tooltip.create(Component.literal("Button information")))
                .build();

        this.addRenderableWidget(configurationButton);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        super.render(guiGraphics, mouseX, mouseY, delta);
        guiGraphics.drawCenteredString(this.font, Component.literal("SkyTrack Configuration"), width/2, 20, 0xFFFFFF);
    }
}
