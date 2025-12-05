package yom.yomSprint.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import yom.yomSprint.YomSprint;

public class SetMainLobbyCommand extends TrackSubCommands{


    public SetMainLobbyCommand(YomSprint plugin) {
        super("track", "setmlobby", "sprint.setmlobby",plugin);
    }

    @Override
    public void runCommand(CommandSender sender, Command command, String label, String[] args, Player player) {
        Location lobbyLocation = player.getLocation();
        FileConfiguration fileConfiguration = plugin.getConfig();
        fileConfiguration.set("main_lobby", lobbyLocation);
        plugin.saveConfig();

        player.sendMessage(ChatColor.GREEN + "Main lobby adicionado com sucesso ");
    }
}
