/*
    Widget design inspired by Skyblocker (https://github.com/SkyblockerMod/Skyblocker)
*/
package com.peakyhell.skytrack.render;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.Text;

public class HudUtils {


    public static HudElement buildWidget(String title, int x, int y, int width, int height,
                                         int backgroundColor, int borderColor, int offset) {

        return (drawContext, renderTickCounter) -> {
            // Background
            drawContext.fill(x+1, y+1, x+width-1, y+height-1, backgroundColor);

            drawContext.drawHorizontalLine(x+1, x+width-2, y, backgroundColor); // Top
            drawContext.drawHorizontalLine(x+1, x+width-2, y+height-1, backgroundColor); // Bottom

            drawContext.drawVerticalLine(x, y, y+height-1, backgroundColor); // Left
            drawContext.drawVerticalLine(x+width-1, y, y+height-1, backgroundColor); // Right

            // Outlines
            drawContext.drawHorizontalLine(x+1+offset, x+width-2-offset, y+offset, borderColor); // Top
            drawContext.drawHorizontalLine(x+1+offset, x+width-2-offset, y+height-1-offset, borderColor); // Bottom

            drawContext.drawVerticalLine(x+offset, y+offset, y+height-1-offset, borderColor); // Top
            drawContext.drawVerticalLine(x+width-1-offset, y+offset, y+height-1-offset, borderColor); // Bottom

            // Title
            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
            Text text = Text.of(title);
            drawContext.drawText(textRenderer, text, x + 8, y + 2, borderColor, false);

        };
    }

}
