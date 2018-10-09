package com.epam.daolayer.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.epam.daolayer.daoentity.DatabaseUser;
import com.epam.daolayer.abstractdao.AbstractDAO;

/**
 * The class that is responsible for working with the 'user' and 'teacher_description' tables in the database
 * 
 * @author Sergey Ahmetshin
 *
 */
public class UserDAO extends AbstractDAO<DatabaseUser> {
    private final static String SQL_FIND_ALL = "SELECT * FROM cources.user";
    private final static String SQL_FIND_TEACHERS = "SELECT * FROM cources.user WHERE role=\'Teacher\'";
    private final static String SQL_FIND_STUDENTS = "SELECT * FROM cources.user WHERE role=\'Student\'";
    private final static String SQL_INSERT = "INSERT INTO cources.user (login, mail, password, name, surname, role, block) VALUES (?, ?, ?, ?, ?, ?, ?);";
    private final static String SQL_UPDATE = "UPDATE cources.user SET login = ?, mail = ?, password = ?, name = ?, surname = ?, role = ?, block = ? WHERE (id = ?)";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM cources.user WHERE id = ";
    private final static String SQL_FIND_BY_LOGIN = "SELECT * FROM cources.user WHERE login = '";
    private final static String SQL_FIND_BY_MAIL = "SELECT * FROM cources.user WHERE mail = '";
    private final static String SQL_DELL_BY_ID = "DELETE FROM cources.user WHERE id = ?";
    private final static String SQL_FIND_TEACHER_DESCRIPTION = "SELECT description FROM user join teacher_description on id=teacherId where id = ?";
    private final static String SQL_UPDATE_TEACHER_DESCRIPTION = "UPDATE cources.teacher_description SET description = ? where teacherId = ?";
    private final static String SQL_INSERT_TEACHER_DESCRIPTION = "INSERT INTO cources.teacher_description (teacherId, description) VALUES (?, ?)";
    private final static String SQL_DELL_TEACHER_DESCRIPTION = "DELETE FROM cources.teacher_description where teacherId = ?";
    

    public List<DatabaseUser> findAll() {
        return findBySQL(SQL_FIND_ALL);
    }

    public List<DatabaseUser> findAllTeachers() {
        return findBySQL(SQL_FIND_TEACHERS);
    }

    public List<DatabaseUser> findAllStudents() {
        return findBySQL(SQL_FIND_STUDENTS);
    }

    public DatabaseUser findUserById(int id) {
        List<DatabaseUser> list = findBySQL(SQL_FIND_BY_ID + id);
        return list.size() > 0 ? list.get(0) : null;
    }

    public DatabaseUser findUserByLogin(String login) {
        List<DatabaseUser> list = findBySQL(SQL_FIND_BY_LOGIN + login+"'");
        return list.size() > 0 ? list.get(0) : null;
    }

    public DatabaseUser findUserByMail(String mail) {
        List<DatabaseUser> list = findBySQL(SQL_FIND_BY_MAIL + mail+"'");
        return list.size() > 0 ? list.get(0) : null;
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
    
    public boolean deleteTeacherDecription(int id) {
        try (PreparedStatement st = connection.prepareStatement(SQL_DELL_TEACHER_DESCRIPTION);) {
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

    public boolean create(DatabaseUser entity) {
        try (PreparedStatement st = connection.prepareStatement(SQL_INSERT)) {
            st.setString(1, entity.getLogin());
            st.setString(2, entity.getMail());
            st.setString(3, entity.getPassword());
            st.setString(4, entity.getName());
            st.setString(5, entity.getSurname());
            st.setString(6, entity.getRole());
            st.setBoolean(7, entity.isBlock());
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
    
    public boolean createTeacherDescription(int id, String description) {
        try (PreparedStatement st = connection.prepareStatement(SQL_INSERT_TEACHER_DESCRIPTION)) {
            st.setInt(1, id);
            st.setString(2, description);
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

    public String getTeacherDescription(int id) {
        try (PreparedStatement st = connection.prepareStatement(SQL_FIND_TEACHER_DESCRIPTION)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            rs.next();
            return rs.getString("description");
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error by creating statment!");
            return null;
        }
    }
    
    public boolean update(DatabaseUser entity) {
        try (PreparedStatement st = connection.prepareStatement(SQL_UPDATE)) {
            st.setString(1, entity.getLogin());
            st.setString(2, entity.getMail());
            st.setString(3, entity.getPassword());
            st.setString(4, entity.getName());
            st.setString(5, entity.getSurname());
            st.setString(6, entity.getRole());
            st.setBoolean(7, entity.isBlock());
            st.setInt(8, entity.getId());
            return st.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error by creating statment!");
            return false;
        }
    }
    
    public boolean setTeacherDescription(int id, String description) {
        try (PreparedStatement st = connection.prepareStatement(SQL_UPDATE_TEACHER_DESCRIPTION)) {
            st.setString(1, description);
            st.setInt(2, id);
            return st.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error by creating statment!");
            return false;
        }
    }

    private List<DatabaseUser> findBySQL(String sql) {
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            List<DatabaseUser> result = new LinkedList<>();
            while (rs.next()) {
                DatabaseUser user = DatabaseUser.newBuilder().setId(rs.getInt("id")).setLogin(rs.getString("login"))
                        .setMail(rs.getString("mail")).setPassword(rs.getString("password"))
                        .setName(rs.getString("name")).setSurname(rs.getString("surname")).setRole(rs.getString("role"))
                        .setBlock(rs.getBoolean("block")).build();
                result.add(user);
            }
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error by creating statment!");
        }
        return null;
    }
}
