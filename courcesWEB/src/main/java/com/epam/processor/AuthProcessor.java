package com.epam.processor;

import static com.epam.utils.CodeHandler.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.epam.annotation.HTTPMethod;
import com.epam.annotation.MethodAnnotationController;
import com.epam.annotation.Processor;
import com.epam.annotation.RequestMapping;
import com.epam.daolayer.daoentity.DatabaseUser;
import com.epam.daolayer.dbfasad.DBFasad;
import com.epam.enums.HttpMethod;
import com.epam.interfaces.DatabaseFasadInterface;
import com.epam.interfaces.ProcessorIntarface;
import com.epam.utils.PasswordDefender;
import com.epam.utils.Validator;

/**
 * The processor that is responsible for the path /auth/*
 * 
 * @author Sergey Ahmetshin
 *
 */
@Processor(path = "/auth")
public class AuthProcessor implements ProcessorIntarface {
    private DatabaseFasadInterface dbFacade = DBFasad.getInstance();
    private static AuthProcessor instance;
    private static final Logger log = Logger.getLogger(AuthProcessor.class);
    private MethodAnnotationController annController;

    private AuthProcessor() {
        annController = new MethodAnnotationController();
        log.debug("AuthProcessor created");
    }

    public static AuthProcessor getInstance() {
        if (instance == null) {
            instance = new AuthProcessor();
        }
        return instance;
    }

    @Override
    public void startProcess(HttpServletRequest request, HttpServletResponse response) {
        annController.redirectToMethod(this, request, response);
    }

    @HTTPMethod(method = HttpMethod.GET)
    @RequestMapping(path = "/logout")
    public void logoutUser(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getSession().invalidate();
            response.sendRedirect("/");
        } catch (Exception e) {
            log.error("Error in method. Parametrs: request -> " + getFullURL(request), e);
            ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
        }
    }

    @HTTPMethod(method = HttpMethod.GET)
    @RequestMapping(path = "/register")
    public void getRegiterPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
        } catch (Exception e) {
            log.error("Error in method. Parametrs: request -> " + getFullURL(request), e);
            ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
        }
    }

    @HTTPMethod(method = HttpMethod.GET)
    @RequestMapping(path = "/login")
    public void getLoginPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
        } catch (Exception e) {
            log.error("Error in method. Parametrs: request -> " + getFullURL(request), e);
            ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
        }
    }

    @HTTPMethod(method = HttpMethod.POST)
    @RequestMapping(path = "/register")
    public void regiterUser(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (checkUserData(request)) {
                DatabaseUser user = DatabaseUser.newBuilder().setName(request.getParameter("name"))
                        .setSurname(request.getParameter("surname")).setLogin(request.getParameter("username"))
                        .setMail(request.getParameter("email")).setPassword(request.getParameter("password"))
                        .encryptPassword().build();
                int code = dbFacade.saveUser(user);
                if (code == OK) {
                    HttpSession session = request.getSession();
                    DatabaseUser tempUser = dbFacade.getUserByLoginOrMail(user.getLogin());
                    session.setAttribute("userId", tempUser.getId());
                    session.setAttribute("role", tempUser.getRole());
                    response.sendRedirect("/");
                } else {
                    request.setAttribute("error", code);
                    request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
                }
            } else {
                request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
            }
        } catch (Exception e) {
            log.error("Error in method. Parametrs: request -> " + getFullURL(request), e);
            ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
        }
    }

    @HTTPMethod(method = HttpMethod.POST)
    @RequestMapping(path = "/login")
    public void loginUser(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (request.getParameter("log") != null && request.getParameter("password") != null) {
                DatabaseUser tempUser = dbFacade.getUserByLoginOrMail(request.getParameter("log"));
                if (tempUser != null) {
                    if (!tempUser.isBlock()) {
                        if (PasswordDefender.checkPass(tempUser.getPassword(), tempUser.getLogin(),
                                request.getParameter("password"))) {
                            HttpSession session = request.getSession(true);
                            session.setAttribute("userId", tempUser.getId());
                            session.setAttribute("role", tempUser.getRole());
                            session.setAttribute("courseList", dbFacade.getUserActualCourses(tempUser.getId()));
                            response.sendRedirect("/");
                            return;
                        } else {
                            request.setAttribute("error", PASSWORD_INCORRECT);
                        }
                    } else {
                        request.setAttribute("error", USER_BLOCKED);
                    }
                } else {
                    request.setAttribute("error", USER_NOT_FOUND);
                }
            } else {
                request.setAttribute("error", PARAMETER_NOT_VALID);
            }
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error in method. Parametrs: request -> " + getFullURL(request), e);
            ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
        }
    }

    private boolean checkUserData(HttpServletRequest request) {
        Validator val = new Validator();
        if (request.getParameter("name") == null || !val.checkNameOrSurname(request.getParameter("name"))) {
            request.setAttribute("error", NAME_INCORRECT);
            return false;
        }
        if (request.getParameter("surname") == null || !val.checkNameOrSurname(request.getParameter("surname"))) {
            request.setAttribute("error", SURNAME_INCORRECT);
            return false;
        }
        if (request.getParameter("username") == null || !val.checkLogin(request.getParameter("username"))) {
            request.setAttribute("error", LOGIN_INCORRECT);
            return false;
        }
        if (request.getParameter("email") == null || !val.checkEmail(request.getParameter("email"))) {
            request.setAttribute("error", MAIL_INCORRECT);
            return false;
        }
        if (request.getParameter("password") == null || !val.checkPassword(request.getParameter("password"))) {
            request.setAttribute("error", WEAK_PASSWORD);
            return false;
        }
        if (!request.getParameter("password").equals(request.getParameter("confirm"))) {
            request.setAttribute("error", PASSWORD_NOT_MATCH);
            return false;
        }
        return true;
    }
}
