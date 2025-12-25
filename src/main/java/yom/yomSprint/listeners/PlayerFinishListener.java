package yom.yomSprint.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import yom.yomSprint.YomSprint;
import yom.yomSprint.events.GameEndEvent;
import yom.yomSprint.events.PlayerFinishEvent;
import yom.yomSprint.managers.ClassBridge;
import yom.yomSprint.managers.SpectatorManager;
import yom.yomSprint.managers.TimeManager;
import yom.yomSprint.models.Track;

public class PlayerFinishListener implements Listener {

    YomSprint plugin;
    ClassBridge classBridge;
    SpectatorManager spectatorManager;

    public PlayerFinishListener(YomSprint plugin) {
        this.plugin = plugin;
        this.classBridge = plugin.getClassBridge();
        this.spectatorManager = plugin.getSpectatorManager();
    }

    @EventHandler
    public void finishEvent(PlayerFinishEvent event){
        Player player = event.getPlayer();
        Track track = event.getTrack();
        track.getMarks().put(player.getUniqueId(),event.getTime());
        long finishTime = event.getTime().getTimeFinished() - track.getWhenGameStarted();

        String time = TimeManager.getTimeInSeconds(finishTime);
        player.sendTitle(time,null);
        classBridge.getAlreadyFinish().put(player.getUniqueId(),true);
        spectatorManager.setSpectate(player,track.getPlayersInGame());
        track.getPlayersInGame().remove(player.getUniqueId());


        if (track.getPlayersInGame().size() == 0){
            Bukkit.getPluginManager().callEvent(new GameEndEvent(track,track.getPlayersInGame(),track.getMarks()));
        }

    }

}
