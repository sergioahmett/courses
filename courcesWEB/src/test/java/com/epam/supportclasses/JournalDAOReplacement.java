package com.epam.supportclasses;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import com.epam.daolayer.dao.JournalDAO;
import com.epam.utils.Journal;

public class JournalDAOReplacement extends JournalDAO {
    public Map<Integer, Journal> journalMap;

    @Override
    public boolean create(int courseId, Journal journal) {
        synchronized (journalMap) {
            journalMap.put(courseId, journal);
            return true;
        }
    }

    @Override
    public boolean update(int courseId, Journal journal) {
        synchronized (journalMap) {
            if (journalMap.containsKey(courseId)) {
                journalMap.put(courseId, journal);
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public Journal getById(int courseId) {
        return journalMap.get(courseId);
    }

    @Override
    public boolean delete(int courseId) {
        synchronized (journalMap) {
            if (journalMap.containsKey(courseId)) {
                journalMap.remove(courseId);
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public void setConnection(Connection connection) {
    }

    @Override
    public void closeConnection() throws SQLException {
    }

}
