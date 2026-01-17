package yom.yomSprint.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import yom.yomSprint.enums.GameStatus;
import yom.yomSprint.events.GameStartEvent;
import yom.yomSprint.models.Track;

import java.util.UUID;


public class GameStartListener implements Listener {

    @EventHandler
    void gameStart(GameStartEvent event){
        Track track = event.getTrack();
        for (UUID uuid : event.getPlayers()){
            track.getLastClickMap().put(uuid,System.currentTimeMillis());
        }
        track.setGameStatus(GameStatus.OCURRING);
        track.setWhenGameStarted(System.currentTimeMillis());
    }



}
