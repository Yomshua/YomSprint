package yom.yomSprint.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import yom.yomSprint.models.Competition;

public class PlayerJoinWaitLobbyEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    Player player;
    Competition competition;

    public PlayerJoinWaitLobbyEvent(Competition competition, Player player) {
        this.competition = competition;
        this.player = player;
    }

    public Competition getCompetition() {
        return competition;
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
