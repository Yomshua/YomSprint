package yom.yomSprint.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import yom.yomSprint.TracksConfiguration;
import yom.yomSprint.YomSprint;
import yom.yomSprint.managers.TrackManager;
import yom.yomSprint.utils.Track;

import java.util.Set;
import java.util.UUID;

public class ReloadCommand extends TrackSubCommands{

    public ReloadCommand(YomSprint plugin) {
        super("reload", null, plugin);
    }

    //HACK: ta meio confuso
    @Override
    public void registerCommand(CommandSender sender, Command command, String label, String[] args, Player player) {
        for(Track track : TrackManager.getTracks()){
            for(UUID uuid : track.getPlayersInGame()){
                Player playerInTrack = Bukkit.getPlayer(uuid);
                // Caso o player esteja em alguma arena!
                playerInTrack.setInvulnerable(false);
                track.getScoreboardsMap().get(uuid).delete();
                if (!plugin.getConfig().contains("main_lobby") || plugin.getConfig().get("main_lobby") == null) return;
                if (!plugin.getConfig().getBoolean("lobby_activated")) return;
                Object obj = plugin.getConfig().get("main_lobby");
                if (!(obj instanceof Location)) {
                    plugin.getLogger().warning("A chave 'main_lobby' não é uma Location válida!");
                    return;
                }
                Location location = (Location) obj;
                playerInTrack.teleport(location);
            }
            track.getPlayersInGame().clear();
        }
    }
}
