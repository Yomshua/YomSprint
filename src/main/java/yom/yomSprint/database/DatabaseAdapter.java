package yom.yomSprint.database;

import yom.yomSprint.models.Runner;

import java.util.UUID;

public interface DatabaseAdapter{

    String getPersonalRecord(UUID uuid);

    int getTotalWins(UUID uuid);

    void setTotalWins(UUID uuid, int total);

    void setPersonalRecord(UUID uuid, String personal_best);

    void shutdown();

    void initialize();
}
