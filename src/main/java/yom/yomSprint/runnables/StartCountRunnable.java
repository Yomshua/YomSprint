package yom.yomSprint.runnables;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import yom.yomSprint.YomSprint;
import yom.yomSprint.enums.GameStatus;
import yom.yomSprint.events.GameSetEvent;
import yom.yomSprint.models.Competition;
import yom.yomSprint.models.Runner;

import java.util.UUID;

public class StartCountRunnable {

    private YomSprint plugin;
    private Competition competition;
    private BukkitRunnable runnable;

    public StartCountRunnable(YomSprint plugin, Competition competition) {
        this.plugin = plugin;
        this.competition = competition;
    }

    public void start(){
        runnable = new BukkitRunnable() {
            int TRACK_COUNTDOWN_SECONDS = 30;
            @Override
            public void run() {

                if (competition.getRunners().isEmpty()){
                    competition.setRunnableRunining(false);
                    cancel();
                }

                for(Runner runner : competition.getRunners()){
                    Player playerTittle = Bukkit.getPlayer(runner.getUuid());
                    playerTittle.sendTitle(ChatColor.GREEN + String.valueOf(TRACK_COUNTDOWN_SECONDS),"");
                }
                if(TRACK_COUNTDOWN_SECONDS == 0){
                    competition.callSet();
                    Bukkit.getPluginManager().callEvent(new GameSetEvent(competition));
                    cancel();
                }
                TRACK_COUNTDOWN_SECONDS--;
            }
        };
        runnable.runTaskTimer(plugin,0,20L);
    }

}
