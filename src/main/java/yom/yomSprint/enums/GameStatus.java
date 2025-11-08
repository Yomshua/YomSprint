package yom.yomSprint.enums;

import org.bukkit.ChatColor;

public enum GameStatus {

    IN_GAME(ChatColor.RED + ""), STOP(ChatColor.RED + ""), JOIN(ChatColor.GREEN + "" + ChatColor.BOLD + "JOIN");

    private String status;

    GameStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
