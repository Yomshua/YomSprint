package yom.yomSprint.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import yom.yomSprint.run.ClickChecker;
import yom.yomSprint.enums.GameStatus;
import yom.yomSprint.managers.TrackManager;
import yom.yomSprint.models.Track;

public class PlayerClickListener implements Listener {

    long lastClick = 0;
    ClickChecker clickChecker = new ClickChecker(lastClick);

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (TrackManager.isPlayerInAnyTrack(player)) {
            Track track = TrackManager.getTrackByPlayer(player);
            if (track.getGameStatus().equals(GameStatus.OCURRING)) {

                if (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                    event.setCancelled(true);

                    clickChecker.setTimeInMillis(lastClick);
                    lastClick = System.currentTimeMillis();


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
