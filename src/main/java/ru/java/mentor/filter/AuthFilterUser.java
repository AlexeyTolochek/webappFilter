package ru.java.mentor.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/user")
public class AuthFilterUser implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("user") != null) {
            chain.doFilter(request, response);
        }

        String path = request.getContextPath() + "/login";
        response.sendRedirect(path);
    }

    @Override
    public void destroy() {
    }
}
