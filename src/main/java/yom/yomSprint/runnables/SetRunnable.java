package yom.yomSprint.runnables;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import yom.yomSprint.YomSprint;
import yom.yomSprint.enums.GameStatus;
import yom.yomSprint.events.GameSetEvent;
import yom.yomSprint.events.GameStartEvent;
import yom.yomSprint.models.Track;

import java.util.Set;
import java.util.UUID;

public class SetRunnable {

    private YomSprint plugin;
    private Track track;
    private BukkitRunnable runnable;

    public SetRunnable(YomSprint plugin,Track track) {
        this.plugin = plugin;
        this.track = track;
    }

    public void start(){
        runnable = new BukkitRunnable() {
            int TRACK_COUNTDOWN_SECONDS = 10;
            @Override
            public void run() {
               if (track.getPlayersInGame().size() == 0){
                   track.reload();
                   track.setGameStatus(GameStatus.JOIN);
                   runnable = null;
                   return;
               }
                if (TRACK_COUNTDOWN_SECONDS == 5) {
                    track.setGameStatus(GameStatus.READY);
                    for (UUID uuid : track.getPlayersInGame()) {
                        Player playerTittle = Bukkit.getPlayer(uuid);
                        playerTittle.sendTitle(ChatColor.GREEN + "SET", "");
                    }
                }
                if (TRACK_COUNTDOWN_SECONDS == 0) {
                    for (UUID uuid : track.getPlayersInGame()) {
                        Player playerTittle = Bukkit.getPlayer(uuid);
                        playerTittle.sendTitle(ChatColor.GREEN + "GO", "");
                    }
                    Set<UUID> players = track.getPlayersInGame();
                    Bukkit.getPluginManager().callEvent(new GameStartEvent(track, track.getPlayersInGame()));
                    cancel();
                }
                TRACK_COUNTDOWN_SECONDS--;

            }
        } ;
        runnable.runTaskTimer(plugin,0,20L);
    }

    public BukkitRunnable getRunnable() {
        return runnable;
    }
}
