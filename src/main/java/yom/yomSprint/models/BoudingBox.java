package yom.yomSprint.models;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class BoudingBox {

    Location pos1;
    Location pos2;

    public BoudingBox(Location pos1, Location pos2) {
        this.pos1 = pos1;
        this.pos2 = pos2;
    }


    public Location getPos1() {
        return pos1;
    }

    public Location getPos2() {
        return pos2;
    }

    public boolean contains(LivingEntity entity){
        double x1 = getPos1().getBlockX();
        double x2 = getPos2().getBlockX();

        double z1 = getPos1().getBlockZ();
        double z2 = getPos2().getBlockZ();

        double xMax =  Math.max(x1, x2);
        double xMin =  Math.min(x1,x2);

        double zMax =  Math.max(z1,z2);
        double zMin =  Math.min(z1,z2);

        Location entityLocation = entity.getLocation();

        double eX = entityLocation.getX();
        double eZ = entityLocation.getZ();

        return eX >= xMin && eX <= xMax &&
               eZ >= zMin && eZ <= zMax;
    }

    public Location getMiddle(World world){
        double x1 = getPos1().getX();
        double z1 = getPos1().getZ();

        double x2 = getPos2().getX();
        double z2 = getPos2().getZ();

        double xMax =  Math.max(x1, x2);
        double xMin =  Math.min(x1,x2);

        double zMax =  Math.max(z1,z2);
        double zMin =  Math.min(z1,z2);

        double x = (xMax - xMin) / 2;
        double z = (zMax - zMin) / 2;

        Location location = new Location(world,xMin + x,getPos1().getY()+1,z+zMin);
        return location;
    }

}
