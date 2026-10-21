package com.shopsphere.listener;
import com.shopsphere.util.DBConnection;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;
import java.util.logging.Logger;
@WebListener
public class AppContextListener implements ServletContextListener {
    private static final Logger LOGGER = Logger.getLogger(AppContextListener.class.getName());
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOGGER.info("Starting ShopSphere E-Commerce Application");
        boolean connected = DBConnection.checkConnection();
        sce.getServletContext().setAttribute("appName", "ShopSphere");
        sce.getServletContext().setAttribute("dbConnected", connected);
    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            try { DriverManager.deregisterDriver(drivers.nextElement()); } catch (Exception ignored) {}
        }
    }
}
