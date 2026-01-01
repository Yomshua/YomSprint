package yom.yomSprint.managers;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import yom.yomSprint.YomSprint;
import yom.yomSprint.models.BoudingBox;
import yom.yomSprint.models.Lane;
import yom.yomSprint.models.Track;

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
            lore.add(ChatColor.WHITE + "▪ Status: " + track.getGameStatus().getStatus());
            lore.add(ChatColor.WHITE + "▪ Players na pista: "
                    + (track.getWaitLobbySize() > 0 ? ChatColor.WHITE : ChatColor.GRAY)
                    + track.getPlayersInGame().size());
            lore.add("");
            lore.add(ChatColor.GRAY.toString() + "Players máximos: " + ChatColor.WHITE + track.getMaxPlayers());
            lore.add(ChatColor.GRAY.toString() + "Players minimos: " + ChatColor.WHITE + track.getMinPlayers());

            meta.setLore(lore);
            item.setItemMeta(meta);
            inventory.addItem(item);
        }
    }

    public static void addTrackToList(Track track) {
        tracks.add(track);
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

    public static Track getTrackByPlayer(Player player) {
        for (Track track : tracks) {
            for (UUID uuid : track.getPlayersInGame()) {
                if (player.getUniqueId().equals(uuid)) return track;
            }
        }
        return null;
    }

    public static Track getTrackByName(String name) {
        for (Track track : TrackManager.getTracks()) {
            if (track.getName().equals(name)) {
                return track;
            }
        }
        return null;
    }

    public static List<Track> getTracks() {
        return tracks;
    }

    public static boolean isAvailableLane(Lane lane) {
        BoudingBox startBox = lane.getStartBoudingBox();
        BoudingBox endBox = lane.getEndBoudingBox();
        Location edge1 = lane.getEdge1();
        Location edge2 = lane.getEdge2();

        if (startBox == null || endBox == null) return false;
        if (startBox.getPos1() == null || startBox.getPos2() == null) return false;
        if (endBox.getPos1() == null || endBox.getPos2() == null) return false;
        if (edge1 == null || edge2 == null) return false;

        if ((isLocation(startBox.getPos1()) && isLocation(startBox.getPos2())
                && (isLocation(endBox.getPos1()) && isLocation(endBox.getPos2())) &&
                (isLocation(edge1) && isLocation(edge2)))){
            return true;
        } else {
            return false;
        }
    }

     public static boolean isInsideLane(Player player,Lane lane){

        Location edge1 = lane.getEdge1();
        Location edge2 = lane.getEdge2();

        double xMax = Math.max(edge1.getX(),edge2.getX());
        double xMin = Math.min(edge1.getX(),edge2.getX());

         double zMax = Math.max(edge1.getZ(),edge2.getZ());
         double zMin = Math.min(edge1.getZ(),edge2.getZ());

         Location pLoc = player.getLocation();

        if (xMax == xMin){

            double pZ = pLoc.getZ();

            if (pZ <= zMax && pZ >= zMin ){
                return true;
            }
            return false;
        } else if (zMax == zMin) {

            double pX = pLoc.getX();

            if (pX <= xMax && pX >= xMin ){
                return true;
            }
            return false;
        }

         return false;
     }

    private static boolean isLocation(Object object) {
        if (object instanceof Location) {
            return true;
        }
        return false;
    }

}
