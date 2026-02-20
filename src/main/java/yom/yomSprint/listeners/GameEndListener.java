package yom.yomSprint.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import yom.yomSprint.YomSprint;
import yom.yomSprint.enums.GameStatus;
import yom.yomSprint.events.GameEndEvent;
import yom.yomSprint.models.Competition;
import yom.yomSprint.models.Track;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class GameEndListener implements Listener {

    YomSprint plugin;

    public GameEndListener(YomSprint plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onGameEnd(GameEndEvent event){
        Competition competition = event.getGame();
        Track track = competition.getTrack();
        ArrayList<UUID> marks = competition.getMarks();
        Set<UUID> players = competition.getRunners();
        competition.setGameStatus(GameStatus.JOIN);
        competition.setRunnableRunining(false);
        new BukkitRunnable() {
            int count = 0;
            @Override
            public void run() {
                count++;
                if (count== 5) {
                    for (UUID uuid : marks) {
                        Player player = Bukkit.getPlayer(uuid);
                        player.sendTitle("Teleporting...",null);
                    }
                }
                if (count == 8) {
                    for (UUID uuid : marks) {
                        track.removeGameBoard(uuid);
                        Player player = Bukkit.getPlayer(uuid);
                        player.teleport(plugin.getLobbyLocation());
                        player.setInvulnerable(false);
                        player.setExp(0);
                        player.removePotionEffect(PotionEffectType.SLOW);
                        players.remove(uuid);
                    }
                    competition.reload();
                    cancel();
                }
                }
        }.runTaskTimer(plugin,0,20L);


    }


}
