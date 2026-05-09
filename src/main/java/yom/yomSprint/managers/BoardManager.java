package yom.yomSprint.managers;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import yom.yomSprint.YomSprint;
import yom.yomSprint.boards.YomBoard;
import yom.yomSprint.models.Competition;
import yom.yomSprint.models.Runner;

import java.util.*;

public class BoardManager {

    YomSprint plugin;

    public BoardManager(YomSprint plugin) {
        this.plugin = plugin;
    }

    public YomBoard getScoreboard(Player player) {
        Competition competition = plugin.getCompetitionManager().getCompetition(player);
        if (competition == null) return null;

        UUID uuid = player.getUniqueId();
        YomBoard watiBoard = competition.getRunner(uuid).getWaitBoard();
        YomBoard competitionBoard = competition.getRunner(uuid).getCompetitionBoard();

        switch (competition.getStatus()) {
            case JOIN:
                return  watiBoard;
            case OCURRING:
                return competitionBoard;
        }

        return null;
    }




}
