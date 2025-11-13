package yom.yomSprint.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import yom.yomSprint.YomSprint;
import yom.yomSprint.events.PlayerJoinWaitLobbyEvent;
import yom.yomSprint.utils.Track;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class TrackManager {

    private YomSprint plugin;
    private static List<Track> tracks = new ArrayList<>();

    public TrackManager(YomSprint plugin) {
        this.plugin = plugin;
    }


    public static void addTracks(Track track, Inventory inventory) {
        if (track.hasAllConfigs()) {
            ItemStack item = new ItemStack(Material.WOOL, 1, (byte) 13);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(track.getName());
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + " Status: " + track.getGameStatus().getStatus());
            lore.add("");
            lore.add(ChatColor.YELLOW + "" + ChatColor.BOLD + "Players na pista: " + ChatColor.GRAY + track.getPlayersInGame().size());
            meta.setLore(lore);
            item.setItemMeta(meta);
            inventory.addItem(item);
        }
    }


    public static void teleportPlayerToWaitLobby(Player player, Track track, YomSprint plugin) {
        if (true) {
            player.teleport((Location) plugin.getTracksConfiguration().getConfig().getConfigurationSection("tracks." + track.getName()).get("location"));
            player.sendMessage("Você entrou na pista " + track.getName());
        } else {
            player.sendMessage("Pista não configurada!");
        }
    }

    public static boolean isPlayerInAnyTrack(Player player) {
        for (Track track : tracks) {
            for (UUID uuid : track.getPlayersInGame()) {
                if (player.getUniqueId().equals(uuid)) return true;
            }
        }
        return false;
    }

    public static Track getTrackByPlayer(Player player){
        for (Track track : tracks) {
            for (UUID uuid : track.getPlayersInGame()) {
                if (player.getUniqueId().equals(uuid)) return track;
            }
        }
        return null;
    }

    public static List<Track> getTracks() {
        return tracks;
    }
}
