package yom.yomSprint.managers;

import org.bukkit.Location;

public class ClassBridge {

    Location blockLocation;
    Boolean canExecute;

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
}
