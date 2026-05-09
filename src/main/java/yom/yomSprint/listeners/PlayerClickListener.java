package yom.yomSprint.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import yom.yomSprint.YomSprint;
import yom.yomSprint.clicks.ClickChecker;
import yom.yomSprint.enums.GameStatus;
import yom.yomSprint.managers.CompetitionManager;
import yom.yomSprint.models.Competition;
import yom.yomSprint.models.Runner;
import yom.yomSprint.models.Track;

public class PlayerClickListener implements Listener {

    YomSprint plugin;

    public PlayerClickListener(YomSprint plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (plugin.getCompetitionManager().isPlayerInAnyGame(player)) {
            Competition competition = plugin.getCompetitionManager().getCompetition(player);
            Track track = competition.getTrack();
            Runner runner = competition.getRunner(player.getUniqueId());

            if (competition.getStatus().equals(GameStatus.OCURRING)) {
                ClickChecker clickChecker = new ClickChecker(runner.getLastClick(), competition, plugin);
                if (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                    event.setCancelled(true);


                    runner.setLastClick(System.currentTimeMillis());

                    if ((player.getEyeLocation().getYaw() > 135.1 && player.getEyeLocation().getYaw() <= 180)
                            || (player.getEyeLocation().getYaw() <= -135.1 && player.getEyeLocation().getYaw() >= -180)) {
                        // NORTE
                        player.setVelocity(new Vector(0, 0, -clickChecker.getBoostByQuality(runner)));
                    } else if (player.getEyeLocation().getYaw() <= 135.0 && player.getEyeLocation().getYaw() > 45) {
                        // OESTE
                        player.setVelocity(new Vector(-clickChecker.getBoostByQuality(runner), 0, 0));
                    } else if (
                            (player.getEyeLocation().getYaw() >= 0 && player.getEyeLocation().getYaw() <= 45)
                                    || (player.getEyeLocation().getYaw() >= -45 && player.getEyeLocation().getYaw() <= 0)
                    ) {
                        // SUL
                        player.setVelocity(new Vector(0, 0, clickChecker.getBoostByQuality(runner)));
                    } else if (
                            (player.getEyeLocation().getYaw() > -135.0 && player.getEyeLocation().getYaw() < -45)
                    ) {
                        // LESTE
                        player.setVelocity(new Vector(clickChecker.getBoostByQuality(runner), 0, 0));
                    }

                }

            }
        }

    }

}
