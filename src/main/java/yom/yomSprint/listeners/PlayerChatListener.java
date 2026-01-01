package yom.yomSprint.listeners;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import yom.yomSprint.YomSprint;
import yom.yomSprint.guis.ChoosePositionsGUI;
import yom.yomSprint.managers.ClassBridge;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerChatListener implements Listener {

    private YomSprint plugin;
    private HashMap<UUID, ClassBridge> classBridgeMap;
    private FileConfiguration trackConfig;


    public PlayerChatListener(YomSprint plugin, HashMap<UUID, ClassBridge> classBridgeMap) {
        this.plugin = plugin;
        this.classBridgeMap = classBridgeMap;
        trackConfig = plugin.getTracksConfiguration().getConfig();
    }

    @EventHandler
    public void onChatEvent(PlayerChatEvent event) {
        Player player = event.getPlayer();
        ClassBridge classBridge = null;
        if (classBridgeMap.containsKey(player.getUniqueId())) {
            classBridge = classBridgeMap.get(player.getUniqueId());
        }
        if (classBridge == null) {
            classBridgeMap.put(player.getUniqueId(), new ClassBridge());
            classBridge = classBridgeMap.get(player.getUniqueId());
        }
        if (classBridge.getCanExecute()) {
            if (!NumberUtils.isNumber(event.getMessage())) return;
            String trackName = classBridge.getTrackName();
            int laneNumber = Integer.valueOf(event.getMessage());
            classBridge.setLaneNumber(laneNumber);
            event.setCancelled(true);
            ItemStack stick = player.getItemInHand();
            if (!stick.hasItemMeta() || !stick.getItemMeta().hasDisplayName()) return;
            player.openInventory(new ChoosePositionsGUI(plugin, trackName, classBridge).getInventory());
            classBridge.setCanExecute(false);
        }
    }

}
