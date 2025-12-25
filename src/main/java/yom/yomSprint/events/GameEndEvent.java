package yom.yomSprint.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import yom.yomSprint.models.Time;
import yom.yomSprint.models.Track;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class GameEndEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    private Set<UUID> players;
    private HashMap<UUID, Time> marks;
    private Track track;

    public GameEndEvent(Track track, Set<UUID> players,HashMap<UUID,Time> marks) {
        this.track = track;
        this.players = players;
        this.marks = marks;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public HashMap<UUID, Time> getMarks() {
        return marks;
    }

    public Set<UUID> getPlayers() {
        return players;
    }


    public Track getTrack() {
        return track;
    }

}
