package yom.yomSprint.utils;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import yom.yomSprint.YomSprint;
import yom.yomSprint.models.Competition;
import yom.yomSprint.models.Runner;
import yom.yomSprint.models.Track;

public class Replacer {

    public static String replace(Player player, String params, YomSprint plugin){
        Competition competition = plugin.getCompetitionManager().getCompetition(player);
        Track track = competition.getTrack();
        if (params.equalsIgnoreCase("track_name")) return track.getDisplayName();
        if (params.equalsIgnoreCase("track_minsize")) return String.valueOf(track.getMinPlayers());
        if (params.equalsIgnoreCase("track_maxsize")) return String.valueOf(track.getMaxPlayers());
        if (params.equalsIgnoreCase("track_length")) return String.valueOf(competition.getGameSize());
        if (params.equalsIgnoreCase("player")) return player.getName();
        if (params.equalsIgnoreCase("player_wins")){
            int wins = plugin.getDatabaseAdapter().getTotalWins(player.getUniqueId());
            return String.valueOf(wins);
        }
        if (params.equalsIgnoreCase("player_stamina")){
            Runner runner = competition.getRunner(player.getUniqueId());

            if (runner == null) return null;

            int level = runner.getStamina().getLevel();
            return String.valueOf(level);
        }
        if (params.equalsIgnoreCase("max_stamina")){
            return String.valueOf(36);
        }
        return "";
    }

}
