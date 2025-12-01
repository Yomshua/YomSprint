package yom.yomSprint.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import yom.yomSprint.models.Track;

import java.util.Set;
import java.util.UUID;


public class GameStartEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    Set<UUID> playersList;
    Track track;

    public GameStartEvent(Track track,Set<UUID> playersList) {
        this.track = track;
        this.playersList = playersList;
    }

    public Track getTrack() {
        return track;
    }

    public Set<UUID> getPlayer() {
        return playersList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}