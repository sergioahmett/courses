package com.epam.listener;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.epam.daolayer.dao.CourseDAO;
import com.epam.daolayer.dao.CourseToUserDAO;
import com.epam.daolayer.dao.JournalDAO;
import com.epam.daolayer.dao.ThemeDAO;
import com.epam.daolayer.dao.UserDAO;
import com.epam.daolayer.dbfacade.DBFacade;
import com.epam.daolayer.dbfacade.DBFacade.Injector;
import com.epam.interfaces.ProcessorIntarface;
import com.epam.processor.AdminProcessor;
import com.epam.processor.AuthProcessor;
import com.epam.processor.CoursesProcessor;
import com.epam.processor.JournalProcessor;
import com.epam.processor.TeacherProcessor;
import com.epam.processor.ThemesProcessor;
import com.epam.processor.UserProcessor;

/**
 * Servlet listener class. Responsible for initial initialization of the logger, processors, DAO and i18n.
 * 
 * @author Sergey Ahmetshin
 *
 */
@WebListener
public class ContextListener implements ServletContextListener {
    private static final Logger log = Logger.getLogger(ContextListener.class);

    public void contextDestroyed(ServletContextEvent event) {
        log("Servlet context destruction starts");
        log("Servlet context destruction finished");
    }

    public void contextInitialized(ServletContextEvent event) {
        log("Servlet context initialization starts");

        ServletContext servletContext = event.getServletContext();
        initLog4J(servletContext);
        initFasade();
        initProcessors(servletContext);
        initI18N(servletContext);

        log("Servlet context initialization finished");
    }

    private void initI18N(ServletContext servletContext) {
        log.debug("Internationalization initialization started");

        String localesValue = servletContext.getInitParameter("local");
        if (localesValue == null || localesValue.isEmpty()) {
            log.warn("'local' init parameter is empty, the default encoding will be used");
        } else {
            log.debug("default local set to " + localesValue);
            servletContext.setAttribute("language", localesValue);
        }

        log.debug("Internationalization subsystem initialization finished");
    }

    private void initLog4J(ServletContext servletContext) {
        log("Log4J initialization started");
        try {
            PropertyConfigurator.configure(servletContext.getRealPath("WEB-INF/log4j.properties"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        log("Log4J initialization finished");
    }

    private void initProcessors(ServletContext servletContext) {
        log.debug("Command processors initialization started");

        List<ProcessorIntarface> processorList = new LinkedList<>();
        processorList.add(AdminProcessor.getInstance());
        processorList.add(JournalProcessor.getInstance());
        processorList.add(CoursesProcessor.getInstance());
        processorList.add(ThemesProcessor.getInstance());
        processorList.add(AuthProcessor.getInstance());
        processorList.add(UserProcessor.getInstance());
        processorList.add(TeacherProcessor.getInstance());
        servletContext.setAttribute("processorsList", processorList);

        log.debug("Command container initialization finished");
    }
    
    private void initFasade() {
        log.debug("Command fasade initialization started");

        Injector inj = ((DBFacade)DBFacade.getInstance()).getInjector();
        inj.setCourseDAO(new CourseDAO());
        inj.setThemeDAO(new ThemeDAO());
        inj.setCourseToUserDAO(new CourseToUserDAO());
        inj.setJournalDAO(new JournalDAO());
        inj.setUserDAO(new UserDAO());

        log.debug("Command fasade initialization finished");
    }

    private void log(String msg) {
        System.out.println("[ContextListener] " + msg);
    }

}