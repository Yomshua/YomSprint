package yom.yomSprint.runnables;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import yom.yomSprint.YomSprint;
import yom.yomSprint.enums.GameStatus;
import yom.yomSprint.events.GameSetEvent;
import yom.yomSprint.models.Track;

import java.util.UUID;

public class StartCountRunnable {

    private YomSprint plugin;
    private Track track;
    private BukkitRunnable runnable;

    public StartCountRunnable(YomSprint plugin,Track track) {
        this.plugin = plugin;
        this.track = track;
    }

    public void start(){
        runnable = new BukkitRunnable() {
            int TRACK_COUNTDOWN_SECONDS = 30;
            @Override
            public void run() {

                if (track.getWaitLobbySize() == 0){
                    track.setRunnableRunining(false);
                    cancel();
                }

                for(UUID uuid : track.getPlayersInGame()){
                    Player playerTittle = Bukkit.getPlayer(uuid);
                    playerTittle.sendTitle(ChatColor.GREEN + String.valueOf(TRACK_COUNTDOWN_SECONDS),"");
                }
                if(TRACK_COUNTDOWN_SECONDS == 0){
                    Bukkit.getPluginManager().callEvent(new GameSetEvent(track, track.getPlayersInGame()));
                    track.setGameStatus(GameStatus.IN_SET);
                    cancel();
                }
                TRACK_COUNTDOWN_SECONDS--;
            }
        };
        runnable.runTaskTimer(plugin,0,20L);
    }

}
