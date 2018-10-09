package com.epam.daolayer.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.epam.daolayer.daoentity.DatabaseTheam;
import com.epam.daolayer.abstractdao.AbstractDAO;

/**
 * The class that is responsible for working with the 'theam' table in the database
 * 
 * @author Sergey Ahmetshin
 *
 */
public class TheamDAO extends AbstractDAO<DatabaseTheam> {
    private final static String SQL_FIND_ALL = "SELECT * FROM cources.theam";
    private final static String SQL_INSERT = "INSERT INTO cources.theam (title, description) VALUES (?, ?);";
    private final static String SQL_UPDATE = "UPDATE cources.theam SET title = ?, description = ? WHERE (theamId = ?)";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM cources.theam WHERE theamId = ?";
    private final static String SQL_DELL_BY_ID = "DELETE FROM cources.theam WHERE theamId = ?";

    public List<DatabaseTheam> findAll() {
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(SQL_FIND_ALL);
            List<DatabaseTheam> result = new LinkedList<>();
            while (rs.next()) {
                result.add(new DatabaseTheam(rs.getInt("theamId"), rs.getString("title"), rs.getString("description")));
            }
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error by creating statment!");
        }
        return null;
    }

    public DatabaseTheam findTheamById(int id) {
        try (PreparedStatement st = connection.prepareStatement(SQL_FIND_BY_ID);) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new DatabaseTheam(rs.getInt("theamId"), rs.getString("title"), rs.getString("description"));
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

    public boolean create(DatabaseTheam entity) {
        try (PreparedStatement st = connection.prepareStatement(SQL_INSERT)) {
            st.setString(1, entity.getTitle());
            st.setString(2, entity.getDescription());
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

    public boolean update(DatabaseTheam entity) {
        try (PreparedStatement st = connection.prepareStatement(SQL_UPDATE)) {
            st.setString(1, entity.getTitle());
            st.setString(2, entity.getDescription());
            st.setInt(3, entity.getId());
            return st.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error by creating statment!");
            return false;
        }
    }
}
