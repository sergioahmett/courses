package com.epam.daolayer.abstractdao;

import java.sql.Connection;
import java.sql.SQLException;

import com.epam.daolayer.daoentity.BaseEntity;

/**
 * This is abstract class to all DAO classes
 * 
 * @author Sergey Ahmetshin
 *
 * @param <T>
 *            T extends BaseEntity
 */
public abstract class AbstractDAO<T extends BaseEntity> {
    protected Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }
}
