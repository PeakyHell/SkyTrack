package com.peakyhell.skytrack.render;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ConfigScreen extends Screen {

    public ConfigScreen(Text title) {
        super(title);
    }

    @Override
    protected void init() {
        ButtonWidget configurationButton = ButtonWidget.builder(Text.literal("SkyTrack Configuration"), button -> {
            // TODO Button action
        })
                .dimensions(width/2 - 205, 40, 200, 20)
                .tooltip(Tooltip.of(Text.literal("Button information")))
                .build();

        this.addDrawableChild(configurationButton);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("SkyTrack Configuration"), width/2, 20, 0xFFFFFF);
    }
}
