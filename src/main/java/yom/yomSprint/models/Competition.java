package yom.yomSprint.models;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import yom.yomSprint.YomSprint;
import yom.yomSprint.boards.CompetitionBoard;
import yom.yomSprint.boards.WaitLobbyBoard;
import yom.yomSprint.database.DatabaseAdapter;
import yom.yomSprint.enums.GameStatus;
import yom.yomSprint.events.GameEndEvent;
import yom.yomSprint.managers.TimeManager;
import yom.yomSprint.runnables.SetRunnable;
import yom.yomSprint.runnables.StartCountRunnable;
import yom.yomSprint.utils.CustomMessage;

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
    private Map<Integer, Runner> marks;
    private Location waitLobby;


    public Competition(YomSprint plugin, Track track) {
        this.plugin = plugin;
        this.track = track;
        this.status = GameStatus.JOIN;
        this.runners = new HashSet<>();
        this.marks = new HashMap<>();
        this.setRunnable = new SetRunnable(plugin, this);
        this.startCountRunnable = new StartCountRunnable(plugin, this);
        this.waitLobby = (Location) plugin.getTracksConfiguration().getConfig().getConfigurationSection("tracks." + this.getTrack().getName()).get("waitLobby_location");
    }

    public void reload() {
        marks.clear();
        runners.clear();
        track.getLaneHashMap().clear();
    }

    public Runner getRunner(UUID uuid) {
        for (Runner runner : this.runners) {
            if (runner.getUuid().equals(uuid)) return runner;
        }
        return null;
    }

    public void finishRunner(Runner runner, Time time) {
        Player player = Bukkit.getPlayer(runner.getUuid());
        Track track = getTrack();
        DatabaseAdapter databaseAdapter = plugin.getDatabaseAdapter();

        runner.setAlreadyFinished(true);

        addMark(runner);

        System.out.println(getMarks().get(1).getUuid());

        long finishTime = time.getTimeFinished() - getWhenGameStarted();

        String sTime = TimeManager.getTimeInSeconds(finishTime);
        String doubleTime = sTime.replace(",", ".");
        double pTime = Double.valueOf(doubleTime);

        String prBefore = databaseAdapter.getPersonalRecord(player.getUniqueId());
        prBefore = prBefore.replace(",", ".");
        double prDouble = Double.valueOf(prBefore);

        for (int i = 0; i < getMarks().size(); i++) {
            if (getMarks().get(i + 1).equals(runner)) {
                player.sendTitle(i + 1 + "° lugar", null);
                int wins = plugin.getDatabaseAdapter().getTotalWins(player.getUniqueId());
                databaseAdapter.setTotalWins(player.getUniqueId(), wins + 1);
            }
            if (prDouble == 0 || pTime < prDouble) {
                player.sendMessage(ChatColor.BLUE + "Novo recorde pessoal " + ChatColor.WHITE + time + ChatColor.BLUE + ", parabéns!! Tu é fera");
                Firework firework = player.getWorld().spawn(player.getLocation(), Firework.class);
                FireworkMeta meta = firework.getFireworkMeta();
                meta.addEffect(FireworkEffect.builder().withColor(Color.PURPLE).build());
                firework.setFireworkMeta(meta);
                databaseAdapter.setPersonalRecord(player.getUniqueId(), sTime);
            } else {
                player.sendMessage("Tempo : " + sTime);
            }
        }

        plugin.getSpectatorManager().setSpectate(getRunner(player.getUniqueId()), getRunners());

        if (getRunners().size() == getMarks().size()) {
            Bukkit.getPluginManager().callEvent(new GameEndEvent(this));
            stop();
        }
    }

    public void stop() {
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

    public void addMark(Runner runner) {
        if (this.marks.containsValue(runner)) return;

        if (this.marks.isEmpty()) {
            this.marks.put(1, runner);
            return;
        }

        // pega a última posição
        int lastMark = this.marks.keySet().stream().toList().get(this.marks.size() - 1);
        int newLastMark = lastMark + 1;

        this.marks.put(newLastMark, runner);
    }

    public void start() {
        long now = System.currentTimeMillis();
        for (Runner runner : this.getRunners()) {
            runner.setLastClick(now);
        }
        this.setGameStatus(GameStatus.OCURRING);
        this.setWhenGameStarted(now);
    }

    public void callSet() {
        Track track = this.getTrack();
        Set<Runner> runners = this.getRunners();
        this.setGameStatus(GameStatus.IN_SET);

        int lineCount = 0;
        for (Runner runner : runners) {

            UUID uuid = runner.getUuid();
            Player player = Bukkit.getPlayer(uuid);

            this.getRunner(uuid).getWaitBoard().delete();
            this.getRunner(uuid).setCompetitionBoard(new CompetitionBoard(plugin, player));

            Lane lane = track.getLanes().get(lineCount);

            player.teleport(lane.getStartBoudingBox().getMiddle(player.getWorld()));
            player.sendTitle(ChatColor.AQUA.toString() + ChatColor.BOLD + "RAIA", String.valueOf(lane.getNumber()));

            track.getLaneHashMap().put(player.getUniqueId(), lane);
            runner.updateBoard(runner.getCompetitionBoard());

            lineCount++;
        }

        this.getSetRunnable().start();
    }



    public void joinWaitLobby(Player player) {
        Track track = this.getTrack();
        player.setInvulnerable(true);

        if (true) {
            player.teleport(waitLobby);
        } else {
            player.sendMessage("Pista não configurada!");
        }

        this.getRunners().add(new Runner(player.getUniqueId()));
        Runner runner = this.getRunner(player.getUniqueId());

        runner.setWaitBoard(new WaitLobbyBoard(plugin,player));

        Stamina stamina = runner.getStamina();

        CustomMessage.sendCustomActionBar(player, ChatColor.GREEN + "Você entrou na pista: " + ChatColor.GRAY + this.getTrack().getName(), plugin);
        this.getRunners().forEach(otherRunner -> {
            otherRunner.updateBoard(otherRunner.getWaitBoard());
        });

        if (this.getGameSize() >= track.getMinPlayers()) {
            if (!this.isRunnableRunining()) {
                this.getStartCountRunnable().start();
                this.setRunnableRunining(true);
            }
        }
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

    public Location getWaitLobby() {
        return waitLobby;
    }

    public YomSprint getPlugin() {
        return plugin;
    }
}
