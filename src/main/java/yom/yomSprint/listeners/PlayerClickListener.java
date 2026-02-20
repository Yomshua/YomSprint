package yom.yomSprint.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import yom.yomSprint.clicks.ClickChecker;
import yom.yomSprint.enums.GameStatus;
import yom.yomSprint.managers.CompetitionManager;
import yom.yomSprint.models.Competition;
import yom.yomSprint.models.Track;

public class PlayerClickListener implements Listener {


    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (CompetitionManager.isPlayerInAnyGame(player)) {
            Competition competition = CompetitionManager.getGame(player);
            Track track = competition.getTrack();
            if (competition.getStatus().equals(GameStatus.OCURRING)) {
                ClickChecker clickChecker = new ClickChecker(competition.getLastClickMap().get(player.getUniqueId()), competition);
                if (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                    event.setCancelled(true);
                    competition.getLastClickMap().put(player.getUniqueId(),System.currentTimeMillis());

                    if ((player.getEyeLocation().getYaw() > 135.1 && player.getEyeLocation().getYaw() <= 180)
                            || (player.getEyeLocation().getYaw() <= -135.1 && player.getEyeLocation().getYaw() >= -180)) {
                        // NORTE
                        player.setVelocity(new Vector(0, 0, -clickChecker.getBoostByQuality(player)));
                    } else if (player.getEyeLocation().getYaw() <= 135.0 && player.getEyeLocation().getYaw() > 45) {
                        // OESTE
                        player.setVelocity(new Vector(-clickChecker.getBoostByQuality(player), 0, 0));
                    } else if (
                            (player.getEyeLocation().getYaw() >= 0 && player.getEyeLocation().getYaw() <= 45)
                                    || (player.getEyeLocation().getYaw() >= -45 && player.getEyeLocation().getYaw() <= 0)
                    ) {
                        // SUL
                        player.setVelocity(new Vector(0, 0, clickChecker.getBoostByQuality(player)));
                    } else if (
                            (player.getEyeLocation().getYaw() > -135.0 && player.getEyeLocation().getYaw() < -45)
                    ) {
                        // LESTE
                        player.setVelocity(new Vector(clickChecker.getBoostByQuality(player), 0, 0));
                    }

                }

            }
        }

    }

}
