package yom.yomSprint.managers;

import me.clip.placeholderapi.PlaceholderAPI;
import yom.yomSprint.boards.fastboardAPI.FastBoard;

import java.util.List;

public class BoardManager {

    public void updateWaitBoard(FastBoard scoreboard) {

        scoreboard.updateTitle(scoreboard.getTitle());

        List<String> formatedBoard = scoreboard.getLines();

        for (String line : formatedBoard){
            line = PlaceholderAPI.setPlaceholders(scoreboard.getPlayer(),line);
        }

        scoreboard.updateLines(scoreboard.getLines());
    }

}
