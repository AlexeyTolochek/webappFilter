package ru.java.mentor.servlets;

import ru.java.mentor.model.User;
import ru.java.mentor.service.UserService;
import ru.java.mentor.util.ExceptionFromReadMethod;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

import static ru.java.mentor.util.ReaderProperty.readProperty;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        try {
            request.getRequestDispatcher(readProperty("pageReg")).forward(request, response);
        } catch (ExceptionFromReadMethod exceptionFromReadMethod) {
            exceptionFromReadMethod.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF8");
        final String name = req.getParameter("name");
        final String login = req.getParameter("login");
        final String password = req.getParameter("password");
        final String address = req.getParameter("address");
        String birthdateStr = req.getParameter("birthdate");
        LocalDate birthdate = null;
        try {
            birthdate = LocalDate.parse(birthdateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (requestIsValid(name, login, password, address, birthdateStr) && birthdate != null) {
            UserService service = UserService.getInstance();


            final User user = new User(name, login, password, address, birthdate);

            if (!service.isExistLogin(login)) {
                service.addUser(user);
                try {
                    req.setAttribute("message", readProperty("trueAdd"));
                } catch (ExceptionFromReadMethod exceptionFromReadMethod) {
                    exceptionFromReadMethod.printStackTrace();
                }
            } else {
                try {
                    req.setAttribute("message", readProperty("falseAdd"));
                } catch (ExceptionFromReadMethod exceptionFromReadMethod) {
                    exceptionFromReadMethod.printStackTrace();
                }
            }

        } else {
            try {
                req.setAttribute("message", readProperty("invalidData"));
            } catch (ExceptionFromReadMethod exceptionFromReadMethod) {
                exceptionFromReadMethod.printStackTrace();
            }
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

