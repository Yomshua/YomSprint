package yom.yomSprint.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import yom.yomSprint.models.Competition;

public class GameEndEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    private Competition competition;

    public GameEndEvent(Competition competition) {
        this.competition = competition;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }


    public Competition getGame(){
        return competition;
    }

}
