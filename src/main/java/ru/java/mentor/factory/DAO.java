package ru.java.mentor.factory;

import ru.java.mentor.model.User;

import java.sql.SQLException;
import java.util.List;

public interface DAO {

    List<User> getAllUsers() throws SQLException;

    User getUserById(Long id) throws SQLException;

    User getUserByLogin(String login) throws SQLException;

    void addUser(User user) throws SQLException;

    void editUser(User user) throws SQLException;

    void deleteUser(User user) throws SQLException;

    void dropTable() throws SQLException;

    void createTable() throws SQLException;

    boolean validateUser(String login, String password) throws SQLException;

    boolean isExistLogin(String login) throws SQLException;
}
