package com.shopsphere.controller;
import com.shopsphere.service.CategoryService;
import com.shopsphere.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
@WebServlet(name = "HomeServlet", urlPatterns = {"/home"})
public class HomeServlet extends HttpServlet {
    private ProductService prodSvc; private CategoryService catSvc;
    @Override public void init() { prodSvc = new ProductService(); catSvc = new CategoryService(); }
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("categories", catSvc.getActiveCategories());
        req.setAttribute("featuredProducts", prodSvc.getFeaturedProducts(8));
        req.setAttribute("latestProducts", prodSvc.getFilteredProducts(null, null, null, null, "newest", 1, 8));
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
