package com.epam.interfaces;

import java.util.List;
import java.util.Map;

import com.epam.daolayer.daoentity.DatabaseCourse;
import com.epam.daolayer.daoentity.DatabaseTheme;
import com.epam.daolayer.daoentity.DatabaseUser;
import com.epam.resultentity.ResultCourse;
import com.epam.resultentity.ResultTeacher;
import com.epam.resultentity.ResultTheme;
import com.epam.resultentity.ResultUser;
import com.epam.utils.Journal;

/**
 * Interface to DatabaseFasad classes. 
 * 
 * @author Sergey Ahmetshin
 *
 */
public interface DatabaseFasadInterface {

    List<DatabaseUser> getAllUser();

    DatabaseCourse getCourseById(int id);

    List<DatabaseTheme> getAllThemes();

    Map<String, Integer> getThemeForHeader();

    ResultTeacher getResultTeacherById(int id);

    List<ResultTeacher> getResultTeacherList();

    int saveUser(DatabaseUser user);

    DatabaseUser getUserByLoginOrMail(String loginOrMail);

    DatabaseUser getUserByID(int id);

    boolean updateUserPass(DatabaseUser user);

    ResultCourse getResultCourseById(int id);

    int regOrUnregUserToCourses(int userId, int courseId, String param);

    ResultUser getResultUserById(int id, String role);

    List<ResultCourse> getUserActualCourses(int id);

    ResultTheme getResultThemeById(int id);

    List<ResultCourse> getResultCourseList();

    List<ResultTheme> getResultThemeList();

    int blockUser(int id);

    int unblockUser(int parseInt);

    List<DatabaseUser> getAllStudents();

    List<DatabaseUser> getAllTeachers();

    int doTeacherById(int id, String string);

    int doStudentById(int id);

    int changeTeacherDescription(int parseInt, String parameter);

    int changeCourse(DatabaseCourse course);

    int createCourse(DatabaseCourse course);

    int deleteCourse(int parameter);

    int changeTheme(DatabaseTheme databaseTheme);

    int createTheme(DatabaseTheme databaseTheme);

    int deleteTheme(int parseInt);

    boolean checkAccessToJournal(int userId, int courseId, String role);

    Journal getJournal(int courseId, String role);

    int addDayToJournal(int courseId, String day);

    int addRaitingToJournal(int courseId, int userId, String day, String raiting);
    
    int addFinalRaitingToUser(int courseId, int userId, int raiting);
}