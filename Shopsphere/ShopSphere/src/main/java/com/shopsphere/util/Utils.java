package com.shopsphere.util;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopsphere.model.User;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/* Consolidated utilities: DBConnection + PasswordUtil + SessionUtil + JsonUtil */
public final class Utils {

    /* ------------------------------- DB -------------------------------- */
    private static HikariDataSource ds;
    public static synchronized HikariDataSource dataSource() {
        if (ds == null) {
            try (InputStream in = Utils.class.getClassLoader().getResourceAsStream("db.properties")) {
                if (in == null) throw new IllegalStateException("db.properties not found");
                Properties p = new Properties(); p.load(in);
                HikariConfig c = new HikariConfig();
                c.setJdbcUrl(p.getProperty("db.url"));
                c.setUsername(p.getProperty("db.username"));
                c.setPassword(p.getProperty("db.password"));
                c.setDriverClassName(p.getProperty("db.driver"));
                c.setMaximumPoolSize(10);
                c.setPoolName("ShopSpherePool");
                ds = new HikariDataSource(c);
            } catch (Exception e) { throw new ExceptionInInitializerError(e); }
        }
        return ds;
    }
    public static void shutdown() { if (ds != null && !ds.isClosed()) ds.close(); }

    /* ----------------------------- Passwords --------------------------- */
    private static final int COST = 10;
    public static String hash(String plain) { return BCrypt.withDefaults().hashToString(COST, plain.toCharArray()); }
    public static boolean verify(String plain, String hash) {
        if (plain == null || hash == null) return false;
        return BCrypt.verifyer().verify(plain.toCharArray(), hash).verified;
    }

    /* ------------------------------ Session ---------------------------- */
    public static final String USER_KEY = "user";
    public static User currentUser(HttpServletRequest req) {
        HttpSession s = req.getSession(false);
        return s == null ? null : (User) s.getAttribute(USER_KEY);
    }
    public static boolean isAdmin(HttpServletRequest req) {
        User u = currentUser(req);
        return u != null && "ADMIN".equalsIgnoreCase(u.getRole());
    }

    /* -------------------------------- JSON ----------------------------- */
    private static final ObjectMapper MAPPER = new ObjectMapper();
    public static ObjectMapper mapper() { return MAPPER; }
    public static void writeJson(HttpServletResponse resp, int status, Object body) throws IOException {
        resp.setStatus(status);
        resp.setContentType("application/json;charset=UTF-8");
        MAPPER.writeValue(resp.getWriter(), body);
    }
}
