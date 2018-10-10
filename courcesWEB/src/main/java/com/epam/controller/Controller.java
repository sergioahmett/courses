package com.epam.controller;

import static com.epam.utils.CodeHandler.ERROR;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.epam.annotation.ProcessorAnnotationController;
import com.epam.enums.HttpMethod;
import com.epam.interfaces.ProcessorIntarface;
import com.epam.processor.*;
import com.epam.utils.CodeHandler;

/**
 * The main servlet that accepts all requests and forwards them to the
 * processors.
 * 
 * @author Sergey Ahmetshin
 *
 */
@WebServlet(name = "headServlet", urlPatterns = { "/teachers/*", "/courses/*", "/profile/*", "/auth/*", "/themes/*",
        "/admin/*", "/journal/*" })
public class Controller extends HttpServlet {
    private static final Logger log = Logger.getLogger(Controller.class);
    private static final long serialVersionUID = 1L;
    private List<ProcessorIntarface> processorList;
    private ProcessorAnnotationController annotationController;
    private Random r = new Random();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        redirect(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        redirect(request, response);
    }

    @SuppressWarnings("unchecked")
    public void init() throws ServletException {
        log.debug("Start init main contoller");
        processorList = (List<ProcessorIntarface>) getServletContext().getAttribute("processorsList");
        annotationController = new ProcessorAnnotationController();
        log.debug("Finish init main contoller");
    }

    /**
     * The main class method searches for the required processor and redirects the
     * request to it.
     * 
     * @param request
     *            - http request
     * @param response
     *            - http response
     * @throws IOException
     * @throws ServletException
     */
    private void redirect(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        log.debug("Start main controller method");
        ProcessorIntarface currentProcessor = null;
        log.trace("requested uri -> " + request.getRequestURI());
        if (dubblePOSTControl(request)) {
            log.debug("Start searching processor");
            for (ProcessorIntarface processor : processorList) {
                if (annotationController.checkPath(processor.getClass(), request.getRequestURI())) {
                    currentProcessor = processor;
                    break;
                }
            }
            log.debug("End searching processor");
            if (currentProcessor != null) {
                log.debug("Reddirect control to processor ->" + currentProcessor.getClass().getSimpleName());
                currentProcessor.startProcess(request, response);
            } else {
                log.debug("Processor not found, redirecting to error processor");
                ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
            }
        } else {
            log.debug("Dubble post catched!");
            ErrorProcessor.getInstance().getErrorPage(request, response, CodeHandler.DOUBBLE_POST);
        }
        log.debug("End main controller method");
    }

    private boolean dubblePOSTControl(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        if (request.getMethod().equals(HttpMethod.POST.toString())) {
            if (request.getParameter("uid") != null) {
                if (session.getAttribute("uid") != null) {
                    if (Integer.parseInt(request.getParameter("uid")) == (int) session.getAttribute("uid")) {
                        session.setAttribute("uid", r.nextInt(1000));
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    session.setAttribute("uid", r.nextInt(1000));
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
}