package yom.yomSprint.boards;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import yom.yomSprint.YomSprint;

import java.util.ArrayList;
import java.util.List;

public final class CompetitionBoard extends YomBoard {

    private List<String> competitionScoreboard;
    private String competitionScoreboardTittle;

    public CompetitionBoard(YomSprint plugin, Player player) {
        super(plugin, player);
        competitionScoreboardTittle = getPlugin().getMessagesConfiguration().getConfig().getString("scoreboards.gameScoreboardTittle");;
        competitionScoreboard = (List<String>) getPlugin().getMessagesConfiguration().getConfig().getList("scoreboards.gameScoreboard");
    }

    @Override
    public List<String> getBoard() {
        List<String> formated = new ArrayList<>();
        for (String line : competitionScoreboard) {
            formated.add(PlaceholderAPI.setPlaceholders(getPlayer(),line));
        }
        return formated;
    }


    @Override
    public String getYomTitle() {
        return competitionScoreboardTittle;
    }
}
