package yom.yomSprint.guis.holders;

import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import yom.yomSprint.models.Track;

public class ChoosePositionGUIHolder implements InventoryHolder {

    private Track track;
    private Location blockLocation;
    private int laneNumber;

    public ChoosePositionGUIHolder(Track track,Location blockPosition, int laneNumber) {
        this.blockLocation = blockPosition;
        this.track = track;
        this.laneNumber = laneNumber;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }

    public Location getBlockLocation(){
        return blockLocation;
    }

    public Track getTrack() {
        return track;
    }

    public int getLaneNumber() {
        return laneNumber;
    }
}
