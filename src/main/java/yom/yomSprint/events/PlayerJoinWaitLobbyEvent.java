package yom.yomSprint.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import yom.yomSprint.utils.Track;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerJoinWaitLobbyEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    Player player;
    Track track;

    public PlayerJoinWaitLobbyEvent(Track track,Player player) {
        this.track = track;
        this.player = player;
    }

    public Track getTrack() {
        return track;
    }

    public Player getPlayer() {
        return player;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
