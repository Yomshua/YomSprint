package yom.yomSprint.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import yom.yomSprint.enums.GameStatus;
import yom.yomSprint.events.GameStartEvent;
import yom.yomSprint.models.Competition;
import yom.yomSprint.models.Runner;


public class GameStartListener implements Listener {

    @EventHandler
    void gameStart(GameStartEvent event){
        Competition competition = event.getCompetition();
        for (Runner runner : event.getCompetition().getRunners()){
            runner.setLastClick(System.currentTimeMillis());
        }
        competition.setGameStatus(GameStatus.OCURRING);
        competition.setWhenGameStarted(System.currentTimeMillis());
    }



}
