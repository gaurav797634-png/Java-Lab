package com.shopsphere.filter;
import com.shopsphere.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;
@WebFilter(filterName = "AdminFilter")
public class AdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        if (path.equals("/admin/login") || path.equals("/admin/login.jsp") || path.startsWith("/css/") || path.startsWith("/js/") || path.startsWith("/images/")) {
            chain.doFilter(request, response); return;
        }
        HttpSession session = httpRequest.getSession(false);
        User currentUser = (session != null) ? (User) session.getAttribute("currentUser") : null;
        if (currentUser != null && currentUser.isAdmin() && currentUser.getStatus() == User.Status.ACTIVE) {
            chain.doFilter(request, response); return;
        }
        if (session != null) session.setAttribute("flashError", "Administrator access required.");
        httpResponse.sendRedirect(httpRequest.getContextPath() + "/admin/login");
    }
}
