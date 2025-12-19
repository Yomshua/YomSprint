package yom.yomSprint.guis;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public interface YomGUI{

    default void open(Player player) {
        player.openInventory(getInventory());
    }

    public Inventory getInventory();

}
