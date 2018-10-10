package com.epam.daolayer.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.epam.daolayer.daoentity.DatabaseCourse;
import com.epam.daolayer.abstractdao.AbstractDAO;

/**
 * The class that is responsible for working with the 'course' table in the
 * database
 * 
 * @author Sergey Ahmetshin
 *
 */
public class CourseDAO extends AbstractDAO<DatabaseCourse> {
    private final static String SQL_FIND_ALL = "SELECT * FROM cources.course";
    private final static String SQL_INSERT = "INSERT INTO cources.course (title, theme, description, duration, maxStudentCount, teacher, startDate) VALUES (?, ?, ?, ?, ?, ?, ?);";
    private final static String SQL_UPDATE = "UPDATE cources.course SET title = ?, theme = ?, description = ?, duration = ?, maxStudentCount = ?, teacher = ?, startDate = ? WHERE (idCourse = ?)";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM cources.course WHERE idCourse = ?";
    private final static String SQL_DELL_BY_ID = "DELETE FROM cources.course WHERE idCourse = ?";
    private final static String SQL_FIND_BY_THEME_ID = "SELECT * FROM cources.course WHERE theme = ?";
    private final static String SQL_FIND_BY_TEACHER_ID = "SELECT * FROM cources.course WHERE teacher = ?";
    private final static String SQL_FIND_BY_USER_ID = "SELECT idCourse, title, theme, description, duration, teacher, maxStudentCount, startDate FROM cources.course join cources.coursetouser on course.idCourse=coursetouser.courseId WHERE coursetouser.userId = ?";

    public List<DatabaseCourse> findAll() {
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(SQL_FIND_ALL);
            List<DatabaseCourse> result = new LinkedList<>();
            while (rs.next()) {
                result.add(createCoureByResultSet(rs));
            }
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error by creating statment!");
        }
        return null;
    }

    public List<DatabaseCourse> findAllByTeacherId(int id) {
        try (PreparedStatement st = connection.prepareStatement(SQL_FIND_BY_TEACHER_ID);) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            List<DatabaseCourse> result = new LinkedList<>();
            while (rs.next()) {
                result.add(createCoureByResultSet(rs));
            }
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error by creating statment!");
        }
        return null;
    }

    public List<DatabaseCourse> findAllByUserId(int id) {
        try (PreparedStatement st = connection.prepareStatement(SQL_FIND_BY_USER_ID);) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            List<DatabaseCourse> result = new LinkedList<>();
            while (rs.next()) {
                result.add(createCoureByResultSet(rs));
            }
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error by creating statment!");
        }
        return null;
    }

    public List<DatabaseCourse> findAllByThemeId(int id) {
        try (PreparedStatement st = connection.prepareStatement(SQL_FIND_BY_THEME_ID);) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            List<DatabaseCourse> result = new LinkedList<>();
            while (rs.next()) {
                result.add(createCoureByResultSet(rs));
            }
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error by creating statment!");
        }
        return null;
    }

    public DatabaseCourse findEntityById(int id) {
        try (PreparedStatement st = connection.prepareStatement(SQL_FIND_BY_ID);) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.first()) {
                return createCoureByResultSet(rs);
            } else {
                return null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error by creating statment!");
        }
        return null;
    }

    public boolean delete(int id) {
        try (PreparedStatement st = connection.prepareStatement(SQL_DELL_BY_ID);) {
            st.setInt(1, id);
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

    public boolean create(DatabaseCourse entity) {
        try (PreparedStatement st = connection.prepareStatement(SQL_INSERT)) {
            st.setString(1, entity.getTitle());
            st.setInt(2, entity.getTheme());
            st.setString(3, entity.getDescription());
            st.setInt(4, entity.getDuration());
            st.setInt(5, entity.getMaxStudentCount());
            st.setInt(6, entity.getTeacher());
            st.setTimestamp(7, entity.getStartDate());
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

    public boolean update(DatabaseCourse entity) {
        try (PreparedStatement st = connection.prepareStatement(SQL_UPDATE)) {
            st.setInt(8, entity.getId());
            st.setString(1, entity.getTitle());
            st.setInt(2, entity.getTheme());
            st.setString(3, entity.getDescription());
            st.setInt(4, entity.getDuration());
            st.setInt(5, entity.getMaxStudentCount());
            st.setInt(6, entity.getTeacher());
            st.setTimestamp(7, entity.getStartDate());
            return st.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error by creating statment!");
            return false;
        }
    }

    private DatabaseCourse createCoureByResultSet(ResultSet rs) throws SQLException {
        return DatabaseCourse.newBuilder().setId(rs.getInt("idCourse")).setTitle(rs.getString("title"))
                .setTheme(rs.getInt("theme")).setDescription(rs.getString("description"))
                .setDuration(rs.getInt("duration")).setStudentCount(rs.getInt("maxStudentCount"))
                .setTeacher(rs.getInt("teacher")).setStartDate(rs.getTimestamp("startDate")).build();
    }
}
