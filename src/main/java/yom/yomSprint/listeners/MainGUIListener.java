package yom.yomSprint.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import yom.yomSprint.YomSprint;
import yom.yomSprint.guis.TracksGUI;
import yom.yomSprint.managers.CompetitionManager;


public class MainGUIListener implements Listener {

    YomSprint plugin;

    public MainGUIListener(YomSprint plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    void onPlayerClickInventoryEvent(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        if (event.getClickedInventory() == null) return;
        if (!event.getView().getTitle().equals("Sprint Game")) return;
        event.setCancelled(true);
        ItemStack item = event.getCurrentItem();
        if (item == null || item.equals(Material.AIR)) return;
        switch (item.getItemMeta().getDisplayName()) {
            case "§eStatus": // yellow
                break;
            case "§a§lGames":
                if (!plugin.getCompetitionManager().isPlayerInAnyGame(player)) {
                    new TracksGUI(plugin).open(player);
                } else {
                    player.sendMessage(ChatColor.RED + "Você não pode mudar de pista, saia primeiro a qual está");
                }
                break;

        }

    }


}
