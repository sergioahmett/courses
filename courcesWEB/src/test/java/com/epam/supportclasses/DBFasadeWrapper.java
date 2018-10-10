package com.epam.supportclasses;

import java.sql.Connection;

import com.epam.daolayer.dbfacade.DBFacade;
import com.epam.interfaces.DatabaseFasadInterface;

public class DBFasadeWrapper extends DBFacade {
    private static DatabaseFasadInterface instance;

    @Override
    public Connection getConnection() {
        return new ConnectionReplacement();
    }

    public synchronized static DatabaseFasadInterface getInstance() {
        if (instance == null) {
            instance = new DBFasadeWrapper();
        }
        return instance;
    }

}
