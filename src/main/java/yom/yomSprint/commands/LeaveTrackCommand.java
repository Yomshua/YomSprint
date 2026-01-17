package yom.yomSprint.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import yom.yomSprint.YomSprint;
import yom.yomSprint.boards.FastBoard;
import yom.yomSprint.commands.managers.TrackSubCommands;
import yom.yomSprint.enums.GameStatus;
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
            player.sendMessage(PlaceholderAPI.setPlaceholders(player,plugin.getMessagesConfiguration().track_leave));
            track.getPlayersInGame().remove(player.getUniqueId());
            track.getWaitLobbyScoreboadMap().get(player.getUniqueId()).delete();
            if (!track.getPlayersInGame().isEmpty()) {
                if (track.getGameStatus().equals(GameStatus.JOIN)){
                    track.updateWaitBoard();
                }else if (track.getGameStatus().getStatus().equals(GameStatus.OCURRING)){
                    track.updateGameBoard();
                }
            }else {
                track.reload();
            }
            player.teleport(plugin.getLobbyLocation());
        } else {
            player.sendMessage(ChatColor.RED + "Você não está e nenhum pista!");
        }
    }
}
