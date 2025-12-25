package yom.yomSprint.configurations;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import yom.yomSprint.YomSprint;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MessagesConfiguration {

    public String track_join = "&aVocê entrou na pista <track_name>";
    public String track_leave = "&aVocê saiu da pista <track_name>";

    YomSprint plugin;
    File file;
    FileConfiguration config;

    public MessagesConfiguration(YomSprint plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "messages.yml");
        notExists();
        this.config = YamlConfiguration.loadConfiguration(file);
        addDefaults();
        saveConfig();
        init();
    }

    private void init() {

        config.addDefault("messages.track_join", track_join);
        config.addDefault("messages.track_leave", track_leave);

        config.options().copyDefaults(true);
        saveConfig();

        track_join = translateColorCodes("messages.track_join");
        track_leave = translateColorCodes("messages.track_leave");

    }

    private void notExists() {
        if (!file.exists()) {
            plugin.saveResource("messages.yml", false);
        }
    }

    private String translateColorCodes(String path) {
        return ChatColor.translateAlternateColorCodes('&', config.getString(path));
    }

    private void addDefaults() {
        String waitLobbyScoreboardTittle = ChatColor.AQUA.toString() + ChatColor.BOLD + "TRACK AND FIELD";
        List<String> waitobbyScoreboard = Arrays.asList(
                "",
                ChatColor.WHITE + "Pista : " + ChatColor.YELLOW + "<name>",
                ChatColor.WHITE + "Jogadores : " + ChatColor.GREEN + "(" + "<track_minSize>" + "/" + "<track_maxSize>" + ")",
                "",
                ChatColor.YELLOW + "neoms.gg");
        config.addDefault("scoreboards.waitLobbyScoreboardTittle", waitLobbyScoreboardTittle);
        config.addDefault("scoreboards.waitLobbyScoreboard", waitobbyScoreboard);

        String gameScoreboardTittle = ChatColor.AQUA.toString() + ChatColor.BOLD + "TRACK AND FIELD";
        List<String> gameScoreboard = Arrays.asList(
                "",
                ChatColor.WHITE + "Pista : " + ChatColor.YELLOW + "<name>",
                ChatColor.WHITE + "Jogadores : " + ChatColor.GREEN + "(" + "<track_minSize>" + "/" + "<track_maxSize>" + ")",
                "",
                ChatColor.YELLOW + "neoms.gg");
        config.addDefault("scoreboards.gameScoreboardTittle", gameScoreboardTittle);
        config.addDefault("scoreboards.gameScoreboard", gameScoreboard);
        config.options().copyDefaults(true);
    }

    public void saveConfig() {
        try {
            config.save(this.file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }

}
