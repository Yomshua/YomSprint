package yom.yomSprint.models;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Stamina {

    private UUID uuid;
    private Track track;
    private int level;
    private Player player;
    private final float EXP_ONE_BAR = 0.055f;

    public Stamina(UUID uuid,Track track) {
        this.uuid = uuid;
        this.track =track;
        player = Bukkit.getPlayer(uuid);
    }

    public void setLevel(int level){
        this.level = level;
    }

    public void setExpAndLevel(int level){
        this.level = level;
        float exp = (level/2) * EXP_ONE_BAR;
        if (exp >= 0) {
            player.setExp(0);
            player.setExp(exp);
        }
    }

    public int getLevel(){
        return level;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Track getTrack() {
        return track;
    }
}
