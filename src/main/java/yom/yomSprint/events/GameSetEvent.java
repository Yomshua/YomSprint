package yom.yomSprint.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import yom.yomSprint.models.Competition;
import yom.yomSprint.models.Runner;

import java.util.Set;

public class GameSetEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    Competition competition;

    public GameSetEvent(Competition competition) {
        this.competition = competition;
    }

    public Competition getGame() {
       return competition;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

}
