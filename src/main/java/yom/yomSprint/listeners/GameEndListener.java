package yom.yomSprint.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import yom.yomSprint.YomSprint;
import yom.yomSprint.enums.GameStatus;
import yom.yomSprint.events.GameEndEvent;
import yom.yomSprint.models.Time;
import yom.yomSprint.models.Track;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class GameEndListener implements Listener {

    YomSprint plugin;

    public GameEndListener(YomSprint plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onGameEnd(GameEndEvent event){
        HashMap<UUID, Time> marks = event.getMarks();
        Set<UUID> players = event.getPlayers();
        Track track = event.getTrack();
        new BukkitRunnable() {
            int count = 0;
            @Override
            public void run() {
                count++;
                if (count== 5) {
                    for (UUID uuid : marks.keySet()) {
                        Player player = Bukkit.getPlayer(uuid);
                        player.sendTitle("Teleporting...",null);
                    }
                }
                if (count == 8) {
                    for (UUID uuid : marks.keySet()) {
                        Player player = Bukkit.getPlayer(uuid);
                        player.teleport(plugin.getLobbyLocation());

                    }
                }
                }
        }.runTaskTimer(plugin,0,20L);
        track.setGameStatus(GameStatus.JOIN);

    }


}
