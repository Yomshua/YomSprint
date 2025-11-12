package yom.yomSprint.managers;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import yom.yomSprint.TracksConfiguration;
import yom.yomSprint.YomSprint;
import yom.yomSprint.utils.Track;

import java.lang.reflect.Field;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class GameGUIs {

    public static Inventory mainGUI(Player player){
        Inventory inventory = Bukkit.createInventory(player,27, "Sprint Game");

        inventory.setItem(11,getSkull(player));
        inventory.setItem(13,getGameSkull());

        return inventory;
    }


    public static Inventory tracksGUI(YomSprint plugin){
        Inventory inventory = Bukkit.createInventory(null,54,"C");
        for (Track track : TrackManager.getTracks()){
            TrackManager.addTracks(track,inventory);
        }
        return inventory;
    }


    private static ItemStack getSkull(Player player){
        ItemStack skull = new ItemStack(Material.SKULL_ITEM,1,(byte) 3  );
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwner(player.getDisplayName());
        skullMeta.setDisplayName(ChatColor.YELLOW + "Status");
        skull.setItemMeta(skullMeta);
        return skull;
    }

    private static ItemStack getGameSkull(){
        ItemStack skull = new ItemStack(Material.SKULL_ITEM,1,(byte) 3 );
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
