package com.epam.processor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.epam.annotation.Processor;
import com.epam.interfaces.ProcessorIntarface;

/**
 * The processor that is responsible for the path /error
 * 
 * @author Sergey Ahmetshin
 *
 */
@Processor(path = "/error")
public class ErrorProcessor implements ProcessorIntarface {
    private static ErrorProcessor instance;
    private static final Logger log = Logger.getLogger(ErrorProcessor.class);

    private ErrorProcessor() {
        log.debug("ErrorProcessor created");
    }

    public static ErrorProcessor getInstance() {
        if (instance == null) {
            instance = new ErrorProcessor();
        }
        return instance;
    }

    @Override
    public void startProcess(HttpServletRequest request, HttpServletResponse response) {
    }

    public void getErrorPage(HttpServletRequest request, HttpServletResponse response, int errorCode) {
        try {
            request.setAttribute("code", errorCode);
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        } catch (Exception e) {
            log.error("Error in method. Parametrs: request -> " + getFullURL(request), e);
        }
    }
}
