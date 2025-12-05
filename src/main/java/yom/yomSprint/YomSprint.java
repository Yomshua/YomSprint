package yom.yomSprint;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import yom.yomSprint.commands.CommandExecutorBase;
import yom.yomSprint.commands.CommandsManager;
import yom.yomSprint.configurations.TracksConfiguration;
import yom.yomSprint.listeners.*;
import yom.yomSprint.managers.TrackManager;
import yom.yomSprint.models.Lane;
import yom.yomSprint.models.Track;

import java.util.ArrayList;
import java.util.List;
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
        getServer().getPluginManager().registerEvents(new MainGUIListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerTrackListener(this), this);
        getServer().getPluginManager().registerEvents(new ChoseGameGUIListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new GameStartListener(),this);
        commandsManager = new CommandsManager(this);
        if (!getConfig().contains("main_lobby") || getConfig().get("main_lobby") == null) return;
        if (!getConfig().getBoolean("lobby_activated")) return;
        Object obj = getConfig().get("main_lobby");

        if (!(obj instanceof Location)) {
            getLogger().warning("A chave 'main_lobby' não é uma Location válida!");
            //Caso a location não seja válida, já é desativado o "lobby_activated"
            getConfig().set("lobby_activated", false);
            return;
        }

        lobbyLocation = (Location) obj;

    }

    @Override
    public void onDisable() {
        for (Track track : TrackManager.getTracks()) {
            for (UUID uuid : track.getPlayersInGame()) {
                Player player = Bukkit.getPlayer(uuid);
                // Caso o player esteja em alguma arena!
                player.setInvulnerable(false);
                if (getConfig().getBoolean("lobby_activated")) {
                    player.teleport(lobbyLocation);
                }
                track.getScoreboardsMap().get(uuid).delete();
            }
        }
    }


    private void loadTracks() {
        Set<String> tracks = tracksConfiguration.getConfig().getConfigurationSection("tracks").getKeys(false);
        for (String key : tracks) {
            List<Lane> lanes = new ArrayList<>();
            int lanesCount = 1;
            for(String string : tracksConfiguration.getConfig().getConfigurationSection("tracks." + key + ".lanes").getKeys(false)){
                Location location = tracksConfiguration.getConfig().getConfigurationSection("tracks." + key + ".lanes").getLocation(String.valueOf(lanesCount));
                lanes.add(new Lane(location,lanesCount));
                lanesCount++;
            }
            Track track = new Track.TrackBuilder()
                    .setName(key)
                    .setDisplayName(tracksConfiguration.getConfig().getString("tracks." + key + ".display_name"))
                    .setMinSize(tracksConfiguration.getConfig().getInt("tracks." + key + ".min_players"))
                    .setMaxSize(tracksConfiguration.getConfig().getInt("tracks." + key + ".max_players"))
                    .setLanes(lanes)
                    .setPlugin(this)
                    .build();
            track.loadConfigs();
            if (track.hasAllConfigs()) {
                TrackManager.addTrackToList(track);
            }
        }

    }


    private void loadDefaulttConfigs() {
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
