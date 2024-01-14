package ru.velialcult.library.java.database;

import org.bukkit.plugin.Plugin;
import ru.velialcult.library.java.database.sqlite.SQLiteConnector;

import java.util.Objects;

public class DataBase {

    private final Connector connector;
    private final Plugin plugin;
    private final DataBaseType type;

    public DataBase(Plugin plugin, DataBaseType type) {
        this.plugin = plugin;
        this.type = type;
        this.connector = connect();
    }

    public Connector connect(String... params) {
        if (type == DataBaseType.MySQL) {
            if (params.length != 3) throw new IllegalArgumentException("Cant connect with database because parameters are not specified");
            return new MySQLConnector(params[0], params[1], params[2]);
        } else if (type == DataBaseType.SQLite) {
            Objects.requireNonNull(plugin, "Cant connect with database because parameters are not specified");
            return new SQLiteConnector(plugin);
        } else {
            throw new NullPointerException("Invalid database specified");
        }
    }

    public Connector getConnector() {
        return connector;
    }

    public Plugin getPlugin() {
        return plugin;
    }
}
