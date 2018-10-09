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
 * The processor that is responsible for the path /profile
 * 
 * @author Sergey Ahmetshin
 *
 */
@Processor(path = "/profile")
public class UserProcessor implements ProcessorIntarface {
    private DatabaseFasadInterface dbFacade = DBFasad.getInstance();
    private static UserProcessor instance;
    private static final Logger log = Logger.getLogger(UserProcessor.class);
    private MethodAnnotationController annController;

    private UserProcessor() {
        annController = new MethodAnnotationController();
        log.debug("UserProcessor created");
    }

    public static UserProcessor getInstance() {
        if (instance == null) {
            instance = new UserProcessor();
        }
        return instance;
    }

    @Override
    public void startProcess(HttpServletRequest request, HttpServletResponse response) {
        annController.redirectToMethod(this, request, response);
    }

    @HTTPMethod(method = HttpMethod.GET)
    @RequestMapping
    public void getProfilePage(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("userId") != null) {
                request.setAttribute("user", dbFacade.getResultUserById((Integer) session.getAttribute("userId"),
                        (String) session.getAttribute("role")));
                request.getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(request, response);
            } else {
                response.sendRedirect("/");
            }
        } catch (Exception e) {
            log.error("Error in method. Parametrs: request -> " + getFullURL(request), e);
            ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
        }
    }

    @HTTPMethod(method = HttpMethod.POST)
    @RequestMapping(requiredParam = "command")
    public void changePassword(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("userId") != null) {
                String currentPass = request.getParameter("currentPass");
                String newPass = request.getParameter("newPass");
                String confirmPass = request.getParameter("confirmPass");
                int userId = (Integer) session.getAttribute("userId");
                if (currentPass != null && newPass != null && confirmPass != null
                        && request.getParameter("command").equals("pchange")) {
                    if (newPass.equals(confirmPass)) {
                        if (new Validator().checkPassword(newPass)) {
                            DatabaseUser user = dbFacade.getUserByID(userId);
                            if (PasswordDefender.checkPass(user.getPassword(), user.getLogin(), currentPass)) {
                                user.getBuilder().setPassword(newPass).encryptPassword();
                                if (dbFacade.updateUserPass(user)) {
                                    request.setAttribute("error", PASSWORD_CHANGE);
                                } else {
                                    request.setAttribute("error", ERROR);
                                }
                            } else {
                                request.setAttribute("error", PASSWORD_INCORRECT);
                            }
                        } else {
                            request.setAttribute("error", WEAK_PASSWORD);
                        }
                    } else {
                        request.setAttribute("error", PASSWORD_NOT_MATCH);
                    }
                } else {
                    request.setAttribute("error", PARAMETER_NOT_VALID);
                }
                getProfilePage(request, response);
            } else {
                response.sendRedirect("/");
            }
        } catch (Exception e) {
            log.error("Error in method. Parametrs: request -> " + getFullURL(request), e);
            ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
        }
    }

    @HTTPMethod(method = HttpMethod.GET)
    @RequestMapping(path = "/{variable}", pathVariable = "local")
    public void setLocal(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(true);
            if (request.getAttribute("local").equals("ru") || request.getAttribute("local").equals("gb"))
                session.setAttribute("language", request.getAttribute("local"));
            response.sendRedirect("/");
        } catch (Exception e) {
            log.error("Error in method. Parametrs: request -> " + getFullURL(request), e);
            ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
        }
    }
}
