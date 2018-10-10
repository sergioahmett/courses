package com.epam.supportclasses;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.epam.daolayer.dao.CourseDAO;
import com.epam.daolayer.daoentity.DatabaseCourse;

public class CourseDAOReplacement extends CourseDAO {
    public List<DatabaseCourse> coursesList = new LinkedList<>();
    public List<int[]> courseToUser;

    @Override
    public List<DatabaseCourse> findAll() {
        return coursesList;
    }

    @Override
    public List<DatabaseCourse> findAllByTeacherId(int id) {
        return coursesList.stream().filter(course -> course.getTeacher() == id).collect(Collectors.toList());
    }

    @Override
    public List<DatabaseCourse> findAllByUserId(int id) {
        List<DatabaseCourse> result = new LinkedList<>();
        for (int[] arr : courseToUser) {
            if (arr[0] == id) {
                for (DatabaseCourse course : coursesList) {
                    if (course.getId() == arr[1]) {
                        result.add(course);
                        break;
                    }
                }
            }
        }
        return result;
    }

    @Override
    public List<DatabaseCourse> findAllByThemeId(int id) {
        return coursesList.stream().filter(course -> course.getTheme() == id).collect(Collectors.toList());
    }

    @Override
    public DatabaseCourse findEntityById(int id) {
        for (DatabaseCourse course : coursesList) {
            if (course.getId() == id) {
                return course;
            }
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        synchronized (coursesList) {
            Iterator<DatabaseCourse> itr = coursesList.iterator();
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
    public boolean create(DatabaseCourse entity) {
        synchronized (coursesList) {
            coursesList.add(entity);
            return true;
        }
    }

    @Override
    public boolean update(DatabaseCourse entity) {
        synchronized (coursesList) {
            Iterator<DatabaseCourse> itr = coursesList.iterator();
            boolean flag = false;
            while (itr.hasNext()) {
                DatabaseCourse course = itr.next();
                if (course.getId() == entity.getId()) {
                    coursesList.remove(course);
                    flag = true;
                }
            }
            if (flag) {
                coursesList.add(entity);
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
