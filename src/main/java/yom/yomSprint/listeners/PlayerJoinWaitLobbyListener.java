package yom.yomSprint.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import yom.yomSprint.events.PlayerJoinWaitLobbyEvent;

public class PlayerJoinWaitLobbyListener implements Listener {

    @EventHandler
    void onJoin(PlayerJoinWaitLobbyEvent event){
        Player player = event.getPlayer();
        event.getTrack().addPlayerInGame(player);
        player.sendMessage(event.getTrack().getPlayersInGame().size() +"");
    }

}
