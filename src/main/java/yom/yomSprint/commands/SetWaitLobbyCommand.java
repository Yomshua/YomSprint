package yom.yomSprint.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import yom.yomSprint.commands.managers.TrackSubCommands;
import yom.yomSprint.configurations.TracksConfiguration;
import yom.yomSprint.YomSprint;

public class SetWaitLobbyCommand extends TrackSubCommands {

    String helpMessage = ChatColor.RED + "/run track setwlobby <track_name>";

    public SetWaitLobbyCommand(YomSprint plugin) {
        super("track", "setwlobby","sprint.setwlobby",plugin);
    }

    @Override
    public void runCommand(CommandSender sender, Command command, String label, String[] args, Player player) {
        if(args.length != 3 ){
            player.sendMessage(helpMessage);
            return;
        }
        String track = args[2];
        TracksConfiguration tracksConfiguation = plugin.getTracksConfiguration();
        if (tracksConfiguation.getConfig().getConfigurationSection("tracks").contains(track)) {
            tracksConfiguation.getConfig().getConfigurationSection("tracks." + track).set("waitLobby_location", player.getLocation());
            tracksConfiguation.saveConfig();
            player.sendMessage(ChatColor.GREEN + "Wait lobby adicionado com sucesso na pista " + track);
        }else {
            player.sendMessage(ChatColor.RED + track + " não existe!");
            return;
        }
    }
}
