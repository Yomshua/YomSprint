package yom.yomSprint.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import yom.yomSprint.enums.GameStatus;
import yom.yomSprint.events.GameStartEvent;
import yom.yomSprint.models.Competition;

import java.util.UUID;


public class GameStartListener implements Listener {

    @EventHandler
    void gameStart(GameStartEvent event){
        Competition competition = event.getGame();
        for (UUID uuid : event.getPlayers()){
            competition.getLastClickMap().put(uuid,System.currentTimeMillis());
        }
        competition.setGameStatus(GameStatus.OCURRING);
        competition.setWhenGameStarted(System.currentTimeMillis());
    }



}
