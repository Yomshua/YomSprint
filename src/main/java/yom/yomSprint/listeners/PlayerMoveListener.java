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
import yom.yomSprint.events.GameEndEvent;
import yom.yomSprint.events.PlayerFinishEvent;
import yom.yomSprint.managers.ClassBridge;
import yom.yomSprint.managers.TrackManager;
import yom.yomSprint.models.Lane;
import yom.yomSprint.models.Stamina;
import yom.yomSprint.models.Time;
import yom.yomSprint.models.Track;

import java.util.HashMap;
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
        if (!TrackManager.isPlayerInAnyTrack(player)) return;
        Track track = TrackManager.getTrackByPlayer(player);
        if (track.getLaneHashMap().get(player.getUniqueId()) == null)return;
        Stamina stamina = track.getStaminaMap().get(player.getUniqueId());
        Lane lane = track.getLaneHashMap().get(player.getUniqueId());
        if (track.getGameStatus().equals(GameStatus.IN_SET)) {
            if (!lane.getStartBoudingBox().contains(player)) {
                player.teleport(lane.getStartBoudingBox().getMiddle(player.getWorld()));
            }
        }

        if (track.getGameStatus().equals(GameStatus.READY)) {
            if (!lane.getStartBoudingBox().contains(player)) {
                track.removeGameBoard(player.getUniqueId());
                track.getPlayersInGame().remove(player.getUniqueId());
                player.sendTitle(ChatColor.RED + "Você queimou a largada!", "");
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.teleport(plugin.getLobbyLocation());
                    }
                }.runTaskLater(plugin, 20 * 3);

                    for (UUID uuid : track.getPlayersInGame()) {
                        Player target = Bukkit.getPlayer(uuid);
                        target.sendTitle(ChatColor.RED + player.getName() + " queimou a largada!", "");
                    }
                    track.getSetRunnable().getRunnable().cancel();
                    track.getSetRunnable().start();


            }
        }

        if (track.getGameStatus().equals(GameStatus.OCURRING)) {
            if (!TrackManager.isInsideLane(player,lane)) {
                player.teleport(lane.getStartBoudingBox().getMiddle(player.getWorld()));
            }
        }

        if (stamina.getLevel() < 18) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000, 0));
        }

        // Detecção de pulo
        if (track.getGameStatus().equals(GameStatus.OCURRING)) {
            Location from = event.getFrom();
            Location to = event.getTo();

            if (from.getY() < to.getY() && (to.getY() - from.getY()) > 0.4) {
                stamina.setExpAndLevel(stamina.getLevel() - 2);
            }
        }

        if (track.getPlayersInGame().contains(player.getUniqueId())) {
            if (lane.getEndBoudingBox().contains(player) && classBridge.getAlreadyFinish().get(player.getUniqueId()) == null) {
                Bukkit.getPluginManager().callEvent(new PlayerFinishEvent(track, player, new Time(System.currentTimeMillis())));
            }
        }
    }


}
