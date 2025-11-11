package yom.yomSprint.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import yom.yomSprint.YomSprint;

public class TestCommands extends TrackSubCommands {


    public TestCommands( YomSprint plugin) {
        super("track", "teste", plugin);
    }

    @Override
    public void registerCommand(CommandSender sender, Command command, String label, String[] args, Player player) {

    }
}
