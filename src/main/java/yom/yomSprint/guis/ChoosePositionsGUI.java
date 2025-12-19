package yom.yomSprint.guis;


import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import yom.yomSprint.YomSprint;
import yom.yomSprint.guis.holders.ChoosePositionGUIHolder;
import yom.yomSprint.managers.ClassBridge;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChoosePositionsGUI implements YomGUI{

    private YomSprint plugin;
    private String trackName;
    private ClassBridge classBridge;
    private int laneNumber;
    private FileConfiguration trackConfig;

    public ChoosePositionsGUI(YomSprint plugin,String trackName,ClassBridge classBridge) {
        this.plugin = plugin;
        this.trackName = trackName;
        this.classBridge = classBridge;
        laneNumber = classBridge.getLaneNumber();
        trackConfig = plugin.getTracksConfiguration().getConfig();
    }

    @Override
    public Inventory getInventory() {
        Inventory gui = Bukkit.createInventory(new ChoosePositionGUIHolder(),27,trackName + " (lane "+classBridge.getLaneNumber()+") positions");
        classBridge.setTittlePositionsGUI(trackName + " (lane "+ classBridge.getLaneNumber() +") positions");
        gui.setItem(10,itemPos("Start Pos1","startPos1"));
        gui.setItem(12,getGameSkull("Start Pos2"));
        gui.setItem(14,getGameSkull("End Pos1"));
        gui.setItem(16,getGameSkull("End Pos2"));
        return gui;
    }

    private ItemStack getGameSkull(String name){
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(),name);
        profile.getProperties().put("textures",new Property("texture","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTQzZmU4ODAyZWRiMmU5ODFhZTZlZDRkZmFiYTNlYWI3OWFhOGZhY2Y5YWJkYzM4MTI2ODk2ZGViZWI3YzZiIn19fQ=="));
        try {
            Field field = meta.getClass().getDeclaredField("profile");
            field.setAccessible(true);
            field.set(meta,profile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        meta.setDisplayName(ChatColor.WHITE + name);
        skull.setItemMeta(meta);
        return skull;
    }

    private ItemStack itemPos(String itemName, String path){
        ItemStack itemStack = getGameSkull(itemName);
        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>();
        if (trackConfig.getConfigurationSection("tracks." + trackName + ".lanes." + laneNumber).contains(path)){
            lore.add(trackConfig.getLocation("tracks." + trackName + ".lanes." + laneNumber+"." + path).toString());
            meta.setLore(lore);
            itemStack.setItemMeta(meta);
        }
        return itemStack;
    }

}
