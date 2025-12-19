package yom.yomSprint.guis;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainGUI implements YomGUI{

    Player player;

    public MainGUI(Player player) {
        this.player = player;
    }

    @Override
    public Inventory getInventory() {
        Inventory gui = Bukkit.createInventory(player,27, "Sprint Game");
        gui.setItem(12,getSkull(player));
        gui.setItem(14,getGameSkull());
        return gui;
    }

    private ItemStack getSkull(Player player){
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwningPlayer(player);
        meta.setDisplayName(ChatColor.YELLOW.toString() + ChatColor.BOLD +  "Status");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.WHITE + " ▪ Player: " + ChatColor.GRAY + player.getDisplayName());
        skull.setItemMeta(meta);
        return skull;
    }

    private ItemStack getGameSkull(){
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(),"Join Game");
        profile.getProperties().put("textures",new Property("texture","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWNjZWZkNWYyYTk0ZjI0MjgxOTg1OGE5NjU1NWExM2JhZWJhOWRhZThkNDY3ZjQwNjE5NzRlZTk5OWI2OTU5YiJ9fX0="));
        try {
            Field field = meta.getClass().getDeclaredField("profile");
            field.setAccessible(true);
            field.set(meta,profile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Games");
        skull.setItemMeta(meta);
        return skull;
    }

}
