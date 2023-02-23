package jm.task.core.jdbc.service;

import jm.task.core.jdbc.Main;
import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
//    UserDao userDao = new UserDaoJDBCImpl();
    UserDaoHibernateImpl userDaoHibernate = new UserDaoHibernateImpl();


    public void createUsersTable() {

       userDaoHibernate.createUsersTable();
    }

    public void dropUsersTable() {
        userDaoHibernate.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {

        userDaoHibernate.saveUser(name,lastName,age);

    }

    public void removeUserById(long id) {
        userDaoHibernate.removeUserById(id);

    }

    public List<User> getAllUsers() {
        //System.out.println(userDaoHibernate.getAllUsers());
        return userDaoHibernate.getAllUsers();
    }

    public void cleanUsersTable() {
        userDaoHibernate.cleanUsersTable();

    }
}
