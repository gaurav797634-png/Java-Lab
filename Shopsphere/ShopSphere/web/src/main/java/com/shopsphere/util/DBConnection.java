package com.shopsphere.util;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.logging.*;
public final class DBConnection {
    private static final Logger LOGGER = Logger.getLogger(DBConnection.class.getName());
    private static String dbUrl, dbUser, dbPassword, dbDriver;
    static { loadConfiguration(); }
    private DBConnection() {}
    public static synchronized void loadConfiguration() {
        Properties props = new Properties();
        try (InputStream in = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in != null) props.load(in);
        } catch (Exception e) { LOGGER.log(Level.WARNING, "Could not load db.properties"); }
        dbDriver = getSetting("DB_DRIVER", props.getProperty("db.driver", "com.mysql.cj.jdbc.Driver"));
        dbUrl = getSetting("DB_URL", props.getProperty("db.url", "jdbc:mysql://localhost:3306/shopsphere"));
        dbUser = getSetting("DB_USER", props.getProperty("db.user", "root"));
        dbPassword = getSetting("DB_PASSWORD", props.getProperty("db.password", "root"));
        try { Class.forName(dbDriver); } catch (ClassNotFoundException e) { LOGGER.log(Level.SEVERE, "Driver not found", e); }
    }
    private static String getSetting(String envKey, String fallback) {
        String envVal = System.getenv(envKey); if (envVal != null && !envVal.trim().isEmpty()) return envVal.trim();
        String sysProp = System.getProperty(envKey); if (sysProp != null && !sysProp.trim().isEmpty()) return sysProp.trim();
        return fallback;
    }
    public static Connection getConnection() throws SQLException { return DriverManager.getConnection(dbUrl, dbUser, dbPassword); }
    public static boolean checkConnection() { try (Connection conn = getConnection()) { return conn != null && !conn.isClosed(); } catch (Exception e) { return false; } }
    public static String getDbUrl() { return dbUrl; }
    public static String getDbUser() { return dbUser; }
}
