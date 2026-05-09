package yom.yomSprint.models;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Stamina {

    private UUID uuid;
    private int level;
    private Player player;
    private final float EXP_ONE_BAR = 0.055f;

    public Stamina(UUID uuid) {
        this.uuid = uuid;
        this.player = Bukkit.getPlayer(uuid);
        this.setExpAndLevel(36);
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

}
