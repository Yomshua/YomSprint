package yom.yomSprint.guis;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import yom.yomSprint.YomSprint;
import yom.yomSprint.guis.holders.ChoosePositionGUIHolder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class ChoosePositionsGUI implements YomGUI {

    private YomSprint plugin;
    private String trackName;
    private int laneNumber;
    private FileConfiguration trackConfig;
    private Location blockLocation;
    private final String prefix = ChatColor.YELLOW.toString() + ChatColor.BOLD + " > " + ChatColor.WHITE;

    public ChoosePositionsGUI(YomSprint plugin, String trackName, int laneNumber, Location blockLocation) {
        this.plugin = plugin;
        this.trackName = trackName;
        this.laneNumber = laneNumber;
        this.blockLocation = blockLocation;
        trackConfig = plugin.getTracksConfiguration().getConfig();
    }

    @Override
    public Inventory getInventory() {
        Inventory gui = Bukkit.createInventory(new ChoosePositionGUIHolder(plugin.getCompetitionManager().getTrackByName(trackName),blockLocation,laneNumber), 45, ChatColor.YELLOW + trackName + " (lane " +laneNumber + ") positions");
        gui.setItem(11, itemPos("Start Pos1", "startPos1"));
        gui.setItem(29, itemPos("Start Pos2", "startPos2"));
        gui.setItem(13, itemPos("End Pos1", "endPos1"));
        gui.setItem(31, itemPos("End Pos2", "endPos2"));
        gui.setItem(15, itemPos("Edge Pos1", "edgePos1"));
        gui.setItem(33, itemPos("Edge Pos2", "edgePos2"));
        return gui;
    }

    private ItemStack getGameSkull(String name) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), name);
        profile.getProperties().put("textures", new Property("texture", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTQzZmU4ODAyZWRiMmU5ODFhZTZlZDRkZmFiYTNlYWI3OWFhOGZhY2Y5YWJkYzM4MTI2ODk2ZGViZWI3YzZiIn19fQ=="));
        try {
            Field field = meta.getClass().getDeclaredField("profile");
            field.setAccessible(true);
            field.set(meta, profile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        meta.setDisplayName(ChatColor.WHITE + name);
        skull.setItemMeta(meta);
        return skull;
    }

    private ItemStack itemPos(String itemName, String path) {
        ItemStack itemStack = getGameSkull(itemName);
        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>();
        if (trackConfig.getConfigurationSection("tracks." + trackName + ".lanes." + laneNumber) == null) {
            if (!trackConfig.getConfigurationSection("tracks." + trackName).contains("lanes")) {
                trackConfig.getConfigurationSection("tracks." + trackName).createSection("lanes");
                plugin.getTracksConfiguration().saveConfig();
            }
            trackConfig.set("tracks." + trackName + ".lanes." + laneNumber, null);
            plugin.getTracksConfiguration().saveConfig();
        }
        if (trackConfig.getConfigurationSection("tracks." + trackName + ".lanes").contains(laneNumber + "." + path)) {
            Object object = trackConfig.get("tracks." + trackName + ".lanes." + laneNumber + "." + path);
            if (object instanceof Location) {
                Location location = trackConfig.getLocation("tracks." + trackName + ".lanes." + laneNumber + "." + path);
                lore.add("");
                lore.add(ChatColor.AQUA.toString() + ChatColor.BOLD + "World : " + ChatColor.WHITE + location.getWorld().getName());
                lore.add(prefix + "X : " + location.getX());
                lore.add(prefix + "Y : " + location.getY());
                lore.add(prefix + "Z : " + location.getZ());
            } else {
                lore.add("");
                lore.add(ChatColor.RED + path + " não é uma location válida!");
                meta.setLore(lore);
                itemStack.setItemMeta(meta);
                return itemStack;
            }
        } else {
            lore.add(ChatColor.RED + path + " não configurada!");
        }
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }


}
