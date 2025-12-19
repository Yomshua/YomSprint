package yom.yomSprint.models;

import org.bukkit.Location;

public class Lane {

    private BoudingBox startBoudingBox;
    private BoudingBox endBoudingBox;


    private int number;

    public Lane(BoudingBox startBoudingBox,BoudingBox endBoudingBox, int number) {
        this.startBoudingBox = startBoudingBox;
        this.endBoudingBox = endBoudingBox;
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
}
