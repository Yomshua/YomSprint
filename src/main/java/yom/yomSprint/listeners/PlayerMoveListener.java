package yom.yomSprint.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import yom.yomSprint.enums.GameStatus;
import yom.yomSprint.managers.TrackManager;
import yom.yomSprint.models.Lane;
import yom.yomSprint.models.Track;

import java.util.UUID;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event){
        Player player = event.getPlayer();
        if(!TrackManager.isPlayerInAnyTrack(player)) return;
        Track track = TrackManager.getTrackByPlayer(player);
        if (track.getGameStatus().equals(GameStatus.IN_SET)){
         event.setCancelled(true);
        }
        int laneNumber = getLaneNumberOfPlayer(player,track);
        Location location = player.getLocation();
        Lane lane = getLane(player,track);

        Location endLocation = lane.getLaneEndLocation();



    }

    private Lane getLane(Player player,Track track){
        for (Lane lane: track.getLanes()){
            if (lane.getNumber() == getLaneNumberOfPlayer(player,track)){
                return lane;
            }
        }
        return null;
    }

    private int getLaneNumberOfPlayer(Player player, Track track){
        int count = 1;
        for (UUID uuid : track.getPlayersInGame()){
            if (uuid.equals(player.getUniqueId())) {
                return count;
            }
            count++;
        }
        return count;
    }

}
