package com.epam.supportclasses;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.epam.daolayer.dao.CourseToUserDAO;

public class CourseToUserDAOReplacement extends CourseToUserDAO {
    public List<int[]> courseToUser;

    @Override
    public List<Integer> findAllUserCourses(int id) {
        List<Integer> list = new LinkedList<>();
        for (int[] arr : courseToUser) {
            if (arr[0] == id) {
                list.add(arr[1]);
            }
        }
        return list;
    }

    @Override
    public List<Integer> findAllCourseUsers(int courseId) {
        List<Integer> list = new LinkedList<>();
        for (int[] arr : courseToUser) {
            if (arr[1] == courseId) {
                list.add(arr[0]);
            }
        }
        return list;
    }

    @Override
    public boolean delete(int userId, int courseId) {
        synchronized (courseToUser) {
            Iterator<int[]> itr = courseToUser.iterator();
            while (itr.hasNext()) {
                int[] arr = itr.next();
                if (arr[0] == userId && arr[1] == courseId) {
                    itr.remove();
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public boolean deleteByCourseId(int courseId) {
        synchronized (courseToUser) {
            Iterator<int[]> itr = courseToUser.iterator();
            boolean flag = false;
            while (itr.hasNext()) {
                int[] arr = itr.next();
                if (arr[1] == courseId) {
                    itr.remove();
                    flag = true;
                }
            }
            return flag;
        }
    }

    @Override
    public boolean deleteByUserId(int userId) {
        synchronized (courseToUser) {
            Iterator<int[]> itr = courseToUser.iterator();
            boolean flag = false;
            while (itr.hasNext()) {
                int[] arr = itr.next();
                if (arr[0] == userId) {
                    itr.remove();
                    flag = true;
                }
            }
            return flag;
        }
    }

    @Override
    public boolean findByUserAndCourse(int userId, int courseId) {
        Iterator<int[]> itr = courseToUser.iterator();
        while (itr.hasNext()) {
            int[] arr = itr.next();
            if (arr[0] == userId && arr[0] == courseId) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean create(int userId, int courseId) {
        synchronized (courseToUser) {
            courseToUser.add(new int[] { userId, courseId });
            return true;
        }
    }

    @Override
    public void setConnection(Connection connection) {
    }

    @Override
    public void closeConnection() throws SQLException {
    }

}
