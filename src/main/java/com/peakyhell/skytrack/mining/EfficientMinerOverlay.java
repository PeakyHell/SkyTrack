/*
    Credit : Coleweight by Vinxey (https://github.com/Vinxey/coleweight)
 */
package com.peakyhell.skytrack.mining;

import com.peakyhell.skytrack.SkyTrack;
import com.peakyhell.skytrack.render.waypoints.Waypoint;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;
import java.util.function.BooleanSupplier;


public class EfficientMinerOverlay {

    static List<Block> airTypes = Arrays.asList(Blocks.AIR, Blocks.SNOW, Blocks.LIGHT_GRAY_CARPET);
    static List<Block> blockStates = Arrays.asList(Blocks.CLAY, Blocks.SMOOTH_RED_SANDSTONE);


    public static void init() {
        int delay = 2; // Start running in 2 ticks
        int interval = 10; // Run every 10 ticks
        BooleanSupplier condition = () -> SkyTrack.PLAYER_INFO.getLOCATION() != null && (SkyTrack.PLAYER_INFO.getLOCATION().equals("mining_3") || SkyTrack.PLAYER_INFO.getLOCATION().equals("mineshaft"));
        boolean keep = true; // Stop running when leaving and restart when joining back

        SkyTrack.SCHEDULER.scheduleRecurringCondition(SkyTrack.WAYPOINT_MANAGER::clear, delay, interval, condition, keep);
        SkyTrack.SCHEDULER.scheduleRecurringCondition(EfficientMinerOverlay::getBLocksAroundPlayer, delay, interval, condition, keep);
    }


    /**
     * Fetch all the blocks in a 13x13x13 box around the player, create waypoints and calculate their priority
     */
    public static void getBLocksAroundPlayer() {
        int fetchRadius = 6; // 13x13x13

        World world = MinecraftClient.getInstance().world;
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (world == null || player == null) return;

        int playerX = (int) Math.floor(player.getX());
        int playerY = (int) Math.floor(player.getY());
        int playerZ = (int) Math.floor(player.getZ());

        Waypoint wp;
        int prio;
        for (int x = playerX - fetchRadius; x <= playerX + fetchRadius; x++) {
            for (int y = playerY - fetchRadius; y <= playerY + fetchRadius; y++) {
                for (int z = playerZ - fetchRadius; z <= playerZ + fetchRadius; z++) {
                    if (!blockStates.contains(world.getBlockState(new BlockPos(x, y, z)).getBlock()) || !isVisible(x, y, z)) {
                        continue;
                    }
                    wp = new Waypoint(x, y, z);
                    prio = findPrio(x, y, z);
                    setColor(wp, prio);
                    SkyTrack.WAYPOINT_MANAGER.add(wp);
                }
            }
        }
    }

    /**
     * Set waypoints color based on their priority
     * @param waypoint The waypoint
     */
    static void setColor(Waypoint waypoint, int prio) {
        int lowPrio = 3;
        int mediumPrio = 5;
        int highPrio = 7;

        float[] lowPrioRGBA = {20f/255f, 90f/255f, 38f/255f, 0.6f};
        float[] mediumPrioRGBA = {145f/255f, 23f/255f, 23f/255f, 0.6f};
        float[] highPrioRGBA = {104f/255f, 210f/255f, 249f/255f, 0.6f};
        float[] veryHighPrioRGBA = {49f/255f, 41f/255f, 165f/255f, 0.6f};

        if (prio >= 10) prio = 1;

        if (prio < lowPrio) {
            waypoint.setRgba(lowPrioRGBA);
        }
        else if (prio < mediumPrio) {
            waypoint.setRgba(mediumPrioRGBA);
        }
        else if (prio < highPrio) {
            waypoint.setRgba(highPrioRGBA);
        }
        else {
            waypoint.setRgba(veryHighPrioRGBA);
        }
    }

    /**
     * Calculate the priority of a block.
     * @param x The x coordinate of the block
     * @param y The y coordinate of the block
     * @param z The z coordinate of the block
     * @return The priority of the block.
     */
    static int findPrio(int x, int y, int z) {
        int fetchRadius = 1; // 3x3x3

        World world = MinecraftClient.getInstance().world;
        if (world == null) return 0;

        int prio = 0;

        for (int i = -fetchRadius; i <= fetchRadius; i++) {
            for (int j = -fetchRadius; j <= fetchRadius; j++) {
                for (int k = -fetchRadius; k <= fetchRadius; k++) {
                    if (blockStates.contains(world.getBlockState(new BlockPos(x+i, y+j, z+k)).getBlock())) {
                        prio++;
                    }
                }
            }
        }

        return prio;
    }

    /**
     * Checks if the block at the given coordinates is visible (next to air or snow layers)
     * @param x The x coordinate of the block
     * @param y The y coordinate of the block
     * @param z The z coordinate of the block
     * @return - true if the block is visible
     *         - false if it's not
     */
    static boolean isVisible(int x, int y, int z) {
        World world = MinecraftClient.getInstance().world;
        if (world == null) return false;

        if (world.getBlockState(new BlockPos(x, y, z)).getBlock() == Blocks.BEDROCK) return false;
        if (airTypes.contains(world.getBlockState(new BlockPos(x+1, y, z)).getBlock())) return true; // east
        if (airTypes.contains(world.getBlockState(new BlockPos(x-1, y, z)).getBlock())) return true; // west
        if (airTypes.contains(world.getBlockState(new BlockPos(x, y+1, z)).getBlock())) return true; // above
        if (airTypes.contains(world.getBlockState(new BlockPos(x, y-1, z)).getBlock())) return true; // below
        if (airTypes.contains(world.getBlockState(new BlockPos(x, y, z+1)).getBlock())) return true; // north
        return (airTypes.contains(world.getBlockState(new BlockPos(x, y, z-1)).getBlock())); // south
    }
}
