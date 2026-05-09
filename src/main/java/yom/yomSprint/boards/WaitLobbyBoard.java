package yom.yomSprint.boards;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import yom.yomSprint.YomSprint;

import java.util.ArrayList;
import java.util.List;

public final class WaitLobbyBoard extends YomBoard {

    private String waitLobbyScoreboardTittle;
    private List<String> waitLobbyScoreboard;

    public WaitLobbyBoard(YomSprint plugin, Player player) {
        super(plugin,player);
        waitLobbyScoreboardTittle = getPlugin().getMessagesConfiguration().getConfig().getString("scoreboards.waitLobbyScoreboardTittle");;
        waitLobbyScoreboard = (List<String>) getPlugin().getMessagesConfiguration().getConfig().getList("scoreboards.waitLobbyScoreboard");
    }

    @Override
    public List<String> getBoard() {
        List<String> formated = new ArrayList<>();
        for (String line : waitLobbyScoreboard) {
            formated.add(PlaceholderAPI.setPlaceholders(getPlayer(),line));
        }
        return formated;
    }

    @Override
    public String getYomTitle() {
        return waitLobbyScoreboardTittle;
    }




}
