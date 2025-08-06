package com.peakyhell.skytrack.render;

import com.mojang.blaze3d.vertex.VertexFormat;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;

import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class RenderUtils {

    public static void renderFilledBox(WorldRenderContext context) {
        BlockPos blockPos = new BlockPos(100, 100, 100);
        Box box = new Box(blockPos);
        float[] rgba = new float[]{245f/255f, 40f/255f, 145f/255f, 0.8f};
        renderFilledBox(context, box, rgba);
    }

    /**
     * Renders a filled box at given coordinates with the given color
     * @param context the WorldRenderContext
     * @param box The box coordinates
     * @param rgba The color parameters
     */
    public static void renderFilledBox(WorldRenderContext context, Box box, float[] rgba) {
        MatrixStack matrixStack = context.matrixStack();
        Vec3d camera = context.camera().getPos();

        if (matrixStack == null || camera == null) return;

        matrixStack.push();
        matrixStack.translate(-camera.x, -camera.y, -camera.z);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);

        VertexRendering.drawFilledBox(matrixStack, buffer, box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ, rgba[0], rgba[1], rgba[2], rgba[3]);

        BuiltBuffer builtBuffer = buffer.end();

        RenderLayer renderLayer = rgba[3] > 1.0f ? RenderLayer.getCutout() : RenderLayer.getSolid();
        renderLayer.draw(builtBuffer);

        matrixStack.pop();
    }

}
