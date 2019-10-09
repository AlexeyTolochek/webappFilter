package ru.java.mentor.servlets;

import ru.java.mentor.model.User;
import ru.java.mentor.service.UserService;
import ru.java.mentor.util.ExceptionFromReadMethod;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static ru.java.mentor.util.ReaderProperty.readProperty;

@WebServlet("/")
public class MainServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        try {
            request.getRequestDispatcher(readProperty("page")).forward(request, response);
        } catch (ExceptionFromReadMethod exceptionFromReadMethod) {
            exceptionFromReadMethod.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF8");
        final String login = req.getParameter("login");
        final String password = req.getParameter("password");

        if (isLoginPasswordEmpty(login, password)) {
            UserService service = UserService.getInstance();
            if (service.validateUser(login, password)) {
                User user = service.getUserByLogin(login);
                resp.setContentType("text/html");
                HttpSession session = req.getSession();
                session.setMaxInactiveInterval(1800);
                session.setAttribute("user", user);
                session.setAttribute("message", "");
                String path = req.getContextPath() + "/user";
                resp.sendRedirect(path);
            } else {
                try {
                    req.setAttribute("message", readProperty("invalidPass"));
                } catch (ExceptionFromReadMethod exceptionFromReadMethod) {
                    exceptionFromReadMethod.printStackTrace();
                }
                doGet(req, resp);
            }
        } else {
            try {
                req.setAttribute("message", readProperty("invalidData"));
            } catch (ExceptionFromReadMethod exceptionFromReadMethod) {
                exceptionFromReadMethod.printStackTrace();
            }
            doGet(req, resp);
        }
    }

    private boolean isLoginPasswordEmpty(String login, String password) {
        return login != null && !login.isEmpty() &&
                password != null && !password.isEmpty();
    }
}

