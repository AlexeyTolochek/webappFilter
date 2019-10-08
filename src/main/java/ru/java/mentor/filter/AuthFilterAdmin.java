package ru.java.mentor.filter;

import ru.java.mentor.model.User;
import ru.java.mentor.model.UserRole;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/admin")
public class AuthFilterAdmin implements Filter {
    private final String userPage = "WEB-INF/view/userPage.jsp";
    private final String onlyAdmin = "Sorry, only Admin have enough rights";
    private final static UserRole adminRole = UserRole.Administrator;
    private final static String login = "/login";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            servletRequest.getServletContext().getRequestDispatcher(login).forward(request, response);
        }

        User user = (User) session.getAttribute("user");

        if (user.getRole().equals(adminRole)) {
            chain.doFilter(request, response);
        }
        request.setAttribute("nameUser", user.getName());
        request.setAttribute("message", onlyAdmin);
        request.getRequestDispatcher(userPage).forward(request, response);
    }

    @Override
    public void destroy() {
    }
}
