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
import yom.yomSprint.models.Competition;
import yom.yomSprint.models.Lane;
import yom.yomSprint.models.Track;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class CompetitionManager {

    private YomSprint plugin;
    private final List<Competition> competitions = new ArrayList<>();

    public CompetitionManager(YomSprint plugin) {
        this.plugin = plugin;
    }

    public void addTracks(Competition competition, Inventory inventory) {
        Track track = competition.getTrack();
        if (track.hasAllConfigs()) {
            ItemStack item = new ItemStack(Material.GREEN_WOOL);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(track.getDisplayName());
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatColor.WHITE + "▪ Status: " + competition.getStatus().getStatus());
            lore.add(ChatColor.WHITE + "▪ Players na pista: "
                    + (competition.getGameSize() > 0 ? ChatColor.WHITE : ChatColor.GRAY)
                    + competition.getRunners().size());
            lore.add("");
            lore.add(ChatColor.GRAY.toString() + "Players máximos: " + ChatColor.WHITE + track.getMaxPlayers());
            lore.add(ChatColor.GRAY.toString() + "Players minimos: " + ChatColor.WHITE + track.getMinPlayers());

            meta.setLore(lore);
            item.setItemMeta(meta);
            inventory.addItem(item);
        }
    }

    public void addGame(Competition competition) {
        competitions.add(competition);
    }

    public void teleportPlayerToWaitLobby(Player player, Competition competition) {
        if (true) {
            player.teleport((Location) plugin.getTracksConfiguration().getConfig().getConfigurationSection("tracks." + competition.getTrack().getName()).get("waitLobby_location"));
        } else {
            player.sendMessage("Pista não configurada!");
        }
    }

    public boolean isPlayerInAnyGame(Player player) {
        for (Competition competition : competitions) {
            for (UUID uuid : competition.getRunners()) {
                if (player.getUniqueId().equals(uuid)) return true;
            }
        }
        return false;
    }

    public Competition getCompetition(Player player) {
        for (Competition competition : competitions) {
            for (UUID uuid : competition.getRunners()) {
                if (player.getUniqueId().equals(uuid)) return competition;
            }
        }
        return null;
    }

    public Track getTrackByName(String name) {
        for (Competition competition : competitions) {
            Track track = competition.getTrack();
            if (track.getName().equals(name)) {
                return track;
            }
        }
        return null;
    }

    public List<Competition> getCompetitions() {
        return competitions;
    }

    public boolean isValidLane(Lane lane) {
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
