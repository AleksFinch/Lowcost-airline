package com.finchuk.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by olexandr on 03.04.17.
 */
@WebListener
public class AppListenner implements ServletContextListener {
    private static final Logger LOGGER = LogManager.getLogger();
    AppTuner app = new AppTuner();
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        sce.getServletContext().setAttribute("webApplication", app);
        app.setServletContext(sce.getServletContext());
        app.init();
        
        LOGGER.info("WebApplication initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
