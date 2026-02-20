package yom.yomSprint.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import yom.yomSprint.YomSprint;
import yom.yomSprint.enums.GameStatus;
import yom.yomSprint.models.Competition;

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
            Set<UUID> listOfPlayers = competition.getRunners();
            listOfPlayers.remove(player.getUniqueId());
            if (competition.getStatus().equals(GameStatus.JOIN)) {
                competition.getTrack().getWaitLobbyScoreboadMap().remove(player.getUniqueId());
                competition.getTrack().updateWaitBoard();
            }else if (competition.getStatus().equals(GameStatus.OCURRING)){
                competition.getTrack().getGameScoreboaMap().remove(player.getUniqueId());
                competition.getTrack().updateGameBoard();
            }
        }
        
    }

}
