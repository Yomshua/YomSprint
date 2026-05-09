package yom.yomSprint.listeners;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import yom.yomSprint.boards.CompetitionBoard;
import yom.yomSprint.boards.WaitLobbyBoard;
import yom.yomSprint.managers.BoardManager;
import yom.yomSprint.models.Competition;
import yom.yomSprint.models.Runner;
import yom.yomSprint.models.Stamina;
import yom.yomSprint.utils.CustomMessage;
import yom.yomSprint.YomSprint;
import yom.yomSprint.events.PlayerJoinWaitLobbyEvent;
import yom.yomSprint.models.Track;

import java.util.Map;
import java.util.UUID;

public class PlayerCompetitionListener implements Listener {

    private YomSprint plugin;

    public PlayerCompetitionListener(YomSprint plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinWaitLobbyEvent event) {
        Player player = event.getPlayer();
        Competition competition = event.getCompetition();
        Track track = competition.getTrack();
        player.setInvulnerable(true);

        competition.getRunners().add(new Runner(player.getUniqueId()));
        Runner runner = competition.getRunner(player.getUniqueId());

        runner.setWaitBoard(new WaitLobbyBoard(plugin,player));

        Stamina stamina = runner.getStamina();

        CustomMessage.sendCustomActionBar(player, ChatColor.GREEN + "Você entrou na pista: " + ChatColor.GRAY + competition.getTrack().getName(), plugin);
        competition.getRunners().forEach(otherRunner -> {
            otherRunner.updateBoard(otherRunner.getWaitBoard());
        });

        if (competition.getGameSize() >= track.getMinPlayers()) {
           if (!competition.isRunnableRunining()) {
               competition.getStartCountRunnable().start();
               competition.setRunnableRunining(true);
           }
        }
    }
}
