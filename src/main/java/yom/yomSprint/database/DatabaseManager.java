package yom.yomSprint.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import yom.yomSprint.YomSprint;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private Connection connection;
    private YomSprint plugin;
    private DatabaseAdapter adapter;

    public DatabaseManager(YomSprint plugin) {
        this.plugin = plugin;
    }


    public DatabaseAdapter getAdapter(String name) {
        switch (name) {
            case "SQLITE" -> {
                return new SQLiteAdapter(plugin);
            }
            case "YAML" -> {
                return new YamlAdapter(plugin);
            }
            default -> {
                return new YamlAdapter(plugin);
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public DatabaseAdapter getAdapter() {
        return adapter;
    }
}
