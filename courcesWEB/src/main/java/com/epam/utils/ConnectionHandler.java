package com.epam.utils;

import java.sql.SQLException;
import java.sql.Connection;

/**
 * The class that is responsible for autorollback. And auto set autocommit to
 * default
 * 
 * @author Sergey Ahmetshin
 *
 */

public class ConnectionHandler implements AutoCloseable {

    private Connection conn;
    private boolean committed;
    private boolean originalAutoCommit;

    public ConnectionHandler(Connection conn, boolean autoCommit) throws SQLException {
        this.conn = conn;
        originalAutoCommit = conn.getAutoCommit();
        conn.setAutoCommit(autoCommit);
    }

    public void commit() throws SQLException {
        conn.commit();
        committed = true;
    }

    @Override
    public void close() throws SQLException {
        if (!committed) {
            conn.rollback();
        }
        conn.setAutoCommit(originalAutoCommit);
    }

}
