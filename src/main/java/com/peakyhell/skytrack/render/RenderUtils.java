package com.peakyhell.skytrack.render;

import com.mojang.blaze3d.vertex.VertexFormat;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;

import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

public class RenderUtils {

    public static void renderFilledBox(WorldRenderContext context) {
        BlockPos blockPos1 = new BlockPos(100, 100, 100);
        Box box1 = new Box(blockPos1);
        float[] rgba1 = new float[]{245f/255f, 40f/255f, 145f/255f, 0.5f};

        BlockPos blockPos2 = new BlockPos(105, 105, 105);
        Box box2 = new Box(blockPos2);
        float[] rgba2 = new float[]{245f/255f, 40f/255f, 145f/255f, 1.0f};

        renderFilledBox(context, box1, rgba1);
        renderFilledBox(context, box2, rgba2);
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

        drawFilledBox(matrixStack.peek().getPositionMatrix(), buffer, box, rgba);

        BuiltBuffer builtBuffer = buffer.end();

        RenderLayer renderLayer = rgba[3] < 1.0f ? RenderLayer.getDebugQuads() : RenderLayer.getSolid();
        renderLayer.draw(builtBuffer);

        matrixStack.pop();
    }

    public static void drawFilledBox(Matrix4f matrix, BufferBuilder buffer, Box box, float[] rgba) {
        float minX = (float) box.minX;
        float maxX = (float) box.maxX;
        float minY = (float) box.minY;
        float maxY = (float) box.maxY;
        float minZ = (float) box.minZ;
        float maxZ = (float) box.maxZ;

        float r = rgba[0];
        float g = rgba[1];
        float b = rgba[2];
        float a = rgba[3];

        // Front (minY)
        buffer.vertex(matrix, minX, minY, minZ).color(r, g, b, a);
        buffer.vertex(matrix, minX, minY, maxZ).color(r, g, b, a);
        buffer.vertex(matrix, maxX, minY, maxZ).color(r, g, b, a);
        buffer.vertex(matrix, maxX, minY, minZ).color(r, g, b, a);

        // East (maxX)
        buffer.vertex(matrix, maxX, minY, minZ).color(r, g, b, a);
        buffer.vertex(matrix, maxX, minY, maxZ).color(r, g, b, a);
        buffer.vertex(matrix, maxX, maxY, maxZ).color(r, g, b, a);
        buffer.vertex(matrix, maxX, maxY, minZ).color(r, g, b, a);

        // South (maxY)
        buffer.vertex(matrix, maxX, maxY, minZ).color(r, g, b, a);
        buffer.vertex(matrix, maxX, maxY, maxZ).color(r, g, b, a);
        buffer.vertex(matrix, minX, maxY, maxZ).color(r, g, b, a);
        buffer.vertex(matrix, minX, maxY, minZ).color(r, g, b, a);

        // West (minX)
        buffer.vertex(matrix, minX, maxY, minZ).color(r, g, b, a);
        buffer.vertex(matrix, minX, maxY, maxZ).color(r, g, b, a);
        buffer.vertex(matrix, minX, minY, maxZ).color(r, g, b, a);
        buffer.vertex(matrix, minX, minY, minZ).color(r, g, b, a);

        // Bottom (minZ)
        buffer.vertex(matrix, minX, minY, minZ).color(r, g, b, a);
        buffer.vertex(matrix, minX, maxY, minZ).color(r, g, b, a);
        buffer.vertex(matrix, maxX, maxY, minZ).color(r, g, b, a);
        buffer.vertex(matrix, maxX, minY, minZ).color(r, g, b, a);

        // Top (maxZ)
        buffer.vertex(matrix, minX, minY, maxZ).color(r, g, b, a);
        buffer.vertex(matrix, minX, maxY, maxZ).color(r, g, b, a);
        buffer.vertex(matrix, maxX, maxY, maxZ).color(r, g, b, a);
        buffer.vertex(matrix, maxX, minY, maxZ).color(r, g, b, a);
    }

}
