package com.epam.supportclasses;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import com.epam.daolayer.dao.ThemeDAO;
import com.epam.daolayer.daoentity.DatabaseTheme;

public class ThemeDAOReplacement extends ThemeDAO {
    public List<DatabaseTheme> list;

    @Override
    public List<DatabaseTheme> findAll() {
        return list;
    }

    @Override
    public DatabaseTheme findThemeById(int id) {
        for (DatabaseTheme theme : list) {
            if (theme.getId() == id) {
                return theme;
            }
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        synchronized (list) {
            Iterator<DatabaseTheme> itr = list.iterator();
            while (itr.hasNext()) {
                if (itr.next().getId() == id) {
                    itr.remove();
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public boolean create(DatabaseTheme entity) {
        synchronized (list) {
            list.add(entity);
            return true;
        }
    }

    @Override
    public boolean update(DatabaseTheme entity) {
        synchronized (list) {
            Iterator<DatabaseTheme> itr = list.iterator();
            boolean flag = false;
            while (itr.hasNext()) {
                if (itr.next().getId() == entity.getId()) {
                    itr.remove();
                    flag = true;
                    break;
                }
            }
            if (flag) {
                list.add(entity);
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
