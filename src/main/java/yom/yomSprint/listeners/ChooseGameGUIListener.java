package yom.yomSprint.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import yom.yomSprint.YomSprint;
import yom.yomSprint.enums.GameStatus;
import yom.yomSprint.events.PlayerJoinWaitLobbyEvent;
import yom.yomSprint.guis.holders.TracksGUIHolder;
import yom.yomSprint.managers.TrackManager;
import yom.yomSprint.models.Track;

public class ChooseGameGUIListener implements Listener {

    YomSprint plugin;

    public ChooseGameGUIListener(YomSprint plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    void onClickEvent(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        if (event.getClickedInventory() == null) return;
        if (!(event.getInventory().getHolder() instanceof TracksGUIHolder)) return;
        event.setCancelled(true);
        ItemStack item = event.getCurrentItem();
        if (item == null || item.equals(Material.AIR)) return;
        FileConfiguration tracksConfig = plugin.getTracksConfiguration().getConfig();
        for (Track track : TrackManager.getTracks()) {
            if (!item.hasItemMeta()) return;
            if (tracksConfig.getConfigurationSection("tracks." + track.getName()).getString("display_name").equals(item.getItemMeta().getDisplayName())) {
                if (track.getGameStatus().equals(GameStatus.JOIN)) {
                    TrackManager.teleportPlayerToWaitLobby(player, track, plugin);
                    Bukkit.getServer().getPluginManager().callEvent(new PlayerJoinWaitLobbyEvent(track, player));
                }
            }
        }
    }


}
