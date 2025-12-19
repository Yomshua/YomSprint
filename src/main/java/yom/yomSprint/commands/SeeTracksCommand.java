package yom.yomSprint.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import yom.yomSprint.YomSprint;
import yom.yomSprint.commands.managers.TrackSubCommands;

public class SeeTracksCommand extends TrackSubCommands {
    public SeeTracksCommand(YomSprint plugin) {
        super("seetracks", null, "sprint.seetracks", plugin);
    }

    @Override
    public void runCommand(CommandSender sender, Command command, String label, String[] args, Player player) {

    }
}
