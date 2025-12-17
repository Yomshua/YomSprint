package yom.yomSprint.listeners;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import yom.yomSprint.YomSprint;
import yom.yomSprint.managers.ClassBridge;

public class PlayerChatListener implements Listener {

    YomSprint plugin;
    ClassBridge classBridge;

    public PlayerChatListener(YomSprint plugin,ClassBridge classBridge) {
        this.plugin = plugin;
        this.classBridge = classBridge;
    }

    @EventHandler
    public void onChatEvent(PlayerChatEvent event){
        Player player = event.getPlayer();
        if (classBridge.getCanExecute()){
            int laneNumber;
            if (!NumberUtils.isNumber(event.getMessage())) return;
            laneNumber = Integer.valueOf(event.getMessage());
            event.setCancelled(true);
            ItemStack stick = player.getItemInHand();
            if (!stick.hasItemMeta() || !stick.getItemMeta().hasDisplayName()) return;
            ItemMeta meta = stick.getItemMeta();
            String trackName = meta.getDisplayName().replace(ChatColor.YELLOW.toString(),"");

            TextComponent start = new TextComponent(ChatColor.BLUE.toString() + ChatColor.BOLD + "  [LARGADA]  ");
            start.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/run track setstart " + trackName + " " + laneNumber));
            TextComponent end = new TextComponent(ChatColor.YELLOW.toString() + ChatColor.BOLD + "[CHEGADA]");
            end.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/run track setend " + trackName + " " + laneNumber));

            player.sendMessage(ChatColor.GRAY + "Escolha Largada/Chegada : ");
            player.spigot().sendMessage(start,end);
            classBridge.setCanExecute(false);
        }
    }

}
