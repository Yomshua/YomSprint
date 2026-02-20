package yom.yomSprint.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import yom.yomSprint.models.Competition;
import yom.yomSprint.models.Time;

public class PlayerFinishEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    Player player;
    Competition competition;
    Time time;

    public PlayerFinishEvent(Competition competition, Player player, Time time) {
        this.competition = competition;
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

    public Competition getGame() {
        return competition;
    }

    public Time getTime() {
        return time;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

}
