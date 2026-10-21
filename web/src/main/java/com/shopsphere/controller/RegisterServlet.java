package com.shopsphere.controller;
import com.shopsphere.model.User;
import com.shopsphere.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    private UserService userService;
    @Override public void init() { userService = new UserService(); }
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { req.getRequestDispatcher("/register.jsp").forward(req, resp); }
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name"), email = req.getParameter("email"), phone = req.getParameter("phone"), pass = req.getParameter("password"), conf = req.getParameter("confirmPassword");
        if (pass == null || !pass.equals(conf)) { req.setAttribute("error", "Passwords do not match."); req.getRequestDispatcher("/register.jsp").forward(req, resp); return; }
        try {
            User u = userService.registerCustomer(name, email, phone, pass);
            HttpSession s = req.getSession(true); req.changeSessionId();
            s.setAttribute("currentUser", u); s.setAttribute("flashSuccess", "Welcome to ShopSphere, " + u.getName() + "!");
            resp.sendRedirect(req.getContextPath() + "/");
        } catch (Exception e) { req.setAttribute("error", e.getMessage()); req.getRequestDispatcher("/register.jsp").forward(req, resp); }
    }
}
