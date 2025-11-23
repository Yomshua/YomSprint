package yom.yomSprint.managers;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import yom.yomSprint.YomSprint;
import yom.yomSprint.utils.Track;

import java.util.ArrayList;
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
            ItemStack item = new ItemStack(Material.GREEN_WOOL);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(track.getDisplayName());
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatColor.WHITE+ "▪ Status: " + track.getGameStatus().getStatus());
            lore.add(ChatColor.WHITE + "▪ Players na pista: "
                    + (track.getWaitLobbySize() > 0 ? ChatColor.WHITE:ChatColor.GRAY)
                    + track.getPlayersInGame().size());
            lore.add("");
            lore.add(ChatColor.GRAY.toString() + "Players máximos: " + ChatColor.WHITE + track.getMaxPlayers());
            lore.add(ChatColor.GRAY.toString() + "Players minimos: " + ChatColor.WHITE + track.getMinPlayers());

            meta.setLore(lore);
            item.setItemMeta(meta);
            inventory.addItem(item);
        }
    }


    public static void teleportPlayerToWaitLobby(Player player, Track track, YomSprint plugin) {
        if (true) {
            player.teleport((Location) plugin.getTracksConfiguration().getConfig().getConfigurationSection("tracks." + track.getName()).get("waitLobby_location"));
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
