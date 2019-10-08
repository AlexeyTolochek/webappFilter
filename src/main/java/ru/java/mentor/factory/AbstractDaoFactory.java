package ru.java.mentor.factory;

import org.hibernate.SessionFactory;
import ru.java.mentor.util.DbHelper;
import ru.java.mentor.util.ExceptionFromReadMethod;
import ru.java.mentor.util.ReaderProperty;

import java.sql.Connection;

public class AbstractDaoFactory {
    private static AbstractDaoFactory instance;
    private static DAO dao;

    private AbstractDaoFactory() {
    }

    public static AbstractDaoFactory getInstance() {
        if (instance == null) {
            instance = new AbstractDaoFactory();
        }
        return instance;
    }


    public DAO getDAO() {
        try {
            if (ReaderProperty.readProperty("MethodToConnectDB").equalsIgnoreCase("Hibernate")) {
                SessionFactory sessionFactory = DbHelper.getSessionFactory();
                dao = HibernateDao.getInstance(sessionFactory);


            } else if (ReaderProperty.readProperty("MethodToConnectDB").equalsIgnoreCase("JDBC")) {
                Connection connection = DbHelper.getJDBCConnection();
                dao = JDBCDao.getInstance(connection);
            } else {
                Connection connection = DbHelper.getDefaultConnection();
                dao = JDBCDao.getInstance(connection);
            }


        } catch (ExceptionFromReadMethod e) {
            e.printStackTrace();
        }
        return dao;
    }
}