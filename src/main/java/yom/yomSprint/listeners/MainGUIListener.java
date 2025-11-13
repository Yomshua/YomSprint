package yom.yomSprint.listeners;

import net.md_5.bungee.api.chat.TranslatableComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.PermissibleBase;
import org.bukkit.permissions.PermissionAttachment;
import yom.yomSprint.YomSprint;
import yom.yomSprint.managers.GameGUIs;
import yom.yomSprint.managers.TrackManager;


public class MainGUIListener implements Listener {

    YomSprint plugin;

   public MainGUIListener(YomSprint plugin){
       this.plugin = plugin;
   }

    @EventHandler
    void onPlayerClickInventoryEvent(InventoryClickEvent event){
        try {
            if (!(event.getWhoClicked() instanceof Player)) return;
            Player player = (Player) event.getWhoClicked();
            if (event.getClickedInventory() == null) return;
            if(!event.getView().getTitle().equals("Sprint Game")) return;
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            if(item == null && item.equals(Material.AIR)) return;
            switch (item.getItemMeta().getDisplayName()){
                case "§eStatus": // yellow
                    break;
                case "§a§lGames":
                    if(!TrackManager.isPlayerInAnyTrack(player)) {
                        player.openInventory(GameGUIs.tracksGUI(plugin));
                    }else {
                        player.sendMessage(ChatColor.RED + "Você não pode mudar de pista, saia primeiro a qual está");
                    }
                    break;

            }
        }catch (NullPointerException exception){

        }


    }



}
