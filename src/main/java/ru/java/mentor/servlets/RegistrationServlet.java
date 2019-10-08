package ru.java.mentor.servlets;

import ru.java.mentor.model.User;
import ru.java.mentor.service.UserService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    private final String page = "/WEB-INF/view/registration.jsp";
    private final String trueAdd = "Thanks 4reg";
    private final String falseAdd = "login is already exist";
    private final String invalidData = "Please enter correct data";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        request.getRequestDispatcher(page).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF8");
        final String name = req.getParameter("name");
        final String login = req.getParameter("login");
        final String password = req.getParameter("password");
        final String address = req.getParameter("address");
        String birthdateStr = req.getParameter("birthdate");
        LocalDate birthdate = LocalDate.parse (birthdateStr);
        final String action = req.getParameter("action");

        if (requestIsValid(name, login, password, address, birthdateStr)) {
            UserService service = UserService.getInstance();
            final User user = new User(name, login, password, address, birthdate);

            if (!service.isExistLogin(login)) {
                service.addUser(user);
                req.setAttribute("message", trueAdd);
            } else {
                req.setAttribute("message", falseAdd);
            }

        } else {
            req.setAttribute("message", invalidData);
        }
        doGet(req, resp);
    }

    private boolean requestIsValid(String name, String login, String password, String address, String postIndexStr) {
        return name != null && !name.isEmpty() &&
                login != null && !login.isEmpty() &&
                password != null && !password.isEmpty() &&
                address != null && !address.isEmpty() &&
                postIndexStr != null && !postIndexStr.isEmpty();
    }
}

