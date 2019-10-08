package ru.java.mentor.filter;

import ru.java.mentor.model.UserRole;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/admin")
public class AuthFilterAdmin implements Filter {
    private final String userPage = "WEB-INF/view/userPage.jsp";
    private final String userRole = "userRole";
    private final String user = String.valueOf(UserRole.User);
    private final String admin = String.valueOf(UserRole.Administrator);
    private final String onlyAdmin = "Sorry, only Admin have enough rights";
    private String role;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

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

        if (cookie!=null) {
            role = cookie.getValue();
        }

        if (cookie == null) {
            servletRequest.getServletContext().getRequestDispatcher("/").forward(request, response);
        }

        if (user.equals(role)) {
            request.setAttribute("message", onlyAdmin);
            request.getRequestDispatcher(userPage).forward(request, response);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
