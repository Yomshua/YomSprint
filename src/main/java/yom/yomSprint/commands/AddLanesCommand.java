package yom.yomSprint.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import yom.yomSprint.configurations.TracksConfiguration;
import yom.yomSprint.YomSprint;

import java.util.ArrayList;
import java.util.List;

public class AddLanesCommand extends TrackSubCommands{
    public AddLanesCommand(YomSprint plugin) {
        super("track", "addlane", plugin);
    }
    //HACK: ta confuso
    @Override
    public void registerCommand(CommandSender sender, Command command, String label, String[] args, Player player) {
        if(args.length != 3) return;
        TracksConfiguration tracksConfiguation = plugin.getTracksConfiguration();
        FileConfiguration config = tracksConfiguation.getConfig();
        String trackName = args[2];
        final String PATH = "tracks." + args[2];
        if (config.getConfigurationSection("tracks").contains(trackName)) {
            List<Integer> integers = new ArrayList<>();
            if(!config.getConfigurationSection(PATH).contains("lanes")) {
                config.getConfigurationSection(PATH).createSection("lanes");
                config.set(PATH + ".lanes.1",player.getLocation());
                tracksConfiguation.saveConfig();
                player.sendMessage(ChatColor.GREEN + "Raia 1 adiciona com sucesso na pista " + trackName + "!");
                return;
            }
            for(String key : config.getConfigurationSection(PATH +".lanes").getKeys(false)){
                integers.add(Integer.parseInt(key));
            }
            int maxPlayers = config.getInt(PATH + ".max_players");
            if(integers.size() >= maxPlayers){
                player.sendMessage(ChatColor.RED + "Você não pode adicionar mais raias do que o número máximo de players");
                return;
            }
            String value = String.valueOf(integers.size() + 1);
            config.set("tracks." + trackName + ".lanes." + value,player.getLocation());
            tracksConfiguation.saveConfig();
            player.sendMessage(ChatColor.GREEN + "Raia " + value +" adiciona com sucesso na pista " + trackName + "!");
        }else {
            player.sendMessage(ChatColor.RED + trackName + " não existe!");
            return;
        }
    }
}
