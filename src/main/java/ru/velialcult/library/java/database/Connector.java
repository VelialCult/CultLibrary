package ru.velialcult.library.java.database;

import org.bukkit.plugin.Plugin;
import ru.velialcult.library.java.database.query.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

/**
 * @author Nicholas Alexandrov 26.07.2023
 */
public interface Connector {

    Connection connect();

    void close();

    boolean isConnected();

    Connection getConnection();

    PreparedStatement createStatementSync(String query, int keys, Object... objects);

    CompletableFuture<PreparedStatement> createStatement(String query, int keys, boolean async, Object... objects);

    int execute(String query, boolean async, Object... objects);

    default int execute(Query query, boolean async, Object... objects) {
        return execute(query.toString(), async, objects);
    }

    <T> T executeQuery(String query, ResponseHandler<ResultSet, T> handler, boolean async, Object... objects);

   default  <T> T executeQuery(Query query, ResponseHandler<ResultSet, T> handler, boolean async, Object... objects) {
       return executeQuery(query.toString(), handler, async);
   }


    <T> T handle(Callable<T> callable, boolean async);

   Plugin getPlugin();
}
