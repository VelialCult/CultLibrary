package ru.velialcult.library.java.database;

import java.sql.SQLException;

/**
 * @author Nicholas Alexandrov 13.06.2023
 */
@Deprecated
public interface ResponseHandler<H, R> {

    R handle(H handle) throws SQLException;
}
