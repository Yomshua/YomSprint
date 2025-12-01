package yom.yomSprint.configurations;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import yom.yomSprint.YomSprint;

import java.io.File;
import java.io.IOException;

public class TracksConfiguration {

    private YomSprint plugin;
    private File file;
    private FileConfiguration config;

    public TracksConfiguration(YomSprint plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "tracks.yml");
        notExists();
        config = YamlConfiguration.loadConfiguration(file);
    }

    private void notExists(){
        if(!file.exists()){
            plugin.saveResource("tracks.yml",false);
        }
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
