package yom.yomSprint.listeners;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import yom.yomSprint.models.Stamina;
import yom.yomSprint.utils.CustomMessage;
import yom.yomSprint.YomSprint;
import yom.yomSprint.boards.FastBoard;
import yom.yomSprint.events.PlayerJoinWaitLobbyEvent;
import yom.yomSprint.models.Track;

import java.util.HashMap;
import java.util.UUID;

public class PlayerTrackListener implements Listener {

    private YomSprint plugin;

    public PlayerTrackListener(YomSprint plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinWaitLobbyEvent event) {
        Player player = event.getPlayer();
        Track track = event.getTrack();
        player.setInvulnerable(true);
        if (!track.getWaitLobbyScoreboadMap().containsKey(player.getUniqueId())){
            track.getWaitLobbyScoreboadMap().put(player.getUniqueId(),new FastBoard(player));
        }
        HashMap<UUID, Stamina> staminaMap = track.getStaminaMap();
        staminaMap.put(player.getUniqueId(),new Stamina(player.getUniqueId(),track));
        Stamina stamina = staminaMap.get(player.getUniqueId());
        stamina.setExpAndLevel(36);
        track.addPlayerInGame(player);
        CustomMessage.sendCustomActionBar(player, ChatColor.GREEN + "Você entrou na pista: " + ChatColor.GRAY + event.getTrack().getName(), plugin);
        // Atualiza o board de todos os players
        track.updateWaitBoard();
        // Começa a contagem regresiva
        if (track.getWaitLobbySize() >= track.getMinPlayers()) {
           if (!track.isRunnableRunining()) {
               track.getStartCountRunnable().start();
               track.setRunnableRunining(true);
           }
        }


    }

}
