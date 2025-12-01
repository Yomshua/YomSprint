package yom.yomSprint.listeners;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import yom.yomSprint.CustomMessage;
import yom.yomSprint.YomSprint;
import yom.yomSprint.boards.FastBoard;
import yom.yomSprint.events.PlayerJoinWaitLobbyEvent;
import yom.yomSprint.utils.Track;

import java.util.UUID;

public class PlayerJoinWaitLobbyListener implements Listener {

    private YomSprint plugin;

    public PlayerJoinWaitLobbyListener(YomSprint plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    void onJoin(PlayerJoinWaitLobbyEvent event){
        Player player = event.getPlayer();
        player.setInvulnerable(true);
        Track track = event.getTrack();
        track.addPlayerInGame(player);
        CustomMessage.sendCustomActionBar(player,ChatColor.GREEN + "Você entrou na pista: " + ChatColor.GRAY + event.getTrack().getName() ,plugin);
        for (UUID playerBoard : track.getPlayersInGame()){
            FastBoard waitLobbyBoard = track.waitLobbyBoard(Bukkit.getPlayer(playerBoard));
            waitLobbyBoard.updateTitle(ChatColor.AQUA.toString() + ChatColor.BOLD + "TRACK AND FIELD");
            waitLobbyBoard.updateLines(
                    "",
                    ChatColor.WHITE +  "Pista : " + ChatColor.YELLOW + track.getDisplayName(),
                    ChatColor.WHITE +  "Jogadores : " + ChatColor.GREEN +  "(" + track.getWaitLobbySize() + "/" + track.getMaxPlayers() + ")",
                    "",
                    ChatColor.YELLOW + "neoms.gg");

        }
    }

}
