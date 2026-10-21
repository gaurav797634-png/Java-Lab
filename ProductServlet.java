package com.shopsphere.controller;
import com.shopsphere.model.Category;
import com.shopsphere.service.CategoryService;
import com.shopsphere.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
@WebServlet(name = "ProductServlet", urlPatterns = {"/products"})
public class ProductServlet extends HttpServlet {
    private ProductService prodSvc; private CategoryService catSvc;
    @Override public void init() { prodSvc = new ProductService(); catSvc = new CategoryService(); }
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer catId = null; String cp = req.getParameter("category");
        if (cp != null && !cp.isBlank()) { try { catId = Integer.parseInt(cp); } catch (Exception e) { Category c = catSvc.getCategoryBySlug(cp); if (c != null) catId = c.getId(); } }
        String key = req.getParameter("q"); BigDecimal minP = null, maxP = null;
        try { String m = req.getParameter("minPrice"); if (m != null) minP = new BigDecimal(m); } catch (Exception ignored) {}
        try { String m = req.getParameter("maxPrice"); if (m != null) maxP = new BigDecimal(m); } catch (Exception ignored) {}
        String sort = req.getParameter("sort"); int page = 1;
        try { String p = req.getParameter("page"); if (p != null) page = Math.max(1, Integer.parseInt(p)); } catch (Exception ignored) {}
        int total = prodSvc.countFilteredProducts(catId, key, minP, maxP);
        int totalPages = (int) Math.ceil((double) total / 9); if (totalPages == 0) totalPages = 1; if (page > totalPages) page = totalPages;
        req.setAttribute("products", prodSvc.getFilteredProducts(catId, key, minP, maxP, sort, page, 9));
        req.setAttribute("categories", catSvc.getActiveCategories());
        req.setAttribute("selectedCategoryId", catId);
        req.setAttribute("keyword", key); req.setAttribute("sortBy", sort);
        req.setAttribute("currentPage", page); req.setAttribute("totalPages", totalPages);
        req.getRequestDispatcher("/products.jsp").forward(req, resp);
    }
}
