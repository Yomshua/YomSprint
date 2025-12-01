package yom.yomSprint.guis;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import yom.yomSprint.YomSprint;
import yom.yomSprint.managers.TrackManager;
import yom.yomSprint.utils.Track;

public class TracksGUI implements YomGUI{

    private YomSprint plugin;

    public TracksGUI(YomSprint plugin) {
        this.plugin = plugin;
    }

    @Override
    public Inventory getInventory() {
        Inventory gui = Bukkit.createInventory(null,54,"C");
        for (Track track : TrackManager.getTracks()){
            TrackManager.addTracks(track,gui);
        }
        return gui;
    }
}
