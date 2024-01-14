package ru.velialcult.library.java.database;

import ru.velialcult.library.java.database.query.Query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author Nicholas Alexandrov 18.08.2023
 */
public abstract class SQLConnector implements Connector {

    @Override
    public PreparedStatement createStatementSync(String query, int keys, Object... objects) {
        try {
            PreparedStatement ps = getConnection().prepareStatement(query, keys);
            if (objects != null) {
                for (int i = 0; i < objects.length; i++) {
                    ps.setObject(i + 1, objects[i]);
                }
            }
            if (objects == null || objects.length == 0) {
                ps.clearParameters();
            }
            return ps;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create statement", e);
        }
    }

    @Override
    public CompletableFuture<PreparedStatement> createStatement(String query, int keys, boolean async, Object... objects) {
        if (async) {
            return CompletableFuture.supplyAsync(() -> createStatementSync(query, keys, objects));
        } else {
            return CompletableFuture.completedFuture(createStatementSync(query, keys, objects));
        }
    }

    @Override
    public int execute(String query, boolean async, Object... objects) {
        Objects.requireNonNull(query, "SQL query must not be null");
        Objects.requireNonNull(objects, "Parameters query must not be null");
        Callable<Integer> callable = () -> {
            try (PreparedStatement ps = createStatement(query, PreparedStatement.RETURN_GENERATED_KEYS, async, objects).get()) {
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    return (rs.next() ? rs.getInt(1) : -1);
                }
            }
        };

        return handle(callable, async);
    }

    @Override
    public <T> T executeQuery(String query, ResponseHandler<ResultSet, T> handler, boolean async, Object... objects) {
        Objects.requireNonNull(query, "SQL query must not be null");
        Objects.requireNonNull(objects, "Parameters query must not be null");
        Callable<T> callable = () -> {
            try (PreparedStatement ps = createStatement(query, PreparedStatement.NO_GENERATED_KEYS, async, objects).get();
                 ResultSet rs = ps.executeQuery()) {
                return handler.handle(rs);
            }
        };

        return handle(callable, async);
    }

    @Override
    public <T> T handle(Callable<T> callable, boolean async) {
        if (async) {
            CompletableFuture<T> future = CompletableFuture.supplyAsync(() -> {
                try {
                    return callable.call();
                } catch (Exception e) {
                    throw new RuntimeException("Failed to execute query", e);
                }
            });
            try {
                return future.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException("Failed to execute async query", e);
            }
        } else {
            try {
                return callable.call();
            } catch (Exception e) {
                throw new RuntimeException("Failed to execute query", e);
            }
        }
    }
}
