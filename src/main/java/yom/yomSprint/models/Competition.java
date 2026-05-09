package yom.yomSprint.models;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import yom.yomSprint.YomSprint;
import yom.yomSprint.boards.YomBoard;
import yom.yomSprint.configurations.PlayersConfiguration;
import yom.yomSprint.enums.GameStatus;
import yom.yomSprint.events.GameEndEvent;
import yom.yomSprint.managers.TimeManager;
import yom.yomSprint.runnables.SetRunnable;
import yom.yomSprint.runnables.StartCountRunnable;

import java.util.*;

public class Competition {

    private YomSprint plugin;
    private Track track;
    private GameStatus status;
    private Set<Runner> runners;
    private SetRunnable setRunnable;
    private boolean runnableRunning;
    private StartCountRunnable startCountRunnable;
    private long whenGameStarted;
    private Map<Integer,Runner> marks;


    public Competition(YomSprint plugin,Track track) {
        this.plugin = plugin;
        this.track = track;
        this.status = GameStatus.JOIN;
        this.runners = new HashSet<>();
        this.marks = new HashMap<>();
        this.setRunnable = new SetRunnable(plugin,this);
        this.startCountRunnable = new StartCountRunnable(plugin,this);
    }

    public void reload() {
        marks.clear();
        runners.clear();
        track.getLaneHashMap().clear();
    }

    public void setGameStatus(GameStatus status) {
        this.status = status;
    }

    public int getGameSize() {
        return runners.size();
    }

    public long getWhenGameStarted() {
        return whenGameStarted;
    }

    public void setWhenGameStarted(long whenGameStarted) {
        this.whenGameStarted = whenGameStarted;
    }

    public Map<Integer, Runner> getMarks() {
        return marks;
    }

    public StartCountRunnable getStartCountRunnable() {
        return startCountRunnable;
    }

    public SetRunnable getSetRunnable() {
        return setRunnable;
    }

    public boolean isRunnableRunining() {
        return runnableRunning;
    }

    public void setRunnableRunining(boolean runnableRunining) {
        this.runnableRunning = runnableRunining;
    }

    public Set<Runner> getRunners() {
        return runners;
    }

    public boolean isRunnableRunning() {
        return runnableRunning;
    }

    public GameStatus getStatus() {
        return status;
    }

    public Track getTrack() {
        return track;
    }

    public boolean isGameOcurring() {
        if (status == GameStatus.OCURRING) {
            return true;
        }
        return false;
    }

    public Runner getRunner(UUID uuid){
        for (Runner runner : this.runners){
            if (runner.getUuid().equals(uuid)) return runner;
        }
        return null;
    }

    public void finishRunner(Runner runner, Time time){
        Player player = Bukkit.getPlayer(runner.getUuid());
        Track track = getTrack();
        PlayersConfiguration playerConfig = new PlayersConfiguration(player.getUniqueId(),plugin);
        FileConfiguration config = playerConfig.getConfig();

        runner.setAlreadyFinished(true);

        addMark(runner);

        System.out.println(getMarks().get(1).getUuid());

        long finishTime = time.getTimeFinished() - getWhenGameStarted();

        String sTime = TimeManager.getTimeInSeconds(finishTime);
        String doubleTime = sTime.replace(",",".");
        double pTime = Double.valueOf(doubleTime);

        String prBefore = playerConfig.getConfig().getString("best_time");
        prBefore = prBefore.replace(",",".");
        double prDouble = Double.valueOf(prBefore);

        for (int i = 0; i < getMarks().size(); i++) {
            if (getMarks().get(i + 1).equals(runner)) {
                player.sendTitle(i + 1 + "° lugar", null);
                int wins = config.getInt("wins");
                config.set("wins", wins + 1);
                playerConfig.saveConfig();
            }
            if (prDouble == 0 || pTime < prDouble) {
                player.sendMessage(ChatColor.BLUE + "Novo recorde pessoal " + ChatColor.WHITE + time + ChatColor.BLUE + ", parabéns!! Tu é fera");
                Firework firework = player.getWorld().spawn(player.getLocation(), Firework.class);
                FireworkMeta meta = firework.getFireworkMeta();
                meta.addEffect(FireworkEffect.builder().withColor(Color.PURPLE).build());
                firework.setFireworkMeta(meta);
                config.set("best_time", time);
                playerConfig.saveConfig();
            } else {
                player.sendMessage("Tempo : " + sTime);
            }
        }

        plugin.getSpectatorManager().setSpectate(getRunner(player.getUniqueId()), getRunners());

        if (getRunners().size() == getMarks().size()){
            Bukkit.getPluginManager().callEvent(new GameEndEvent(this));
            stop();
        }
    }

    public void stop(){
        Track track = getTrack();
        Set<Runner> runners = getRunners();
        setGameStatus(GameStatus.JOIN);
        setRunnableRunining(false);
        new BukkitRunnable() {
            int count = 0;

            @Override
            public void run() {
                count++;
                if (count == 5) {
                    for (Runner runner : runners) {
                        Player player = Bukkit.getPlayer(runner.getUuid());
                        player.sendTitle("Teleportando...", null);
                    }
                }
                if (count == 8) {
                    for (Runner runner : runners) {
                        Player player = Bukkit.getPlayer(runner.getUuid());
                        player.teleport(plugin.getLobbyLocation());
                        player.setInvulnerable(false);
                        player.setExp(0);
                        player.removePotionEffect(PotionEffectType.SLOW);
                        runner.deleteBoard(runner.getCompetitionBoard());
                    }
                    reload();
                }
            }
        }.runTaskTimer(plugin, 0, 20L);
    }

    public void addMark(Runner runner){
        if (this.marks.containsValue(runner)) return;

        if (this.marks.isEmpty()){
            this.marks.put(1,runner);
            return;
        }

        // pega a última posição
        int lastMark = this.marks.keySet().stream().toList().get(this.marks.size() - 1);
        int newLastMark = lastMark + 1;

        this.marks.put(newLastMark,runner);
    }

    public void start(){

    }


}
