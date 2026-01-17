package yom.yomSprint.enums;

import org.bukkit.ChatColor;

public enum GameStatus {

    IN_SET(ChatColor.RED + "IN SET"), OCURRING(ChatColor.GREEN + "OCURRING"), JOIN(ChatColor.GREEN + "JOIN"), READY(ChatColor.BLUE + "READY");

    private String status;

    GameStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
