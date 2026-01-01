package yom.yomSprint.models;

import org.bukkit.Location;

public class Lane {


    private BoudingBox startBoudingBox;
    private BoudingBox endBoudingBox;
    private Location edge1;
    private Location edge2;

    private int number;

    public Lane(BoudingBox startBoudingBox,BoudingBox endBoudingBox, Location edge1,Location edge2,int number) {
        this.startBoudingBox = startBoudingBox;
        this.endBoudingBox = endBoudingBox;
        this.edge1 = edge1;
        this.edge2 = edge2;
        this.number = number;
    }
    public int getNumber() {
        return number;
    }

    public BoudingBox getStartBoudingBox() {
        return startBoudingBox;
    }

    public BoudingBox getEndBoudingBox() {
        return endBoudingBox;
    }

    public Location getEdge1() {
        return edge1;
    }

    public Location getEdge2() {
        return edge2;
    }
}
