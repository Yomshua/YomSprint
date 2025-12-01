package yom.yomSprint;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import yom.yomSprint.commands.CommandExecutorBase;
import yom.yomSprint.commands.CommandsManager;
import yom.yomSprint.listeners.*;
import yom.yomSprint.managers.TrackManager;
import yom.yomSprint.utils.Track;

import java.util.Set;
import java.util.UUID;

public final class YomSprint extends JavaPlugin {

    private TracksConfiguration tracksConfiguration;
    private CommandsManager commandsManager;
    private CommandExecutorBase base;
    private Location lobbyLocation;

    @Override
    public void onEnable() {
        this.loadDefaulttConfigs();
        tracksConfiguration = new TracksConfiguration(this);
        loadTracks();
        base = new CommandExecutorBase();
        getCommand("run").setExecutor(base);
        getServer().getPluginManager().registerEvents(new MainGUIListener(this),this);
        getServer().getPluginManager().registerEvents(new PlayerJoinWaitLobbyListener(this),this);
        getServer().getPluginManager().registerEvents(new ChoseGameGUIListener(this),this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(),this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this),this);
        commandsManager = new CommandsManager(this);
        if (!getConfig().contains("main_lobby") || getConfig().get("main_lobby") == null) return;
        if (!getConfig().getBoolean("lobby_activated")) return;
        Object obj = getConfig().get("main_lobby");
        if (!(obj instanceof Location)) {
            getLogger().warning("A chave 'main_lobby' não é uma Location válida!");
            return;
        }
        lobbyLocation = (Location) obj;

    }

    @Override
    public void onDisable() {
      for(Track track : TrackManager.getTracks()){
          for(UUID uuid : track.getPlayersInGame()){
              Player player = Bukkit.getPlayer(uuid);
              // Caso o player esteja em alguma arena!
              player.setInvulnerable(false);
              track.getScoreboardsMap().get(uuid).delete();
          }
      }
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
                    TrackManager.addTrackToList(track);
                }
            }

    }

    private void loadDefaulttConfigs(){
        saveDefaultConfig();
    }

    public CommandExecutorBase getBase() {
        return base;
    }

    public CommandsManager getCommandsManager() {
        return commandsManager;
    }

    public Location getLobbyLocation() {
        return lobbyLocation;
    }

    public TracksConfiguration getTracksConfiguration() {
        return tracksConfiguration;
    }
}
