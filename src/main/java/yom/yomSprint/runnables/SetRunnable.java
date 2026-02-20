package yom.yomSprint.runnables;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import yom.yomSprint.YomSprint;
import yom.yomSprint.enums.GameStatus;
import yom.yomSprint.events.GameStartEvent;
import yom.yomSprint.models.Competition;

import java.util.Set;
import java.util.UUID;

public class SetRunnable {

    private YomSprint plugin;
    private Competition competition;
    private BukkitRunnable runnable;

    public SetRunnable(YomSprint plugin, Competition competition) {
        this.plugin = plugin;
        this.competition = competition;
    }

    public void start(){
        runnable = new BukkitRunnable() {
            int TRACK_COUNTDOWN_SECONDS = 10;
            @Override
            public void run() {
               if (competition.getRunners().isEmpty()){
                   competition.reload();
                   runnable = null;
                   return;
               }
                if (TRACK_COUNTDOWN_SECONDS == 5) {
                   competition.setGameStatus(GameStatus.READY);
                    for (UUID uuid : competition.getRunners()) {
                        Player playerTittle = Bukkit.getPlayer(uuid);
                        playerTittle.sendTitle(ChatColor.GREEN + "SET", "");
                    }
                }
                if (TRACK_COUNTDOWN_SECONDS == 0) {
                    for (UUID uuid : competition.getRunners()) {
                        Player playerTittle = Bukkit.getPlayer(uuid);
                        playerTittle.sendTitle(ChatColor.GREEN + "GO", "");
                    }
                    Set<UUID> players = competition.getRunners();
                    Bukkit.getPluginManager().callEvent(new GameStartEvent(competition, competition.getRunners()));
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
