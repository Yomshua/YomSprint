package yom.yomSprint.database;

import org.bukkit.Bukkit;
import yom.yomSprint.YomSprint;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.UUID;

public class SQLiteAdapter implements DatabaseAdapter{

    private YomSprint plugin;
    private Connection connection;

    public SQLiteAdapter(YomSprint plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getPersonalRecord(UUID uuid) {
        String sql = "SELECT personal_best FROM runners_info WHERE uuid = ?";
        String personalRecord = "";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,uuid.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                personalRecord = resultSet.getString("personal_best");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return personalRecord;
    }

    @Override
    public int getTotalWins(UUID uuid) {
        String sql = "SELECT wins FROM runners_info WHERE uuid = ?";
        int totalWins = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,uuid.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                totalWins = resultSet.getInt("wins");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return totalWins;
    }

    @Override
    public void setTotalWins(UUID uuid, int total) {
        String sql = "INSERT OR REPLACE INTO runners_info (wins) VALUES (?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,total);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setPersonalRecord(UUID uuid, String personal_best) {
        String sql = "INSERT OR REPLACE INTO runners_info (personal_best) VALUES (?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,personal_best);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void shutdown() {
        try {
            connection.close();
            Bukkit.getLogger().info("SQLITE Desabilitado!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String path = plugin.getDataFolder().getAbsolutePath() + "/database.db";
        File db = new File(path);
        if (!db.exists()) {
            try {
                db.createNewFile();
                Bukkit.getLogger().info("Banco de Dados criado!");
            } catch (IOException e) {
                Bukkit.getLogger().info("Falha ao criar o banco de Dados!");
                throw new RuntimeException(e);
            }
        }

        var url = "jdbc:sqlite:" + path;

        try{
            this.connection = DriverManager.getConnection(url);
            Bukkit.getLogger().info("Banco de Dados iniciado com sucesso!");
            setupTable();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void setupTable() {
        String sql = "CREATE TABLE IF NOT EXISTS runners_info ( "
                + "uuid VARCHAR(36) PRIMARY KEY, "
                + "personal_best VARCHAR(16), "
                + "total_wins INT"
                + ")";
        try {
            connection.createStatement().execute(sql);
            Bukkit.getLogger().info("Tabela criada!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
