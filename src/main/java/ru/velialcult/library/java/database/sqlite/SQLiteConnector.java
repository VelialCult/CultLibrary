package ru.velialcult.library.java.database.sqlite;

import org.bukkit.plugin.Plugin;
import ru.velialcult.library.java.database.SQLConnector;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Класс для подключения к базе данных SQLite
 *
 * @author Nicholas Alexandrov 13.06.2023
 */
public class SQLiteConnector extends SQLConnector {

    private final Connection connection;
    private final Plugin plugin;

    public SQLiteConnector(Plugin plugin) {
        this.plugin = plugin;
        this.connection = connect();
    }

    /**
     *
     * Подключение к базе данных
     *
     * @return подключение
     */

    @Override
    public Connection connect() {
        Connection connection = null;
        try {
            String path = plugin.getDataFolder() + File.separator + "data.db";
            String url = "jdbc:sqlite:" + path;
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            plugin.getLogger().severe("Произошла ошибка при подключении к базе данных SQLite: " + e.getSQLState());
        }
        return connection;
    }

    @Override
    public void close() {
        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                plugin.getLogger().severe("Произошла ошибка при отключении от базы данных SQLite: " + e.getSQLState());
            }
        }
    }

    @Override
    public boolean isConnected() {
        return connection != null;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }
}
