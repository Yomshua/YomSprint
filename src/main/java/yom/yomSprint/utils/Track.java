package yom.yomSprint.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import yom.yomSprint.enums.GameStatus;
import yom.yomSprint.managers.TrackManager;
import yom.yomSprint.YomSprint;

import javax.xml.stream.Location;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Track {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";


    private YomSprint plugin;
    private String name;
    private String displayName;
    private Location waitLobby;
    private GameStatus gameStatus = GameStatus.JOIN;
    private List<Location> laneLocations;

    public Track(YomSprint plugin,String name) {
        this.plugin = plugin;
        this.name = name;
        createTrack();
        loadConfigs();
    }

    private void loadConfigs(){
        HashMap<String,String> configs = new HashMap<>();
        configs.put("location", "Localizacoes");
        configs.put("display_name","Display Name");;
        for(String config : configs.keySet()){
            if(!plugin.getTracksConfiguration().getConfig().getConfigurationSection("tracks." + name).contains(config)){
                System.out.println((ANSI_RED + "A configuracao da pista "+ name + ": "  + configs.get(config) + ", nao foi configurada" + ANSI_RESET));
            }
        }
    }

    private void createTrack(){
        TrackManager.getTracks().add(this);
    }

    public Location getWaitLobbyLocation(){
        return waitLobby;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public String getName(){
        return name;
    }

}
