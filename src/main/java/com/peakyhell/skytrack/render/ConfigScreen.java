package com.peakyhell.skytrack.render;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ConfigScreen extends Screen {

    public ConfigScreen(Text title) {
        super(title);
    }

    @Override
    protected void init() {
        ButtonWidget configurationButton = ButtonWidget.builder(Text.of("SkyTrack Configuration"), (btn) -> {
        }).dimensions(40, 40, 120, 20).build();

        this.addDrawableChild(configurationButton);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        context.drawText(this.textRenderer, "SkyTrack", 40, 40 - this.textRenderer.fontHeight - 10, 0xFFFFFFFF, true);
    }
}
