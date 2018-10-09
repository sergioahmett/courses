package com.epam.taglib;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.epam.daolayer.dbfasad.DBFasad;
import com.epam.interfaces.DatabaseFasadInterface;

/**
 * The class that is responsible for adding a list of theme to the header.
 * 
 * @author Sergey Ahmetshin
 *
 */

public class AddTheamToHeaderTag extends TagSupport {
    private static final long serialVersionUID = 1L;

    private int theamListLength;
    private DatabaseFasadInterface dbFacade = DBFasad.getInstance();

    public void setTheamListLength(int theamListLength) {
        this.theamListLength = theamListLength;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            Map<String, Integer> map = dbFacade.getTheamForHeader();
            int count = 0;
            JspWriter writer = pageContext.getOut();
            for (Entry<String, Integer> entry : map.entrySet()) {
                if (count++ < theamListLength) {
                    writer.print("<li><a href=\"http://localhost:8080/theams/" + entry.getValue() + "\">"
                            + entry.getKey() + "</a></li>");
                } else {
                    break;
                }
            }
        } catch (IOException ioException) {
            throw new JspException("Error: " + ioException.getMessage());
        }
        return SKIP_BODY;
    }
}