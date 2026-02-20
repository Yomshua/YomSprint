package yom.yomSprint.boards;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import yom.yomSprint.YomSprint;

import java.util.List;

public class WaitLobbyBoard extends CompetitionBoard {

    private String waitLobbyScoreboardTittle;
    private List<String> waitLobbyScoreboard;

    public WaitLobbyBoard(YomSprint plugin, Player player) {
        super(plugin,player);
        waitLobbyScoreboardTittle = getPlugin().getMessagesConfiguration().getConfig().getString("scoreboard.gameScoreboardTittle");;
        waitLobbyScoreboard = (List<String>) getPlugin().getMessagesConfiguration().getConfig().getList("scoreboards.gameScoreboard");
    }

    @Override
    public List<String> getBoard() {
        for (String line : waitLobbyScoreboard) {
            line = PlaceholderAPI.setPlaceholders(getPlayer(),line);
        }
        return waitLobbyScoreboard;
    }

    @Override
    public String getTittle() {
        return waitLobbyScoreboardTittle;
    }




}
