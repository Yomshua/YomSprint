package yom.yomSprint.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import yom.yomSprint.YomSprint;
import yom.yomSprint.enums.GameStatus;
import yom.yomSprint.managers.BoardManager;
import yom.yomSprint.models.Competition;
import yom.yomSprint.models.Runner;

import java.util.Set;
import java.util.UUID;


public class PlayerQuitListener implements Listener {

    YomSprint plugin;

    public PlayerQuitListener(YomSprint plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    void onQuitEvent(PlayerQuitEvent event){
        Player player = event.getPlayer();
        player.setInvulnerable(false);
        if(plugin.getCompetitionManager().isPlayerInAnyGame(player)){
            Competition competition = plugin.getCompetitionManager().getCompetition(player);
            Runner runner = competition.getRunner(player.getUniqueId());
            Set<Runner> runners = competition.getRunners();
            runners.remove(runner);

            for (Runner otherRunner : runners){
                if (competition.getStatus().equals(GameStatus.JOIN)) {
                    otherRunner.updateBoard(otherRunner.getWaitBoard());
                }else if (competition.getStatus().equals(GameStatus.OCURRING)){
                    otherRunner.updateBoard(otherRunner.getCompetitionBoard());
                }
            }


        }
        
    }

}
