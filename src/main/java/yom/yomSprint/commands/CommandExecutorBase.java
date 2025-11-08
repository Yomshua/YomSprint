package yom.yomSprint.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import yom.yomSprint.managers.GameGUIs;

import java.util.ArrayList;
import java.util.List;

public class CommandExecutorBase implements TabExecutor {

    private final List<TrackSubCommands> subCommandsMain = new ArrayList<>();


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {



        return new ArrayList<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return onlyPlayers();
        Player player = ((Player) sender).getPlayer();
        if (args.length == 0){
            openMainGui(player);
            return true;
        }
        TrackSubCommands subCommand = getSubCommand(args);
        if (subCommand == null) return false;
        subCommand.registerCommand(sender, command, label, args,player);
        return true;
    }

    private TrackSubCommands getSubCommand(String[] args) {
        String command = args.length > 1 ? args[0] : "";
            for (TrackSubCommands subCommand : subCommandsMain) {
                if(subCommand.getSubCommand().equals(args[1]) && subCommand.getCommand().equals(command)){
                    return subCommand;
                }
            }
        return null;
    }

    public void add(TrackSubCommands subCommand) {
        this.subCommandsMain.add(subCommand);
    }

    private boolean openMainGui(Player player) {
        player.openInventory(GameGUIs.mainGUI(player));
        return true;
    }

    private boolean onlyPlayers() {
        Bukkit.getLogger().info(ChatColor.RED + "Only players can execute this command!");
        return true;
    }
}
