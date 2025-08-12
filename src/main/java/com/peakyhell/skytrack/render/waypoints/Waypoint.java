package com.peakyhell.skytrack.render.waypoints;

import com.google.gson.JsonObject;
import com.peakyhell.skytrack.render.RenderUtils;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.util.math.Box;

public class Waypoint {

    private String name;
    private final int[] coordinates;
    private final float[] rgba;

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

    // Getters
    public String getName() { return this.name; }

    public int getX() { return this.coordinates[0]; }
    public int getY() { return this.coordinates[1]; }
    public int getZ() { return this.coordinates[2]; }
    public int[] getCoordinates() { return this.coordinates; }

    public float getR() { return this.rgba[0]; }
    public float getG() { return this.rgba[1]; }
    public float getB() { return this.rgba[2]; }
    public float getA() { return this.rgba[3]; }
    public float[] getRgba() { return this.rgba; }

    // Setters
    public void setName(String name) { this.name = name; }

    public void setX(int x) { this.coordinates[0] = x; }
    public void setY(int y) { this.coordinates[1] = y; }
    public void setZ(int z) { this.coordinates[2] = z; }
    public void setCoordinates(int x, int y, int z) {
        this.setX(x);
        this.setY(y);
        this.setZ(z);
    }
    public void setCoordinates(int[] coordinates) { this.setCoordinates(coordinates[0], coordinates[1], coordinates[2]); }

    public void setR(float r) { this.rgba[0] = r; }
    public void setG(float g) { this.rgba[1] = g; }
    public void setB(float b) { this.rgba[2] = b; }
    public void setA(float a) { this.rgba[3] = a; }
    public void setRgba(float r, float g, float b, float a) {
        this.setR(r);
        this.setG(g);
        this.setB(b);
        this.setA(a);
    }
    public void setRgba(float[] rgba) { this.setRgba(rgba[0], rgba[1], rgba[2], rgba[3]); }

    public void renderOutlined(WorldRenderContext context) {
        RenderUtils.renderOutlinedBox(context, this.buildBlockBox(), this.rgba);
    }

    public void renderFilled(WorldRenderContext context) {
        RenderUtils.renderFilledBox(context, this.buildBlockBox(), this.rgba);
    }

    public Box buildBlockBox() {
        double blockSize = 1;
        double offset = 0.01;

        int x = this.getX();
        int y = this.getY();
        int z = this.getZ();

        return new Box(
                x - offset, y - offset, z - offset,
                x + blockSize + offset, y + blockSize + offset, z + blockSize + offset
        );
    }
}
