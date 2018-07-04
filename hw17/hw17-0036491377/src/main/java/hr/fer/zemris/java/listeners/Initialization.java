package hr.fer.zemris.java.listeners;

import hr.fer.zemris.java.image.ImageDB;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * This listener implementation sets the correct path to the WEB-INF folder for the image database.
 *
 * @author matej
 */
@WebListener
public class Initialization implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String webinfPath = sce.getServletContext().getRealPath("WEB-INF");
        try {
            ImageDB.setWebinfPath(Paths.get(webinfPath));
        } catch (IOException ignored) {
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}