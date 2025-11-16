package yom.yomSprint;

import org.bukkit.plugin.java.JavaPlugin;
import yom.yomSprint.commands.CommandExecutorBase;
import yom.yomSprint.commands.CommandsManager;
import yom.yomSprint.listeners.ChoseGameGUIListener;
import yom.yomSprint.listeners.MainGUIListener;
import yom.yomSprint.listeners.PlayerJoinWaitLobbyListener;
import yom.yomSprint.listeners.PlayerQuitListener;
import yom.yomSprint.utils.Track;

import java.util.Set;

public final class YomSprint extends JavaPlugin {

    private TracksConfiguration tracksConfiguration;
    private CommandsManager commandsManager;
    private CommandExecutorBase base;


    @Override
    public void onEnable() {

        tracksConfiguration = new TracksConfiguration(this);
        loadTracks();
        base = new CommandExecutorBase();
        getCommand("run").setExecutor(base);
        getServer().getPluginManager().registerEvents(new MainGUIListener(this),this);
        getServer().getPluginManager().registerEvents(new PlayerJoinWaitLobbyListener(),this);
        getServer().getPluginManager().registerEvents(new ChoseGameGUIListener(this),this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(),this);
        this.loadDefaulttConfigs();
        commandsManager = new CommandsManager(this);

    }

    @Override
    public void onDisable() {
      
    }

    private void loadTracks(){
            Set<String> tracks = tracksConfiguration.getConfig().getConfigurationSection("tracks").getKeys(false);
            for (String key : tracks) {
                Track track = new Track.TrackBuilder()
                        .setName(key)
                        .setDisplayName(tracksConfiguration.getConfig().getString("tracks." + key + ".display_name"))
                        .setMinSize(tracksConfiguration.getConfig().getInt("tracks." + key + ".min_players"))
                        .setMaxSize(tracksConfiguration.getConfig().getInt("tracks." + key + ".max_players"))
                        .setPlugin(this)
                        .build();
                track.loadConfigs();
                if(track.hasAllConfigs()){
                    track.addToList();
                }
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
