package com.shopsphere.controller;
import com.shopsphere.model.Product;
import com.shopsphere.model.User;
import com.shopsphere.service.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
@WebServlet(name = "ProductDetailsServlet", urlPatterns = {"/product"})
public class ProductDetailsServlet extends HttpServlet {
    private ProductService prodSvc; private ReviewService revSvc; private WishlistService wishSvc;
    @Override public void init() { prodSvc = new ProductService(); revSvc = new ReviewService(); wishSvc = new WishlistService(); }
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id == null || id.isBlank()) { resp.sendRedirect(req.getContextPath() + "/products"); return; }
        try {
            Long pid = Long.parseLong(id);
            Product p = prodSvc.getProductById(pid);
            if (p == null || p.getStatus() != Product.Status.ACTIVE) { req.getSession().setAttribute("flashError", "Product unavailable."); resp.sendRedirect(req.getContextPath() + "/products"); return; }
            req.setAttribute("product", p);
            req.setAttribute("reviews", revSvc.getReviewsForProduct(pid));
            req.setAttribute("relatedProducts", prodSvc.getRelatedProducts(pid, p.getCategoryId(), 4));
            boolean inWish = false; boolean hasRev = false;
            HttpSession s = req.getSession(false);
            if (s != null) { User u = (User) s.getAttribute("currentUser"); if (u != null) { inWish = wishSvc.isProductInWishlist(u.getId(), pid); hasRev = revSvc.hasUserReviewed(u.getId(), pid); } }
            req.setAttribute("inWishlist", inWish); req.setAttribute("hasReviewed", hasRev);
            req.getRequestDispatcher("/product-details.jsp").forward(req, resp);
        } catch (NumberFormatException e) { resp.sendRedirect(req.getContextPath() + "/products"); }
    }
}
