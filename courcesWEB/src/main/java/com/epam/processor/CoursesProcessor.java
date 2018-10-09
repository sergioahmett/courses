package com.epam.processor;

import static com.epam.utils.CodeHandler.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.epam.annotation.HTTPMethod;
import com.epam.annotation.MethodAnnotationController;
import com.epam.annotation.Processor;
import com.epam.annotation.RequestMapping;
import com.epam.daolayer.dbfasad.DBFasad;
import com.epam.enums.HttpMethod;
import com.epam.interfaces.DatabaseFasadInterface;
import com.epam.interfaces.ProcessorIntarface;
import com.epam.resultentity.ResultCourse;
import com.epam.utils.CourseHelper;

/**
 * The processor that is responsible for the path /courses
 * 
 * @author Sergey Ahmetshin
 *
 */
@Processor(path = "/courses")
public class CoursesProcessor implements ProcessorIntarface {
    private DatabaseFasadInterface dbFacade = DBFasad.getInstance();
    private static CoursesProcessor instance;
    private static final Logger log = Logger.getLogger(CoursesProcessor.class);
    private MethodAnnotationController annController;

    private CoursesProcessor() {
        annController = new MethodAnnotationController();
        log.debug("CoursesProcessor created");
    }

    public static CoursesProcessor getInstance() {
        if (instance == null) {
            instance = new CoursesProcessor();
        }
        return instance;
    }

    @Override
    public void startProcess(HttpServletRequest request, HttpServletResponse response) {
        annController.redirectToMethod(this, request, response);
    }

    @SuppressWarnings("unchecked")
    @HTTPMethod(method = HttpMethod.POST)
    @RequestMapping(path = "/{variable}", pathVariable = "courseId", requiredParam = "command")
    public void userOperationWithCourse(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("role") != null) {
                if (session.getAttribute("role").equals("Student")) {
                    int courseId = Integer.parseInt((String) request.getAttribute("courseId"));
                    int code = dbFacade.regOrUnregUserToCourses((Integer) session.getAttribute("userId"), courseId,
                            request.getParameter("command"));
                    if (code == REGISTER_TO_COURSE_SUCCSESS || code == UNREGISTER_TO_COURSE_SUCCSESS) {
                        List<ResultCourse> list = (List<ResultCourse>) session.getAttribute("courseList");
                        ResultCourse course = dbFacade.getResultCourseById(courseId);
                        if (code == UNREGISTER_TO_COURSE_SUCCSESS) {
                            list.remove(course);
                        } else {
                            list.add(course);
                        }
                        request.setAttribute("courseList", list);
                    }
                    request.setAttribute("msg", code);
                    returnCorse(request, response);
                } else {
                    request.setAttribute("msg", ONLY_STUDENTS_CAN_RAGISTER);
                    returnCorse(request, response);
                }
            } else {
                response.sendRedirect("/auth/login");
            }
        } catch (Exception e) {
            log.error("Error in method. Parametrs: request -> " + getFullURL(request), e);
            ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
        }
    }

    @HTTPMethod(method = HttpMethod.GET)
    @RequestMapping
    public void returnCorsesList(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("resultList", getResultListByParam(request.getParameter("show")));
            request.getRequestDispatcher("/WEB-INF/jsp/courses.jsp").forward(request, response);
        } catch (Exception e) {
            log.error("Error in method. Parametrs: request -> " + getFullURL(request), e);
            ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
        }
    }

    @HTTPMethod(method = HttpMethod.GET)
    @RequestMapping(path = "/{variable}", pathVariable = "courseId")
    public void returnCorse(HttpServletRequest request, HttpServletResponse response) {
        try {
            int courseId = Integer.parseInt((String) request.getAttribute("courseId"));
            request.setAttribute("resultCourse", dbFacade.getResultCourseById(courseId));
            request.getRequestDispatcher("/WEB-INF/jsp/course.jsp").forward(request, response);
        } catch (Exception e) {
            log.error("Error in method. Parametrs: request -> " + getFullURL(request), e);
            ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
        }
    }

    private List<ResultCourse> getResultListByParam(String param) {
        CourseHelper<ResultCourse> helper = new CourseHelper<>();
        switch (param != null ? param : "") {
        default:
        case "all":
            return dbFacade.getResultCourseList();
        case "actual":
            return helper.getActualCourses(dbFacade.getResultCourseList());
        case "current":
            return helper.getCurrentCourses(dbFacade.getResultCourseList());
        case "archive":
            return helper.getArchiveCourses(dbFacade.getResultCourseList());
        }
    }
}
