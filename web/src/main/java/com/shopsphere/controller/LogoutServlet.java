package com.shopsphere.controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
@WebServlet(name = "LogoutServlet", urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession(false); if (s != null) s.invalidate();
        HttpSession ns = req.getSession(true); ns.setAttribute("flashSuccess", "Logged out successfully.");
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
