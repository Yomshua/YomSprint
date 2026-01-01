package yom.yomSprint.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import yom.yomSprint.clicks.ClickChecker;
import yom.yomSprint.enums.GameStatus;
import yom.yomSprint.managers.TrackManager;
import yom.yomSprint.models.Stamina;
import yom.yomSprint.models.Track;

import java.util.HashMap;
import java.util.UUID;

public class PlayerClickListener implements Listener {


    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (TrackManager.isPlayerInAnyTrack(player)) {
            Track track = TrackManager.getTrackByPlayer(player);
            if (track.getGameStatus().equals(GameStatus.OCURRING)) {
                ClickChecker clickChecker = new ClickChecker(track.getLastClickMap().get(player.getUniqueId()),track);
                if (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                    event.setCancelled(true);
                    track.getLastClickMap().put(player.getUniqueId(),System.currentTimeMillis());

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
