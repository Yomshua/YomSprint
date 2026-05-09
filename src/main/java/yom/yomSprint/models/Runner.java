package yom.yomSprint.models;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import yom.yomSprint.boards.YomBoard;

import java.util.UUID;

public class Runner {

    private UUID uuid;
    private Stamina stamina;
    private int lane;
    private long lastClick;
    private YomBoard waitBoard;
    private YomBoard competitionBoard;
    private boolean alreadyFinished;

    public Runner(@NotNull UUID uuid) {
        this.uuid = uuid;
        this.stamina = new Stamina(uuid);
    }

    public void setStamina(Stamina stamina) {
        this.stamina = stamina;
    }

    public void setLane(int lane) {
        this.lane = lane;
    }

    public void setLastClick(long lastClick) {
        this.lastClick = lastClick;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Stamina getStamina() {
        return stamina;
    }

    public int getLane() {
        return lane;
    }

    public long getLastClick() {
        return lastClick;
    }

    public YomBoard getWaitBoard() {
        return waitBoard;
    }

    public YomBoard getCompetitionBoard() {
        return competitionBoard;
    }

    public boolean isAlreadyFinished() {
        return alreadyFinished;
    }

    public void setCompetitionBoard(YomBoard competitionBoard) {
        this.competitionBoard = competitionBoard;
    }

    public void setWaitBoard(YomBoard waitBoard) {
        this.waitBoard = waitBoard;
    }

    public void setAlreadyFinished(boolean alreadyFinished) {
        this.alreadyFinished = alreadyFinished;
    }

    public void deleteBoard(YomBoard yomBoard) {
        YomBoard scoreboard = yomBoard;

        if (scoreboard == null) return;
        scoreboard.delete();

    }

    public void updateBoard(YomBoard scoreboard) {

        if (scoreboard == null) return;

        scoreboard.updateTitle(scoreboard.getYomTitle());
        scoreboard.updateLines(scoreboard.getBoard());

    }


}
