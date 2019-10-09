package ru.java.mentor.servlets;

import ru.java.mentor.model.User;
import ru.java.mentor.util.ExceptionFromReadMethod;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static ru.java.mentor.util.ReaderProperty.readProperty;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String message = (String) session.getAttribute("message");

        if (user != null) {
            request.setAttribute("nameUser", user.getName());
            request.setAttribute("birthdateUser", user.getbirthdate());
        }

        if (message != null) {
            request.setAttribute("message", message);
        }
        response.setContentType("text/html");
        try {
            request.getRequestDispatcher(readProperty("userPage")).forward(request, response);
        } catch (ExceptionFromReadMethod exceptionFromReadMethod) {
            exceptionFromReadMethod.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}


