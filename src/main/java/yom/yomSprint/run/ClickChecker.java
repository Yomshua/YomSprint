package yom.yomSprint.run;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ClickChecker {

    long timeInMillis;
    ClickQuality clickQuality;

    public ClickChecker(long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }

    public ClickQuality getClickQuality(long timeInMillis){
        long timeInMillisNow = System.currentTimeMillis();

        long timeQuality = timeInMillisNow - timeInMillis;

        if (timeQuality >= 410 && timeQuality <= 480) return ClickQuality.PERFECT;
        if ((timeQuality >= 350 && timeQuality <= 409) || (timeQuality >= 481 && timeQuality <= 540)) return ClickQuality.GOOD;
        if ((timeQuality >= 250 && timeQuality <= 351) || (timeQuality >= 541 && timeQuality <= 640)) return ClickQuality.OK;
        return ClickQuality.BAD;
    }

    public double getBoostByQuality(Player player){
        if (getClickQuality(timeInMillis).equals(ClickQuality.PERFECT)){
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent(ChatColor.GREEN + "Perfeito"));
            return ClickQuality.PERFECT.quality;
        } else if (getClickQuality(timeInMillis).equals(ClickQuality.GOOD)) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent(ChatColor.GOLD + "Bom"));
            return ClickQuality.GOOD.quality;
        } else if (getClickQuality(timeInMillis).equals(ClickQuality.OK)) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent(ChatColor.RED + "OK"));
            return ClickQuality.OK.quality;
        } else if (getClickQuality(timeInMillis).equals(ClickQuality.BAD)) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent(ChatColor.DARK_RED + "Ruim"));
            return ClickQuality.BAD.quality;
        }
        return clickQuality.getQuality();
    }

    public void setTimeInMillis(long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }
}
