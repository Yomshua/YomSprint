package yom.yomSprint.listeners;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import yom.yomSprint.boards.FastBoard;
import yom.yomSprint.events.PlayerJoinWaitLobbyEvent;
import yom.yomSprint.utils.Track;

public class PlayerJoinWaitLobbyListener implements Listener {

    @EventHandler
    void onJoin(PlayerJoinWaitLobbyEvent event){
        Player player = event.getPlayer();
        Track track = event.getTrack();
        track.addPlayerInGame(player);
        player.sendMessage(ChatColor.GREEN + "Você entrou na pista: " + ChatColor.GRAY + event.getTrack().getName());

        FastBoard waitLobbyBoard = track.waitLobbyBoard(player);
        waitLobbyBoard.updateTitle(ChatColor.AQUA.toString() + ChatColor.BOLD + track.getName());
        waitLobbyBoard.updateLines(
                "",
                ChatColor.GRAY +  "Players :  " + ChatColor.WHITE +  track.getWaitLobbySize());

    }

}
