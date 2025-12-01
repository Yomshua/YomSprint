package yom.yomSprint.listeners;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import yom.yomSprint.utils.CustomMessage;
import yom.yomSprint.YomSprint;
import yom.yomSprint.boards.FastBoard;
import yom.yomSprint.events.GameStartEvent;
import yom.yomSprint.events.PlayerJoinWaitLobbyEvent;
import yom.yomSprint.models.Track;

import java.util.UUID;

public class PlayerTrackListener implements Listener {

    private YomSprint plugin;

    public PlayerTrackListener(YomSprint plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinWaitLobbyEvent event) {
        Player player = event.getPlayer();
        player.setInvulnerable(true);
        Track track = event.getTrack();
        track.addPlayerInGame(player);
        CustomMessage.sendCustomActionBar(player, ChatColor.GREEN + "Você entrou na pista: " + ChatColor.GRAY + event.getTrack().getName(), plugin);
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
        if (track.getWaitLobbySize() >= track.getMinPlayers()) {
            new BukkitRunnable() {
                int TRACK_COUNTDOWN_SECONDS = 30;
                @Override
                public void run() {
                    for(UUID uuid : track.getPlayersInGame()){
                        Player playerTittle = Bukkit.getPlayer(uuid);
                        playerTittle.sendTitle(ChatColor.GREEN + String.valueOf(TRACK_COUNTDOWN_SECONDS),"");
                    }
                    if(TRACK_COUNTDOWN_SECONDS == 0){
                        Bukkit.getPluginManager().callEvent(new GameStartEvent(track, track.getPlayersInGame()));
                        cancel();
                    }
                    TRACK_COUNTDOWN_SECONDS--;
                }
            }.runTaskTimer(plugin, 0, 20L);
        }


    }

}
