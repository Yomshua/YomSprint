package yom.yomSprint.models;


import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import yom.yomSprint.boards.FastBoard;
import yom.yomSprint.enums.GameStatus;
import yom.yomSprint.YomSprint;
import yom.yomSprint.runnables.StartCountRunnable;
import yom.yomSprint.utils.CustomMessage;

import java.util.*;

import static yom.yomSprint.utils.CustomMessage.ANSI_RESET;

public class Track {

    private YomSprint plugin;
    private String name;
    private String displayName;
    private Location waitLobby;
    private GameStatus gameStatus = GameStatus.JOIN;
    private int minPlayers;
    private int maxPlayers;
    private final Set<UUID> playersInWaitLobby = new HashSet<>();
    private final Set<UUID> playersInGame = new HashSet<>();
    private final HashMap<String, String> needConfigs = new HashMap<>();
    private Map<UUID, FastBoard> waitLobbyScoreboadMap = new HashMap<>();
    private Map<UUID, FastBoard> gameScoreboaMap = new HashMap<>();
    private List<String> waitLobbyScoreboard;
    private String waitLobbyScoreboardTittle;
    private List<String> gameScoreboard;
    private String gameScoreboardTittle;
    private List<Lane> lanes = new ArrayList<>();
    private long whenGameStarted;
    private ArrayList<UUID> marks = new ArrayList<>();
    private HashMap<UUID, Long> lastClickMap = new HashMap<>();
    private StartCountRunnable startCountRunnable;
    private boolean runnableRunning;
    private HashMap<UUID, Stamina> staminaMap = new HashMap<>();

    private Track(YomSprint plugin, String name, String displayName, int maxPlayers, int minPlayers, List<Lane> lanes, List<String> waitLobbyScoreboad, String waitLobbyScoreboardTittle, List<String> gameScoreboad, String gameScoreboardTittle) {
        this.plugin = plugin;
        this.name = name;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.displayName = displayName;
        this.lanes = lanes;
        this.waitLobbyScoreboard = waitLobbyScoreboad;
        this.waitLobbyScoreboardTittle = waitLobbyScoreboardTittle;
        this.gameScoreboard = gameScoreboad;
        this.gameScoreboardTittle = gameScoreboardTittle;
        needConfigs.put("waitLobby_location", "Localizacoes");
        needConfigs.put("display_name", "Display Name");
        needConfigs.put("min_players", "Players Minimos");
        needConfigs.put("max_players", "Players Maximos");
        needConfigs.put("lanes", "Raias");
        needConfigs.put("lanes_length", "Comprimento das Raias");
        startCountRunnable = new StartCountRunnable(plugin, this);
    }

    public void reload() {
        lastClickMap.clear();
        waitLobbyScoreboadMap.clear();
        marks.clear();
        playersInGame.clear();
        playersInWaitLobby.clear();
        staminaMap.clear();
        gameScoreboaMap.clear();
        waitLobbyScoreboadMap.clear();
    }

    public static class TrackBuilder {
        YomSprint plugin;
        String name;
        String displayName;
        int maxPlayers;
        int minPlayers;
        List<Lane> lanes;
        List<String> waitLobbyScoreboard;
        String waitLobbyScoreboardTittle;
        List<String> gameScoreboad;
        String gameScoreboardTittle;

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

        public TrackBuilder setWaitLobbyScoreboad(List<String> waitLobbyScoreboard) {
            this.waitLobbyScoreboard = waitLobbyScoreboard;
            return this;
        }

        public TrackBuilder setWaitLobbyScoreboardTittle(String waitLobbyScoreboardTittle) {
            this.waitLobbyScoreboardTittle = waitLobbyScoreboardTittle;
            return this;
        }

        public TrackBuilder setGameScoreboard(List<String> gameScoreboad) {
            this.gameScoreboad = gameScoreboad;
            return this;
        }

        public TrackBuilder setGameScoreboardTittle(String gameScoreboardTittle) {
            this.gameScoreboardTittle = gameScoreboardTittle;
            return this;
        }

        public Track build() {
            if (plugin == null || name == null) {
                throw new IllegalStateException("Está faltando arugmentos para a construção da pista");
            }
            if (maxPlayers < minPlayers) {
                throw new IllegalStateException("O número players máximos é menor do que o número de players mínimos!");
            }
            return new Track(plugin, name, displayName, maxPlayers, minPlayers, lanes, waitLobbyScoreboard, waitLobbyScoreboardTittle, gameScoreboad, gameScoreboardTittle);
        }

    }

    public void loadConfigs() {
        for (String config : needConfigs.keySet()) {
            if (!plugin.getTracksConfiguration().getConfig().getConfigurationSection("tracks." + name).contains(config)) {
                System.out.println((CustomMessage.ANSI_RED + "A configuracao " + needConfigs.get(config) + " da pista " + CustomMessage.ANSI_WHITE + name + CustomMessage.ANSI_RED + " está incompleta!" + ANSI_RESET));
            }
        }
    }

    public boolean isGameOcurring() {
        if (getGameStatus() == GameStatus.OCURRING) {
            return true;
        }
        return false;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public void addPlayerInGame(Player player) {
        playersInGame.add(player.getUniqueId());
    }

    public void removePlayerInGame(Player player) {
        playersInGame.remove(player.getUniqueId());
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

    public int getWaitLobbySize() {
        return playersInGame.size();
    }

    public Map<UUID, FastBoard> getWaitLobbyScoreboadMap() {
        return waitLobbyScoreboadMap;
    }

    public List<String> getWaitLobbyScoreboad(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        List<String> score = new ArrayList<>();
        for (String line : waitLobbyScoreboard) {
            score.add(PlaceholderAPI.setPlaceholders(player, line));
        }
        return score;
    }

    public void updateWaitBoard() {
        for (UUID playerBoard : this.getWaitLobbyScoreboadMap().keySet()) {
            FastBoard waitLobbyBoard = this.getWaitLobbyScoreboadMap().get(playerBoard);
            waitLobbyBoard.updateTitle(this.getWaitLobbyScoreboardTittle());
            waitLobbyBoard.updateLines(this.getWaitLobbyScoreboad(playerBoard));
        }
    }

    public void removeWaitBoard(UUID uuid) {
        FastBoard fastBoard = waitLobbyScoreboadMap.get(uuid);
        if (fastBoard == null) return;
        fastBoard.delete();
        getWaitLobbyScoreboadMap().remove(uuid);
    }

    public String getGameScoreboardTittle() {
        return gameScoreboardTittle;
    }

    public List<String> getGameScoreboard(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        List<String> score = new ArrayList<>();
        for (String line : gameScoreboard) {
            score.add(PlaceholderAPI.setPlaceholders(player, line));
        }
        return score;
    }

    public void updateGameBoard() {
        for (UUID playerBoard : this.getGameScoreboaMap().keySet()) {
            FastBoard waitLobbyBoard = this.getGameScoreboaMap().get(playerBoard);
            waitLobbyBoard.updateTitle(this.getGameScoreboardTittle());
            waitLobbyBoard.updateLines(this.getGameScoreboard(playerBoard));
        }
    }

    public void updateGameBoard(UUID uuid) {
            FastBoard waitLobbyBoard = this.getGameScoreboaMap().get(uuid);
            waitLobbyBoard.updateTitle(this.getGameScoreboardTittle());
            waitLobbyBoard.updateLines(this.getGameScoreboard(uuid));
    }

    public void removeGameBoard(UUID uuid) {
        FastBoard fastBoard = getGameScoreboaMap().get(uuid);
        if (fastBoard == null) return;
        fastBoard.delete();
        getGameScoreboaMap().remove(uuid);
    }

    public Map<UUID, FastBoard> getGameScoreboaMap() {
        return gameScoreboaMap;
    }

    public long getWhenGameStarted() {
        return whenGameStarted;
    }

    public void setWhenGameStarted(long whenGameStarted) {
        this.whenGameStarted = whenGameStarted;
    }

    public String getWaitLobbyScoreboardTittle() {
        return waitLobbyScoreboardTittle;
    }

    public List<Lane> getLanes() {
        return lanes;
    }

    public Location getWaitLobbyLocation() {
        return waitLobby;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public Set<UUID> getPlayersInWaitLobby() {
        return playersInWaitLobby;
    }

    public Set<UUID> getPlayersInGame() {
        return playersInGame;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ArrayList<UUID> getMarks() {
        return marks;
    }

    public HashMap<UUID, Long> getLastClickMap() {
        return lastClickMap;
    }

    public StartCountRunnable getStartCountRunnable() {
        return startCountRunnable;
    }

    public boolean isRunnableRunining() {
        return runnableRunning;
    }

    public void setRunnableRunining(boolean runnableRunining) {
        this.runnableRunning = runnableRunining;
    }

    public HashMap<UUID, Stamina> getStaminaMap() {
        return staminaMap;
    }
}
