package yom.yomSprint.commands.managers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import yom.yomSprint.YomSprint;

import java.util.ArrayList;
import java.util.List;

public abstract class TrackSubCommands {

    private String command;
    private String subCommand;
    private String permission;
    private List<TrackSubCommands> subcommands = new ArrayList<>();
    public YomSprint plugin;

    public TrackSubCommands(String command, String subCommand,String permission,YomSprint plugin){
        this.command = command;
        this.subCommand = subCommand;
        this.plugin = plugin;
        this.permission = permission;
    }

    abstract public void runCommand(CommandSender sender, Command command, String label, String[] args, Player player);

    public String getCommand() {
        return command;
    }

    public String getSubCommand() {
        return subCommand;
    }

    public String getPermission() {
        return permission;
    }

    public List<TrackSubCommands> getSubcommands() {
        return subcommands;
    }
}
