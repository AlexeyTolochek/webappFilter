package ru.java.mentor.service;

import ru.java.mentor.factory.AbstractDaoFactory;
import ru.java.mentor.factory.DAO;
import ru.java.mentor.model.User;

import java.sql.SQLException;
import java.util.List;

public class UserService implements DAO {
    private static UserService instance;
    private static DAO dao;

    private UserService() {
    }

    public static UserService getInstance() {
        if (instance == null || dao == null) {
            instance = new UserService();
            dao = AbstractDaoFactory.getInstance().getDAO();
        }
        return instance;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        try {
            users = dao.getAllUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void addUser(User user) {
        try {
            dao.addUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editUser(User user) {
        try {
            dao.editUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteUser(User user) {
        try {
            dao.deleteUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropTable() {
        try {
            dao.dropTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createTable() {
        try {
            dao.createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean validateUser(String login, String password)  {
        try {
            if (dao.validateUser(login, password)) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isExistLogin(String login) {
        try {
            if (dao.isExistLogin(login)) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User getUserById(Long id) {
        User user = null;
        try {
            user = dao.getUserById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User getUserByLogin(String login) {
        User user = null;
        try {
            user = dao.getUserByLogin(login);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
