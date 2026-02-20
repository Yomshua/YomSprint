package yom.yomSprint.guis;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import yom.yomSprint.YomSprint;
import yom.yomSprint.guis.holders.TracksGUIHolder;
import yom.yomSprint.managers.CompetitionManager;
import yom.yomSprint.models.Competition;

public class TracksGUI implements YomGUI{

    private YomSprint plugin;

    public TracksGUI(YomSprint plugin) {
        this.plugin = plugin;
    }

    @Override
    public Inventory getInventory() {
        Inventory gui = Bukkit.createInventory(new TracksGUIHolder(),54, ChatColor.AQUA + "Pistas");
        for (Competition competition : CompetitionManager.getCompetitions()){
            CompetitionManager.addTracks(competition,gui);
        }
        return gui;
    }
}
