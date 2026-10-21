package com.shopsphere.filter;
import com.shopsphere.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
@WebFilter(filterName = "AuthFilter")
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        User currentUser = (session != null) ? (User) session.getAttribute("currentUser") : null;
        if (currentUser != null && currentUser.getStatus() == User.Status.ACTIVE) {
            chain.doFilter(request, response); return;
        }
        String requestUri = httpRequest.getRequestURI();
        String queryString = httpRequest.getQueryString();
        String targetUrl = requestUri + (queryString != null ? "?" + queryString : "");
        String encodedTarget = URLEncoder.encode(targetUrl, StandardCharsets.UTF_8);
        if (session != null) session.setAttribute("flashError", "Please sign in to access this page.");
        httpResponse.sendRedirect(httpRequest.getContextPath() + "/login?redirect=" + encodedTarget);
    }
}
