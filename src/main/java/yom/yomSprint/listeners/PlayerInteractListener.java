package yom.yomSprint.listeners;


import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;
import yom.yomSprint.YomSprint;
import yom.yomSprint.models.ChatInput;
import yom.yomSprint.models.Track;

import java.util.Map;
import java.util.UUID;

public class PlayerInteractListener implements Listener {

    private YomSprint plugin;

    public PlayerInteractListener(YomSprint plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("sprint.addlanes")) return;
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.getPlayer().getItemInHand().getType().equals(Material.STICK)) {
            ItemStack stick = player.getItemInHand();
            if (!stick.hasItemMeta() || !stick.getItemMeta().hasDisplayName()) return;
            ItemMeta meta = stick.getItemMeta();
            String trackName = meta.getDisplayName().replace(ChatColor.YELLOW.toString(), "");
            if (plugin.getTracksConfiguration().getConfig().contains("tracks." + trackName)) {
                Track track = plugin.getCompetitionManager().getTrackByName(trackName);
                Location clickedBlock = event.getClickedBlock().getLocation().add(new Vector(1, 0, 1));
                player.sendMessage("Digite o número da raia a qual quer adicionar: ");
                plugin.getChatInputManager().getChatInputs().add(new ChatInput(player.getUniqueId(), track, clickedBlock));

            }
        }
    }
}
