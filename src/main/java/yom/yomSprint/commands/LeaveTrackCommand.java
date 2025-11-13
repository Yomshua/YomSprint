package yom.yomSprint.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import yom.yomSprint.YomSprint;
import yom.yomSprint.managers.TrackManager;
import yom.yomSprint.utils.Track;

public class LeaveTrackCommand extends TrackSubCommands{

    public LeaveTrackCommand(YomSprint plugin) {
        super("leave", null, plugin);
    }

    @Override
    public void registerCommand(CommandSender sender, Command command, String label, String[] args, Player player) {
        if(TrackManager.isPlayerInAnyTrack(player)){
            Track track = TrackManager.getTrackByPlayer(player);
            track.getPlayersInGame().remove(player.getUniqueId());
            player.sendMessage(ChatColor.GREEN + "Você saiu da pista: " + ChatColor.GRAY +  track.getName());
        }else{
            player.sendMessage(ChatColor.RED + "Você não está e nenhum pista!");
        }
    }
}
