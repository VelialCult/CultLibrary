package ru.velialcult.library.java.database.query;

import ru.velialcult.library.java.database.DataBase;
import ru.velialcult.library.java.database.SQLConnector;

import java.sql.ResultSet;
import java.util.*;
import java.util.stream.Collectors;

public class SQLQuery implements Query {

    private static String quoteIfNeeded(Object value) {
        if (value instanceof String || value instanceof UUID) {
            return "'" + value.toString() + "'";
        } else {
            return value.toString();
        }
    }

    public static Select selectFrom(String table) {
        return new Select(table);
    }

    public static Insert insertTo(String table) {
        return new Insert(table);
    }

    public static Update update(String table) {
        return new Update(table);
    }

    public static Delete deleteFrom(String table) {
        return new Delete(table);
    }

    public static InsertOrUpdate insertOrUpdate(DataBase dataBase, String table) { return new InsertOrUpdate(dataBase, table); }

    public static class Select implements Query {
        private final String table;
        private final List<Where> wheres = new ArrayList<>();
        private String result;
        private int limitSize = 0;

        private Select(String table) {
            this.table = table;
        }

        public Select where(String column, QuerySymbol symbol, Object result) {

            wheres.add(new Where(column, symbol, result));

            return this;
        }

        public Select limit() {
            return limit(1);
        }

        public Select limit(int limit) {
            if (limit < 0)
                return this;
            this.limitSize = limit;
            return this;
        }

        public Select result(String result) {
            if (!result.equals("*"))
                this.result = result;
            return this;
        }

        @Override
        public String toString() {

            StringBuilder whereString = new StringBuilder();

            int size = 0;
            for (Where where : wheres) {
                Object result = where.result;
                whereString.append("`")
                        .append(where.column)
                        .append("` ")
                        .append(where.symbol.getSymbol())
                        .append(" ")
                        .append(result instanceof String ? "\"" + result + "\"" : "'" + result + "'");
                size++;

                if (size < wheres.size())
                    whereString.append(" AND ");
            }

            String query =  "SELECT " + (result == null ? "*" : "`" + result + "`") + " FROM `" + table + "`";

            if (!wheres.isEmpty()) {

                query = query.concat(" WHERE " + whereString);
            }

            return query.concat(limitSize != 0 ? " LIMIT " + limitSize : "") + ";";
        }
    }

    private static class Where {
        private final String column;
        private final QuerySymbol symbol;
        private final Object result;

        public Where(String column, QuerySymbol symbol, Object result) {
            this.column = column;
            this.symbol = symbol;
            this.result = result;
        }
    }

    public static class Insert implements Query {

        private final String table;
        private final Map<String, Object> inserts = new HashMap<>();
        private final Map<String, Object> duplicate = new HashMap<>();

        private Insert(String table) {
            this.table = table;
        }

        public Insert set(String column, Object data) {

            inserts.put(column, data);

            return this;
        }

        public Insert setDuplicate(String column, Object data) {
            duplicate.put(column, data);
            return this;
        }

        @Override
        public String toString() {
            String columns = inserts.keySet()
                    .stream()
                    .map(s -> s = "`" + s + "`")
                    .collect(Collectors.joining(", "));

            String values = inserts.values()
                    .stream()
                    .map(Object::toString)
                    .map(s -> s = "'" + s + "'")
                    .collect(Collectors.joining(", "));

            StringBuilder duplicates = new StringBuilder();
            int size = 0;
            for (Map.Entry<String, Object> mapa : duplicate.entrySet()) {
                Object value = mapa.getValue();
                duplicates.append("`")
                        .append(mapa.getKey())
                        .append("` = ")
                        .append(value instanceof String ? "\"" + value + "\"" : "'" + value + "'");

                size++;

                if (size < duplicate.size())
                    duplicates.append(", ");
            }

            return "INSERT INTO `" + table + "` (" + columns + ") VALUES (" + values + ")"
                    + (duplicate.size() > 0 ? " ON DUPLICATE KEY UPDATE " + duplicates : "") + ";";
        }
    }

    public static class Update implements Query {

        private final String table;
        private final List<Where> wheres = new ArrayList<>();
        private final Map<String, Object> sets = new HashMap<>();
        private final Map<String, Object> adds = new HashMap<>();
        private int limitSize = 0;

        private Update(String table) {
            this.table = table;
        }

        public Update set(String column, Object data) {
            sets.put(column, data);
            return this;
        }

        public Update add(String column, Object data) {
            adds.put(column, data);
            return this;
        }

        public Update where(String column, QuerySymbol symbol, Object result) {
            wheres.add(new Where(column, symbol, result));
            return this;
        }

        public Update limit() {
            return limit(1);
        }

        public Update limit(int limit) {
            if (limit < 0)
                return this;

            this.limitSize = limit;
            return this;
        }

        @Override
        public String toString() {

            if (wheres.size() < 1)
                throw new NullPointerException("не указаны параметры WHERE");

            StringBuilder set = new StringBuilder();
            int size = 0;
            for (Map.Entry<String, Object> mapa : sets.entrySet()) {
                Object value = mapa.getValue();
                set.append("`")
                        .append(mapa.getKey())
                        .append("`")
                        .append(" = ")
                        .append(value instanceof String ? "\"" + value + "\"" : "'" + value + "'");

                size++;

                if (size < sets.size() || adds.size() != 0)
                    set.append(", ");
            }

            size = 0;
            for (Map.Entry<String, Object> mapa : adds.entrySet()) {
                Object value = mapa.getValue();
                set.append("`")
                        .append(mapa.getKey())
                        .append("`")
                        .append(" = `")
                        .append(mapa.getKey())
                        .append("` + ")
                        .append(value instanceof String ? "\"" + value + "\"" : value);

                size++;

                if (size < adds.size())
                    set.append(", ");
            }

            StringBuilder whereString = new StringBuilder();

            size = 0;
            for (Where where : wheres) {
                Object result = where.result;
                whereString.append("`")
                        .append(where.column)
                        .append("`")
                        .append(" ")
                        .append(where.symbol.getSymbol())
                        .append(" ")
                        .append(result instanceof String ? "\"" + result + "\"" : result);
                size++;

                if (size < wheres.size())
                    whereString.append(" AND ");
            }

            return "UPDATE `" + table + "` SET " + set + " WHERE " + whereString.toString()
                    + (limitSize != 0 ? " LIMIT " + limitSize : "") + ";";
        }

    }

    public static class Delete implements Query {

        private final String table;
        private final List<Where> wheres = new ArrayList<>();
        private int limitSize = 0;

        private Delete(String table) {
            this.table = table;
        }

        public Delete where(String column, QuerySymbol symbol, Object result) {

            wheres.add(new Where(column, symbol, result));

            return this;
        }

        public Delete limit() {
            return limit(1);
        }

        public Delete limit(int limit) {
            if (limit < 0)
                return this;

            this.limitSize = limit;
            return this;
        }

        @Override
        public String toString() {
            if (wheres.size() < 1)
                throw new NullPointerException("не указаны параметры WHERE");

            StringBuilder whereString = new StringBuilder();

            int size = 0;
            for (Where where : wheres) {
                Object result = where.result;
                whereString.append("`")
                        .append(where.column)
                        .append("`")
                        .append(" ")
                        .append(where.symbol.getSymbol())
                        .append(" ")
                        .append(result instanceof String ? "\"" + result + "\"" : "'" + result + "'");
                size++;

                if (size < wheres.size())
                    whereString.append(" AND ");
            }

            return "DELETE FROM `" + table + "` WHERE " + whereString.toString()
                    + (limitSize != 0 ? " LIMIT " + limitSize : "") + ";";
        }
    }

    public static class InsertOrUpdate implements Query {
        private final String targetTable;
        private final Map<String, Object> sets = new HashMap<>();
        private final List<Where> wheres = new ArrayList<>();
        private final SQLConnector sqlConnector;

        private InsertOrUpdate(DataBase dataBase, String targetTable) {
            this.sqlConnector = (SQLConnector) dataBase.getConnector();
            this.targetTable = targetTable;
        }

        public InsertOrUpdate set(String column, Object value) {
            sets.put(column, value);
            return this;
        }

        public InsertOrUpdate where(String column, QuerySymbol symbol, Object result) {
            wheres.add(new Where(column, symbol, result));
            return this;
        }

        private String buildWhereString(List<Where> wheres) {

            StringBuilder whereString = new StringBuilder();

            int size = 0;

            for (Where where : wheres) {

                whereString.append("`").append(where.column).append("`");

                whereString.append(" ").append(where.symbol.getSymbol()).append(" ");

                whereString.append(where.result instanceof String ? "\"" + where.result + "\"" : "'" + where.result + "'");

                size++;

                if (size < wheres.size()) {

                    whereString.append(" AND ");
                }
            }
            return whereString.toString();
        }

        private boolean whereConditionIsTrueInDatabase() {
            if (wheres.isEmpty()) {
                return false;
            }
            return sqlConnector.executeQuery("SELECT * FROM `" + targetTable + "` WHERE " + buildWhereString(wheres),
                    ResultSet::next, true);
        }

        @Override
        public String toString() {
            if (sets.size() < 1)
                throw new NullPointerException("не указаны параметры SET");

            if (whereConditionIsTrueInDatabase()) {

                StringBuilder setString = new StringBuilder();

                int size = 0;

                for (Map.Entry<String, Object> entry : sets.entrySet()) {

                    String column = entry.getKey();

                    Object value = entry.getValue();

                    setString.append("`").append(column).append("`");

                    setString.append(" = ");

                    setString.append(quoteIfNeeded(value));

                    size++;

                    if (size < sets.size()) {
                        setString.append(", ");
                    }
                }

                String whereString = buildWhereString(wheres);

                return "UPDATE `" + targetTable + "` SET " +
                        setString +
                        " WHERE " +
                        whereString + ";";
            } else {

                for (Where where : wheres) {
                    if (where.result != null) {
                        sets.put(where.column, where.result);
                    }
                }

                StringBuilder columnsString = new StringBuilder();

                StringBuilder valuesString = new StringBuilder();

                int size = 0;
                for (Map.Entry<String, Object> entry : sets.entrySet()) {

                    String column = entry.getKey();

                    Object value = entry.getValue();

                    columnsString.append("`").append(column).append("`");

                    valuesString.append(quoteIfNeeded(value));

                    size++;

                    if (size < sets.size()) {

                        columnsString.append(", ");

                        valuesString.append(", ");

                    }
                }

                return "INSERT INTO `" + targetTable + "` (" +
                        columnsString +
                        ") VALUES (" +
                        valuesString + ");";
            }
        }
    }
 }
