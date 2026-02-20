package yom.yomSprint.listeners;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import yom.yomSprint.models.Competition;
import yom.yomSprint.models.Stamina;
import yom.yomSprint.utils.CustomMessage;
import yom.yomSprint.YomSprint;
import yom.yomSprint.boards.fastboardAPI.FastBoard;
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
        if (!track.getWaitLobbyScoreboadMap().containsKey(player.getUniqueId())){
            track.getWaitLobbyScoreboadMap().put(player.getUniqueId(),new FastBoard(player));
        }
        Map<UUID, Stamina> staminaMap = competition.getStaminaMap();
        staminaMap.put(player.getUniqueId(),new Stamina(player.getUniqueId(),track));
        Stamina stamina = staminaMap.get(player.getUniqueId());
        stamina.setExpAndLevel(36);
        competition.getRunners().add(player.getUniqueId());
        CustomMessage.sendCustomActionBar(player, ChatColor.GREEN + "Você entrou na pista: " + ChatColor.GRAY + competition.getTrack().getName(), plugin);

        track.updateWaitBoard();

        if (competition.getGameSize() >= track.getMinPlayers()) {
           if (!competition.isRunnableRunining()) {
               competition.getStartCountRunnable().start();
               competition.setRunnableRunining(true);
           }
        }


    }

}
