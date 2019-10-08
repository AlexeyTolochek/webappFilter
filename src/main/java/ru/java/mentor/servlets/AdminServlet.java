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
import java.util.List;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private final String page = "/WEB-INF/view/admin.jsp";


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService service = UserService.getInstance();
        response.setContentType("text/html");
        String idStr = request.getParameter("id");
        String edit = request.getParameter("edit");
        String delete = request.getParameter("delete");
        Long id;

        if (delete != null) {
            id = Long.parseLong(idStr);
            User user = service.getUserById(id);
            if (user != null) {
                service.deleteUser(user);
            }
        }
        if (edit != null) {
            id = Long.parseLong(idStr);
            User user = service.getUserById(id);
            request.setAttribute("userEdit", user);
        }

        List<User> list = service.getAllUsers();

        request.setAttribute("list", list);
        request.getServletContext().getRequestDispatcher(page).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF8");
        final String idStr = req.getParameter("id");
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

            if (action != null) {
                if (action.equalsIgnoreCase("add")) {
                    service.addUser(user);
                }
                if (action.equalsIgnoreCase("edit")) {
                    user.setId(Long.parseLong(idStr));
                    service.editUser(user);
                }
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

