package ru.velialcult.library.java.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector extends SQLConnector {

    private final Connection connection;

    private final String url, user, password;

    public MySQLConnector(String url, String user, String password) {
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
            System.out.println("An error occurred while connecting to SQLite database: " + e.getSQLState());
        }
        return connection;
    }

    @Override
    public void close() {
        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("An error occurred while disconnect to SQLite database: " + e.getSQLState());
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
}
