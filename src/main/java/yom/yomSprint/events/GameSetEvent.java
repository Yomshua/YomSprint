package yom.yomSprint.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import yom.yomSprint.models.Competition;
import yom.yomSprint.models.Runner;

import java.util.Set;

public class GameSetEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    Set<Runner> runners;
    Competition competition;

    public GameSetEvent(Competition competition, Set<Runner> runners) {
        this.competition = competition;
        this.runners = runners;
    }

    public Competition getGame() {
       return competition;
    }

    public Set<Runner> getRunners() {
        return runners;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

}
