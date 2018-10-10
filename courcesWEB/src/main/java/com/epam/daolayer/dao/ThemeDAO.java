package com.epam.daolayer.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.epam.daolayer.daoentity.DatabaseTheme;
import com.epam.daolayer.abstractdao.AbstractDAO;

/**
 * The class that is responsible for working with the 'theme' table in the database
 * 
 * @author Sergey Ahmetshin
 *
 */
public class ThemeDAO extends AbstractDAO<DatabaseTheme> {
    private final static String SQL_FIND_ALL = "SELECT * FROM cources.theme";
    private final static String SQL_INSERT = "INSERT INTO cources.theme (title, description) VALUES (?, ?);";
    private final static String SQL_UPDATE = "UPDATE cources.theme SET title = ?, description = ? WHERE (themeId = ?)";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM cources.theme WHERE themeId = ?";
    private final static String SQL_DELL_BY_ID = "DELETE FROM cources.theme WHERE themeId = ?";

    public List<DatabaseTheme> findAll() {
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(SQL_FIND_ALL);
            List<DatabaseTheme> result = new LinkedList<>();
            while (rs.next()) {
                result.add(new DatabaseTheme(rs.getInt("themeId"), rs.getString("title"), rs.getString("description")));
            }
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error by creating statment!");
        }
        return null;
    }

    public DatabaseTheme findThemeById(int id) {
        try (PreparedStatement st = connection.prepareStatement(SQL_FIND_BY_ID);) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new DatabaseTheme(rs.getInt("themeId"), rs.getString("title"), rs.getString("description"));
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

    public boolean create(DatabaseTheme entity) {
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

    public boolean update(DatabaseTheme entity) {
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
