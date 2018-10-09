package com.epam.processor;

import static com.epam.utils.CodeHandler.*;

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
import com.epam.resultentity.ResultTheam;

/**
 * The processor that is responsible for the path /theams
 * 
 * @author Sergey Ahmetshin
 *
 */
@Processor(path = "/theams")
public class TheamsProcessor implements ProcessorIntarface {
    private DatabaseFasadInterface dbFacade = DBFasad.getInstance();
    private static TheamsProcessor instance;
    private static final Logger log = Logger.getLogger(TheamsProcessor.class);
    private MethodAnnotationController annController;

    private TheamsProcessor() {
        annController = new MethodAnnotationController();
        log.debug("TheamsProcessor created");
    }

    public static TheamsProcessor getInstance() {
        if (instance == null) {
            instance = new TheamsProcessor();
        }
        return instance;
    }

    @Override
    public void startProcess(HttpServletRequest request, HttpServletResponse response) {
        annController.redirectToMethod(this, request, response);
    }

    @HTTPMethod(method = HttpMethod.GET)
    @RequestMapping(path = "/{variable}", pathVariable = "theamId")
    public void returnTheam(HttpServletRequest request, HttpServletResponse response) {
        try {
            int theamId = Integer.parseInt((String) request.getAttribute("theamId"));
            ResultTheam theam = dbFacade.getResultTheamById(theamId);
            if (theam != null) {
                request.setAttribute("theam", theam);
                request.getRequestDispatcher("/WEB-INF/jsp/theam.jsp").forward(request, response);
            } else {
                request.setAttribute("error", THEAM_NOT_FOUND);
                returnTheamList(request, response);
            }
        } catch (Exception e) {
            log.error("Error in method. Parametrs: request -> " + getFullURL(request), e);
            ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
        }
    }

    @HTTPMethod(method = HttpMethod.GET)
    @RequestMapping
    public void returnTheamList(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("resultList", dbFacade.getResultTheamList());
            request.getRequestDispatcher("/WEB-INF/jsp/theams.jsp").forward(request, response);
        } catch (Exception e) {
            log.error("Error in method. Parametrs: request -> " + getFullURL(request), e);
            ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
        }
    }

}
