package com.peakyhell.skytrack.render;

import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public class Waypoint {

    String name;
    int[] coordinates;
    float[] rgba;


    /**
     * Build a waypoint from a JSON object, used to import from a JSON file using Soopy waypoints format.
     * @param json A JSON object containing the waypoint data
     */
    public Waypoint(JsonObject json) {
        this.name = json.get("options").getAsJsonObject().get("name").getAsString();
        this.coordinates = new int[]{json.get("x").getAsInt(), json.get("y").getAsInt(), json.get("z").getAsInt()};
        this.rgba = new float[]{json.get("r").getAsFloat(), json.get("g").getAsFloat(), json.get("b").getAsFloat(), 1.0f};
    }

    /**
     * Build a Waypoint with coordinates only, name and color will be set to default values.
     * @param x The x coordinate
     * @param y The x coordinate
     * @param z The x coordinate
     */
    public Waypoint(int x, int y, int z) {
        this("Waypoint", x, y, z, 245f/255f, 40f/255f, 145f/255f, 0.8f);
    }

    /**
     * Build a Waypoint with name and coordinates only, color will be set to a default value.
     * @param name The name of the waypoint
     * @param x The x coordinate
     * @param y The x coordinate
     * @param z The x coordinate
     */
    public Waypoint(String name, int x, int y, int z) {
        this(name, x, y, z, 245f/255f, 40f/255f, 145f/255f, 0.8f); // Default color to pink
    }

    /**
     * Build a Waypoint with coordinates and color only, name will be set to a default value.
     * @param x The x coordinate
     * @param y The x coordinate
     * @param z The x coordinate
     * @param r The red value
     * @param g The green value
     * @param b The blue value
     * @param a The alpha value
     */
    public Waypoint(int x, int y, int z,
                    float r, float g, float b, float a) {
        this("Waypoint", x, y, z, r, g, b, a);
    }

    /**
     *
     * @param name The name of the waypoint
     * @param x The x coordinate
     * @param y The x coordinate
     * @param z The x coordinate
     * @param r The red value
     * @param g The green value
     * @param b The blue value
     * @param a The alpha value
     */
    public Waypoint(String name,
                    int x, int y, int z,
                    float r, float g, float b, float a) {

        this.name = name;
        this.coordinates = new int[]{x, y, z};
        this.rgba = new float[]{r, g, b, a};
    }


    public void render(WorldRenderContext context) {
        int x = this.coordinates[0];
        int y = this.coordinates[1];
        int z = this.coordinates[2];
        BlockPos block = new BlockPos(x, y, z);
        RenderUtils.renderFilledBox(context, new Box(block), this.rgba);
    }
}
