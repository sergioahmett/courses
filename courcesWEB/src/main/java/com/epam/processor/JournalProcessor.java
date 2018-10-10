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
import com.epam.daolayer.dbfacade.DBFacade;
import com.epam.enums.HttpMethod;
import com.epam.interfaces.DatabaseFasadInterface;
import com.epam.interfaces.ProcessorIntarface;
import com.epam.utils.Journal;

/**
 * The processor that is responsible for the path /journal
 * 
 * @author Sergey Ahmetshin
 *
 */
@Processor(path = "/journal")
public class JournalProcessor implements ProcessorIntarface {
    private DatabaseFasadInterface dbFacade = DBFacade.getInstance();
    private static JournalProcessor instance;
    private static final Logger log = Logger.getLogger(JournalProcessor.class);
    private MethodAnnotationController annController;

    private JournalProcessor() {
        annController = new MethodAnnotationController();
        log.debug("JournalProcessor created");
    }

    public static JournalProcessor getInstance() {
        if (instance == null) {
            instance = new JournalProcessor();
        }
        return instance;
    }

    @Override
    public void startProcess(HttpServletRequest request, HttpServletResponse response) {
        annController.redirectToMethod(this, request, response);
    }

    @HTTPMethod(method = HttpMethod.GET)
    @RequestMapping(path = "/{variable}", pathVariable = "courseId")
    public void getJournalPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("role") != null) {
                if (dbFacade.checkAccessToJournal((int) session.getAttribute("userId"),
                        Integer.parseInt((String) request.getAttribute("courseId")),
                        (String) session.getAttribute("role"))) {
                    Journal j = dbFacade.getJournal(Integer.parseInt((String) request.getAttribute("courseId")),
                            (String) session.getAttribute("role"));
                    if (j != null) {
                        request.setAttribute("journal", j);
                        request.getRequestDispatcher("/WEB-INF/jsp/journal.jsp").forward(request, response);
                    } else {
                        request.setAttribute("error", JOURNAL_NOT_FOUND);
                        UserProcessor.getInstance().getProfilePage(request, response);
                    }
                } else {
                    request.setAttribute("error", ACCESS_DENIED);
                    UserProcessor.getInstance().getProfilePage(request, response);
                }
            } else {
                response.sendRedirect("/");
            }
        } catch (Exception e) {
            log.error("Error in method. Parametrs: request -> " + getFullURL(request), e);
            ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
        }
    }

    @HTTPMethod(method = HttpMethod.POST)
    @RequestMapping(path = "/{variable}", pathVariable = "courseId", requiredParam = { "command",
            "day" }, requiredParamVariable = "addDay")
    public void addDayToJournal(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("role") != null) {
                if (dbFacade.checkAccessToJournal((int) session.getAttribute("userId"),
                        Integer.parseInt((String) request.getAttribute("courseId")),
                        (String) session.getAttribute("role"))) {
                    request.setAttribute("msg", dbFacade.addDayToJournal(
                            Integer.parseInt((String) request.getAttribute("courseId")), request.getParameter("day")));
                    getJournalPage(request, response);
                } else {
                    request.setAttribute("error", ACCESS_DENIED);
                    UserProcessor.getInstance().getProfilePage(request, response);
                }
            } else {
                response.sendRedirect("/");
            }
        } catch (Exception e) {
            log.error("Error in method. Parametrs: request -> " + getFullURL(request), e);
            ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
        }
    }

    @HTTPMethod(method = HttpMethod.POST)
    @RequestMapping(path = "/{variable}", pathVariable = "courseId", requiredParam = { "command", "day", "userId",
            "raiting" }, requiredParamVariable = "addRaiting")
    public void addRaitingToJournal(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("role") != null) {
                if (dbFacade.checkAccessToJournal((int) session.getAttribute("userId"),
                        Integer.parseInt((String) request.getAttribute("courseId")),
                        (String) session.getAttribute("role"))) {
                    request.setAttribute("msg",
                            dbFacade.addRaitingToJournal(Integer.parseInt((String) request.getAttribute("courseId")),
                                    Integer.parseInt(request.getParameter("userId")), request.getParameter("day"),
                                    request.getParameter("raiting")));
                    getJournalPage(request, response);
                } else {
                    request.setAttribute("error", ACCESS_DENIED);
                    UserProcessor.getInstance().getProfilePage(request, response);
                }
            } else {
                response.sendRedirect("/");
            }
        } catch (Exception e) {
            log.error("Error in method. Parametrs: request -> " + getFullURL(request), e);
            ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
        }
    }

    @HTTPMethod(method = HttpMethod.POST)
    @RequestMapping(path = "/{variable}", pathVariable = "courseId", requiredParam = { "command", "userId",
            "final" }, requiredParamVariable = "addFinal")
    public void addFinalToJournal(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("role") != null) {
                if (dbFacade.checkAccessToJournal((int) session.getAttribute("userId"),
                        Integer.parseInt((String) request.getAttribute("courseId")),
                        (String) session.getAttribute("role"))) {
                    request.setAttribute("msg",
                            dbFacade.addFinalRaitingToUser(Integer.parseInt((String) request.getAttribute("courseId")),
                                    Integer.parseInt(request.getParameter("userId")),
                                    Integer.parseInt(request.getParameter("final"))));
                    getJournalPage(request, response);
                } else {
                    request.setAttribute("error", ACCESS_DENIED);
                    UserProcessor.getInstance().getProfilePage(request, response);
                }
            } else {
                response.sendRedirect("/");
            }
        } catch (Exception e) {
            log.error("Error in method. Parametrs: request -> " + getFullURL(request), e);
            ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
        }
    }
}
