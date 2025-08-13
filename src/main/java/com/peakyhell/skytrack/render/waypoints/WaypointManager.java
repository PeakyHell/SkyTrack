package com.peakyhell.skytrack.render.waypoints;

import java.util.ArrayList;
import java.util.List;


public class WaypointManager {

    private final List<Waypoint> loadedWaypoints;


// === Constructors ===
    public WaypointManager() {
        this.loadedWaypoints = new ArrayList<>();
    }


// === Getters ===
    public List<Waypoint> getWaypoints() {
        return this.loadedWaypoints;
    }


// === Methods ===
    public void add(Waypoint waypoint) {
        this.loadedWaypoints.add(waypoint);
    }

    public void remove(Waypoint waypoint) {
        this.loadedWaypoints.remove(waypoint);
    }

    public void clear() {
        this.loadedWaypoints.clear();
    }

}
