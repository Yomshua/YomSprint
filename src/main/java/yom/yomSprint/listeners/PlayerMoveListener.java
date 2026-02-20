package yom.yomSprint.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import yom.yomSprint.YomSprint;
import yom.yomSprint.enums.GameStatus;
import yom.yomSprint.events.PlayerFinishEvent;
import yom.yomSprint.managers.ClassBridge;
import yom.yomSprint.managers.CompetitionManager;
import yom.yomSprint.models.*;

import java.util.UUID;

public class PlayerMoveListener implements Listener {

    YomSprint plugin;
    ClassBridge classBridge;

    public PlayerMoveListener(YomSprint plugin) {
        this.plugin = plugin;
        this.classBridge = plugin.getClassBridge();
    }

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!CompetitionManager.isPlayerInAnyGame(player)) return;
        Competition competition = CompetitionManager.getGame(player);
        Track track = competition.getTrack();
        if (track.getLaneHashMap().get(player.getUniqueId()) == null)return;
        Stamina stamina = competition.getStaminaMap().get(player.getUniqueId());
        Lane lane = track.getLaneHashMap().get(player.getUniqueId());
        if (competition.getStatus().equals(GameStatus.IN_SET)) {
            if (!lane.getStartBoudingBox().contains(player)) {
                player.teleport(lane.getStartBoudingBox().getMiddle(player.getWorld()));
            }
        }

        if (competition.getStatus().equals(GameStatus.READY)) {
            if (!lane.getStartBoudingBox().contains(player)) {
                track.removeGameBoard(player.getUniqueId());
                competition.getRunners().remove(player.getUniqueId());
                player.sendTitle(ChatColor.RED + "Você queimou a largada!", "");
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.teleport(plugin.getLobbyLocation());
                    }
                }.runTaskLater(plugin, 20 * 3);

                    for (UUID uuid : competition.getRunners()) {
                        Player target = Bukkit.getPlayer(uuid);
                        target.sendTitle(ChatColor.RED + player.getName() + " queimou a largada!", "");
                    }
                    competition.getSetRunnable().getRunnable().cancel();
                    competition.getSetRunnable().start();


            }
        }

        if (competition.getStatus().equals(GameStatus.OCURRING)) {
            if (!CompetitionManager.isInsideLane(player,lane)) {
                player.teleport(lane.getStartBoudingBox().getMiddle(player.getWorld()));
            }
        }

        if (stamina.getLevel() < 18) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000, 0));
        }

        // Detecção de pulo
        if (competition.getStatus().equals(GameStatus.OCURRING)) {
            Location from = event.getFrom();
            Location to = event.getTo();

            if (from.getY() < to.getY() && (to.getY() - from.getY()) > 0.4) {
                stamina.setExpAndLevel(stamina.getLevel() - 2);
            }
        }

        if (competition.getRunners().contains(player.getUniqueId())) {
            if (lane.getEndBoudingBox().contains(player) && classBridge.getAlreadyFinish().get(player.getUniqueId()) == null) {
                Bukkit.getPluginManager().callEvent(new PlayerFinishEvent(competition, player, new Time(System.currentTimeMillis())));
            }
        }
    }


}
