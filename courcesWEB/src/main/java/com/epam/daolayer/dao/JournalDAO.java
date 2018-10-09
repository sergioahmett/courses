package com.epam.daolayer.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.epam.daolayer.abstractdao.AbstractDAO;
import com.epam.utils.Journal;

/**
 * The class that is responsible for working with the 'journal' table in the database
 * 
 * @author Sergey Ahmetshin
 *
 */
public class JournalDAO extends AbstractDAO<Journal> {
    private final static String SQL_INSERT = "INSERT INTO cources.journal (courseId, jsonJournal) VALUES (?, ?);";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM cources.journal WHERE courseId = ?";
    private final static String SQL_DELL = "DELETE FROM cources.journal WHERE courseId = ?";
    private final static String SQL_UPDATE = "UPDATE cources.journal SET jsonJournal = ? WHERE (courseId = ?)";

    public boolean create(int courseId, Journal journal) {
        try (PreparedStatement st = connection.prepareStatement(SQL_INSERT)) {
            st.setInt(1, courseId);
            st.setString(2, journal.getJSON());
            return st.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error by creating statment!");
            return false;
        }
    }

    public boolean update(int courseId, Journal journal) {
        try (PreparedStatement st = connection.prepareStatement(SQL_UPDATE)) {
            st.setString(1, journal.getJSON());
            st.setInt(2, courseId);
            return st.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error by creating statment!");
            return false;
        }
    }

    public Journal getById(int courseId) {
        try (PreparedStatement st = connection.prepareStatement(SQL_FIND_BY_ID);) {
            st.setInt(1, courseId);
            ResultSet rs = st.executeQuery();
            return rs.first() ? Journal.getFromJson(rs.getString("jsonJournal")) : null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error by creating statment!");
            return null;
        }
    }

    public boolean delete(int courseId) {
        try (PreparedStatement st = connection.prepareStatement(SQL_DELL);) {
            st.setInt(1, courseId);
            return st.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error by creating statment!");
        }
        return false;
    }
}
