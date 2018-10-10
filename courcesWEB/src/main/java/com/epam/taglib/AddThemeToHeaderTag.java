package com.epam.taglib;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.epam.daolayer.dbfacade.DBFacade;
import com.epam.interfaces.DatabaseFasadInterface;

/**
 * The class that is responsible for adding a list of theme to the header.
 * 
 * @author Sergey Ahmetshin
 *
 */

public class AddThemeToHeaderTag extends TagSupport {
    private static final long serialVersionUID = 1L;

    private int themeListLength;
    private DatabaseFasadInterface dbFacade = DBFacade.getInstance();

    public void setThemeListLength(int themeListLength) {
        this.themeListLength = themeListLength;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            Map<String, Integer> map = dbFacade.getThemeForHeader();
            int count = 0;
            JspWriter writer = pageContext.getOut();
            for (Entry<String, Integer> entry : map.entrySet()) {
                if (count++ < themeListLength) {
                    writer.print("<li><a href=\"/themes/" + entry.getValue() + "\">"
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