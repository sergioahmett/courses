package com.epam.supportclasses;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.epam.daolayer.dao.UserDAO;
import com.epam.daolayer.daoentity.DatabaseUser;

public class UserDAOReplacement extends UserDAO {
    public List<DatabaseUser> list;
    public Map<Integer, String> teacherDescription;

    @Override
    public List<DatabaseUser> findAll() {
        return list;
    }

    @Override
    public List<DatabaseUser> findAllTeachers() {
        synchronized (list) {
            return list.stream().filter(user -> user.getRole().equals("Teacher")).collect(Collectors.toList());
        }
    }

    @Override
    public List<DatabaseUser> findAllStudents() {
        synchronized (list) {
            return list.stream().filter(user -> user.getRole().equals("Student")).collect(Collectors.toList());
        }
    }

    @Override
    public DatabaseUser findUserById(int id) {
        synchronized (list) {
            for (DatabaseUser user : list) {
                if (user.getId() == id) {
                    return user;
                }
            }
            return null;
        }
    }

    @Override
    public DatabaseUser findUserByLogin(String login) {
        synchronized (list) {
            for (DatabaseUser user : list) {
                if (user.getLogin().equals(login)) {
                    return user;
                }
            }
            return null;
        }
    }

    @Override
    public DatabaseUser findUserByMail(String mail) {
        for (DatabaseUser user : list) {
            if (user.getMail().equals(mail)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        synchronized (list) {
            Iterator<DatabaseUser> itr = list.iterator();
            while (itr.hasNext()) {
                DatabaseUser user = itr.next();
                if (user.getId() == id) {
                    itr.remove();
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public boolean deleteTeacherDecription(int id) {
        synchronized (teacherDescription) {
            if (teacherDescription.containsKey(id)) {
                teacherDescription.remove(id);
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean create(DatabaseUser entity) {
        synchronized (list) {
            list.add(entity);
            return true;
        }
    }

    @Override
    public boolean createTeacherDescription(int id, String description) {
        synchronized (teacherDescription) {
            if (!teacherDescription.containsKey(id)) {
                teacherDescription.put(id, description);
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public String getTeacherDescription(int id) {
        return teacherDescription.get(id);
    }

    @Override
    public boolean update(DatabaseUser entity) {
        synchronized (list) {
            Iterator<DatabaseUser> itr = list.iterator();
            boolean flag = false;
            while (itr.hasNext()) {
                DatabaseUser user = itr.next();
                if (user.getId() == entity.getId()) {
                    list.remove(user);
                    flag = true;
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
    public boolean setTeacherDescription(int id, String description) {
        synchronized (teacherDescription) {
            if (teacherDescription.containsKey(id)) {
                teacherDescription.put(id, description);
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
