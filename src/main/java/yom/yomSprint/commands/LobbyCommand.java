package yom.yomSprint.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import yom.yomSprint.YomSprint;
import yom.yomSprint.commands.managers.TrackSubCommands;
import yom.yomSprint.utils.Replacer;

public class LobbyCommand extends TrackSubCommands {
    public LobbyCommand(YomSprint plugin) {
        super("lobby", null, null, plugin);
    }

    @Override
    public void runCommand(CommandSender sender, Command command, String label, String[] args, Player player) {
        if (args.length != 1) return;
        if (!plugin.isMainLobbyValid()) {
           player.sendMessage(ChatColor.RED +  "MainLobby não configurado corretamente!");
           return;
        }
        player.teleport(plugin.getLobbyLocation());
        player.sendMessage(Replacer.replace(player,plugin.getMessagesConfiguration().join,plugin));

    }
}
