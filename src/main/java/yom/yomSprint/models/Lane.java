package yom.yomSprint.models;

import org.bukkit.Location;

public class Lane {

    private Location laneStartLocation;
    private Location laneEndLocation;
    private int number;

    public Lane(Location laneStartLocation,Location laneEndLocation, int number) {
        this.laneStartLocation = laneStartLocation;
        this.laneEndLocation = laneEndLocation;
        this.number = number;
    }

    public Location getLaneStartLocation() {
        return laneStartLocation;
    }

    public Location getLaneEndLocation() {
        return laneEndLocation;
    }

    public int getNumber() {
        return number;
    }
}
