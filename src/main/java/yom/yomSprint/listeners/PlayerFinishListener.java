package yom.yomSprint.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.meta.FireworkMeta;
import yom.yomSprint.YomSprint;
import yom.yomSprint.configurations.PlayersConfiguration;
import yom.yomSprint.events.GameEndEvent;
import yom.yomSprint.events.PlayerFinishEvent;
import yom.yomSprint.managers.ClassBridge;
import yom.yomSprint.managers.SpectatorManager;
import yom.yomSprint.managers.TimeManager;
import yom.yomSprint.models.Competition;
import yom.yomSprint.models.Track;

public class PlayerFinishListener implements Listener {

    YomSprint plugin;
    ClassBridge classBridge;
    SpectatorManager spectatorManager;

    public PlayerFinishListener(YomSprint plugin) {
        this.plugin = plugin;
        this.classBridge = plugin.getClassBridge();
        this.spectatorManager = plugin.getSpectatorManager();
    }

    @EventHandler
    public void finishEvent(PlayerFinishEvent event){
        Player player = event.getPlayer();
        Competition competition = event.getGame();
        Track track = competition.getTrack();
        PlayersConfiguration playerConfig = new PlayersConfiguration(player.getUniqueId(),plugin);
        FileConfiguration config = playerConfig.getConfig();
        competition.getMarks().add(player.getUniqueId());
        long finishTime = event.getTime().getTimeFinished() - competition.getWhenGameStarted();

        String time = TimeManager.getTimeInSeconds(finishTime);
        String doubleTime = time.replace(",",".");
        double pTime = Double.valueOf(doubleTime);

        String prBefore = playerConfig.getConfig().getString("best_time");
        prBefore = prBefore.replace(",",".");
        double prDouble = Double.valueOf(prBefore);

        for (int i = 0; i < competition.getMarks().size(); i++){
            if (competition.getMarks().get(i).equals(player.getUniqueId())){
                player.sendTitle(i+1+ "° lugar",null);
                int wins = config.getInt("wins");
                config.set("wins",wins+1);
                playerConfig.saveConfig();
            }
            if (prDouble == 0 || pTime < prDouble){
                player.sendMessage(ChatColor.BLUE + "Novo recorde pessoal "+ ChatColor.WHITE +time +ChatColor.BLUE+", parabéns!! Tu é fera");
                Firework firework = player.getWorld().spawn(player.getLocation(),Firework.class);
                FireworkMeta meta = firework.getFireworkMeta();
                meta.addEffect(FireworkEffect.builder().withColor(Color.PURPLE).build());
                firework.setFireworkMeta(meta);
                config.set("best_time",time);
                playerConfig.saveConfig();
            }else {
                player.sendMessage("Tempo : " + time);
            }
        }
        classBridge.getAlreadyFinish().put(player.getUniqueId(),true);
        spectatorManager.setSpectate(player, competition.getRunners());

        if (competition.getRunners().size() == competition.getMarks().size()){
            Bukkit.getPluginManager().callEvent(new GameEndEvent(competition));
        }

    }

}
