package yom.yomSprint.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import yom.yomSprint.YomSprint;
import yom.yomSprint.commands.managers.TrackSubCommands;
import yom.yomSprint.configurations.TracksConfiguration;
import yom.yomSprint.managers.ClassBridge;

public class SetEndLaneCommand extends TrackSubCommands {

    ClassBridge classBridge = plugin.getClassBridge();

    public SetEndLaneCommand(YomSprint plugin) {
        super("track", "setend", "sprint.addlanes", plugin);
    }

    @Override
    public void runCommand(CommandSender sender, Command command, String label, String[] args, Player player) {
        if (args.length != 4) return;

        TracksConfiguration tracksConfiguation = plugin.getTracksConfiguration();
        FileConfiguration config = tracksConfiguation.getConfig();
        String trackName = args[2];
        int laneNumber = Integer.valueOf(args[3]);

        if (classBridge.getBlockLocation() == null) {
            tracksConfiguation.getConfig().set("tracks.." + trackName + ".lanes." +laneNumber+".end" ,player.getLocation());
            tracksConfiguation.saveConfig();
        }else {
            tracksConfiguation.getConfig().set("tracks." + trackName + ".lanes." +laneNumber+".end" ,classBridge.getBlockLocation());
            tracksConfiguation.saveConfig();
        }

    }
}
