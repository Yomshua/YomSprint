package yom.yomSprint.managers;

import org.bukkit.Location;

import java.util.HashMap;
import java.util.UUID;

public class ClassBridge {

    Location blockLocation;
    Boolean canExecute = false;
    String tittlePositionsGUI;
    int laneNumber;
    String trackName;
    HashMap<UUID,Boolean> alreadyFinish = new HashMap<>();

    public Location getBlockLocation() {
        return blockLocation;
    }

    public void setBlockLocation(Location blockLocation) {
        this.blockLocation = blockLocation;
    }

    public Boolean getCanExecute() {
        return canExecute;
    }

    public void setCanExecute(Boolean canExecute) {
        this.canExecute = canExecute;
    }

    public String getTittlePositionsGUI() {
        return tittlePositionsGUI;
    }

    public void setTittlePositionsGUI(String tittlePositionsGUI) {
        this.tittlePositionsGUI = tittlePositionsGUI;
    }

    public int getLaneNumber() {
        return laneNumber;
    }

    public void setLaneNumber(int laneNumber) {
        this.laneNumber = laneNumber;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public HashMap<UUID, Boolean> getAlreadyFinish() {
        return alreadyFinish;
    }

    public void setAlreadyFinish(HashMap<UUID, Boolean> alreadyFinish) {
        this.alreadyFinish = alreadyFinish;
    }
}
