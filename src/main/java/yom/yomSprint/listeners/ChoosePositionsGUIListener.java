package yom.yomSprint.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import yom.yomSprint.YomSprint;
import yom.yomSprint.configurations.TracksConfiguration;
import yom.yomSprint.guis.holders.ChoosePositionGUIHolder;

public class ChoosePositionsGUIListener implements Listener {

    private YomSprint plugin;

    public ChoosePositionsGUIListener(YomSprint plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerClickInventoryEvent(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();

        if (event.getClickedInventory() == null) return;


        if (!(event.getInventory().getHolder() instanceof ChoosePositionGUIHolder choosePositionGUIHolder)) return;
        TracksConfiguration tracksConfiguration = plugin.getTracksConfiguration();
        event.setCancelled(true);
        ItemStack item = event.getCurrentItem();

        if (item == null || item.getType() == Material.AIR) return;

        String trackName = choosePositionGUIHolder.getTrack().getName();
        int laneNumber = choosePositionGUIHolder.getLaneNumber();
        String itemName = item.getItemMeta().getDisplayName();
        String startPos1 = ChatColor.WHITE.toString() + "Start Pos1";
        String startPos2 = ChatColor.WHITE.toString() + "Start Pos2";
        String endPos1 = ChatColor.WHITE.toString() + "End Pos1";
        String endPos2 = ChatColor.WHITE.toString() + "End Pos2";
        String edgePos1 = ChatColor.WHITE.toString() + "Edge Pos1";
        String edgePos2 = ChatColor.WHITE.toString() + "Edge Pos2";

        Location blockLocation = choosePositionGUIHolder.getBlockLocation();

        if (itemName.equals(startPos1)) {
            tracksConfiguration.getConfig().set("tracks."+trackName+".lanes."+laneNumber +".startPos1",blockLocation);
            tracksConfiguration.saveConfig();
            player.sendMessage(ChatColor.GREEN + "StartPos1 adicionando com sucesso na pista " + trackName + ", na raia " + laneNumber );
            player.closeInventory();
        }else if (itemName.equals(startPos2)){
            tracksConfiguration.getConfig().set("tracks."+trackName+".lanes."+laneNumber +".startPos2",blockLocation);
            tracksConfiguration.saveConfig();
            player.sendMessage(ChatColor.GREEN + "StartPos2 adicionando com sucesso na pista " + trackName + ", na raia " + laneNumber );
            player.closeInventory();
        }else if (itemName.equals(endPos1)){
            tracksConfiguration.getConfig().set("tracks."+trackName+".lanes."+laneNumber +".endPos1",blockLocation);
            tracksConfiguration.saveConfig();
            player.sendMessage(ChatColor.GREEN + "EndPos1 adicionando com sucesso na pista " + trackName + ", na raia " + laneNumber );
            player.closeInventory();
        }else if (itemName.equals(endPos2)){
            tracksConfiguration.getConfig().set("tracks."+trackName+".lanes."+laneNumber +".endPos2",blockLocation);
            tracksConfiguration.saveConfig();
            player.sendMessage(ChatColor.GREEN + "EndPos2 adicionando com sucesso na pista " + trackName + ", na raia " + laneNumber );
            player.closeInventory();
        } else if (itemName.equals(edgePos1)) {
            tracksConfiguration.getConfig().set("tracks."+trackName+".lanes."+laneNumber +".edgePos1",blockLocation);
            tracksConfiguration.saveConfig();
            player.sendMessage(ChatColor.GREEN + "EdgePos1 adicionando com sucesso na pista " + trackName + ", na raia " + laneNumber );
            player.closeInventory();
        } else if (itemName.equals(edgePos2)) {
            tracksConfiguration.getConfig().set("tracks."+trackName+".lanes."+laneNumber +".edgePos2",blockLocation);
            tracksConfiguration.saveConfig();
            player.sendMessage(ChatColor.GREEN + "EdgePos2 adicionando com sucesso na pista " + trackName + ", na raia " + laneNumber );
            player.closeInventory();
        }


    }

}
