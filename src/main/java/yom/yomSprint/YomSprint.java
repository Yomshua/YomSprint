package yom.yomSprint;

import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.PermissibleBase;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;
import yom.yomSprint.commands.CommandExecutorBase;
import yom.yomSprint.commands.CommandsManager;
import yom.yomSprint.listeners.ChoseGameGUIListener;
import yom.yomSprint.listeners.MainGUIListener;
import yom.yomSprint.managers.TrackManager;
import yom.yomSprint.utils.Track;

import javax.xml.stream.Location;
import java.util.Set;

public final class YomSprint extends JavaPlugin {

    private TracksConfiguration tracksConfiguration = new TracksConfiguration(this);
    private CommandsManager commandsManager;
    private CommandExecutorBase base;

    @Override
    public void onEnable() {
        loadTracks();
        base = new CommandExecutorBase();
        getCommand("run").setExecutor(base);
        getServer().getPluginManager().registerEvents(new MainGUIListener(this),this);
        getServer().getPluginManager().registerEvents(new ChoseGameGUIListener(this),this);
        this.loadDefaulttConfigs();
        commandsManager = new CommandsManager(this);
    }

    @Override
    public void onDisable() {
      
    }

    private void loadTracks(){
        Set<String> tracks = tracksConfiguration.getConfig().getConfigurationSection("tracks").getKeys(false);
        for (String key : getTracksConfiguration().getConfig().getConfigurationSection("tracks").getKeys(false)){
            Track track = new Track(this,getTracksConfiguration().getConfig().getString("tracks." + key + ".display_name" ));
        }
    }

    private void loadDefaulttConfigs(){
        getConfig().options().copyDefaults(false);
        saveConfig();
    }

    public CommandExecutorBase getBase() {
        return base;
    }

    public CommandsManager getCommandsManager() {
        return commandsManager;
    }


    public TracksConfiguration getTracksConfiguration() {
        return tracksConfiguration;
    }
}
