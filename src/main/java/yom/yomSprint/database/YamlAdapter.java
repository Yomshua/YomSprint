package yom.yomSprint.database;

import yom.yomSprint.YomSprint;
import yom.yomSprint.configurations.PlayersConfiguration;

import java.util.UUID;

public class YamlAdapter implements DatabaseAdapter{

    private YomSprint plugin;

    public YamlAdapter(YomSprint plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getPersonalRecord(UUID uuid) {
        PlayersConfiguration pConfig = new PlayersConfiguration(uuid,plugin);
        return pConfig.getConfig().getString("best_time");
    }

    @Override
    public int getTotalWins(UUID uuid) {
        PlayersConfiguration pConfig = new PlayersConfiguration(uuid,plugin);
        return pConfig.getConfig().getInt("wins");
    }

    @Override
    public void setTotalWins(UUID uuid, int total) {
        PlayersConfiguration pConfig = new PlayersConfiguration(uuid,plugin);
        pConfig.getConfig().set("wins",total);
        pConfig.saveConfig();
    }

    @Override
    public void setPersonalRecord(UUID uuid, String personal_best) {
        PlayersConfiguration pConfig = new PlayersConfiguration(uuid,plugin);
        pConfig.getConfig().set("best_time",personal_best);
        pConfig.saveConfig();
    }

    @Override
    public void shutdown() {

    }

    @Override
    public void initialize() {

    }
}
