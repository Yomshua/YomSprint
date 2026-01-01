package yom.yomSprint.boards;

import org.bukkit.entity.Player;
import yom.yomSprint.YomSprint;

import java.util.List;

public class WaitLobbyBoard extends SprintBoard{

    public WaitLobbyBoard(YomSprint plugin, Player player) {
        super(plugin,player);
    }

    @Override
    public List<String> getBoard() {
        return (List<String>) getPlugin().getMessagesConfiguration().getConfig().getList("scoreboards.gameScoreboard");
    }

    @Override
    public String getTittle() {
        return getPlugin().getMessagesConfiguration().getConfig().getString("scoreboard.gameScoreboardTittle");
    }
}
