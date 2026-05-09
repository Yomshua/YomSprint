package yom.yomSprint.managers;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import yom.yomSprint.YomSprint;
import yom.yomSprint.models.Runner;

import java.util.Set;
import java.util.UUID;

public class SpectatorManager {

    YomSprint plugin;

    public SpectatorManager(YomSprint plugin) {
        this.plugin = plugin;
    }

    public void setSpectate(Runner runner, Set<Runner> runners){
        if (runner == null) return;
        Player player = Bukkit.getPlayer(runner.getUuid());

        player.setGameMode(GameMode.SPECTATOR);

        runners.forEach((target) -> {
            Player other = Bukkit.getPlayer(target.getUuid());
            if (other == null) return;
            other.hidePlayer(plugin,player);
        });
    }


}
