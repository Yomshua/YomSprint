package yom.yomSprint.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import yom.yomSprint.YomSprint;

public class PlayerJoinListener implements Listener {

    YomSprint plugin;

    public PlayerJoinListener(YomSprint plugin){
        this.plugin = plugin;
    }


    @EventHandler(priority = EventPriority.HIGH)
    void onJoinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if (plugin.isMainLobbyValid()) {
            player.teleport(plugin.getLobbyLocation());
        }
    }

}
