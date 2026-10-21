package com.shopsphere.controller;
import com.shopsphere.model.*;
import com.shopsphere.service.CartService;
import com.shopsphere.service.CouponService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
@WebServlet(name = "CartServlet", urlPatterns = {"/cart"})
public class CartServlet extends HttpServlet {
    private CartService cartSvc; private CouponService coupSvc;
    @Override public void init() { cartSvc = new CartService(); coupSvc = new CouponService(); }
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession(true); User u = (User) s.getAttribute("currentUser");
        req.setAttribute("cart", cartSvc.getCart(s, u != null ? u.getId() : null));
        req.getRequestDispatcher("/cart.jsp").forward(req, resp);
    }
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession(true); User u = (User) s.getAttribute("currentUser");
        String action = req.getParameter("action"); if (action == null) action = "view"; String ret = req.getParameter("returnUrl");
        try {
            switch (action) {
                case "add": cartSvc.addItem(s, u != null ? u.getId() : null, Long.parseLong(req.getParameter("productId")), 1); s.setAttribute("flashSuccess", "Item added!"); break;
                case "update": cartSvc.updateQuantity(s, u != null ? u.getId() : null, Long.parseLong(req.getParameter("productId")), Integer.parseInt(req.getParameter("quantity"))); s.setAttribute("flashSuccess", "Cart updated."); break;
                case "remove": cartSvc.removeItem(s, u != null ? u.getId() : null, Long.parseLong(req.getParameter("productId"))); s.setAttribute("flashSuccess", "Item removed."); break;
                case "applyCoupon": { Cart c = cartSvc.getCart(s, u != null ? u.getId() : null); Coupon cp = coupSvc.validateCoupon(req.getParameter("couponCode"), c.getSubtotal()); c.setAppliedCoupon(cp); s.setAttribute("appliedCoupon", cp); s.setAttribute("flashSuccess", "Coupon applied!"); break; }
            }
        } catch (Exception e) { s.setAttribute("flashError", e.getMessage()); }
        if (ret != null && !ret.isBlank() && !ret.contains("://")) resp.sendRedirect(ret); else resp.sendRedirect(req.getContextPath() + "/cart");
    }
}
