package yom.yomSprint.managers;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import yom.yomSprint.YomSprint;

import java.util.Set;
import java.util.UUID;

public class SpectatorManager {

    YomSprint plugin;

    public SpectatorManager(YomSprint plugin) {
        this.plugin = plugin;
    }

    public void setSpectate(Player player, Set<UUID> players){
        if (player == null) return;

        player.setGameMode(GameMode.SPECTATOR);

        players.forEach((uuid) -> {
            Player other = Bukkit.getPlayer(uuid);
            if (other == null) return;
            other.hidePlayer(plugin,player);
        });
    }


}
