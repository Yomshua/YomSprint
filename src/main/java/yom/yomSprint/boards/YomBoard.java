package yom.yomSprint.boards;

import org.bukkit.entity.Player;
import yom.yomSprint.YomSprint;
import yom.yomSprint.boards.fastboardAPI.FastBoard;

import java.util.List;

public abstract class YomBoard extends FastBoard {

    private YomSprint plugin;

    public YomBoard(YomSprint plugin, Player player) {
        super(player);
        this.plugin = plugin;

    }

    public abstract List<String> getBoard();

    public abstract String getYomTitle();

    public YomSprint getPlugin() {
        return plugin;
    }
}
