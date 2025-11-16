package yom.yomSprint.utils;

import org.apache.logging.log4j.spi.LoggerContextFactory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import yom.yomSprint.boards.FastBoard;
import yom.yomSprint.enums.GameStatus;
import yom.yomSprint.managers.TrackManager;
import yom.yomSprint.YomSprint;

import javax.xml.stream.Location;
import java.util.*;
import java.util.logging.Logger;

public class Track {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    private YomSprint plugin;
    private String name;
    private String displayName;
    private Location waitLobby;
    private GameStatus gameStatus = GameStatus.JOIN;
    private int minPlayers;
    private List<Location> laneLocations;
    private List<UUID> playersInGame;
    private HashMap<String,String> configs = new HashMap<>();
    private Map<UUID, FastBoard> scoreboardsMap = new HashMap<>();

    private Track(YomSprint plugin,String name) {
        this.plugin = plugin;
        this.name = name;
        this.playersInGame = new ArrayList<>();
        configs.put("location", "Localizacoes");
        configs.put("display_name","Display Name");
        configs.put("min_players","Players Minimos");
    }

    public static class TrackBuilder {

        YomSprint plugin;
        String name;


        public TrackBuilder setPlugin(YomSprint plugin){
            this.plugin = plugin;
            return this;
        }

        public TrackBuilder setName(String name){
            this.name = name;
            return this;
        }

        public Track build(){
            if (plugin == null || name == null){
                throw new IllegalStateException("Está faltando arugmentos para a construção da pista");
            }
            return new Track(plugin,name);
        }

    }

    public void loadConfigs(){
        for(String config : configs.keySet()){
            if(!plugin.getTracksConfiguration().getConfig().getConfigurationSection("tracks." + name).contains(config)){
                System.out.print((ANSI_RED + "A configuração " +configs.get(config)+ " da pista " + name + " está incompleta!" + ANSI_RESET));
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
        for(String config : configs.keySet()){
            if(!plugin.getTracksConfiguration().getConfig().getConfigurationSection("tracks." + name).contains(config)) return false;
        }
        return true;
    }

    public int getMinPlayers(){
        if(!plugin.getTracksConfiguration().getConfig().getConfigurationSection("tracks." + name).contains("min_players")) return 0;
        return (int) plugin.getTracksConfiguration().getConfig().getConfigurationSection("tracks." + name).get("min_players");
    }

    public Map<UUID, FastBoard> getScoreboardsMap() {
        return scoreboardsMap;
    }

    public void build(){
        TrackManager.getTracks().add(this);
    }

    public Location getWaitLobbyLocation(){
        return waitLobby;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public List<UUID> getPlayersInGame() {
        return playersInGame;
    }

    public String getName(){
        return name;
    }

}
