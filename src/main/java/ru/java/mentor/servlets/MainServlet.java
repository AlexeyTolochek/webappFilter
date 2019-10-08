package ru.java.mentor.servlets;

import ru.java.mentor.model.User;
import ru.java.mentor.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/")
public class MainServlet extends HttpServlet {
    private final String page = "WEB-INF/view/index.jsp";
    private final String invalidData = "Please enter correct data";


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        request.getRequestDispatcher(page).forward(request, response);

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

                // req.setAttribute("nameUser", user.getName());
                //req.setAttribute("birthdateUser", user.getbirthdate());

                HttpSession session = req.getSession();
                session.setMaxInactiveInterval(1800);
                session.setAttribute("user", user);
                req.getServletContext().getRequestDispatcher("/user").forward(req, resp);
            }
        } else {
            req.setAttribute("message", invalidData);
            doGet(req, resp);
        }
    }

    private boolean isLoginPasswordEmpty(String login, String password) {
        return login != null && !login.isEmpty() &&
                password != null && !password.isEmpty();
    }
}

