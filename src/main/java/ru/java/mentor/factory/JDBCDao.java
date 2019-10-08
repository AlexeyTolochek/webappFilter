package ru.java.mentor.factory;

import ru.java.mentor.model.User;
import ru.java.mentor.model.UserRole;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JDBCDao implements DAO {
    private static JDBCDao instance;
    private Connection connection;

    private JDBCDao(Connection connection) {
        this.connection = connection;
    }

    static JDBCDao getInstance(Connection connection) {
        if (instance == null) {
            instance = new JDBCDao(connection);
        }
        return instance;
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> list = new ArrayList<>();
        Statement stmt = connection.createStatement();
        stmt.execute("select * from users");
        ResultSet result = stmt.getResultSet();
        while (result.next()) {
            long id = result.getLong("id");
            String name = result.getNString("name");
            String login = result.getNString("login");
            String pass = result.getNString("password");
            String address = result.getNString("address");
            Date birthdateSQL = result.getDate("birthdate");
            LocalDate birthdate = birthdateSQL.toLocalDate();
            UserRole role = UserRole.valueOf(result.getNString("role"));
            User user = new User(id, name, login, pass, address, birthdate, role);
            list.add(user);
        }
        result.close();
        stmt.close();
        return list;
    }

    @Override
    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO users(name, login, password, address, birthdate, role) values (?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.getName());
        statement.setString(2, user.getLogin());
        statement.setString(3, user.getPassword());
        statement.setString(4, user.getAddress());
        statement.setDate(5, Date.valueOf(user.getbirthdate()));
        statement.setString(6, String.valueOf(user.getRole()));
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public void editUser(User user) throws SQLException {
        String sql = "UPDATE users set name=?, login=?, password=?, address=?, birthdate=?, role=? where id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.getName());
        statement.setString(2, user.getLogin());
        statement.setString(3, user.getPassword());
        statement.setString(4, user.getAddress());
        statement.setDate(5, Date.valueOf(user.getbirthdate()));
        statement.setString(6, String.valueOf(user.getRole()));
        statement.setLong(7, user.getId());
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public void deleteUser(User user) throws SQLException {
        long id = user.getId();
        String sql = "DELETE FROM users WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, id);
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public void dropTable() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DROP TABLE IF EXISTS users");
        stmt.close();
    }


    @Override
    public User getUserById(Long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from users where id=?");
        preparedStatement.setLong(1, id);
        ResultSet result = preparedStatement.executeQuery();
        result.next();
        String name = result.getNString("name");
        String login = result.getNString("login");
        String pass = result.getNString("password");
        String address = result.getNString("address");
        Date birthdateSQL = result.getDate("birthdate");
        LocalDate birthdate = birthdateSQL.toLocalDate();
        UserRole role = UserRole.valueOf(result.getNString("role"));
        User user = new User(id, name, login, pass, address, birthdate, role);
        result.close();
        preparedStatement.close();
        return user;
    }

    public boolean validateUser(String login, String password) throws SQLException {
        User user = getUserByLogin(login);
        if (user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isExistLogin(String login) throws SQLException {
        User user = getUserByLogin(login);
        if (user!=null) {
            return true;
        }
        return false;
    }


    public User getUserByLogin(String login) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from users where login=?");
        preparedStatement.setString(1, login);
        ResultSet result = preparedStatement.executeQuery();
        result.next();
        long id = result.getLong("id");
        String name = result.getNString("name");
        String pass = result.getNString("password");
        String address = result.getNString("address");
        Date birthdateSQL = result.getDate("birthdate");
        LocalDate birthdate = birthdateSQL.toLocalDate();
        UserRole role = UserRole.valueOf(result.getNString("role"));
        User user = new User(id, name, login, pass, address, birthdate, role);
        result.close();
        preparedStatement.close();
        return user;
    }

    @Override
    public void createTable() throws SQLException {
        Statement stmt = connection.createStatement();
        /*
        stmt.execute("create table if not exists users (id bigint auto_increment,  " +
                "name varchar(256)," +
                "login varchar(20)," +
                "password varchar(256)," +
                "address varchar(20)," +
                "birthdate data," +
                "role varchar(20)," +
                "primary key (id))");
                */
        stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                "id BIGINT AUTO_INCREMENT, " +
                "name VARCHAR (256), " +
                "login VARCHAR (20), " +
                "password VARCHAR (256), " +
                "address VARCHAR (20), " +
                "birthdate DATE, " +
                "role VARCHAR (20), " +
                "primary key (id)" +
                ")");
        stmt.close();
    }
}
