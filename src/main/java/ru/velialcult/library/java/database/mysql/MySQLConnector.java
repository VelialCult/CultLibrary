package ru.velialcult.library.java.database.mysql;

import org.bukkit.plugin.Plugin;
import ru.velialcult.library.java.database.SQLConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector extends SQLConnector {

    private final Plugin plugin;
    private final Connection connection;

    private final String url, user, password;

    public MySQLConnector(Plugin plugin, String url, String user, String password) {
        this.plugin = plugin;
        this.url = url;
        this.user = user;
        this.password = password;
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
            String url = "jdbc:mysql//" + this.url;
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            plugin.getLogger().severe("Произошла ошибка при подключении к базе данных MySQL: " + e.getSQLState());
        }
        return connection;
    }

    @Override
    public void close() {
        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                plugin.getLogger().severe("Произошла ошибка при отключении от базы данных MySQL: " + e.getSQLState());
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
