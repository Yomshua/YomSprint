package yom.yomSprint.configurations;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import yom.yomSprint.YomSprint;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class PlayersConfiguration {

    private UUID uuid;
    YomSprint plugin;
    File file;
    FileConfiguration config;

    public PlayersConfiguration(UUID uuid,YomSprint plugin) {
        this.uuid =  uuid;
        this.plugin = plugin;
        file = new File(plugin.getDataFolder().getAbsolutePath() + File.separator + "players",uuid.toString()+".yml");
        notExists();
        config = YamlConfiguration.loadConfiguration(file);

    }

    private void notExists(){
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
