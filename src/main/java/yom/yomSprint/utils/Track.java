package yom.yomSprint.utils;


import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import yom.yomSprint.boards.FastBoard;
import yom.yomSprint.enums.GameStatus;
import yom.yomSprint.managers.TrackManager;
import yom.yomSprint.YomSprint;

import java.util.*;

public class Track {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_WHITE = "\033[37m";

    private YomSprint plugin;
    private String name;
    private String displayName;
    private Location waitLobby;
    private GameStatus gameStatus = GameStatus.JOIN;
    private int minPlayers;
    private int maxPlayers;
    private final Set<UUID> playersInGame = new HashSet<>();
    private final HashMap<String,String> needConfigs = new HashMap<>();
    private Map<UUID, FastBoard> scoreboardsMap = new HashMap<>();
    private List<Location> lanes = new ArrayList<>();

    private Track(YomSprint plugin,String name, String displayName, int maxPlayers, int minPlayers) {
        this.plugin = plugin;
        this.name = name;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.displayName = displayName;
        needConfigs.put("waitLobby_location", "Localizacoes");
        needConfigs.put("display_name","Display Name");
        needConfigs.put("min_players","Players Minimos");
        needConfigs.put("max_players","Players Maximos");
        needConfigs.put("lanes","Raias");
    }

    public static class TrackBuilder {
        YomSprint plugin;
        String name;
        String displayName;
        int maxPlayers;
        int minPlayers;

        public TrackBuilder setPlugin(YomSprint plugin){
            this.plugin = plugin;
            return this;
        }

        public TrackBuilder setName(String name){
            this.name = name;
            return this;
        }

        public TrackBuilder setDisplayName(String displayName){
            this.displayName = displayName;
            return this;
        }

        public TrackBuilder setMinSize(int minPlayers){
            this.minPlayers = minPlayers;
            return this;
        }

        public TrackBuilder setMaxSize(int maxPlayers){
            this.maxPlayers = maxPlayers;
            return this;
        }

        public Track build(){
            if (plugin == null || name == null){
                throw new IllegalStateException("Está faltando arugmentos para a construção da pista");
            }
            if(maxPlayers < minPlayers){
                throw new IllegalStateException("O número players máximos é menor do que o número de players mínimos!");
            }
            return new Track(plugin,name,displayName,maxPlayers,minPlayers);
        }

    }

    public void loadConfigs(){
        for(String config : needConfigs.keySet()){
            if(!plugin.getTracksConfiguration().getConfig().getConfigurationSection("tracks." + name).contains(config)){
                System.out.println((ANSI_RED + "A configuracao " +needConfigs.get(config)+ " da pista " + ANSI_WHITE + name + ANSI_RED + " está incompleta!" + ANSI_RESET));
            }
        }
    }

    public FastBoard waitLobbyBoard(Player player){
        FastBoard board = new FastBoard(player);
        board.updateTitle(ChatColor.GREEN.toString() + ChatColor.BOLD + getName());
        board.updateLines("",ChatColor.BLACK +  "Players :  " + getPlayersInGame().size());
        scoreboardsMap.put(player.getUniqueId(),board);
        return board;
    }

    public void addPlayerInGame(Player player){
        playersInGame.add(player.getUniqueId());
    }

    public void removePlayerInGame(Player player){
        playersInGame.remove(player.getUniqueId());
    }

    public boolean hasAllConfigs(){
        for(String config : needConfigs.keySet()){
            if(!plugin.getTracksConfiguration().getConfig().getConfigurationSection("tracks." + name).contains(config)) return false;
        }
        return true;
    }

    public int getMinPlayers(){
        return minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getWaitLobbySize(){
        return playersInGame.size();
    }

    public Map<UUID, FastBoard> getScoreboardsMap() {
        return scoreboardsMap;
    }


    public Location getWaitLobbyLocation(){
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

    public String getDisplayName(){
        return displayName;
    }

}
