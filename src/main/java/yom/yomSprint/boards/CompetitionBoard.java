package yom.yomSprint.boards;

import org.bukkit.entity.Player;
import yom.yomSprint.YomSprint;
import yom.yomSprint.boards.fastboardAPI.FastBoard;

import java.util.List;

public abstract class CompetitionBoard extends FastBoard {

    private YomSprint plugin;

    public CompetitionBoard(YomSprint plugin, Player player) {
        super(player);
        this.plugin = plugin;
    }

    public abstract List<String> getBoard();

    public abstract String getTittle();

    public YomSprint getPlugin() {
        return plugin;
    }
}
