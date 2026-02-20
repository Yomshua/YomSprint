package yom.yomSprint.utils;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yom.yomSprint.YomSprint;
import yom.yomSprint.configurations.PlayersConfiguration;
import yom.yomSprint.managers.CompetitionManager;
import yom.yomSprint.models.Competition;
import yom.yomSprint.models.Track;

public class PlacheHolderSprint extends PlaceholderExpansion {

    YomSprint plugin;

    public PlacheHolderSprint(YomSprint plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "yomsprint";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Yomshua";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }
        @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        Competition competition = CompetitionManager.getGame(player);
        Track track = competition.getTrack();
        if (params.equalsIgnoreCase("track_name")) return track.getDisplayName();
        if (params.equalsIgnoreCase("track_minsize")) return String.valueOf(track.getMinPlayers());
        if (params.equalsIgnoreCase("track_maxsize")) return String.valueOf(track.getMaxPlayers());
        if (params.equalsIgnoreCase("track_length")) return String.valueOf(competition.getGameSize());
        if (params.equalsIgnoreCase("player")) return player.getName();
        if (params.equalsIgnoreCase("player_wins")){
            PlayersConfiguration playersConfiguration = new PlayersConfiguration(player.getUniqueId(),plugin);
            int wins = playersConfiguration.getConfig().getInt("wins");
            return String.valueOf(wins);
        }
        if (params.equalsIgnoreCase("player_stamina")){
           int level = competition.getStaminaMap().get(player.getUniqueId()).getLevel();
           return String.valueOf(level);
        }
        if (params.equalsIgnoreCase("max_stamina")){
            return String.valueOf(36);
        }

        return null;
    }
}
