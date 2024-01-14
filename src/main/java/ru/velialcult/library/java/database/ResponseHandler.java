package ru.velialcult.library.java.database;

import java.sql.SQLException;

/**
 * @author Nicholas Alexandrov 13.06.2023
 */
public interface ResponseHandler<H, R> {

    R handle(H handle) throws SQLException;
}
