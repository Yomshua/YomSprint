package yom.yomSprint.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import yom.yomSprint.managers.CompetitionManager;
import yom.yomSprint.models.Competition;
import yom.yomSprint.models.Runner;

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
        for (Competition competition : plugin.getCompetitionManager().getCompetitions()) {
            if (!item.hasItemMeta()) return;
            if (tracksConfig.getConfigurationSection("tracks." + competition.getTrack().getName()).getString("display_name").equals(item.getItemMeta().getDisplayName())) {
                if (competition.getStatus().equals(GameStatus.JOIN)) {
                    if (competition.getTrack().getLanes().size() == competition.getRunners().size()) {
                        player.sendMessage(ChatColor.RED + "Você pode no entrar, as raias não foram configuradas corretamente!");
                    }else {
                        plugin.getCompetitionManager().teleportPlayerToWaitLobby(player,competition);
                        Bukkit.getServer().getPluginManager().callEvent(new PlayerJoinWaitLobbyEvent(competition, player));
                    }
                }
            }
        }
    }


}
