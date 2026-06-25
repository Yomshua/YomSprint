package yom.yomSprint.utils;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yom.yomSprint.YomSprint;

public class PlaceholderSprint extends PlaceholderExpansion {

    YomSprint plugin;

    public PlaceholderSprint(YomSprint plugin) {
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
        return Replacer.replace(player,params,plugin);
    }
}

