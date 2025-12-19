package yom.yomSprint.models;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import yom.yomSprint.boards.FastBoard;
import yom.yomSprint.utils.PlacheholderReplace;
import yom.yomSprint.enums.GameStatus;
import yom.yomSprint.YomSprint;
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
    private final Set<UUID> playersInGame = new HashSet<>();
    private final HashMap<String, String> needConfigs = new HashMap<>();
    private Map<UUID, FastBoard> waitLobbyScoreboadMap = new HashMap<>();
    private List<String> waitLobbyScoreboard;
    private String waitLobbyScoreboardTittle;
    private List<String> gameScoreboard;
    private String gameScoreboardTittle;
    private Map<UUID, FastBoard> gameLobbyScoreboaMap = new HashMap<>();
    private List<Lane> lanes = new ArrayList<>();
    private long whenGameStarted;
    private List<UUID> order = new ArrayList<>();



    private Track(YomSprint plugin, String name, String displayName, int maxPlayers, int minPlayers, List<Lane> lanes, List<String> waitLobbyScoreboad, String waitLobbyScoreboardTittle,List<String> gameScoreboad, String gameScoreboardTittle) {
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
        needConfigs.put("lanes_length","Comprimento das Raias");
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

        public TrackBuilder setGameScoreboard(List<String> gameScoreboad){
            this.gameScoreboad = gameScoreboad;
            return this;
        }

        public TrackBuilder setGameScoreboardTittle(String gameScoreboardTittle){
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
            return new Track(plugin, name, displayName, maxPlayers, minPlayers, lanes, waitLobbyScoreboard, waitLobbyScoreboardTittle,gameScoreboad,gameScoreboardTittle);
        }

    }

    public void loadConfigs() {
        for (String config : needConfigs.keySet()) {
            if (!plugin.getTracksConfiguration().getConfig().getConfigurationSection("tracks." + name).contains(config)) {
                System.out.println((CustomMessage.ANSI_RED + "A configuracao " + needConfigs.get(config) + " da pista " + CustomMessage.ANSI_WHITE + name + CustomMessage.ANSI_RED + " está incompleta!" + ANSI_RESET));
            }
        }
    }

    public FastBoard waitLobbyBoard(Player player) {
        FastBoard board = new FastBoard(player);
        board.updateTitle(ChatColor.GREEN.toString() + ChatColor.BOLD + getName());
        board.updateLines("", ChatColor.BLACK + "Players :  " + getPlayersInGame().size());
        waitLobbyScoreboadMap.put(player.getUniqueId(), board);
        return board;
    }

    public boolean isGameOcurring(){
        if(getGameStatus() == GameStatus.OCURRING){
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

    public Map<UUID, FastBoard> getwaitLobbyScoreboadMap() {
        return waitLobbyScoreboadMap;
    }

    public List<String> getWaitLobbyScoreboad() {
        List<String> score = new ArrayList<>();
        for(String line : waitLobbyScoreboard){
            score.add(PlacheholderReplace.apply(line,this));
        }
        return score;
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

    public Set<UUID> getPlayersInGame() {
        return playersInGame;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void addOrder(Player player){
        order.add(player.getUniqueId());
    }

}
