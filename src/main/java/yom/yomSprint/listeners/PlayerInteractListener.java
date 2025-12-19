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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerInteractListener implements Listener {

    private YomSprint plugin;
    private Map<UUID,ClassBridge> classBridgeMap;

    public PlayerInteractListener(YomSprint plugin, Map<UUID,ClassBridge> classBridgeMap) {
        this.plugin = plugin;
        this.classBridgeMap = classBridgeMap;
    }

    @EventHandler
    public void onInteractEvent(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (!player.hasPermission("sprint.addlanes")) return;
        if (!classBridgeMap.containsKey(player.getUniqueId())){classBridgeMap.put(player.getUniqueId(),new ClassBridge());}
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.getPlayer().getItemInHand().getType().equals(Material.STICK)){
            ItemStack stick = player.getItemInHand();
            if (!stick.hasItemMeta() || !stick.getItemMeta().hasDisplayName()) return;
            ItemMeta meta = stick.getItemMeta();
            String trackName = meta.getDisplayName().replace(ChatColor.YELLOW.toString(),"");
            if (plugin.getTracksConfiguration().getConfig().contains("tracks." + trackName)) {
                ClassBridge classBridge = classBridgeMap.get(player.getUniqueId());
                player.sendMessage("Digite o número da raia a qual quer adicionar: ");
                classBridge.setCanExecute(true);
                classBridge.setBlockLocation(event.getClickedBlock().getLocation());
                classBridge.setTrackName(trackName);
            }
        }
    }
}
