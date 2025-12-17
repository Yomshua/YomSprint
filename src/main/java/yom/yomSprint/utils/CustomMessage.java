package yom.yomSprint.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import yom.yomSprint.YomSprint;

import java.util.concurrent.CompletableFuture;

public class CustomMessage {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_WHITE = "\033[37m";

    public static CompletableFuture<Void> sendCustomActionBar(Player player, String message, YomSprint plugin) {
        return CompletableFuture.runAsync(() -> {
            int count = 0;
            final StringBuilder builder = new StringBuilder();
            for(char c : message.toCharArray()) {
                builder.append(c);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (!player.isOnline()) return;
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(builder.toString()));
                    }
                }.runTask(plugin);
                try {
                    Thread.sleep(60);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


}
