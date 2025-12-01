package yom.yomSprint.models;

import org.bukkit.Location;

public class Lane {

    private Location laneLocation;
    private int number;

    public Lane(Location laneLocation, int number) {
        this.laneLocation = laneLocation;
        this.number = number;
    }

    public Location getLineLocation() {
        return laneLocation;
    }

    public int getNumber() {
        return number;
    }
}
