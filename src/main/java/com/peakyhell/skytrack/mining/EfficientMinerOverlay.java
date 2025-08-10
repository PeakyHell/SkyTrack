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

public class EfficientMinerOverlay {
    static List<Block> airTypes = Arrays.asList(Blocks.AIR, Blocks.SNOW, Blocks.LIGHT_GRAY_CARPET);
    static List<Block> blockStates = Arrays.asList(Blocks.CLAY, Blocks.SMOOTH_RED_SANDSTONE);

    public static void init() {
        // TODO Implement enabling when joining island and disabling when leaving
        SkyTrack.SCHEDULER.scheduleInterval(SkyTrack.WAYPOINT_MANAGER::clear, 10, 5, 10 * 60 * 20);
        SkyTrack.SCHEDULER.scheduleInterval(EfficientMinerOverlay::getBLocksAroundPlayer, 10, 5, 10 * 60 * 20); // Set for 10 minutes
    }

    /**
     * Fetch all the blocks in a 13x13x13 box around the player, create waypoints and calculate their priority
     */
    public static void getBLocksAroundPlayer() {
        String playerLocation = SkyTrack.PLAYER_INFO.LOCATION;
        if (playerLocation == null) return;

        // Activate only if in Glacite Tunnels or Mineshaft
        if (!playerLocation.contains("Glacite Tunnels") && !playerLocation.contains("Glacite Mineshaft")) return;

        World world = MinecraftClient.getInstance().world;
        ClientPlayerEntity player = MinecraftClient.getInstance().player;

        if (world == null || player == null) {
            return;
        }

        int playerX = (int) Math.floor(player.getX());
        int playerY = (int) Math.floor(player.getY());
        int playerZ = (int) Math.floor(player.getZ());

        Waypoint wp;
        int prio;
        for (int x = playerX - 6; x <= playerX + 6; x++) {
            for (int y = playerY - 6; y <= playerY + 6; y++) {
                for (int z = playerZ - 6; z <= playerZ + 6; z++) {
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
        if (prio >= 10) {
            prio = 1;
        }

        if (prio < 3) {
            waypoint.setR(20f/255f);
            waypoint.setG(90f/255f);
            waypoint.setB(38f/255f);
            waypoint.setA(0.6f);
        }
        else if (prio < 5) {
            waypoint.setR(145f/255f);
            waypoint.setG(23f/255f);
            waypoint.setB(23f/255f);
            waypoint.setA(0.6f);
        }
        else if (prio < 7) {
            waypoint.setR(104f/255f);
            waypoint.setG(210f/255f);
            waypoint.setB(249f/255f);
            waypoint.setA(0.6f);
        }
        else {
            waypoint.setR(49f/255f);
            waypoint.setG(41f/255f);
            waypoint.setB(165f/255f);
            waypoint.setA(0.6f);
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
