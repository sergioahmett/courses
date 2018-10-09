package com.epam.daolayer.dbfasad;

import static com.epam.utils.CodeHandler.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.epam.daolayer.dao.*;
import com.epam.daolayer.daoentity.DatabaseCourse;
import com.epam.daolayer.daoentity.DatabaseTheam;
import com.epam.daolayer.daoentity.DatabaseUser;
import com.epam.daolayer.abstractdao.AbstractDAO;
import com.epam.interfaces.DatabaseFasadInterface;
import com.epam.resultentity.ResultCourse;
import com.epam.resultentity.ResultTeacher;
import com.epam.resultentity.ResultTheam;
import com.epam.resultentity.ResultUser;
import com.epam.utils.ConnectionHandler;
import com.epam.utils.CourseHelper;
import com.epam.utils.Journal;

/**
 * The main class to work with the database. Contains all DAO objects. 
 * Combines them to work properly and complete the tasks.
 * 
 * @author Sergey Ahmetshin
 * 
 */
public class DBFasad implements DatabaseFasadInterface {
    private CourseDAO courseDAO;
    private UserDAO userDAO;
    private TheamDAO theamDAO;
    private CourseToUserDAO courseToUserDAO;
    private JournalDAO journalDAO;
    private static DatabaseFasadInterface instance;
    private static final Logger log = Logger.getLogger(DBFasad.class);

    private DBFasad() {
        log.debug("DBFasad creater by constructor");
    }

    public Map<String, Integer> getTheamForHeader() {
        try (Connection con = getConnection();) {
            setConnectionToDAO(con, theamDAO);
            List<DatabaseTheam> theamList = theamDAO.findAll();
            Map<String, Integer> result = new HashMap<>();
            theamList.forEach(theam -> {
                result.put(theam.getTitle(), theam.getId());
            });
            return result;
        } catch (SQLException e) {
            log.warn("Error in method", e);
            return null;
        }
    }

    public ResultTeacher getResultTeacherById(int id) {
        try (Connection con = getConnection();) {
            setConnectionToDAO(con, userDAO, courseDAO, theamDAO, courseToUserDAO);
            return createResultTeacher(id);
        } catch (SQLException e) {
            log.warn("Error in method. Parametr id=" + id, e);
            return null;
        }
    }

    public List<ResultTeacher> getResultTeacherList() {
        try (Connection con = getConnection();) {
            setConnectionToDAO(con, userDAO, courseDAO, theamDAO, courseToUserDAO);
            List<ResultTeacher> result = new LinkedList<>();
            List<DatabaseUser> teachers = userDAO.findAllTeachers();
            teachers.forEach(teacher -> result.add(createResultTeacher(teacher.getId())));
            return result;
        } catch (SQLException e) {
            log.warn("Error in method", e);
            return null;
        }
    }

    public int saveUser(DatabaseUser user) {
        try (Connection con = getConnection();) {
            setConnectionToDAO(con, userDAO);
            if (userDAO.findUserByLogin(user.getLogin()) != null) {
                return LOGIN_ALREADY_EXIST;
            }
            if (userDAO.findUserByMail(user.getMail()) != null) {
                return MAIL_ALREADY_EXIST;
            }
            return userDAO.create(user) ? OK : ERROR;
        } catch (SQLException e) {
            log.warn("Error in method. Parametr user = " + user, e);
            return ERROR;
        }
    }

    public DatabaseUser getUserByLoginOrMail(String loginOrMail) {
        try (Connection con = getConnection();) {
            setConnectionToDAO(con, userDAO);
            DatabaseUser user = userDAO.findUserByLogin(loginOrMail);
            if (user != null) {
                return user;
            }
            user = userDAO.findUserByMail(loginOrMail);
            if (user != null) {
                return user;
            }
            return null;
        } catch (SQLException e) {
            log.warn("Error in method. Parametr loginOrMail = " + loginOrMail, e);
            return null;
        }
    }

    public DatabaseUser getUserByID(int id) {
        try (Connection con = getConnection();) {
            setConnectionToDAO(con, userDAO);
            DatabaseUser user = userDAO.findUserById(id);
            if (user != null) {
                return user;
            }
            return null;
        } catch (SQLException e) {
            log.warn("Error in method. Parametr id = " + id, e);
            return null;
        }
    }

    public ResultCourse getResultCourseById(int id) {
        try (Connection con = getConnection();) {
            setConnectionToDAO(con, userDAO, courseDAO, theamDAO, courseToUserDAO);
            DatabaseCourse course = courseDAO.findEntityById(id);
            ResultCourse result = null;
            if (course != null) {
                DatabaseUser teacher = userDAO.findUserById(course.getTeacher());
                DatabaseTheam theam = theamDAO.findTheamById(course.getTheme());
                int regStudents = courseToUserDAO.findAllCourseUsers(course.getId()).size();
                result = new ResultCourse(course, theam, teacher, regStudents);
            }
            return result;
        } catch (SQLException e) {
            log.warn("Error in method. Parametr id = " + id, e);
            return null;
        }
    }

    public int regOrUnregUserToCourses(int userId, int courseId, String param) {
        try (Connection con = getConnection();) {
            setConnectionToDAO(con, courseToUserDAO, courseDAO, userDAO);
            DatabaseCourse course = courseDAO.findEntityById(courseId);
            DatabaseUser user = userDAO.findUserById(userId);
            if (course != null) {
                if (user != null) {
                    boolean register = courseToUserDAO.findByUserAndCourse(userId, courseId);
                    switch (param) {
                    case "register":
                        return regToCourse(userId, course, param, register);
                    case "unregister":
                        return unregToCourse(userId, course, param, register);
                    default:
                        return PARAMETER_NOT_VALID;
                    }
                } else {
                    return USER_NOT_FOUND;
                }
            } else {
                return COURSE_NOT_FOUND;
            }
        } catch (SQLException e) {
            log.warn("Error in method. Parametr userId = " + userId + ", courseId=" + courseId + ", param" + param, e);
            return ERROR;
        }
    }

    private int regToCourse(int userId, DatabaseCourse course, String param, boolean register) {
        if (!register) {
            int regCount = courseToUserDAO.findAllCourseUsers(course.getId()).size();
            if (CourseHelper.checkActual(course)) {
                if (regCount < course.getMaxStudentCount()) {
                    if (courseToUserDAO.create(userId, course.getId())) {
                        return REGISTER_TO_COURSE_SUCCSESS;
                    } else {
                        return ERROR;
                    }
                } else {
                    return COURSE_FULL;
                }
            } else {
                return COURSE_NOT_ACTUAL;
            }
        } else {
            return USER_ALREADY_REGISTER_TO_COURSE;
        }
    }

    private int unregToCourse(int userId, DatabaseCourse course, String param, boolean register) {
        if (register) {
            if (courseToUserDAO.delete(userId, course.getId())) {
                return UNREGISTER_TO_COURSE_SUCCSESS;
            } else {
                return ERROR;
            }
        } else {
            return USER_ALREADY_NOTREGISTER_TO_COURSE;
        }
    }

    public ResultUser getResultUserById(int id, String role) {
        try (Connection con = getConnection();) {
            setConnectionToDAO(con, userDAO, courseDAO, theamDAO, courseToUserDAO);
            DatabaseUser user = userDAO.findUserById(id);
            if (user != null) {
                List<DatabaseCourse> list;
                switch (role) {
                case "Teacher":
                    list = courseDAO.findAllByTeacherId(id);
                    break;
                case "Student":
                    list = courseDAO.findAllByUserId(id);
                    System.out.println(id + " " + role + " " + list.size());
                    break;
                default:
                    list = new LinkedList<>();
                }
                List<ResultCourse> resList = new LinkedList<>();
                list.forEach(course -> {
                    resList.add(new ResultCourse(course, theamDAO.findTheamById(course.getTheme()),
                            userDAO.findUserById(course.getTeacher()),
                            courseToUserDAO.findAllCourseUsers(course.getId()).size()));
                });
                ResultUser resultUser = new ResultUser();
                resultUser.setId(id);
                resultUser.setLogin(user.getLogin());
                resultUser.setFullName(user.getName() + " " + user.getSurname());
                resultUser.setMail(user.getMail());
                resultUser.setCourseList(resList);
                return resultUser;
            } else {
                return null;
            }
        } catch (SQLException e) {
            log.warn("Error in method. Parametr id = " + id + ", role=" + role, e);
            return null;
        }
    }

    public List<ResultCourse> getUserActualCourses(int id) {
        try (Connection con = getConnection();) {
            setConnectionToDAO(con, userDAO, courseDAO, theamDAO, courseToUserDAO);
            List<Integer> courses = courseToUserDAO.findAllUserCourses(id);
            List<ResultCourse> result = new LinkedList<>();
            courses.forEach(courseId -> {
                DatabaseCourse course = courseDAO.findEntityById(courseId);
                if (CourseHelper.checkActual(course)) {
                    DatabaseUser teacher = userDAO.findUserById(course.getTeacher());
                    DatabaseTheam theam = theamDAO.findTheamById(course.getTheme());
                    int regStudents = courseToUserDAO.findAllCourseUsers(course.getId()).size();
                    result.add(new ResultCourse(course, theam, teacher, regStudents));
                }
            });
            return result;
        } catch (SQLException e) {
            log.warn("Error in method. Parametr id = " + id, e);
            return null;
        }
    }

    public ResultTheam getResultTheamById(int id) {
        try (Connection con = getConnection();) {
            setConnectionToDAO(con, userDAO, courseDAO, theamDAO, courseToUserDAO);
            DatabaseTheam theam = theamDAO.findTheamById(id);
            if (theam != null) {
                List<ResultCourse> resultCoureList = new LinkedList<>();
                courseDAO.findAllByTheamId(theam.getId()).forEach(course -> {
                    resultCoureList.add(new ResultCourse(course, theam, userDAO.findUserById(course.getTeacher()),
                            courseToUserDAO.findAllCourseUsers(course.getId()).size()));
                });
                return new ResultTheam(theam, resultCoureList);
            } else {
                return null;
            }
        } catch (SQLException e) {
            log.warn("Error in method. Parametr id = " + id, e);
            return null;
        }
    }

    public List<ResultCourse> getResultCourseList() {
        try (Connection con = getConnection();) {
            setConnectionToDAO(con, userDAO, courseDAO, theamDAO, courseToUserDAO);
            List<DatabaseCourse> list = courseDAO.findAll();
            List<ResultCourse> resultList = new LinkedList<>();
            list.forEach(course -> {
                resultList.add(new ResultCourse(course, theamDAO.findTheamById(course.getTheme()),
                        userDAO.findUserById(course.getTeacher()),
                        courseToUserDAO.findAllCourseUsers(course.getId()).size()));
            });
            return resultList;
        } catch (SQLException e) {
            log.warn("Error in method", e);
            return null;
        }
    }

    public List<ResultTheam> getResultTheamList() {
        try (Connection con = getConnection();) {
            setConnectionToDAO(con, userDAO, courseDAO, theamDAO, courseToUserDAO);
            List<DatabaseTheam> list = theamDAO.findAll();
            List<ResultTheam> resultList = new LinkedList<>();
            list.forEach(theam -> {
                List<ResultCourse> resultCoureList = new LinkedList<>();
                courseDAO.findAllByTheamId(theam.getId()).forEach(course -> {
                    resultCoureList.add(new ResultCourse(course, theam, userDAO.findUserById(course.getId()),
                            courseToUserDAO.findAllCourseUsers(course.getId()).size()));
                });
                resultList.add(new ResultTheam(theam, resultCoureList));
            });
            return resultList;
        } catch (SQLException e) {
            log.warn("Error in method", e);
            return null;
        }
    }

    public int changeCourse(DatabaseCourse course) {
        try (Connection con = getConnection();) {
            setConnectionToDAO(con, courseDAO);
            DatabaseCourse currentCourse = courseDAO.findEntityById(course.getId());
            if (currentCourse != null) {
                return courseDAO.update(course) ? COURSE_CHANGED : ERROR;
            } else {
                return COURSE_NOT_FOUND;
            }
        } catch (SQLException e) {
            log.warn("Error in method. Parametr course = " + course, e);
            return ERROR;
        }
    }

    public int createCourse(DatabaseCourse course) {
        try (Connection con = getConnection();) {
            setConnectionToDAO(con, courseDAO);
            return courseDAO.create(course) ? COURSE_CREATED : ERROR;
        } catch (SQLException e) {
            log.warn("Error in method. Parametr course = " + course, e);
            return ERROR;
        }
    }

    public int deleteCourse(int courseId) {
        try (Connection con = getConnection(); ConnectionHandler ch = new ConnectionHandler(con, false);) {
            setConnectionToDAO(con, courseDAO, courseToUserDAO, journalDAO);
            if (courseDAO.findEntityById(courseId) != null) {
                if (courseDAO.delete(courseId) && courseToUserDAO.deleteByCourseId(courseId)
                        && journalDAO.delete(courseId)) {
                    ch.commit();
                    return COURSE_DELETED;
                }
                return ERROR;
            } else {
                return COURSE_NOT_FOUND;
            }
        } catch (SQLException e) {
            log.warn("Error in method. Parametr courseId = " + courseId, e);
            return ERROR;
        }
    }

    public boolean updateUserPass(DatabaseUser user) {
        try (Connection con = getConnection();) {
            setConnectionToDAO(con, userDAO);
            return userDAO.update(user);
        } catch (SQLException e) {
            log.warn("Error in method. Parametr user = " + user, e);
            return false;
        }
    }

    public List<DatabaseUser> getAllUser() {
        try (Connection con = getConnection();) {
            setConnectionToDAO(con, userDAO);
            return userDAO.findAll();
        } catch (SQLException e) {
            log.warn("Error in method", e);
            return null;
        }
    }

    public DatabaseCourse getCourseById(int id) {
        try (Connection con = getConnection();) {
            setConnectionToDAO(con, courseDAO);
            return courseDAO.findEntityById(id);
        } catch (SQLException e) {
            log.warn("Error in method. Parametr id = " + id, e);
            return null;
        }
    }

    public List<DatabaseTheam> getAllTheams() {
        try (Connection con = getConnection();) {
            setConnectionToDAO(con, theamDAO);
            return theamDAO.findAll();
        } catch (SQLException e) {
            log.warn("Error in method", e);
            return null;
        }
    }

    public List<DatabaseUser> getAllStudents() {
        try (Connection con = getConnection();) {
            setConnectionToDAO(con, userDAO);
            return userDAO.findAllStudents();
        } catch (SQLException e) {
            log.warn("Error in method", e);
            return null;
        }
    }

    public List<DatabaseUser> getAllTeachers() {
        try (Connection con = getConnection();) {
            setConnectionToDAO(con, userDAO);
            return userDAO.findAllTeachers();
        } catch (SQLException e) {
            log.warn("Error in method", e);
            return null;
        }
    }

    public int doTeacherById(int id, String description) {
        try (Connection con = getConnection(); ConnectionHandler ch = new ConnectionHandler(con, false)) {
            setConnectionToDAO(con, userDAO, courseToUserDAO);
            DatabaseUser user = userDAO.findUserById(id);
            if (user != null) {
                if (user.getRole().equals("Student")) {
                    user.getBuilder().setRole("Teacher");
                    courseToUserDAO.deleteByUserId(id);
                    if (userDAO.update(user) && userDAO.createTeacherDescription(id, description)) {
                        ch.commit();
                        return USER_SET_TO_TEACHER;
                    } else {
                        return ERROR;
                    }
                } else {
                    return USER_ALREADY_TEACHER;
                }
            } else {
                return USER_NOT_FOUND;
            }
        } catch (SQLException e) {
            log.warn("Error in method. Parametr id = " + id + ", description=" + description, e);
            return ERROR;
        }
    }

    public int changeTeacherDescription(int id, String description) {
        try (Connection con = getConnection(); ConnectionHandler ch = new ConnectionHandler(con, false)) {
            setConnectionToDAO(con, userDAO, courseToUserDAO);
            DatabaseUser user = userDAO.findUserById(id);
            if (user != null) {
                if (user.getRole().equals("Teacher")) {
                    if (userDAO.setTeacherDescription(id, description)) {
                        ch.commit();
                        return DESCRIPTION_SET;
                    } else {
                        return ERROR;
                    }
                } else {
                    return USER_NOT_TEACHER;
                }
            } else {
                return USER_NOT_FOUND;
            }
        } catch (SQLException e) {
            log.warn("Error in method. Parametr id = " + id + ", description=" + description, e);
            return ERROR;
        }
    }

    public int doStudentById(int id) {
        try (Connection con = getConnection(); ConnectionHandler ch = new ConnectionHandler(con, false)) {
            setConnectionToDAO(con, userDAO, courseDAO);
            DatabaseUser user = userDAO.findUserById(id);
            if (user != null) {
                if (user.getRole().equals("Teacher")) {
                    user.getBuilder().setRole("Student");
                    List<DatabaseCourse> list = courseDAO.findAllByTeacherId(id);
                    list.forEach(course -> {
                        course.getBuilder().setTeacher(-1);
                        courseDAO.update(course);
                    });
                    if (userDAO.update(user) && userDAO.deleteTeacherDecription(id)) {
                        ch.commit();
                        return USER_SET_TO_STUDENT;
                    } else {
                        return ERROR;
                    }
                } else {
                    return USER_ALREADY_STUDENT;
                }
            } else {
                return USER_NOT_FOUND;
            }
        } catch (SQLException e) {
            log.warn("Error in method. Parametr id = " + id, e);
            return ERROR;
        }
    }

    public int blockUser(int id) {
        try (Connection con = getConnection();) {
            setConnectionToDAO(con, userDAO);
            DatabaseUser user = userDAO.findUserById(id);
            if (user != null) {
                if (!user.getRole().equals("Admin")) {
                    if (!user.isBlock()) {
                        user.getBuilder().setBlock(true);
                        return userDAO.update(user) ? USER_SET_TO_BLOCK : ERROR;
                    } else {
                        return USER_ALREADY_BLOCKED;
                    }
                } else {
                    return USER_ADMIN;
                }
            } else {
                return USER_NOT_FOUND;
            }
        } catch (SQLException e) {
            log.warn("Error in method. Parametr id = " + id, e);
            return ERROR;
        }
    }

    public int unblockUser(int id) {
        try (Connection con = getConnection();) {
            setConnectionToDAO(con, userDAO);
            DatabaseUser user = userDAO.findUserById(id);
            if (user != null) {
                if (user.isBlock()) {
                    user.getBuilder().setBlock(false);
                    return userDAO.update(user) ? USER_UNBLOCK : ERROR;
                } else {
                    return USER_NOT_BLOCK;
                }
            } else {
                return USER_NOT_FOUND;
            }
        } catch (SQLException e) {
            log.warn("Error in method. Parametr id = " + id, e);
            return ERROR;
        }
    }

    public static DatabaseFasadInterface getInstance() {
        if (instance == null) {
            instance = new DBFasad();
        }
        return instance;
    }

    // private static Connection getConnection() throws SQLException {
    // ResourceBundle resource = ResourceBundle.getBundle("database");
    // Properties props = new Properties();
    // String url = resource.getString("db.url");
    // props.setProperty("user", resource.getString("db.user"));
    // props.setProperty("password", resource.getString("db.password"));
    // props.setProperty("useSSL", resource.getString("db.useSSL"));
    // props.setProperty("useLegacyDatetimeCode",
    // resource.getString("db.useLegacyDatetimeCode"));
    // props.setProperty("serverTimezone", resource.getString("db.serverTimezone"));
    // return DriverManager.getConnection(url, props);
    // }

    private ResultTeacher createResultTeacher(int id) {
        DatabaseUser teacher = userDAO.findUserById(id);
        if (teacher != null) {
            String descr = userDAO.getTeacherDescription(id);
            List<DatabaseCourse> courses = courseDAO.findAllByTeacherId(id);
            List<ResultCourse> result = new LinkedList<>();
            courses.forEach(course -> {
                DatabaseTheam theam = theamDAO.findTheamById(course.getTheme());
                int regStudents = courseToUserDAO.findAllCourseUsers(course.getId()).size();
                result.add(new ResultCourse(course, theam, teacher, regStudents));
            });
            return new ResultTeacher(id, teacher.getName() + " " + teacher.getSurname(), descr, result);
        } else {
            return null;
        }
    }

    public static Connection getConnection(){
        Context contx;
        try {
            contx = (Context) (new InitialContext().lookup("java:comp/env"));
            DataSource ds = (DataSource) contx.lookup("jdbc/cources");
            return ds.getConnection();
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
            log.error("Error creating connection!");
            return null;
        }
    }

    @SuppressWarnings("rawtypes")
    private void setConnectionToDAO(Connection con, AbstractDAO... dao) {
        for (AbstractDAO abstractDAO : dao) {
            abstractDAO.setConnection(con);
        }

    }

    public int changeTheam(DatabaseTheam databaseTheam) {
        try (Connection con = getConnection();) {
            setConnectionToDAO(con, theamDAO);
            if (theamDAO.findTheamById(databaseTheam.getId()) != null) {
                return theamDAO.update(databaseTheam) ? THEAM_CHANGED : ERROR;
            } else {
                return THEAM_NOT_FOUND;
            }
        } catch (SQLException e) {
            log.warn("Error in method. Parametr databaseTheam = " + databaseTheam, e);
            return ERROR;
        }
    }

    public int createTheam(DatabaseTheam databaseTheam) {
        try (Connection con = getConnection();) {
            setConnectionToDAO(con, theamDAO);
            return theamDAO.create(databaseTheam) ? THEAM_CREATED : ERROR;
        } catch (SQLException e) {
            log.warn("Error in method. Parametr databaseTheam = " + databaseTheam, e);
            return ERROR;
        }
    }

    public int deleteTheam(int theamId) {
        try (Connection con = getConnection(); ConnectionHandler ch = new ConnectionHandler(con, false);) {
            setConnectionToDAO(con, courseDAO, theamDAO);
            if (theamDAO.findTheamById(theamId) != null) {
                List<DatabaseCourse> courses = courseDAO.findAllByTheamId(theamId);
                for (DatabaseCourse course : courses) {
                    course.getBuilder().setTheme(-1);
                    courseDAO.update(course);
                }
                if (theamDAO.delete(theamId)) {
                    ch.commit();
                    return THEAM_DELETED;
                }
                return ERROR;
            } else {
                return COURSE_NOT_FOUND;
            }
        } catch (SQLException e) {
            log.warn("Error in method. Parametr theamId = " + theamId, e);
            return ERROR;
        }
    }

    public boolean checkAccessToJournal(int userId, int courseId, String role) {
        try (Connection con = getConnection();) {
            setConnectionToDAO(con, courseDAO, courseToUserDAO);
            switch (role) {
            case "Teacher":
                DatabaseCourse course = courseDAO.findEntityById(courseId);
                return course != null && course.getTeacher() == userId;
            case "Student":
                List<Integer> list = courseToUserDAO.findAllUserCourses(userId);
                return list.contains(courseId);
            default:
                return false;
            }
        } catch (SQLException e) {
            log.warn("Error in method. Parametr userId = " + userId + ", courseId = " + courseId + ", role = " + role,
                    e);
            return false;
        }
    }

    public Journal getJournal(int courseId, String role) {
        try (Connection con = getConnection();) {
            setConnectionToDAO(con, userDAO, courseDAO, journalDAO, courseToUserDAO);
            Journal journal = journalDAO.getById(courseId);
            if (journal == null && role.equals("Teacher")) {
                DatabaseCourse course = courseDAO.findEntityById(courseId);
                if (CourseHelper.checkCurrent(course) || CourseHelper.checkArchive(course)) {
                    journal = new Journal();
                    List<DatabaseUser> userList = new LinkedList<>();
                    courseToUserDAO.findAllCourseUsers(courseId).forEach(id -> {
                        userList.add(userDAO.findUserById(id));
                    });
                    journal.addStudents(userList);
                    return journalDAO.create(courseId, journal) ? journal : null;
                } else {
                    return null;
                }
            } else {
                return journal;
            }
        } catch (SQLException e) {
            log.warn("Error in method. Parametr courseId = " + courseId + ", role = " + role, e);
            return null;
        }
    }

    public int addDayToJournal(int courseId, String day) {
        try (Connection con = getConnection();) {
            setConnectionToDAO(con, journalDAO);
            Journal journal = journalDAO.getById(courseId);
            if (journal != null) {
                journal.addDayToJournal(day);
                return journalDAO.update(courseId, journal) ? DAY_ADDED : ERROR;
            } else {
                return JOURNAL_NOT_FOUND;
            }
        } catch (SQLException e) {
            log.warn("Error in method. Parametr courseId = " + courseId + ", day = " + day, e);
            return ERROR;
        }
    }

    public int addRaitingToJournal(int courseId, int userId, String day, String raiting) {
        try (Connection con = getConnection();) {
            setConnectionToDAO(con, journalDAO);
            Journal journal = journalDAO.getById(courseId);
            if (journal != null) {
                journal.addRaitingToUserByDay(userId, day, raiting);
                return journalDAO.update(courseId, journal) ? RAITING_ADDED : ERROR;
            } else {
                return JOURNAL_NOT_FOUND;
            }
        } catch (SQLException e) {
            log.warn("Error in method. Parametr courseId = " + courseId + ", day = " + day + ", userId = " + userId
                    + ", raiting = " + raiting, e);
            return ERROR;
        }
    }

    public int addFinalRaitingToUser(int courseId, int userId, int raiting) {
        try (Connection con = getConnection();) {
            setConnectionToDAO(con, journalDAO, courseDAO);
            Journal journal = journalDAO.getById(courseId);
            if (CourseHelper.checkArchive(courseDAO.findEntityById(courseId))) {
                if (journal != null) {
                    journal.addFinalRaitingToUser(userId, raiting);
                    return journalDAO.update(courseId, journal) ? RAITING_ADDED : ERROR;
                } else {
                    return JOURNAL_NOT_FOUND;
                }
            } else {
                return COURS_NOT_END_YET;
            }
        } catch (SQLException e) {
            log.warn("Error in method. Parametr courseId = " + courseId + ", userId = " + userId + ", raiting = "
                    + raiting, e);
            return ERROR;
        }
    }

    public Injector getInjector() {
        log.trace("Injector geted");
        return new Injector();
    }

    public class Injector {

        private Injector() {
        }

        public void setCourseDAO(CourseDAO courseDAO) {
            DBFasad.this.courseDAO = courseDAO;
            log.trace(courseDAO.getClass().getSimpleName() + " set to courseDAO");

        }

        public void setUserDAO(UserDAO userDAO) {
            DBFasad.this.userDAO = userDAO;
            log.trace(userDAO.getClass().getSimpleName() + " set to userDAO");
        }

        public void setTheamDAO(TheamDAO theamDAO) {
            DBFasad.this.theamDAO = theamDAO;
            log.trace(theamDAO.getClass().getSimpleName() + " set to theamDAO");
        }

        public void setCourseToUserDAO(CourseToUserDAO courseToUserDAO) {
            DBFasad.this.courseToUserDAO = courseToUserDAO;
            log.trace(courseToUserDAO.getClass().getSimpleName() + " set to courseToUserDAO");
        }

        public void setJournalDAO(JournalDAO journalDAO) {
            DBFasad.this.journalDAO = journalDAO;
            log.trace(journalDAO.getClass().getSimpleName() + " set to journalDAO");
        }
    }

}
