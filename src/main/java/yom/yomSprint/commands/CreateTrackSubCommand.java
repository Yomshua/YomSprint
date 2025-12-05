package yom.yomSprint.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import yom.yomSprint.YomSprint;

public class CreateTrackSubCommand extends TrackSubCommands {

    private String helpMessage = ChatColor.RED +  "/run track create <track_name>";

    public CreateTrackSubCommand(YomSprint plugin) {
        super("track", "create","sprint.createtrack", plugin);
    }

    @Override
    public void runCommand(CommandSender sender, Command command, String label, String[] args, Player player) {
        if(!(sender instanceof Player)) return;
        if(args.length != 3 ){
            player.sendMessage(helpMessage);
            return;

        }
        String arenaName = args[2];
        FileConfiguration trackConfig = plugin.getTracksConfiguration().getConfig();
        trackConfig.set("tracks." + arenaName + ".display_name", arenaName);
        plugin.getTracksConfiguration().saveConfig();
        player.sendMessage(ChatColor.GREEN + "Pista " + arenaName + " criada com sucesso!");
        
    }

}
