package yom.yomSprint.models;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import yom.yomSprint.boards.fastboardAPI.FastBoard;
import yom.yomSprint.YomSprint;
import yom.yomSprint.utils.CustomMessage;

import java.util.*;

import static yom.yomSprint.utils.CustomMessage.ANSI_RESET;

public class Track {

    private YomSprint plugin;
    private String name;
    private String displayName;
    private Location waitLobby;
    private int minPlayers;
    private int maxPlayers;
    private List<Lane> lanes = new ArrayList<>();
    private final Map<String, String> needConfigs = new HashMap<>();
    private Map<UUID,Lane> laneHashMap = new HashMap<>();

    private Track(YomSprint plugin, String name, String displayName,int maxPlayers,int minPlayers, List<Lane> lanes) {
        this.plugin = plugin;
        this.name = name;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.displayName = displayName;
        this.lanes = lanes;
        needConfigs.put("waitLobby_location", "Localizacoes");
        needConfigs.put("display_name", "Display Name");
        needConfigs.put("min_players", "Players Minimos");
        needConfigs.put("max_players", "Players Maximos");
        needConfigs.put("lanes", "Raias");
        needConfigs.put("lanes_length", "Comprimento das Raias");

    }

    public static class TrackBuilder {
        YomSprint plugin;
        String name;
        String displayName;
        int maxPlayers;
        int minPlayers;
        List<Lane> lanes;

        public TrackBuilder setPlugin(YomSprint plugin) {
            this.plugin = plugin;
            return this;
        }

        public TrackBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public TrackBuilder setLanes(List<Lane> lanes) {
            this.lanes = lanes;
            return this;
        }

        public TrackBuilder setDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public TrackBuilder setMinSize(int minPlayers) {
            this.minPlayers = minPlayers;
            return this;
        }

        public TrackBuilder setMaxSize(int maxPlayers) {
            this.maxPlayers = maxPlayers;
            return this;
        }


        public Track build() {
            if (plugin == null || name == null) {
                throw new IllegalStateException("Está faltando arugmentos para a construção da pista");
            }
            if (maxPlayers < minPlayers) {
                throw new IllegalStateException("O número players máximos é menor do que o número de players mínimos!");
            }
            return new Track(plugin, name, displayName, maxPlayers, minPlayers, lanes);
        }

    }

    public void loadConfigs() {
        for (String config : needConfigs.keySet()) {
            if (!plugin.getTracksConfiguration().getConfig().getConfigurationSection("tracks." + name).contains(config)) {
                System.out.println((CustomMessage.ANSI_RED + "A configuracao " + needConfigs.get(config) + " da pista " + CustomMessage.ANSI_WHITE + name + CustomMessage.ANSI_RED + " está incompleta!" + ANSI_RESET));
            }
        }
    }


    public boolean hasAllConfigs() {
        for (String config : needConfigs.keySet()) {
            if (!plugin.getTracksConfiguration().getConfig().getConfigurationSection("tracks." + name).contains(config))
                return false;
        }
        return true;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public List<Lane> getLanes() {
        return lanes;
    }

    public Location getWaitLobbyLocation() {
        return waitLobby;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Map<UUID, Lane> getLaneHashMap() {
        return laneHashMap;
    }
}
