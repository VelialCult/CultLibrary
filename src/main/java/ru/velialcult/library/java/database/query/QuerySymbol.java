package ru.velialcult.library.java.database.query;

/**
 *
 * Символы логических операций SQL
 *
 * @author Nicholas Alexandor 15.06.23
 *
 */

public enum QuerySymbol {
    EQUALLY("="),
    MORE_OR_EQUAL(">="),
    MORE(">"),
    LESS("<"),
    LESS_OR_EQUAL("<="),
    NOT_EQUAL("!=");

    private final String symbol;

    QuerySymbol(String symbol) {

        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
