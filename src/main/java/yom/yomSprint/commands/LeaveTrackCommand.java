package yom.yomSprint.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import yom.yomSprint.YomSprint;
import yom.yomSprint.boards.FastBoard;
import yom.yomSprint.managers.TrackManager;
import yom.yomSprint.models.Track;

import java.util.UUID;

public class LeaveTrackCommand extends TrackSubCommands {

    public LeaveTrackCommand(YomSprint plugin) {
        super("leave", null,null, plugin);
    }

    @Override
    public void runCommand(CommandSender sender, Command command, String label, String[] args, Player player) {
        player.setInvulnerable(false);
        if (TrackManager.isPlayerInAnyTrack(player)) {
            Track track = TrackManager.getTrackByPlayer(player);
            track.getPlayersInGame().remove(player.getUniqueId());
            player.sendMessage(ChatColor.GREEN + "Você saiu da pista: " + ChatColor.GRAY + track.getDisplayName());
            track.getScoreboardsMap().get(player.getUniqueId()).delete();
            if (!track.getPlayersInGame().isEmpty()) {
                for (UUID playerBoard : track.getPlayersInGame()) {
                    FastBoard waitLobbyBoard = track.waitLobbyBoard(Bukkit.getPlayer(playerBoard));
                    waitLobbyBoard.updateTitle(ChatColor.AQUA.toString() + ChatColor.BOLD + "TRACK AND FIELD");
                    waitLobbyBoard.updateLines(
                            "",
                            ChatColor.WHITE + "Pista : " + ChatColor.YELLOW + track.getDisplayName(),
                            ChatColor.WHITE + "Jogadores : " + ChatColor.GREEN + "(" + track.getWaitLobbySize() + "/" + track.getMaxPlayers() + ")",
                            "",
                            ChatColor.YELLOW + "neoms.gg");
                }
            }
            player.teleport(plugin.getLobbyLocation());
        } else {
            player.sendMessage(ChatColor.RED + "Você não está e nenhum pista!");
        }
    }
}
