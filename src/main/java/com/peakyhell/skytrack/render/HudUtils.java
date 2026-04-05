/*
    Widget design inspired by Skyblocker (https://github.com/SkyblockerMod/Skyblocker)
*/
package com.peakyhell.skytrack.render;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;


public class HudUtils {

    private static final int TEXT_POSITION = 12;

    public static HudElement buildWidget(String title, int x, int y, int width, int height,
                                         int backgroundColor, int borderColor, int borderOffset) {

        return (drawContext, renderTickCounter) -> {
            // Prepare title
            Component text = Component.literal(title);
            int textWidth = Minecraft.getInstance().font.width(text);

            drawWidgetBackground(drawContext, x, y, width, height, backgroundColor);
            drawWidgetBorders(drawContext, x, y, width, height, borderColor, borderOffset, textWidth);
            drawWidgetTitle(drawContext, x, y, text, borderColor);
        };
    }

    private static void drawWidgetBackground(GuiGraphics drawContext, int x, int y, int width, int height, int backgroundColor) {
        // Background 1px smaller on each side
        drawContext.fill(
                x + 1,
                y + 1,
                x + width - 1,
                y + height - 1,
                backgroundColor
        );

        // Missing outer lines without the corner pixel
        // Top
        drawContext.hLine(
                x + 1,        // Remove the corner
                x + width - 2,  // On each side
                y,
                backgroundColor
        );

        // Bottom
        drawContext.hLine(
                x + 1,
                x + width - 2,
                y  + height - 1,
                backgroundColor
        );

        // Left
        drawContext.vLine(
                x,
                y,
                y + height - 1,
                backgroundColor
        );

        // Right
        drawContext.vLine(
                x + width - 1,
                y,
                y + height - 1,
                backgroundColor
        );
    }

    private static void drawWidgetTitle(GuiGraphics drawContext, int x, int y, Component title, int titleColor) {
        drawContext.drawString(
                Minecraft.getInstance().font,
                title,
                x + 1 + TEXT_POSITION,
                y,
                titleColor,
                false
        );
    }

    private static void drawWidgetBorders(GuiGraphics drawContext, int x, int y, int width, int height, int borderColor, int offset, int titleWidth) {
        // Top left
        drawContext.hLine(
                x + 1 + offset,
                x + TEXT_POSITION - offset,
                y + offset,
                borderColor
        );

        // Top right
        drawContext.hLine(
                x + TEXT_POSITION + titleWidth + offset,
                x + width - 2 - offset,
                y + offset,
                borderColor
        );

        // Bottom
        drawContext.hLine(
                x + 1 + offset,
                x + width - 2 - offset,
                y + height - 1 - offset,
                borderColor
        );

        // Left
        drawContext.vLine(
                x + offset,
                y + offset,
                y + height - 1 - offset,
                borderColor
        );

        // Right
        drawContext.vLine(
                x + width - 1 - offset,
                y + offset,
                y + height - 1 - offset,
                borderColor
        );
    }
}
