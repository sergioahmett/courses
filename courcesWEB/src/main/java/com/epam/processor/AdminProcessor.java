package com.epam.processor;

import static com.epam.utils.CodeHandler.ERROR;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.function.BiConsumer;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.epam.annotation.HTTPMethod;
import com.epam.annotation.MethodAnnotationController;
import com.epam.annotation.Processor;
import com.epam.annotation.RequestMapping;
import com.epam.daolayer.daoentity.DatabaseCourse;
import com.epam.daolayer.daoentity.DatabaseTheam;
import com.epam.daolayer.dbfasad.DBFasad;
import com.epam.enums.HttpMethod;
import com.epam.interfaces.DatabaseFasadInterface;
import com.epam.interfaces.ProcessorIntarface;
import com.epam.utils.CodeHandler;

/**
 * The processor that is responsible for the path /admin/*
 * 
 * @author Sergey Ahmetshin
 *
 */
@Processor(path = "/admin")
public class AdminProcessor implements ProcessorIntarface {
    private DatabaseFasadInterface dbFacade = DBFasad.getInstance();
    private static AdminProcessor instance;
    private static final Logger log = Logger.getLogger(AdminProcessor.class);
    private MethodAnnotationController annController;

    private AdminProcessor() {
        annController = new MethodAnnotationController();
        log.debug("AdminProcessor created");
    }

    public static AdminProcessor getInstance() {
        if (instance == null) {
            instance = new AdminProcessor();
        }
        return instance;
    }

    @Override
    public void startProcess(HttpServletRequest request, HttpServletResponse response) {
        log.debug("Main processor method start");
        annController.redirectToMethod(this, request, response);
        log.debug("Main processor method end");
    }

    @HTTPMethod(method = HttpMethod.GET)
    @RequestMapping(path = "/users")
    public void getAllStudents(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (checkAdminSession(request)) {
                request.setAttribute("userList", dbFacade.getAllStudents());
                request.getRequestDispatcher("/WEB-INF/jsp/admin/users.jsp").forward(request, response);
            } else {
                response.sendRedirect("/");
            }
        } catch (Exception e) {
            log.error("Error in method. Parametrs: request -> " + getFullURL(request), e);
            ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
        }
    }

    @HTTPMethod(method = HttpMethod.GET)
    @RequestMapping
    public void getStartAdminPage(HttpServletRequest request, HttpServletResponse response) {
        getAllStudents(request, response);
    }

    @HTTPMethod(method = HttpMethod.GET)
    @RequestMapping(path = "/teachers")
    public void getAllTeachers(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (checkAdminSession(request)) {
                request.setAttribute("userList", dbFacade.getAllTeachers());
                request.getRequestDispatcher("/WEB-INF/jsp/admin/adminTeachers.jsp").forward(request, response);
            } else {
                response.sendRedirect("/");
            }
        } catch (Exception e) {
            log.error("Error in method. Parametrs: request -> " + getFullURL(request), e);
            ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
        }
    }

    @HTTPMethod(method = HttpMethod.GET)
    @RequestMapping(path = "/courses")
    public void getAllCourses(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (checkAdminSession(request)) {
                request.setAttribute("resultList", dbFacade.getResultCourseList());
                request.getRequestDispatcher("/WEB-INF/jsp/admin/adminCourses.jsp").forward(request, response);
            } else {
                response.sendRedirect("/");
            }
        } catch (Exception e) {
            log.error("Error in method. Parametrs: request -> " + getFullURL(request), e);
            ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
        }
    }

    @HTTPMethod(method = HttpMethod.GET)
    @RequestMapping(path = "/theams")
    public void getAllTheams(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (checkAdminSession(request)) {
                request.setAttribute("theamsList", dbFacade.getAllTheams());
                request.getRequestDispatcher("/WEB-INF/jsp/admin/adminTheams.jsp").forward(request, response);
            } else {
                response.sendRedirect("/");
            }
        } catch (Exception e) {
            log.error("Error in method. Parametrs: request -> " + getFullURL(request), e);
            ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
        }
    }

    @HTTPMethod(method = HttpMethod.GET)
    @RequestMapping(path = "/courses/add", requiredParam = { "command", "courseId" }, requiredParamVariable = "change")
    public void getChangeCoursePage(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (checkAdminSession(request)) {
                request.setAttribute("course",
                        dbFacade.getCourseById(Integer.parseInt(request.getParameter("courseId"))));
                request.setAttribute("teachersList", dbFacade.getAllTeachers());
                request.setAttribute("theamsList", dbFacade.getAllTheams());
                request.getRequestDispatcher("/WEB-INF/jsp/admin/adminAddCourse.jsp").forward(request, response);
            } else {
                response.sendRedirect("/");
            }
        } catch (Exception e) {
            log.error("Error in method. Parametrs: request -> " + getFullURL(request), e);
            ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
        }
    }

    @HTTPMethod(method = HttpMethod.GET)
    @RequestMapping(path = "/courses/add", requiredParam = "command", requiredParamVariable = "create")
    public void getAddCoursePage(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (checkAdminSession(request)) {
                request.setAttribute("teachersList", dbFacade.getAllTeachers());
                request.setAttribute("theamsList", dbFacade.getAllTheams());
                request.getRequestDispatcher("/WEB-INF/jsp/admin/adminAddCourse.jsp").forward(request, response);
            } else {
                response.sendRedirect("/");
            }
        } catch (Exception e) {
            log.error("Error in method. Parametrs: request -> " + getFullURL(request), e);
            ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
        }
    }

    @HTTPMethod(method = HttpMethod.POST)
    @RequestMapping(path = "/courses/add", requiredParam = { "command", "courseId", "duration", "maxStudent",
            "courseName", "teacher", "description", "theam", "startDate" }, requiredParamVariable = "change")
    public void changeCourse(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (checkAdminSession(request)) {
                DatabaseCourse course = getCourse(request.getParameter("courseId"), request.getParameter("courseName"),
                        request.getParameter("theam"), request.getParameter("description"),
                        request.getParameter("duration"), request.getParameter("maxStudent"),
                        request.getParameter("teacher"), request.getParameter("startDate"));

                if (course != null) {
                    request.setAttribute("msg", dbFacade.changeCourse(course));
                } else {
                    request.setAttribute("msg", CodeHandler.PARAMETER_NOT_VALID);
                }
                getAllCourses(request, response);
            } else {
                response.sendRedirect("/");
            }
        } catch (Exception e) {
            log.error("Error in method. Parametrs: request -> " + getFullURL(request), e);
            ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
        }
    }

    @HTTPMethod(method = HttpMethod.POST)
    @RequestMapping(path = "/theams", requiredParam = { "command", "theamId", "title",
            "description" }, requiredParamVariable = "change")
    public void changeTheam(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (checkAdminSession(request)) {
                System.out.println(request.getParameter("theamId"));
                request.setAttribute("msg",
                        dbFacade.changeTheam(new DatabaseTheam(Integer.parseInt(request.getParameter("theamId")),
                                request.getParameter("title"), request.getParameter("description"))));
                getAllTheams(request, response);
            } else {
                response.sendRedirect("/");
            }
        } catch (Exception e) {
            log.error("Error in method. Parametrs: request -> " + getFullURL(request), e);
            ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
        }
    }

    @HTTPMethod(method = HttpMethod.POST)
    @RequestMapping(path = "/theams", requiredParam = { "command", "theamId" }, requiredParamVariable = "delete")
    public void deleteTheam(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (checkAdminSession(request)) {
                request.setAttribute("msg", dbFacade.deleteTheam(Integer.parseInt(request.getParameter("theamId"))));
                getAllTheams(request, response);
            } else {
                response.sendRedirect("/");
            }
        } catch (Exception e) {
            log.error("Error in method. Parametrs: request -> " + getFullURL(request), e);
            ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
        }
    }

    @HTTPMethod(method = HttpMethod.POST)
    @RequestMapping(path = "/theams", requiredParam = { "command", "title",
            "description" }, requiredParamVariable = "create")
    public void createTheam(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (checkAdminSession(request)) {
                request.setAttribute("msg", dbFacade.createTheam(
                        new DatabaseTheam(0, request.getParameter("title"), request.getParameter("description"))));
                getAllTheams(request, response);
            } else {
                response.sendRedirect("/");
            }
        } catch (Exception e) {
            log.error("Error in method. Parametrs: request -> " + getFullURL(request), e);
            ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
        }
    }

    @HTTPMethod(method = HttpMethod.POST)
    @RequestMapping(path = "/courses/add", requiredParam = { "command", "courseId" }, requiredParamVariable = "delete")
    public void deleteCourse(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (checkAdminSession(request)) {
                request.setAttribute("msg", dbFacade.deleteCourse(Integer.parseInt(request.getParameter("courseId"))));
                getAllCourses(request, response);
            } else {
                response.sendRedirect("/");
            }
        } catch (Exception e) {
            log.error("Error in method. Parametrs: request -> " + getFullURL(request), e);
            ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
        }
    }

    @HTTPMethod(method = HttpMethod.POST)
    @RequestMapping(path = "/courses/add", requiredParam = { "command", "duration", "maxStudent", "courseName",
            "teacher", "description", "theam", "startDate" }, requiredParamVariable = "create")
    public void createCourse(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (checkAdminSession(request)) {
                DatabaseCourse course = getCourse("0", request.getParameter("courseName"),
                        request.getParameter("theam"), request.getParameter("description"),
                        request.getParameter("duration"), request.getParameter("maxStudent"),
                        request.getParameter("teacher"), request.getParameter("startDate"));

                if (course != null) {
                    request.setAttribute("msg", dbFacade.createCourse(course));
                } else {
                    request.setAttribute("msg", CodeHandler.PARAMETER_NOT_VALID);
                }
                getAllCourses(request, response);
            } else {
                response.sendRedirect("/");
            }
        } catch (Exception e) {
            log.error("Error in method. Parametrs: request -> " + getFullURL(request), e);
            ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
        }
    }

    @HTTPMethod(method = HttpMethod.POST)
    @RequestMapping(path = "/users/{variable}", pathVariable = "userId", requiredParam = "command", requiredParamVariable = "block")
    public void blockUser(HttpServletRequest request, HttpServletResponse response) {
        blockOrUnblockUser(request, response, dbFacade::blockUser, this::getAllStudents);
    }

    @HTTPMethod(method = HttpMethod.POST)
    @RequestMapping(path = "/users/{variable}", pathVariable = "userId", requiredParam = "command", requiredParamVariable = "unblock")
    public void unblockUser(HttpServletRequest request, HttpServletResponse response) {
        blockOrUnblockUser(request, response, dbFacade::unblockUser, this::getAllStudents);
    }

    @HTTPMethod(method = HttpMethod.POST)
    @RequestMapping(path = "/teachers/{variable}", pathVariable = "userId", requiredParam = "command", requiredParamVariable = "block")
    public void blockTeacher(HttpServletRequest request, HttpServletResponse response) {
        blockOrUnblockUser(request, response, dbFacade::blockUser, this::getAllTeachers);
    }

    @HTTPMethod(method = HttpMethod.POST)
    @RequestMapping(path = "/teachers/{variable}", pathVariable = "userId", requiredParam = "command", requiredParamVariable = "unblock")
    public void unblockTeacher(HttpServletRequest request, HttpServletResponse response) {
        blockOrUnblockUser(request, response, dbFacade::unblockUser, this::getAllTeachers);
    }

    private void blockOrUnblockUser(HttpServletRequest request, HttpServletResponse response,
            Function<Integer, Integer> func, BiConsumer<HttpServletRequest, HttpServletResponse> bicons) {
        try {
            if (checkAdminSession(request)) {
                request.setAttribute("msg", func.apply(Integer.parseInt((String) request.getAttribute("userId"))));
                bicons.accept(request, response);
            } else {
                response.sendRedirect("/");
            }
        } catch (Exception e) {
            log.error("Error in method. Parametrs: request -> " + getFullURL(request), e);
            ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
        }
    }

    @HTTPMethod(method = HttpMethod.POST)
    @RequestMapping(path = "/users/{variable}", pathVariable = "userId", requiredParam = { "command",
            "description" }, requiredParamVariable = "setTeacher")
    public void setUserToTeacher(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (checkAdminSession(request)) {
                request.setAttribute("msg",
                        dbFacade.doTeacherById(Integer.parseInt((String) request.getAttribute("userId")),
                                request.getParameter("description")));
                getAllStudents(request, response);
            } else {
                response.sendRedirect("/");
            }
        } catch (Exception e) {
            log.error("Error in method. Parametrs: request -> " + getFullURL(request), e);
            ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
        }
    }

    @HTTPMethod(method = HttpMethod.POST)
    @RequestMapping(path = "/teachers/{variable}", pathVariable = "userId", requiredParam = "command", requiredParamVariable = "setStudent")
    public void setUserToStudent(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (checkAdminSession(request)) {
                request.setAttribute("msg",
                        dbFacade.doStudentById(Integer.parseInt((String) request.getAttribute("userId"))));
                getAllTeachers(request, response);
            } else {
                response.sendRedirect("/");
            }
        } catch (Exception e) {
            log.error("Error in method. Parametrs: request -> " + getFullURL(request), e);
            ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
        }
    }

    @HTTPMethod(method = HttpMethod.POST)
    @RequestMapping(path = "/teachers/{variable}", pathVariable = "userId", requiredParam = { "command",
            "description" }, requiredParamVariable = "changeDescription")
    public void changeDescription(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (checkAdminSession(request)) {
                request.setAttribute("msg",
                        dbFacade.changeTeacherDescription(Integer.parseInt((String) request.getAttribute("userId")),
                                request.getParameter("description")));
                getAllTeachers(request, response);
            } else {
                response.sendRedirect("/");
            }
        } catch (Exception e) {
            log.error("Error in method. Parametrs: request -> " + getFullURL(request), e);
            ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
        }
    }

    private boolean checkAdminSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("role") != null) {
            if (session.getAttribute("role").equals("Admin")) {
                return true;
            }
        }
        return false;
    }

    private DatabaseCourse getCourse(String courseId, String title, String theam, String description, String duration,
            String maxStudent, String teacher, String startDate) {
        try {
            System.out.println(startDate);
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            DatabaseCourse course = DatabaseCourse.newBuilder().setId(Integer.parseInt(courseId)).setTitle(title)
                    .setTheme(Integer.parseInt(theam)).setDescription(description)
                    .setDuration(Integer.parseInt(duration)).setStudentCount(Integer.parseInt(maxStudent))
                    .setTeacher(Integer.parseInt(teacher))
                    .setStartDate(new Timestamp(formatter.parse(startDate).getTime())).build();
            return course;
        } catch (Exception e) {
            log.error("Error in method. Parametrs: courseId = " + courseId + ", title" + title + ", theam" + theam
                    + ", description" + description + ", duration" + duration + ", maxStudent" + maxStudent
                    + ", teacher" + teacher + ", startDate" + startDate, e);
            return null;
        }
    }
}
