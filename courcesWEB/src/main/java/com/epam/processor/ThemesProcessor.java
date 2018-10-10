package com.epam.processor;

import static com.epam.utils.CodeHandler.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.epam.annotation.HTTPMethod;
import com.epam.annotation.MethodAnnotationController;
import com.epam.annotation.Processor;
import com.epam.annotation.RequestMapping;
import com.epam.daolayer.dbfacade.DBFacade;
import com.epam.enums.HttpMethod;
import com.epam.interfaces.DatabaseFasadInterface;
import com.epam.interfaces.ProcessorIntarface;
import com.epam.resultentity.ResultTheme;

/**
 * The processor that is responsible for the path /themes
 * 
 * @author Sergey Ahmetshin
 *
 */
@Processor(path = "/themes")
public class ThemesProcessor implements ProcessorIntarface {
    private DatabaseFasadInterface dbFacade = DBFacade.getInstance();
    private static ThemesProcessor instance;
    private static final Logger log = Logger.getLogger(ThemesProcessor.class);
    private MethodAnnotationController annController;

    private ThemesProcessor() {
        annController = new MethodAnnotationController();
        log.debug("ThemesProcessor created");
    }

    public static ThemesProcessor getInstance() {
        if (instance == null) {
            instance = new ThemesProcessor();
        }
        return instance;
    }

    @Override
    public void startProcess(HttpServletRequest request, HttpServletResponse response) {
        annController.redirectToMethod(this, request, response);
    }

    @HTTPMethod(method = HttpMethod.GET)
    @RequestMapping(path = "/{variable}", pathVariable = "themeId")
    public void returnTheam(HttpServletRequest request, HttpServletResponse response) {
        try {
            int themeId = Integer.parseInt((String) request.getAttribute("themeId"));
            ResultTheme theme = dbFacade.getResultThemeById(themeId);
            if (theme != null) {
                request.setAttribute("theme", theme);
                request.getRequestDispatcher("/WEB-INF/jsp/theme.jsp").forward(request, response);
            } else {
                request.setAttribute("error", THEME_NOT_FOUND);
                returnThemeList(request, response);
            }
        } catch (Exception e) {
            log.error("Error in method. Parametrs: request -> " + getFullURL(request), e);
            ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
        }
    }

    @HTTPMethod(method = HttpMethod.GET)
    @RequestMapping
    public void returnThemeList(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("resultList", dbFacade.getResultThemeList());
            request.getRequestDispatcher("/WEB-INF/jsp/themes.jsp").forward(request, response);
        } catch (Exception e) {
            log.error("Error in method. Parametrs: request -> " + getFullURL(request), e);
            ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
        }
    }

}
