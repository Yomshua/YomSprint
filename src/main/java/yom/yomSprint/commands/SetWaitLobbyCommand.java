package yom.yomSprint.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import yom.yomSprint.TracksConfiguration;
import yom.yomSprint.YomSprint;

public class SetWaitLobbyCommand extends TrackSubCommands {

    String helpMessage = ChatColor.RED + "/run track setwlobby <track_name>";

    public SetWaitLobbyCommand(YomSprint plugin) {
        super("track", "setwlobby",plugin);
    }

    @Override
    public void registerCommand(CommandSender sender, Command command, String label, String[] args, Player player) {
        if(args.length != 3 ){
            player.sendMessage(helpMessage);
            return;
        }
        String track = args[2];
        TracksConfiguration tracksConfiguation = plugin.getTracksConfiguration();
        if (tracksConfiguation.getConfig().getConfigurationSection("tracks").contains(track)) {
            player.sendMessage("a");
            tracksConfiguation.getConfig().getConfigurationSection("tracks." + track).set("location", player.getLocation());
            tracksConfiguation.saveConfig();
        }else {
            player.sendMessage(ChatColor.RED + track + " não existe!");
            return;
        }
    }
}
