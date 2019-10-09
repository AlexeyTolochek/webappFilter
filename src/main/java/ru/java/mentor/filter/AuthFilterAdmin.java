package ru.java.mentor.filter;

import ru.java.mentor.model.User;
import ru.java.mentor.model.UserRole;
import ru.java.mentor.util.ExceptionFromReadMethod;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static ru.java.mentor.util.ReaderProperty.readProperty;

@WebFilter("/admin")
public class AuthFilterAdmin implements Filter {
    private final static UserRole adminRole = UserRole.Administrator;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        HttpSession session = request.getSession(false);
        String path = request.getContextPath() + "/login";

        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");

            if (user.getRole().equals(adminRole)) {
                chain.doFilter(request, response);
            }

            path = request.getContextPath() + "/user";
            try {
                session.setAttribute("message", readProperty("onlyAdmin"));
            } catch (ExceptionFromReadMethod exceptionFromReadMethod) {
                exceptionFromReadMethod.printStackTrace();
            }
        }
        response.sendRedirect(path);
    }

    @Override
    public void destroy() {
    }
}
