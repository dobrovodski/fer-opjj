package hr.fer.zemris.java.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * This listener implementation logs the time at which the server was started and forwards it to the {@link
 * javax.servlet.ServletContext}.
 *
 * @author matej
 */
public class ServerInformation implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("serverStartTime", System.currentTimeMillis());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
