/*
    Credit : Coleweight by Vinxey (https://github.com/Vinxey/coleweight)
 */
package com.peakyhell.skytrack.mining;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public class EfficientMinerOverlay {
    static List<Block> airTypes = Arrays.asList(Blocks.AIR, Blocks.SNOW);

    class Waypoint {
        int x, y, z, prio;
        public Waypoint(int x, int y, int z, int prio) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.prio = prio;
        }
    }

    static int findPrio(int x, int y, int z) {
        List<Block> blockStates = Arrays.asList(Blocks.CLAY, Blocks.RED_SANDSTONE_SLAB);
        World world = MinecraftClient.getInstance().world;
        if (world == null) {
            return 0;
        }

        int prio = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                for (int k = -1; k <= 1; k++) {
                    if (blockStates.contains(world.getBlockState(new BlockPos(x+i, y+j, z+k)).getBlock())) {
                        prio++;
                    }
                }
            }
        }

        return prio;
    }

    static boolean isVisible(int x, int y, int z) {
        World world = MinecraftClient.getInstance().world;
        if (world == null) {
            return false;
        }

        if (world.getBlockState(new BlockPos(x, y, z)).getBlock() == Blocks.BEDROCK) return false;
        if (airTypes.contains(world.getBlockState(new BlockPos(x+1, y, z)).getBlock())) return true; // east
        if (airTypes.contains(world.getBlockState(new BlockPos(x-1, y, z)).getBlock())) return true; // west
        if (airTypes.contains(world.getBlockState(new BlockPos(x, y+1, z)).getBlock())) return true; // above
        if (airTypes.contains(world.getBlockState(new BlockPos(x, y-1, z)).getBlock())) return true; // below
        if (airTypes.contains(world.getBlockState(new BlockPos(x, y, z+1)).getBlock())) return true; // north
        return (airTypes.contains(world.getBlockState(new BlockPos(x, y, z-1)).getBlock())); // south
    }
}
