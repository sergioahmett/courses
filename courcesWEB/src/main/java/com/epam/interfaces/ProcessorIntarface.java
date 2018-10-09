package com.epam.interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface to Processor classes. 
 * 
 * @author Sergey Ahmetshin
 *
 */
public interface ProcessorIntarface {
    public void startProcess(HttpServletRequest request, HttpServletResponse response);
    
    default String getFullURL(HttpServletRequest request) {
        StringBuilder requestURL = new StringBuilder(request.getRequestURL().toString());
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }
}
