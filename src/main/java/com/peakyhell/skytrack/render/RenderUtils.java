package com.peakyhell.skytrack.render;

import com.mojang.blaze3d.vertex.VertexFormat;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;

import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

public class RenderUtils {

    /**
     * Renders an outlined box at given coordinates with the given color
     * @param context the WorldRenderContext
     * @param box The box coordinates
     * @param rgba The color parameters
     */
    public static void renderOutlinedBox(WorldRenderContext context, Box box, float[] rgba) {
        MatrixStack matrixStack = context.matrixStack();
        Vec3d camera = context.camera().getPos();

        if (matrixStack == null || camera == null) return;

        matrixStack.push();
        matrixStack.translate(-camera.x, -camera.y, -camera.z);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.LINES, VertexFormats.POSITION_COLOR);

        drawOutlinedBox(matrixStack.peek().getPositionMatrix(), buffer, box, rgba);

        BuiltBuffer builtBuffer = buffer.end();

        RenderLayer renderLayer = RenderLayer.getDebugQuads();
        renderLayer.draw(builtBuffer);

        matrixStack.pop();
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

        RenderLayer renderLayer = RenderLayer.getDebugQuads();
        renderLayer.draw(builtBuffer);

        matrixStack.pop();
    }

    /**
     * Draw an outlined box with the given dimensions and color in a buffer to be ready to be rendered
     * @param matrix The matrix
     * @param buffer The buffer
     * @param box The box coordinates
     * @param rgba The color parameters
     */
    public static void drawOutlinedBox(Matrix4f matrix, BufferBuilder buffer, Box box, float[]rgba) {
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

        // Bottom
        buffer.vertex(matrix, minX, minY, minZ).color(r, g, b, a);
        buffer.vertex(matrix, minX, minY, maxZ).color(r, g, b, a);

        buffer.vertex(matrix, minX, minY, maxZ).color(r, g, b, a);
        buffer.vertex(matrix, maxX, minY, maxZ).color(r, g, b, a);

        buffer.vertex(matrix, maxX, minY, maxZ).color(r, g, b, a);
        buffer.vertex(matrix, maxX, minY, minZ).color(r, g, b, a);

        buffer.vertex(matrix, maxX, minY, minZ).color(r, g, b, a);
        buffer.vertex(matrix, minX, minY, minZ).color(r, g, b, a);

        // Top
        buffer.vertex(matrix, minX, maxY, minZ).color(r, g, b, a);
        buffer.vertex(matrix, minX, maxY, maxZ).color(r, g, b, a);

        buffer.vertex(matrix, minX, maxY, maxZ).color(r, g, b, a);
        buffer.vertex(matrix, maxX, maxY, maxZ).color(r, g, b, a);

        buffer.vertex(matrix, maxX, maxY, maxZ).color(r, g, b, a);
        buffer.vertex(matrix, maxX, maxY, minZ).color(r, g, b, a);

        buffer.vertex(matrix, maxX, maxY, minZ).color(r, g, b, a);
        buffer.vertex(matrix, minX, maxY, minZ).color(r, g, b, a);

        // Vertical
        buffer.vertex(matrix, minX, minY, minZ).color(r, g, b, a);
        buffer.vertex(matrix, minX, maxY, minZ).color(r, g, b, a);

        buffer.vertex(matrix, minX, minY, maxZ).color(r, g, b, a);
        buffer.vertex(matrix, minX, maxY, maxZ).color(r, g, b, a);

        buffer.vertex(matrix, maxX, minY, maxZ).color(r, g, b, a);
        buffer.vertex(matrix, maxX, maxY, maxZ).color(r, g, b, a);

        buffer.vertex(matrix, maxX, minY, minZ).color(r, g, b, a);
        buffer.vertex(matrix, maxX, maxY, minZ).color(r, g, b, a);
    }

    /**
     * Draw an outlined box with the given dimensions and color in a buffer to be ready to be rendered
     * @param matrix The matrix
     * @param buffer The buffer
     * @param box The box coordinates
     * @param rgba The color parameters
     */
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

        // Bottom (minY)
        buffer.vertex(matrix, minX, minY, minZ).color(r, g, b, a);
        buffer.vertex(matrix, minX, minY, maxZ).color(r, g, b, a);
        buffer.vertex(matrix, maxX, minY, maxZ).color(r, g, b, a);
        buffer.vertex(matrix, maxX, minY, minZ).color(r, g, b, a);

        // East (maxX)
        buffer.vertex(matrix, maxX, minY, minZ).color(r, g, b, a);
        buffer.vertex(matrix, maxX, minY, maxZ).color(r, g, b, a);
        buffer.vertex(matrix, maxX, maxY, maxZ).color(r, g, b, a);
        buffer.vertex(matrix, maxX, maxY, minZ).color(r, g, b, a);

        // Bottom (maxY)
        buffer.vertex(matrix, maxX, maxY, minZ).color(r, g, b, a);
        buffer.vertex(matrix, maxX, maxY, maxZ).color(r, g, b, a);
        buffer.vertex(matrix, minX, maxY, maxZ).color(r, g, b, a);
        buffer.vertex(matrix, minX, maxY, minZ).color(r, g, b, a);

        // West (minX)
        buffer.vertex(matrix, minX, maxY, minZ).color(r, g, b, a);
        buffer.vertex(matrix, minX, maxY, maxZ).color(r, g, b, a);
        buffer.vertex(matrix, minX, minY, maxZ).color(r, g, b, a);
        buffer.vertex(matrix, minX, minY, minZ).color(r, g, b, a);

        // North (minZ)
        buffer.vertex(matrix, minX, minY, minZ).color(r, g, b, a);
        buffer.vertex(matrix, minX, maxY, minZ).color(r, g, b, a);
        buffer.vertex(matrix, maxX, maxY, minZ).color(r, g, b, a);
        buffer.vertex(matrix, maxX, minY, minZ).color(r, g, b, a);

        // South (maxZ)
        buffer.vertex(matrix, minX, minY, maxZ).color(r, g, b, a);
        buffer.vertex(matrix, minX, maxY, maxZ).color(r, g, b, a);
        buffer.vertex(matrix, maxX, maxY, maxZ).color(r, g, b, a);
        buffer.vertex(matrix, maxX, minY, maxZ).color(r, g, b, a);
    }

}
