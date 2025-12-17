package yom.yomSprint.listeners;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import yom.yomSprint.YomSprint;
import yom.yomSprint.managers.ClassBridge;

public class PlayerInteractListener implements Listener {

    private YomSprint plugin;
    private ClassBridge classBridge;

    public PlayerInteractListener(YomSprint plugin,ClassBridge classBridge) {
        this.plugin = plugin;
        this.classBridge = classBridge;
    }

    @EventHandler
    public void onInteractEvent(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (!player.hasPermission("sprint.addlanes")) return;
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.getPlayer().getItemInHand().getType().equals(Material.STICK)){
            ItemStack stick = player.getItemInHand();
            if (!stick.hasItemMeta() || !stick.getItemMeta().hasDisplayName()) return;
            ItemMeta meta = stick.getItemMeta();
            String trackName = meta.getDisplayName().replace(ChatColor.YELLOW.toString(),"");
            if (plugin.getTracksConfiguration().getConfig().contains("tracks." + trackName)) {
                player.sendMessage("Digite o número da raia a qual quer adicionar: ");
                classBridge.setCanExecute(true);
                classBridge.setBlockLocation(event.getClickedBlock().getLocation());

            }
        }
    }
}
