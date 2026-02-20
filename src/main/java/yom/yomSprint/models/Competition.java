package yom.yomSprint.models;

import org.bukkit.entity.Player;
import yom.yomSprint.YomSprint;
import yom.yomSprint.boards.CompetitionBoard;
import yom.yomSprint.boards.WaitLobbyBoard;
import yom.yomSprint.enums.GameStatus;
import yom.yomSprint.runnables.SetRunnable;
import yom.yomSprint.runnables.StartCountRunnable;

import java.util.*;

public class Competition {

    private YomSprint plugin;
    private Track track;
    private GameStatus status;
    private Set<UUID> runners;
    private Map<UUID, Long> lastClickMap;
    private Map<UUID, Stamina> staminaMap;
    private ArrayList<UUID> marks;
    private SetRunnable setRunnable;
    private boolean runnableRunning;
    private StartCountRunnable startCountRunnable;
    private long whenGameStarted;
    private final Map<UUID,WaitLobbyBoard> lobbyBoardMap = new HashMap<>();
    private final Map<UUID, CompetitionBoard> competitionBoardMap = new HashMap<>();

    public Competition(YomSprint plugin, Track track) {
        this.plugin = plugin;
        this.track = track;
        this.status = GameStatus.JOIN;
        this.runners = new HashSet<>();
        this.lastClickMap = new HashMap<>();
        this.staminaMap = new HashMap<>();
        this.marks = new ArrayList<>();
        this.setRunnable = new SetRunnable(plugin,this);
        this.startCountRunnable = new StartCountRunnable(plugin, this);
        this.runnableRunning = false;

    }

    public void reload() {
        lastClickMap.clear();
        marks.clear();
        runners.clear();
        staminaMap.clear();
        track.getGameScoreboaMap().clear();
        track.getWaitLobbyScoreboadMap().clear();
        track.getLaneHashMap().clear();
        runnableRunning = false;
        setRunnable = new SetRunnable(plugin,this);
        startCountRunnable = new StartCountRunnable(plugin,this);
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

    public ArrayList<UUID> getMarks() {
        return marks;
    }

    public Map<UUID, Long> getLastClickMap() {
        return lastClickMap;
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

    public Map<UUID, Stamina> getStaminaMap() {
        return staminaMap;
    }

    public Set<UUID> getRunners() {
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

    public Map<UUID, WaitLobbyBoard> getLobbyBoardMap() {
        return lobbyBoardMap;
    }

    public Map<UUID, CompetitionBoard> getCompetitionBoardMap() {
        return competitionBoardMap;
    }
}
