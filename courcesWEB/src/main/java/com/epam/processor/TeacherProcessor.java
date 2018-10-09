package com.epam.processor;

import static com.epam.utils.CodeHandler.*;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.epam.annotation.HTTPMethod;
import com.epam.annotation.MethodAnnotationController;
import com.epam.annotation.Processor;
import com.epam.annotation.RequestMapping;
import com.epam.daolayer.dbfasad.DBFasad;
import com.epam.enums.HttpMethod;
import com.epam.interfaces.DatabaseFasadInterface;
import com.epam.interfaces.ProcessorIntarface;
import com.epam.resultentity.ResultTeacher;

/**
 * The processor that is responsible for the path /teachers
 * 
 * @author Sergey Ahmetshin
 *
 */
@Processor(path = "/teachers")
public class TeacherProcessor implements ProcessorIntarface {
    private DatabaseFasadInterface dbFacade = DBFasad.getInstance();
    private static TeacherProcessor instance;
    private static final Logger log = Logger.getLogger(TeacherProcessor.class);
    private MethodAnnotationController annController;

    private TeacherProcessor() {
        annController = new MethodAnnotationController();
        log.debug("TeacherProcessor created");
    }

    public static TeacherProcessor getInstance() {
        if (instance == null) {
            instance = new TeacherProcessor();
        }
        return instance;
    }

    @Override
    public void startProcess(HttpServletRequest request, HttpServletResponse response) {
        annController.redirectToMethod(this, request, response);
    }

    @HTTPMethod(method = HttpMethod.GET)
    @RequestMapping(path = "/{variable}", pathVariable = "teacherId")
    public void getTeacherPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            int teacherId = Integer.parseInt((String) request.getAttribute("teacherId"));
            ResultTeacher teacher = dbFacade.getResultTeacherById(teacherId);
            if (teacher != null) {
                List<ResultTeacher> teachers = new LinkedList<>();
                teachers.add(teacher);
                request.setAttribute("teachers", teachers);
                request.getRequestDispatcher("/WEB-INF/jsp/teachers.jsp").forward(request, response);
            } else {
                request.setAttribute("error", TEACHER_NOT_FOUND);
                getTeachersPage(request, response);
            }
        } catch (Exception e) {
            log.error("Error in method. Parametrs: request -> " + getFullURL(request), e);
            ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
        }
    }

    @HTTPMethod(method = HttpMethod.GET)
    @RequestMapping
    public void getTeachersPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<ResultTeacher> teachers = dbFacade.getResultTeacherList();
            request.setAttribute("teachers", teachers);
            request.getRequestDispatcher("/WEB-INF/jsp/teachers.jsp").forward(request, response);
        } catch (Exception e) {
            log.error("Error in method. Parametrs: request -> " + getFullURL(request), e);
            ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
        }
    }

}
