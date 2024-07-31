package ru.velialcult.library.java.database.table;

import ru.velialcult.library.java.database.DataBase;
import ru.velialcult.library.java.database.SQLConnector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public final class TableConstructor {

    private final String name;
    private final List<TableColumn> tableColumns;
    private final List<String> columns = new ArrayList<>();

    public TableConstructor(String name, TableColumn... tableColumns) {
        this.name = name;
        this.tableColumns = Arrays.asList(tableColumns);
    }

    public void addIndex(String column) {
        this.columns.add(column);
    }

    @Override
    public String toString() {
        String columnSql = tableColumns.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        String primary = tableColumns.stream()
                .filter(TableColumn::isPrimaryKey)
                .map(TableColumn::getName)
                .collect(Collectors.joining(", "));

        if (primary != null && !primary.isEmpty()) {
            columnSql = columnSql + ", PRIMARY KEY (" + primary + ")";
        }

        return "CREATE TABLE IF NOT EXISTS `" + name + "` (" + columnSql
                + ");";
    }

    public void create(DataBase dataBase) {
        if (dataBase.getConnector() instanceof SQLConnector) {
            SQLConnector sqlConnector = (SQLConnector) dataBase.getConnector();
            sqlConnector.execute(this.toString(), false);
            for (String columnName : columns) {
                sqlConnector.execute("ALTER TABLE `" + name + "` ADD INDEX (`" + columnName + "`);", true);
            }
        }
    }
}
