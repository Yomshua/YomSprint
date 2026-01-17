package yom.yomSprint.listeners;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import yom.yomSprint.YomSprint;
import yom.yomSprint.boards.FastBoard;
import yom.yomSprint.enums.GameStatus;
import yom.yomSprint.events.GameSetEvent;
import yom.yomSprint.events.GameStartEvent;
import yom.yomSprint.models.Lane;
import yom.yomSprint.models.Track;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class GameSetListener implements Listener {

    private YomSprint plugin;

    public GameSetListener(YomSprint plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSetEvent(GameSetEvent event){
        Track track = event.getTrack();
        int countToGetLane = 0;
        Set<UUID> players = track.getPlayersInGame();
        List<UUID> playersList = players.stream().toList();
        for (int i = 0; i < players.size(); i++) {
            UUID uuid = playersList.get(i);
            Player player = Bukkit.getPlayer(uuid);
            track.removeWaitBoard(uuid);
            track.getGameScoreboaMap().put(player.getUniqueId(),new FastBoard(player));
            Lane lane = track.getLanes().get(i);
            player.teleport(lane.getStartBoudingBox().getMiddle(player.getWorld()));
            player.sendTitle(ChatColor.AQUA.toString() + ChatColor.BOLD + "RAIA", String.valueOf(lane.getNumber()));
            track.getLaneHashMap().put(player.getUniqueId(),lane);
            track.updateGameBoard();
        }
       track.getSetRunnable().start();
    }

}
