package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    Connection connection = Util.getConnection();
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String createTable = "create table if not exists users ( " +
                "user_id bigint not null primary key auto_increment," +
                "first_name varchar(50)," +
                "last_name varchar(50)," +
                "age integer );";
        try( PreparedStatement preparedStatement =  connection.prepareStatement(createTable)) {
            preparedStatement.execute();
            System.out.println("Table was created");
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        String deleteTable = "drop table if exists users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteTable)) {
            preparedStatement.execute();
            System.out.println("Table users was deleted");
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
        } catch (Exception e ) {
            transaction.rollback();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class,id);
            session.remove(user);
            transaction.commit();
        } catch (Exception e ) {
            transaction.rollback();
        }

    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> listUser = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            List<User> list = session.createQuery("from User ").getResultList();
            listUser = list;
            transaction.commit();
        } catch (Exception e ) {
            transaction.rollback();
        }finally {
            return listUser;
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            List<User> list = session.createQuery("from User ").getResultList();
            for (User u : list) {
                session.remove(u);
            }
            transaction.commit();
        } catch (Exception e ) {
            transaction.rollback();
        }
    }
}
