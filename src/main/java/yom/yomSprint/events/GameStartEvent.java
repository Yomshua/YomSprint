package yom.yomSprint.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import yom.yomSprint.models.Competition;

import java.util.Set;
import java.util.UUID;


public class GameStartEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    Set<UUID> playersList;
    Competition competition;

    public GameStartEvent(Competition competition, Set<UUID> playersList) {
        this.competition = competition;
        this.playersList = playersList;
    }

    public Competition getGame() {
        return competition;
    }

    public Set<UUID> getPlayers() {
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