package yom.yomSprint.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import yom.yomSprint.YomSprint;

import java.util.ArrayList;
import java.util.List;

public abstract class TrackSubCommands {

    private String command;
    private String subCommand;
    private List<TrackSubCommands> subcommands = new ArrayList<>();
    YomSprint plugin;

    public TrackSubCommands(String command, String subCommand,YomSprint plugin){
        this.command = command;
        this.subCommand = subCommand;
        this.plugin = plugin;
    }

    public String getSubCommand() {
        return subCommand;
    }

    abstract public void registerCommand(CommandSender sender, Command command, String label, String[] args, Player player);


    public String getCommand() {
        return command;
    }

    public List<TrackSubCommands> getSubcommands() {
        return subcommands;
    }
}
