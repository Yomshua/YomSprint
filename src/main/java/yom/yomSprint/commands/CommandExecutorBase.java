package yom.yomSprint.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import yom.yomSprint.guis.MainGUI;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommandExecutorBase implements TabExecutor {

    private final List<TrackSubCommands> subCommandsMain = new ArrayList<>();

    // FIXME: Tá bugado kkk
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        Set<String> list = new HashSet<>();
        if (args.length == 1) {
            for (TrackSubCommands commands : subCommandsMain) {
                    list.add(commands.getCommand());
            }
            return StringUtil.copyPartialMatches(args[0], list, new ArrayList<>());
        } else if (args.length == 2) {
            for (TrackSubCommands commands : subCommandsMain) {
                if(commands.getSubCommand() != null) {
                    list.add(commands.getSubCommand());
                }
            }
            return StringUtil.copyPartialMatches(args[1], list, new ArrayList<>());
        }
        return new ArrayList<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return onlyPlayers();
        Player player = ((Player) sender).getPlayer();
        if (args.length == 0) {
            new MainGUI(player).open(player);
            return true;
        }
        TrackSubCommands subCommand = getSubCommand(args);
        if (subCommand == null) return false;
        subCommand.registerCommand(sender, command, label, args, player);
        return true;
    }

    private TrackSubCommands getSubCommand(String[] args) {
        for (TrackSubCommands subCommand : subCommandsMain) {
            if (subCommand.getCommand().equals(args[0]) && subCommand.getSubCommand() == null) {
                return subCommand;
            } else if (subCommand.getCommand().equals(args[0]) && subCommand.getSubCommand().equals(args[1])) {
                return subCommand;
            }

        }
        return null;
    }

    public void add(TrackSubCommands subCommand) {
        this.subCommandsMain.add(subCommand);
    }


    private boolean onlyPlayers() {
        Bukkit.getLogger().info(ChatColor.RED + "Only players can execute this command!");
        return true;
    }
}
