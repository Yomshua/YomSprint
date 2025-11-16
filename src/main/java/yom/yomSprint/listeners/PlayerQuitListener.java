package yom.yomSprint.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import yom.yomSprint.managers.TrackManager;

import java.util.List;
import java.util.UUID;


public class PlayerQuitListener implements Listener {
    @EventHandler
    void onLeaveEvent(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if(TrackManager.isPlayerInAnyTrack(player)){
            List<UUID> listOfPlayers = TrackManager.getTrackByPlayer(player).getPlayersInGame();
            listOfPlayers.remove(player.getUniqueId());
        }
        
    }

}
