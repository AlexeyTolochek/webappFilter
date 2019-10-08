package ru.java.mentor.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private final String page = "WEB-INF/view/index.jsp";
    private final String userPage = "WEB-INF/view/userPage.jsp";
    private final String invalidData = "Please enter correct data";


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        request.getRequestDispatcher(userPage).forward(request, response);

    }
}


