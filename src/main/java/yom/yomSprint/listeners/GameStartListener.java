package yom.yomSprint.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import yom.yomSprint.enums.GameStatus;
import yom.yomSprint.events.GameStartEvent;
import yom.yomSprint.models.Track;


public class GameStartListener implements Listener {

    //Quando o jogo começar, todos os players são teleportados para sua raia perspectivamente na ordem de entrada
    @EventHandler
    void gameStart(GameStartEvent event){
        Track track = event.getTrack();
        track.setGameStatus(GameStatus.OCURRING);
        track.setWhenGameStarted(System.currentTimeMillis());
    }



}
