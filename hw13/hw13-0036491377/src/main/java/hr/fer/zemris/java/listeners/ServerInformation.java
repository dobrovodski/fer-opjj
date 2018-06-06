package hr.fer.zemris.java.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Date;

public class ServerInformation implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("serverStartTime", new Date().getTime());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
