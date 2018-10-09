package com.epam.daolayer.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.epam.daolayer.daoentity.BaseEntity;
import com.epam.daolayer.abstractdao.AbstractDAO;

/**
 * The class that is responsible for working with the 'coursetouser' table in the database
 * 
 * @author Sergey Ahmetshin
 *
 */
public class CourseToUserDAO extends AbstractDAO<BaseEntity> {
    private final static String SQL_INSERT = "INSERT INTO cources.coursetouser (userId, courseId) VALUES (?, ?);";
    private final static String SQL_FIND_BY_USER_ID = "SELECT * FROM cources.coursetouser WHERE userId = ";
    private final static String SQL_FIND_BY_COURSE_ID = "SELECT * FROM cources.coursetouser WHERE courseId = ";
    private final static String SQL_DELL = "DELETE FROM cources.coursetouser WHERE userId = ? and courseId = ?";
    private final static String SQL_DELL_BY_COURSE_ID = "DELETE FROM cources.coursetouser WHERE courseId = ?";
    private final static String SQL_DELL_BY_USER_ID = "DELETE FROM cources.coursetouser WHERE userId = ?";
    private final static String SQL_FIND_BY_USER_AND_COURSE = "SELECT * FROM cources.coursetouser WHERE userId = ? and courseId = ?";
    
    public List<Integer> findAllUserCourses(int id){
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(SQL_FIND_BY_USER_ID + id);
            List<Integer> result = new LinkedList<>();
            while (rs.next()) {
                result.add(rs.getInt("courseId"));
            }
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error by creating statment!");
        }
        return null;
    }
    
    public List<Integer> findAllCourseUsers(int courseId){
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(SQL_FIND_BY_COURSE_ID + courseId);
            List<Integer> result = new LinkedList<>();
            while (rs.next()) {
                result.add(rs.getInt("userId"));
            }
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error by creating statment!");
        }
        return null;
    }

    public boolean delete(int userId, int courseId) {
        try (PreparedStatement st = connection.prepareStatement(SQL_DELL);) {
            st.setInt(1, userId);
            st.setInt(2, courseId);
            if (st.executeUpdate() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error by creating statment!");
        }
        return false;
    }
    public boolean deleteByCourseId(int courseId) {
        try (PreparedStatement st = connection.prepareStatement(SQL_DELL_BY_COURSE_ID);) {
            st.setInt(1, courseId);
            if (st.executeUpdate() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error by creating statment!");
        }
        return false;
    }
    public boolean deleteByUserId(int userId) {
        try (PreparedStatement st = connection.prepareStatement(SQL_DELL_BY_USER_ID);) {
            st.setInt(1, userId);
            if (st.executeUpdate() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error by creating statment!");
        }
        return false;
    }
    
    public boolean findByUserAndCourse(int userId, int courseId) {
        try (PreparedStatement st = connection.prepareStatement(SQL_FIND_BY_USER_AND_COURSE);) {
            st.setInt(1, userId);
            st.setInt(2, courseId);
            ResultSet rs = st.executeQuery();
            return rs.first();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error by creating statment!");
            return false;
        }
    }
    
    public boolean create(int userId, int courseId) {
        try (PreparedStatement st = connection.prepareStatement(SQL_INSERT)) {
            st.setInt(1, userId);
            st.setInt(2, courseId);
            if (st.executeUpdate() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error by creating statment!");
        }
        return false;
    }

}
