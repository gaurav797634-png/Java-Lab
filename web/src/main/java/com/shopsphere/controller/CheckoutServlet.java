package com.shopsphere.controller;
import com.shopsphere.dao.AddressDAO;
import com.shopsphere.model.*;
import com.shopsphere.service.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.UUID;
@WebServlet(name = "CheckoutServlet", urlPatterns = {"/checkout"})
public class CheckoutServlet extends HttpServlet {
    private CheckoutService checkoutSvc; private CartService cartSvc; private AddressDAO addrDao;
    @Override public void init() { checkoutSvc = new CheckoutService(); cartSvc = new CartService(); addrDao = new AddressDAO(); }
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession(false); User u = (s != null) ? (User) s.getAttribute("currentUser") : null;
        if (u == null) { resp.sendRedirect(req.getContextPath() + "/login?redirect=" + req.getContextPath() + "/checkout"); return; }
        Cart c = cartSvc.getCart(s, u.getId());
        if (c == null || c.getItems().isEmpty()) { s.setAttribute("flashError", "Cart empty."); resp.sendRedirect(req.getContextPath() + "/cart"); return; }
        req.setAttribute("addresses", addrDao.findByUserId(u.getId())); req.setAttribute("cart", c);
        String token = UUID.randomUUID().toString(); s.setAttribute("checkoutToken", token); req.setAttribute("checkoutToken", token);
        req.getRequestDispatcher("/checkout.jsp").forward(req, resp);
    }
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession(false); User u = (s != null) ? (User) s.getAttribute("currentUser") : null;
        if (u == null) { resp.sendRedirect(req.getContextPath() + "/login"); return; }
        String token = req.getParameter("checkoutToken"); String sessionToken = (String) s.getAttribute("checkoutToken");
        if (token == null || !token.equals(sessionToken)) { s.setAttribute("flashError", "Order already processed."); resp.sendRedirect(req.getContextPath() + "/orders
