package yom.yomSprint;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.CompletableFuture;

public class CustomMessage {

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
