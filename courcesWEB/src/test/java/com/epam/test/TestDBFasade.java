package com.epam.test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static com.epam.utils.CodeHandler.*;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.epam.daolayer.dao.CourseDAO;
import com.epam.daolayer.dao.JournalDAO;
import com.epam.daolayer.dao.ThemeDAO;
import com.epam.daolayer.dao.UserDAO;
import com.epam.daolayer.daoentity.DatabaseCourse;
import com.epam.daolayer.daoentity.DatabaseTheme;
import com.epam.daolayer.daoentity.DatabaseUser;
import com.epam.daolayer.dbfacade.DBFacade;
import com.epam.daolayer.dbfacade.DBFacade.Injector;
import com.epam.interfaces.DatabaseFasadInterface;
import com.epam.resultentity.ResultCourse;
import com.epam.resultentity.ResultTeacher;
import com.epam.resultentity.ResultTheme;
import com.epam.resultentity.ResultUser;
import com.epam.supportclasses.CourseDAOReplacement;
import com.epam.supportclasses.CourseToUserDAOReplacement;
import com.epam.supportclasses.DBFasadeWrapper;
import com.epam.supportclasses.JournalDAOReplacement;
import com.epam.supportclasses.ThemeDAOReplacement;
import com.epam.supportclasses.UserDAOReplacement;
import com.epam.utils.Journal;

public class TestDBFasade {
    private static DatabaseFasadInterface dbFasade;
    private static List<DatabaseCourse> courseList;
    private static List<int[]> courseToUser;
    private static Map<Integer, Journal> journalMap;
    private static List<DatabaseTheme> themeList;
    private static List<DatabaseUser> userList;
    private static Map<Integer, String> teacherDescription;
    private static int teacherCount;
    private static int studCount;

    @BeforeAll
    public static void init() {
        Injector inj = ((DBFacade) DBFasadeWrapper.getInstance()).getInjector();
        inj.setCourseDAO(initCourseDAO());
        inj.setCourseToUserDAO(initCourseToUserDAO());
        inj.setJournalDAO(initJournalDAO());
        inj.setThemeDAO(initThemeDAO());
        inj.setUserDAO(initUserDAO());
        dbFasade = DBFasadeWrapper.getInstance();
    }

    @Test
    public void getThemeForHeader() {
        Map<String, Integer> result = dbFasade.getThemeForHeader();
        assertEquals(themeList.size(), result.size());
    }

    @Test
    public void getResultTeacherById() {
        ResultTeacher result = dbFasade.getResultTeacherById(5);
        ResultTeacher expectedTeacher = new ResultTeacher(5, "5 5", "5", null);
        assertTrue(expectedTeacher.compereTeacher(result));
    }

    @Test
    public void getResultTeacherById2() {
        ResultTeacher result = dbFasade.getResultTeacherById(6);
        ResultTeacher expectedTeacher = new ResultTeacher(6, "5 5", "5", null);
        assertFalse(expectedTeacher.compereTeacher(result));
    }

    @Test
    public void getResultTeacherById3() {
        ResultTeacher result = dbFasade.getResultTeacherById(3);
        assertNull(result);
    }

    @Test
    public void getResultTeacherById4() {
        ResultTeacher result = dbFasade.getResultTeacherById(20);
        assertNull(result);
    }

    @Test
    public void getResultTeacherList() {
        List<ResultTeacher> result = dbFasade.getResultTeacherList();
        assertEquals(teacherCount, result.size());
    }

    @Test
    public void saveUser() {
        DatabaseUser testUser = DatabaseUser.newBuilder().setLogin("1").setMail("2").build();
        int result = dbFasade.saveUser(testUser);
        assertEquals(LOGIN_ALREADY_EXIST, result);
    }

    @Test
    public void saveUser2() {
        DatabaseUser testUser = DatabaseUser.newBuilder().setLogin("55").setMail("1").build();
        int result = dbFasade.saveUser(testUser);
        assertEquals(MAIL_ALREADY_EXIST, result);
    }

    @Test
    public void saveUser3() {
        DatabaseUser testUser = DatabaseUser.newBuilder().setLogin("55").setMail("55").build();
        int result = dbFasade.saveUser(testUser);
        studCount++;
        assertEquals(OK, result);
    }

    @Test
    public void getUserByLoginOrMail() {
        DatabaseUser result = dbFasade.getUserByLoginOrMail("login");
        assertNotNull(result);
    }

    @Test
    public void getUserByLoginOrMail2() {
        DatabaseUser result = dbFasade.getUserByLoginOrMail("mail");
        assertNotNull(result);
    }

    @Test
    public void getUserByLoginOrMail3() {
        DatabaseUser result = dbFasade.getUserByLoginOrMail("asdasdasdafa");
        assertNull(result);
    }

    @Test
    public void getUserByID() {
        DatabaseUser result = dbFasade.getUserByID(0);
        assertNotNull(result);
    }

    @Test
    public void getUserByID2() {
        DatabaseUser result = dbFasade.getUserByID(10);
        assertNull(result);
    }

    @Test
    public void getAllThemes() {
        List<DatabaseTheme> result = dbFasade.getAllThemes();
        assertEquals(themeList.size(), result.size());
    }

    @Test
    public void getAllStudents() {
        List<DatabaseUser> result = dbFasade.getAllStudents();
        assertEquals(studCount, result.size());
    }

    @Test
    public void getAllTeachers() {
        List<DatabaseUser> result = dbFasade.getAllTeachers();
        assertEquals(teacherCount, result.size());
    }

    @Test
    public void getResultCourseById() {
        ResultCourse result = dbFasade.getResultCourseById(10);
        assertNull(result);
    }

    @Test
    public void getResultCourseById2() {
        ResultCourse result = dbFasade.getResultCourseById(2);
        assertNotNull(result);
    }

    @Test
    public void getResultCourseById3() {
        ResultCourse result = dbFasade.getResultCourseById(2);
        assertEquals(2, result.getThemeId());
        assertEquals(6, result.getTeacheId());
    }

    @Test
    public void getResultCourseById4() {
        ResultCourse result = dbFasade.getResultCourseById(4);
        assertEquals(0, result.getThemeId());
        assertEquals(4, result.getTeacheId());
    }

    @Test
    public void regOrUnregUserToCourses() {
        int result = dbFasade.regOrUnregUserToCourses(0, 0, "register");
        assertEquals(USER_ALREADY_REGISTER_TO_COURSE, result);
    }

    @Test
    public void regOrUnregUserToCourses2() {
        int result = dbFasade.regOrUnregUserToCourses(0, 2, "register");
        assertEquals(COURSE_NOT_ACTUAL, result);
    }

    @Test
    public void regOrUnregUserToCourses4() {
        int result = dbFasade.regOrUnregUserToCourses(0, 4, "register");
        assertEquals(REGISTER_TO_COURSE_SUCCSESS, result);
    }

    @Test
    public void regOrUnregUserToCourses5() {
        int result = dbFasade.regOrUnregUserToCourses(0, 4, "adsads");
        assertEquals(PARAMETER_NOT_VALID, result);
    }

    @Test
    public void regOrUnregUserToCourses6() {
        int result = dbFasade.regOrUnregUserToCourses(234, 4, "register");
        assertEquals(USER_NOT_FOUND, result);
    }

    @Test
    public void regOrUnregUserToCourses7() {
        int result = dbFasade.regOrUnregUserToCourses(0, 234, "register");
        assertEquals(COURSE_NOT_FOUND, result);
    }

    @Test
    public void regOrUnregUserToCourses8() {
        int result = dbFasade.regOrUnregUserToCourses(1, 0, "unregister");
        assertEquals(USER_ALREADY_NOTREGISTER_TO_COURSE, result);
    }

    @Test
    public void regOrUnregUserToCourses9() {
        int result = dbFasade.regOrUnregUserToCourses(1, 1, "unregister");
        assertEquals(UNREGISTER_TO_COURSE_SUCCSESS, result);
    }

    @Test
    public void getResultUserById() {
        ResultUser result = dbFasade.getResultUserById(0, "Student");
        assertNotNull(result);
    }

    @Test
    public void getResultUserById2() {
        ResultUser result = dbFasade.getResultUserById(0, "Teacher");
        assertNull(result);
    }

    @Test
    public void getResultUserById3() {
        ResultUser result = dbFasade.getResultUserById(5, "Student");
        assertNull(result);
    }

    @Test
    public void getResultUserById4() {
        ResultUser result = dbFasade.getResultUserById(5, "Teacher");
        assertNotNull(result);
    }

    @Test
    public void getUserActualCourses() {
        List<ResultCourse> result = dbFasade.getUserActualCourses(3);
        assertEquals(2, result.size());
    }

    @Test
    public void getResultThemeById() {
        ResultTheme result = dbFasade.getResultThemeById(10);
        assertNull(result);
    }

    @Test
    public void getResultThemeById2() {
        ResultTheme result = dbFasade.getResultThemeById(1);
        assertNotNull(result);
    }

    @Test
    public void getResultCourseList() {
        int size = courseList.size();
        List<ResultCourse> result = dbFasade.getResultCourseList();
        assertEquals(size, result.size());
    }

    @Test
    public void getResultThemeList() {
        int size = themeList.size();
        List<ResultTheme> result = dbFasade.getResultThemeList();
        assertEquals(size, result.size());
    }

    @Test
    public void changeCourse() {
        int result = dbFasade.changeCourse(DatabaseCourse.newBuilder().setId(5).build());
        assertEquals(COURSE_CHANGED, result);
        assertTrue(courseList.contains(DatabaseCourse.newBuilder().setId(5).build()));
    }

    @Test
    public void changeCourse2() {
        int result = dbFasade.changeCourse(DatabaseCourse.newBuilder().setId(123).build());
        assertEquals(COURSE_NOT_FOUND, result);
    }

    @Test
    public void createCourse() {
        int size = courseList.size();
        int result = dbFasade.createCourse(DatabaseCourse.newBuilder().setId(133).build());
        assertEquals(COURSE_CREATED, result);
        assertEquals(size + 1, courseList.size());
    }

    @Test
    public void deleteCourse() {
        int size = courseList.size();
        int result = dbFasade.deleteCourse(666);
        assertEquals(COURSE_DELETED, result);
        assertEquals(size - 1, courseList.size());
    }

    @Test
    public void deleteCourse2() {
        int result = dbFasade.deleteCourse(665);
        assertEquals(COURSE_NOT_FOUND, result);
    }

    @Test
    public void updateUserPass() {
        DatabaseUser temp = DatabaseUser.newBuilder().setId(44).setLogin("newLogin").setPassword("newPass")
                .encryptPassword().build();
        studCount++;
        userList.add(temp);
        boolean result = dbFasade.updateUserPass(temp.getBuilder().setPassword("123123123").encryptPassword().build());
        assertTrue(result);
    }

    @Test
    public void getAllUser() {
        List<DatabaseUser> result = dbFasade.getAllUser();
        assertEquals(userList.size(), result.size());
    }

    @Test
    public void getCourseById() {
        DatabaseCourse result = dbFasade.getCourseById(2);
        assertNotNull(result);
    }

    @Test
    public void getCourseById2() {
        DatabaseCourse result = dbFasade.getCourseById(32);
        assertNull(result);
    }

    @Test
    public void doTeacherById() {
        int result = dbFasade.doTeacherById(6, "6");
        assertEquals(USER_ALREADY_TEACHER, result);
    }

    @Test
    public void doTeacherById2() {
        int result = dbFasade.doTeacherById(643, "6");
        assertEquals(USER_NOT_FOUND, result);
    }

    @Test
    public void changeTeacherDescription() {
        int result = dbFasade.changeTeacherDescription(641, "6");
        assertEquals(USER_NOT_FOUND, result);
    }

    @Test
    public void changeTeacherDescription2() {
        int result = dbFasade.changeTeacherDescription(1, "6");
        assertEquals(USER_NOT_TEACHER, result);
    }

    @Test
    public void changeTeacherDescription3() {
        int result = dbFasade.changeTeacherDescription(6, "6");
        assertEquals(DESCRIPTION_SET, result);
    }

    @Test
    public void doStudentById() {
        int result = dbFasade.doStudentById(1241);
        assertEquals(USER_NOT_FOUND, result);
    }

    @Test
    public void doStudentById2() {
        int result = dbFasade.doStudentById(1);
        assertEquals(USER_ALREADY_STUDENT, result);
    }

    @Test
    public void blockUser() {
        int result = dbFasade.blockUser(12341);
        assertEquals(USER_NOT_FOUND, result);
    }

    @Test
    public void blockUser2() {
        int result = dbFasade.blockUser(3);
        assertEquals(USER_ALREADY_BLOCKED, result);
    }

    @Test
    public void unblockUser() {
        int result = dbFasade.unblockUser(11245);
        assertEquals(USER_NOT_FOUND, result);
    }

    @Test
    public void unblockUser2() {
        int result = dbFasade.unblockUser(2);
        assertEquals(USER_NOT_BLOCK, result);
    }

    private static CourseDAO initCourseDAO() {
        CourseDAOReplacement dao = new CourseDAOReplacement();
        List<DatabaseCourse> courseList = new LinkedList<>();
        List<int[]> courseToUser = new LinkedList<>();
        courseList.add(DatabaseCourse.newBuilder().setId(0).setStudentCount(10).setTheme(0).setTeacher(5)
                .setStartDate(new Timestamp(971156333L * 1000)).setDuration(1).build());
        courseList.add(DatabaseCourse.newBuilder().setId(1).setStudentCount(10).setTheme(1).setTeacher(5)
                .setStartDate(new Timestamp(1001156333L * 1000)).setDuration(1).build());
        courseList.add(DatabaseCourse.newBuilder().setId(2).setStudentCount(10).setTheme(2).setTeacher(6)
                .setStartDate(new Timestamp(1536557933L * 1000)).setDuration(365).build());
        courseList.add(DatabaseCourse.newBuilder().setId(3).setStudentCount(10).setTheme(-1).setTeacher(3)
                .setStartDate(new Timestamp(1599716333L * 1000)).setDuration(10).build());
        courseList.add(DatabaseCourse.newBuilder().setId(4).setStudentCount(10).setTheme(-1).setTeacher(4)
                .setStartDate(new Timestamp(1599816333L * 1000)).setDuration(10).build());
        courseList.add(DatabaseCourse.newBuilder().setId(5).setStudentCount(10).setTheme(-1).setTeacher(4)
                .setStartDate(new Timestamp(1599816333L * 1000)).setDuration(10).build());
        courseList.add(DatabaseCourse.newBuilder().setId(666).build());
        courseToUser.add(new int[] { 0, 0 });
        courseToUser.add(new int[] { 0, 3 });
        courseToUser.add(new int[] { 1, 1 });
        courseToUser.add(new int[] { 1, 4 });
        courseToUser.add(new int[] { 2, 0 });
        courseToUser.add(new int[] { 2, 1 });
        courseToUser.add(new int[] { 2, 2 });
        courseToUser.add(new int[] { 2, 3 });
        courseToUser.add(new int[] { 3, 0 });
        courseToUser.add(new int[] { 3, 1 });
        courseToUser.add(new int[] { 3, 2 });
        courseToUser.add(new int[] { 3, 3 });
        courseToUser.add(new int[] { 3, 4 });
        dao.coursesList = Collections.synchronizedList(courseList);
        dao.courseToUser = Collections.synchronizedList(courseToUser);
        TestDBFasade.courseList = dao.coursesList;
        TestDBFasade.courseToUser = dao.courseToUser;
        return dao;
    }

    private static CourseToUserDAOReplacement initCourseToUserDAO() {
        CourseToUserDAOReplacement dao = new CourseToUserDAOReplacement();
        dao.courseToUser = courseToUser;
        return dao;
    }

    private static JournalDAO initJournalDAO() {
        JournalDAOReplacement dao = new JournalDAOReplacement();
        Map<Integer, Journal> journalMap = new HashMap<>();
        journalMap.put(2, new Journal());
        journalMap.put(3, new Journal());
        journalMap.put(4, new Journal());
        TestDBFasade.journalMap = Collections.synchronizedMap(journalMap);
        dao.journalMap = TestDBFasade.journalMap;
        return dao;
    }

    private static ThemeDAO initThemeDAO() {
        ThemeDAOReplacement dao = new ThemeDAOReplacement();
        List<DatabaseTheme> themeList = new LinkedList<>();
        themeList.add(new DatabaseTheme(0, "0", "0"));
        themeList.add(new DatabaseTheme(1, "1", "1"));
        themeList.add(new DatabaseTheme(2, "2", "2"));
        dao.list = Collections.synchronizedList(themeList);
        TestDBFasade.themeList = dao.list;
        return dao;
    }

    private static UserDAO initUserDAO() {
        UserDAOReplacement dao = new UserDAOReplacement();
        List<DatabaseUser> userList = new LinkedList<>();
        Map<Integer, String> teacherDescription = new HashMap<>();
        userList.add(DatabaseUser.newBuilder().setId(0).setRole("Student").setLogin("0").setMail("0").setPassword("0")
                .encryptPassword().build());
        userList.add(DatabaseUser.newBuilder().setId(1).setRole("Student").setLogin("1").setMail("1").setPassword("1")
                .encryptPassword().build());
        userList.add(DatabaseUser.newBuilder().setId(2).setRole("Student").setLogin("2").setMail("2").setPassword("2")
                .encryptPassword().build());
        userList.add(DatabaseUser.newBuilder().setId(3).setRole("Student").setLogin("3").setMail("3").setPassword("4")
                .setBlock(true).encryptPassword().build());
        userList.add(DatabaseUser.newBuilder().setId(4).setRole("Student").setLogin("4").setMail("4").setPassword("4")
                .encryptPassword().build());
        userList.add(DatabaseUser.newBuilder().setId(8).setRole("Student").setLogin("8").setMail("8").setPassword("8")
                .encryptPassword().build());
        userList.add(DatabaseUser.newBuilder().setLogin("login").setMail("mail").build());
        userList.add(DatabaseUser.newBuilder().setId(5).setName("5").setSurname("5").setRole("Teacher").setLogin("5")
                .setMail("5").setPassword("5").encryptPassword().build());
        userList.add(DatabaseUser.newBuilder().setId(6).setName("6").setSurname("6").setRole("Teacher").setLogin("6")
                .setMail("6").setPassword("6").encryptPassword().build());
        userList.add(DatabaseUser.newBuilder().setId(7).setName("7").setSurname("7").setRole("Teacher").setLogin("7")
                .setMail("7").setPassword("7").encryptPassword().build());
        teacherCount = 3;
        studCount = 7;
        teacherDescription.put(5, "5");
        teacherDescription.put(6, "6");
        dao.list = Collections.synchronizedList(userList);
        dao.teacherDescription = Collections.synchronizedMap(teacherDescription);
        TestDBFasade.userList = dao.list;
        TestDBFasade.teacherDescription = dao.teacherDescription;
        return dao;
    }
}
