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
import yom.yomSprint.managers.CompetitionManager;
import yom.yomSprint.models.*;

public class PlayerMoveListener implements Listener {

    YomSprint plugin;


    public PlayerMoveListener(YomSprint plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!plugin.getCompetitionManager().isPlayerInAnyGame(player)) return;
        Competition competition = plugin.getCompetitionManager().getCompetition(player);
        Runner runner = competition.getRunner(player.getUniqueId());
        Track track = competition.getTrack();
        if (track.getLaneHashMap().get(player.getUniqueId()) == null)return;
        Stamina stamina = runner.getStamina();
        Lane lane = track.getLaneHashMap().get(player.getUniqueId());
        if (competition.getStatus().equals(GameStatus.IN_SET)) {
            if (!lane.getStartBoudingBox().contains(player)) {
                player.teleport(lane.getStartBoudingBox().getMiddle(player.getWorld()));
            }
        }

        if (competition.getStatus().equals(GameStatus.READY)) {
            if (!lane.getStartBoudingBox().contains(player)) {
                runner.deleteBoard(runner.getCompetitionBoard());

                competition.getRunners().forEach(otherRunner -> {
                    otherRunner.updateBoard(otherRunner.getCompetitionBoard());
                });

                competition.getRunners().remove(player.getUniqueId());
                player.sendTitle(ChatColor.RED + "Você queimou a largada!", "");
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.teleport(plugin.getLobbyLocation());
                    }
                }.runTaskLater(plugin, 20 * 3);

                    for (Runner otherRunner : competition.getRunners()) {
                        Player target = Bukkit.getPlayer(runner.getUuid());
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

        if (competition.getRunners().contains(runner)) {
            if (lane.getEndBoudingBox().contains(player) && runner.isAlreadyFinished() == false) {
                competition.finishRunner(runner,new Time(System.currentTimeMillis()));
                Bukkit.getPluginManager().callEvent(new PlayerFinishEvent(competition, runner, new Time(System.currentTimeMillis())));
            }
        }
    }


}
