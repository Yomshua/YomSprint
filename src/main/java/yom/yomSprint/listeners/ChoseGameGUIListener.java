package yom.yomSprint.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import yom.yomSprint.YomSprint;
import yom.yomSprint.events.PlayerJoinWaitLobbyEvent;
import yom.yomSprint.managers.TrackManager;
import yom.yomSprint.utils.Track;

public class ChoseGameGUIListener implements Listener {

    YomSprint plugin;

    public ChoseGameGUIListener(YomSprint plugin) {
        this.plugin = plugin;
    }

    @EventHandler (priority = EventPriority.HIGH)
    void onClickEvent(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        if (event.getClickedInventory() == null) return;
        if (!event.getView().getTitle().equals("C")) return;
        event.setCancelled(true);
        ItemStack item = event.getCurrentItem();
        if (item == null || item.equals(Material.AIR)) return;
        FileConfiguration tracksConfig = plugin.getTracksConfiguration().getConfig();
        for (Track track : TrackManager.getTracks()) {
            if(!item.hasItemMeta()) return;
            if(tracksConfig.getConfigurationSection("tracks." + track.getName()) != null &&
                    tracksConfig.getConfigurationSection("tracks." + track.getName()).contains("location")) {
                if (track.getName().equals(item.getItemMeta().getDisplayName())) {
                    TrackManager.teleportPlayerToWaitLobby(player,track,plugin);
                    Bukkit.getServer().getPluginManager().callEvent(new PlayerJoinWaitLobbyEvent(track,player));
                }
            }
        }
    }

}
