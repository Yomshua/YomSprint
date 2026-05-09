package yom.yomSprint.listeners;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import yom.yomSprint.YomSprint;
import yom.yomSprint.boards.CompetitionBoard;
import yom.yomSprint.boards.fastboardAPI.FastBoard;
import yom.yomSprint.events.GameSetEvent;
import yom.yomSprint.managers.BoardManager;
import yom.yomSprint.models.Competition;
import yom.yomSprint.models.Lane;
import yom.yomSprint.models.Runner;
import yom.yomSprint.models.Track;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class GameSetListener implements Listener {

    private YomSprint plugin;

    public GameSetListener(YomSprint plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSetEvent(GameSetEvent event){
        Competition competition = event.getGame();
        Track track = competition.getTrack();
        Set<Runner> runners = competition.getRunners();

        int lineCount = 0;
        for (Runner runner : runners){

            UUID uuid = runner.getUuid();
            Player player = Bukkit.getPlayer(uuid);

            competition.getRunner(uuid).getWaitBoard().delete();
            competition.getRunner(uuid).setCompetitionBoard(new CompetitionBoard(plugin,player));

            Lane lane = track.getLanes().get(lineCount);

            player.teleport(lane.getStartBoudingBox().getMiddle(player.getWorld()));
            player.sendTitle(ChatColor.AQUA.toString() + ChatColor.BOLD + "RAIA", String.valueOf(lane.getNumber()));

            track.getLaneHashMap().put(player.getUniqueId(),lane);
            runner.updateBoard(runner.getCompetitionBoard());

            lineCount++;
        }

       competition.getSetRunnable().start();
    }




}
