package yom.yomSprint.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import yom.yomSprint.YomSprint;

public class PlayerJoinListener implements Listener {

    YomSprint plugin;

    public PlayerJoinListener(YomSprint plugin){
        this.plugin = plugin;
    }

    @EventHandler
    void onJoinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if (!plugin.getConfig().contains("main_lobby") || plugin.getConfig().get("main_lobby") == null) return;
        if (!plugin.getConfig().getBoolean("lobby_activated")) return;
        Object obj = plugin.getConfig().get("main_lobby");
        if (!(obj instanceof Location)) {
            plugin.getLogger().warning("A chave 'main_lobby' não é uma Location válida!");
            return;
        }
        Location location = (Location) obj;
        player.teleport(location);
    }

}
