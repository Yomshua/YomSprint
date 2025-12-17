package yom.yomSprint.commands;

import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import yom.yomSprint.YomSprint;
import yom.yomSprint.commands.managers.TrackSubCommands;
import yom.yomSprint.configurations.TracksConfiguration;

public class SetLanesLengthCommand extends TrackSubCommands {

    String helpMessage = ChatColor.RED + "/run track setlanelength <track_name> <length>";

    public SetLanesLengthCommand (YomSprint plugin) {
        super("track", "setlanelength", "sprint", plugin);
    }

    @Override
    public void runCommand(CommandSender sender, Command command, String label, String[] args, Player player) {
        if (args.length != 4) return;

        TracksConfiguration tracksConfiguation = plugin.getTracksConfiguration();
        FileConfiguration config = tracksConfiguation.getConfig();
        String trackName = args[2];
        if (!tracksConfiguation.getConfig().contains("tracks." + trackName)){
            player.sendMessage(ChatColor.RED + "Pista não encontrada!");
            return;
        }
        if (!NumberUtils.isNumber(args[3])){
            player.sendMessage(helpMessage);
            return;
        }
        int laneLength = Integer.valueOf(args[3]);

        tracksConfiguation.getConfig().set("tracks." + trackName+".lanes_length",laneLength);
        player.sendMessage(ChatColor.GREEN + "Raias da pista " + trackName + " agora tem um comprimento de " + laneLength);

    }
}
