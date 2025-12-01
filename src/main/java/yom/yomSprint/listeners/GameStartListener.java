package yom.yomSprint.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import yom.yomSprint.models.Lane;
import yom.yomSprint.events.GameStartEvent;
import yom.yomSprint.models.Track;

import java.util.Set;
import java.util.UUID;

public class GameStartListener implements Listener {

    //Quando o jogo começar, todos os players são teleportados para sua raia perspectivamente na ordem de entrada
    @EventHandler
    void gameStart(GameStartEvent event){
        Track track = event.getTrack();
        Set<UUID> players = event.getPlayer();
        int countToGetLane = 0;
        for(UUID uuid : players){
            Player player = Bukkit.getPlayer(uuid);
            Lane lane = track.getLanes().get(countToGetLane);
            player.teleport(lane.getLineLocation());
            player.sendTitle(ChatColor.AQUA.toString() + ChatColor.BOLD + "RAIA", String.valueOf(lane.getNumber()));
            countToGetLane++;
        }

    }

}
