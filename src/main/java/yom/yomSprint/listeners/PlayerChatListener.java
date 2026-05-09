package yom.yomSprint.listeners;

import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import yom.yomSprint.YomSprint;
import yom.yomSprint.guis.ChoosePositionsGUI;
import yom.yomSprint.models.ChatInput;

public class PlayerChatListener implements Listener {

    private YomSprint plugin;
    private FileConfiguration trackConfig;


    public PlayerChatListener(YomSprint plugin ) {
        this.plugin = plugin;
        trackConfig = plugin.getTracksConfiguration().getConfig();
    }

    @EventHandler
    public void onChatEvent(PlayerChatEvent event) {
        Player player = event.getPlayer();

        if (plugin.getChatInputManager().getChatInput(player) == null) return;
        event.setCancelled(true);

        ChatInput chatInput = plugin.getChatInputManager().getChatInput(player);
        if (!NumberUtils.isNumber(event.getMessage())) return;
        int laneNumber = Integer.valueOf(event.getMessage());
        String trackName = chatInput.getTrack().getName();

        ItemStack stick = player.getItemInHand();
        if (!stick.hasItemMeta() || !stick.getItemMeta().hasDisplayName()) return;
        player.openInventory(new ChoosePositionsGUI(plugin, trackName, laneNumber,chatInput.getLocation()).getInventory());

    }

}
