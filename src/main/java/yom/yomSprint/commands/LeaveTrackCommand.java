package yom.yomSprint.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import yom.yomSprint.YomSprint;
import yom.yomSprint.managers.BoardManager;
import yom.yomSprint.commands.managers.TrackSubCommands;
import yom.yomSprint.enums.GameStatus;
import yom.yomSprint.managers.CompetitionManager;
import yom.yomSprint.models.Competition;

public class LeaveTrackCommand extends TrackSubCommands {

    public LeaveTrackCommand(YomSprint plugin) {
        super("leave", null,null, plugin);
    }

    @Override
    public void runCommand(CommandSender sender, Command command, String label, String[] args, Player player) {
        player.setInvulnerable(false);
        if (CompetitionManager.isPlayerInAnyGame(player)) {
            Competition competition = CompetitionManager.getGame(player);
            player.sendMessage(PlaceholderAPI.setPlaceholders(player,plugin.getMessagesConfiguration().track_leave));
            competition.getRunners().remove(player.getUniqueId());
            competition.getTrack().getWaitLobbyScoreboadMap().get(player.getUniqueId()).delete();
            if (!competition.getRunners().isEmpty()) {
                if (competition.getStatus().equals(GameStatus.JOIN)){
                    new BoardManager()
                }else if (competition.getStatus().getStatus().equals(GameStatus.OCURRING)){
                    competition.getTrack().updateGameBoard();
                }
            }else {
                competition.reload();
            }
            player.teleport(plugin.getLobbyLocation());
        } else {
            player.sendMessage(ChatColor.RED + "Você não está e nenhum pista!");
        }
    }
}
