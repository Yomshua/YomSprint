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
import yom.yomSprint.models.Runner;

public class LeaveTrackCommand extends TrackSubCommands {

    public LeaveTrackCommand(YomSprint plugin) {
        super("leave", null,null, plugin);
    }

    @Override
    public void runCommand(CommandSender sender, Command command, String label, String[] args, Player player) {
        player.setInvulnerable(false);
        if (plugin.getCompetitionManager().isPlayerInAnyGame(player)) {
            Competition competition = plugin.getCompetitionManager().getCompetition(player);
            Runner runner = competition.getRunner(player.getUniqueId());

            player.sendMessage(PlaceholderAPI.setPlaceholders(player,plugin.getMessagesConfiguration().track_leave));
            competition.getRunners().remove(player.getUniqueId());
            runner.deleteBoard(runner.getWaitBoard());
            runner.deleteBoard(runner.getCompetitionBoard());

            if (!competition.getRunners().isEmpty()) {
                for (Runner otherRunner : competition.getRunners()){
                    if (competition.getStatus().equals(GameStatus.JOIN)){
                        otherRunner.deleteBoard(otherRunner.getWaitBoard());
                    }else if (competition.getStatus().getStatus().equals(GameStatus.OCURRING)){
                        otherRunner.deleteBoard(otherRunner.getCompetitionBoard());
                    }
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
