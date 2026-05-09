package yom.yomSprint.clicks;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import yom.yomSprint.YomSprint;
import yom.yomSprint.boards.YomBoard;
import yom.yomSprint.managers.BoardManager;
import yom.yomSprint.models.Competition;
import yom.yomSprint.models.Runner;
import yom.yomSprint.models.Stamina;

public class ClickChecker {

    long timeInMillis;
    ClickQuality clickQuality;
    Competition competition;
    YomSprint plugin;

    public ClickChecker(long timeInMillis, Competition competition,YomSprint plugin) {
        this.timeInMillis = timeInMillis;
        this.competition = competition;
        this.plugin = plugin;
    }

    public ClickQuality getClickQuality(long timeInMillis) {
        long timeInMillisNow = System.currentTimeMillis();

        long timeQuality = timeInMillisNow - timeInMillis;

        if (timeQuality >= 410 && timeQuality <= 480) return ClickQuality.PERFECT;
        if ((timeQuality >= 350 && timeQuality <= 409) || (timeQuality >= 481 && timeQuality <= 540))
            return ClickQuality.GOOD;
        if ((timeQuality >= 250 && timeQuality <= 351) || (timeQuality >= 541 && timeQuality <= 640))
            return ClickQuality.OK;
        return ClickQuality.BAD;
    }

    public double getBoostByQuality(Runner runner) {

        Stamina stamina = runner.getStamina();
        runner.updateBoard(runner.getCompetitionBoard());

        Player player = Bukkit.getPlayer(runner.getUuid());

        if (getClickQuality(timeInMillis).equals(ClickQuality.PERFECT)) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "Perfeito"));
            stamina.setExpAndLevel(stamina.getLevel() - 0);
            return ClickQuality.PERFECT.quality;
        } else if (getClickQuality(timeInMillis).equals(ClickQuality.GOOD)) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GOLD + "Bom"));
            stamina.setExpAndLevel(stamina.getLevel() - 1);
            return ClickQuality.GOOD.quality;
        } else if (getClickQuality(timeInMillis).equals(ClickQuality.OK)) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "OK"));
            stamina.setExpAndLevel(stamina.getLevel() - 1);
            return ClickQuality.OK.quality;
        } else if (getClickQuality(timeInMillis).equals(ClickQuality.BAD)) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_RED + "Ruim"));
            stamina.setExpAndLevel(stamina.getLevel() - 2);
            return ClickQuality.BAD.quality;
        }
        return clickQuality.getQuality();
    }

    public void setTimeInMillis(long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }
}
