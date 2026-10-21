package com.shopsphere.controller;
import com.shopsphere.model.User;
import com.shopsphere.service.UserService;
import com.shopsphere.service.CartService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    private UserService userService; private CartService cartService;
    @Override public void init() { userService = new UserService(); cartService = new CartService(); }
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession(false) != null && req.getSession(false).getAttribute("currentUser") != null) { resp.sendRedirect(req.getContextPath() + "/"); return; }
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email"), pass = req.getParameter("password"), redirect = req.getParameter("redirect");
        try {
            User u = userService.authenticate(email, pass);
            if (u != null) {
                HttpSession s = req.getSession(true); req.changeSessionId();
                s.setAttribute("currentUser", u); s.setAttribute("flashSuccess", "Welcome back, " + u.getName() + "!");
                try { cartService.syncCartOnLogin(s, u.getId()); } catch (Exception ignored) {}
                if (redirect != null && !redirect.isBlank() && !redirect.contains("://")) resp.sendRedirect(redirect);
                else if (u.isAdmin()) resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
                else resp.sendRedirect(req.getContextPath() + "/");
            } else { req.setAttribute("error", "Invalid credentials."); req.getRequestDispatcher("/login.jsp").forward(req, resp); }
        } catch (Exception e) { req.setAttribute("error", e.getMessage()); req.getRequestDispatcher("/login.jsp").forward(req, resp); }
    }
}
