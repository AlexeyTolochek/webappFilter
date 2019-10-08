package ru.java.mentor.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/user")
public class AuthFilterUser implements Filter {
    private final String userPage = "WEB-INF/view/userPage.jsp";
    private final String userRole = "userRole";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        HttpSession session = request.getSession(false);

        Cookie[] cookies = request.getCookies();
        Cookie cookie = null;

        if (cookies!=null) {
            for (Cookie c : cookies) {
                if (userRole.equals(c.getName())) {
                    cookie = c;
                    break;
                }
            }
        }

        if (cookie == null) {
            servletRequest.getServletContext().getRequestDispatcher("/").forward(request, response);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
