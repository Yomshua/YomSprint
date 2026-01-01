package yom.yomSprint.listeners;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import yom.yomSprint.YomSprint;
import yom.yomSprint.boards.FastBoard;
import yom.yomSprint.events.GameSetEvent;
import yom.yomSprint.events.GameStartEvent;
import yom.yomSprint.models.Lane;
import yom.yomSprint.models.Track;

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
        for(UUID uuid : players){
            Player player = Bukkit.getPlayer(uuid);
            track.removeWaitBoard(uuid);
            track.getGameScoreboaMap().put(player.getUniqueId(),new FastBoard(player));
            track.updateGameBoard();
            Lane lane = track.getLanes().get(countToGetLane);
            player.teleport(lane.getStartBoudingBox().getMiddle(player.getWorld()));
            player.sendTitle(ChatColor.AQUA.toString() + ChatColor.BOLD + "RAIA", String.valueOf(lane.getNumber()));
            countToGetLane++;
        }
        new BukkitRunnable() {
            int TRACK_COUNTDOWN_SECONDS = 10;
            @Override
            public void run() {
                if (TRACK_COUNTDOWN_SECONDS == 5) {
                    for (UUID uuid : track.getPlayersInGame()) {
                        Player playerTittle = Bukkit.getPlayer(uuid);
                        playerTittle.sendTitle(ChatColor.GREEN + "SET", "");
                    }
                }
                if(TRACK_COUNTDOWN_SECONDS == 0){
                    for (UUID uuid : track.getPlayersInGame()) {
                        Player playerTittle = Bukkit.getPlayer(uuid);
                        playerTittle.sendTitle(ChatColor.GREEN + "GO", "");
                    }
                    Set<UUID> players = event.getPlayers();
                    Bukkit.getPluginManager().callEvent(new GameStartEvent(track, track.getPlayersInGame()));
                    cancel();
                }
                TRACK_COUNTDOWN_SECONDS--;
                
            }
        }.runTaskTimer(plugin, 0, 20L);
    }

}
