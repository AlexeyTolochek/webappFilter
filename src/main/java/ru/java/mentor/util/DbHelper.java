package ru.java.mentor.util;

import com.mysql.cj.jdbc.Driver;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.java.mentor.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static ru.java.mentor.util.ReaderProperty.readProperty;

public class DbHelper {

    private static SessionFactory sessionFactory;

    private DbHelper() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                sessionFactory = createSessionFactory();
            } catch (ExceptionFromReadMethod e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }


    private static Configuration getConfiguration() throws ExceptionFromReadMethod {
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
        configuration.addAnnotatedClass(User.class);
        configuration.setProperty("hibernate.dialect", readProperty("hibernate.dialect"));
        configuration.setProperty("hibernate.connection.driver_class", readProperty("hibernate.connection.driver_class"));
        configuration.setProperty("hibernate.connection.url", readProperty("hibernate.connection.url"));
        configuration.setProperty("hibernate.connection.username", readProperty("hibernate.connection.username"));
        configuration.setProperty("hibernate.connection.password", readProperty("hibernate.connection.password"));
        configuration.setProperty("hibernate.show_sql", readProperty("hibernate.show_sql"));
        configuration.setProperty("hibernate.hbm2ddl.auto", readProperty("hibernate.hbm2ddl.auto"));
        return configuration;
    }

    private static SessionFactory createSessionFactory() throws ExceptionFromReadMethod {
        Configuration configuration = getConfiguration();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public static Connection getJDBCConnection() {
        Connection connection = null;
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.cj.jdbc.Driver").newInstance());
            connection = DriverManager.getConnection(readProperty("jdbc.connection.url"),
                    readProperty("jdbc.connection.user"),
                    readProperty("jdbc.connection.password"));
        } catch (SQLException | IllegalAccessException | ClassNotFoundException | ExceptionFromReadMethod | InstantiationException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return connection;
    }

    public static Connection getDefaultConnection() {
        Connection connection = null;
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.cj.jdbc.Driver").newInstance());
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?serverTimezone=UTC",
                    "testuser",
                    "testuser");
        } catch (SQLException | IllegalAccessException | ClassNotFoundException | InstantiationException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return connection;
    }
}
