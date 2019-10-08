package ru.java.mentor.factory;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.java.mentor.model.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HibernateDao implements DAO {

    private static HibernateDao instance;
    private SessionFactory sessionFactory;

    private HibernateDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    static HibernateDao getInstance(SessionFactory sessionFactory) {
        if (instance == null) {
            instance = new HibernateDao(sessionFactory);
        }
        return instance;
    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from User");
        List<User> list = new ArrayList<>(query.list());
        session.getTransaction().commit();
        session.close();
        return list;
    }

    @Override
    public void addUser(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void editUser(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deleteUser(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void dropTable() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("delete from User");
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void createTable() {

    }

    @Override
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

    @Override
    public User getUserById(Long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from User where id=:id")
                .setParameter("id", id);
        User user = (User) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return user;
    }

    public User getUserByLogin(String login) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from User where login=:login")
                .setParameter("login", login);
        User user = (User) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return user;
    }
}