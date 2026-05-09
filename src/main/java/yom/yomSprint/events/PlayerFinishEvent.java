package yom.yomSprint.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import yom.yomSprint.models.Competition;
import yom.yomSprint.models.Runner;
import yom.yomSprint.models.Time;

public class PlayerFinishEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    Runner runner;
    Competition competition;
    Time time;

    public PlayerFinishEvent(Competition competition, Runner runner, Time time) {
        this.competition = competition;
        this.runner = runner;
        this.time = time;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public Runner getRunner() {
        return runner;
    }

    public Competition getCompetition() {
        return competition;
    }

    public Time getTime() {
        return time;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

}
