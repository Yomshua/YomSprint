package yom.yomSprint.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import yom.yomSprint.enums.GameStatus;
import yom.yomSprint.managers.TrackManager;
import yom.yomSprint.models.Track;

import java.util.Set;
import java.util.UUID;


public class PlayerQuitListener implements Listener {
    @EventHandler
    void onQuitEvent(PlayerQuitEvent event){
        Player player = event.getPlayer();
        player.setInvulnerable(false);
        if(TrackManager.isPlayerInAnyTrack(player)){
            Track track = TrackManager.getTrackByPlayer(player);
            Set<UUID> listOfPlayers = TrackManager.getTrackByPlayer(player).getPlayersInGame();
            listOfPlayers.remove(player.getUniqueId());
            if (track.getGameStatus().equals(GameStatus.JOIN)) {
                track.getWaitLobbyScoreboadMap().remove(player.getUniqueId());
                track.updateWaitBoard();
            }else if (track.getGameStatus().equals(GameStatus.OCURRING)){
                track.getGameScoreboaMap().remove(player.getUniqueId());
                track.updateGameBoard();
            }
        }
        
    }

}
