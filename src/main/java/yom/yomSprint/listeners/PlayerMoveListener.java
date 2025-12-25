package yom.yomSprint.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import yom.yomSprint.YomSprint;
import yom.yomSprint.enums.GameStatus;
import yom.yomSprint.events.GameEndEvent;
import yom.yomSprint.events.PlayerFinishEvent;
import yom.yomSprint.managers.ClassBridge;
import yom.yomSprint.managers.TrackManager;
import yom.yomSprint.models.Lane;
import yom.yomSprint.models.Time;
import yom.yomSprint.models.Track;

import java.util.HashMap;
import java.util.UUID;

public class PlayerMoveListener implements Listener {

    YomSprint plugin;
    ClassBridge classBridge;

    public PlayerMoveListener(YomSprint plugin) {
        this.plugin = plugin;
        this.classBridge = plugin.getClassBridge();
    }

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!TrackManager.isPlayerInAnyTrack(player)) return;
        Track track = TrackManager.getTrackByPlayer(player);
        Lane lane = getLane(player, track);
        if (track.getGameStatus().equals(GameStatus.IN_SET)) {
            if (!lane.getStartBoudingBox().contains(player)) {
                player.teleport(lane.getStartBoudingBox().getMiddle(player.getWorld()));
            }
        }
        int laneNumber = getLaneNumberOfPlayer(player, track);
        Location playerLocation = player.getLocation();

        if (track.getPlayersInGame().contains(player.getUniqueId())) {
            if (lane.getEndBoudingBox().contains(player)) {
                Bukkit.getPluginManager().callEvent(new PlayerFinishEvent(track, player, new Time(System.currentTimeMillis())));
            }
        }
    }

    private Lane getLane(Player player, Track track) {
        for (Lane lane : track.getLanes()) {
            if (lane.getNumber() == getLaneNumberOfPlayer(player, track)) {
                return lane;
            }
        }
        return null;
    }

    private int getLaneNumberOfPlayer(Player player, Track track) {
        int count = 1;
        for (UUID uuid : track.getPlayersInGame()) {
            if (uuid.equals(player.getUniqueId())) {
                return count;
            }
            count++;
        }
        return count;
    }

}
