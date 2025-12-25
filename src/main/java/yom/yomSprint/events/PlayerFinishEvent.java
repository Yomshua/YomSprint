package yom.yomSprint.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import yom.yomSprint.models.Time;
import yom.yomSprint.models.Track;

import java.util.List;

public class PlayerFinishEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    Player player;
    Track track;
    Time time;

    public PlayerFinishEvent(Track track, Player player,Time time) {
        this.track = track;
        this.player = player;
        this.time = time;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public Player getPlayer() {
        return player;
    }

    public Track getTrack() {
        return track;
    }

    public Time getTime() {
        return time;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

}
