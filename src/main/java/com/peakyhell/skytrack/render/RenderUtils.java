package com.peakyhell.skytrack.render;

import com.mojang.blaze3d.vertex.VertexFormat;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexRendering;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class RenderUtils {

    public static void renderFilledBox(WorldRenderContext context) {
        BlockPos blockPos = new BlockPos(100, 100, 100);
        Box box = new Box(blockPos);
        float[] rgba = new float[]{245f, 40f, 145f, 0.8f};
        renderFilledBox(context, box, rgba);
    }

    public static void renderFilledBox(WorldRenderContext context, Box box, float[] rgba) {
        MatrixStack matrixStack = context.matrixStack();
        Vec3d camera = context.camera().getPos();

        matrixStack.push();
        matrixStack.translate(-camera.x, -camera.y, -camera.z);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);

        VertexRendering.drawFilledBox(matrixStack, buffer, box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ, rgba[0], rgba[1], rgba[2], rgba[3]);

        matrixStack.pop();
    }

}
