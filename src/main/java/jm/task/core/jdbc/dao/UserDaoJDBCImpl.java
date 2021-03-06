package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private String tableName = "usersTable";
    private Util connection;
    private Savepoint savepoint;

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try {
            connection = new Util();
            Statement statement = connection.getConnection().createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS "
                    + tableName
                    + " ( id BIGINT auto_increment, name varchar(40) null, lastName varchar(40) null, age TINYINT null, constraint users_pk primary key (id))");

            connection.commit();
            savepoint = connection.setSavepoint(); // SET SAVEPOINT AFTER CREATE NEW TABLE
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("can not create table");
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try {
            connection = new Util();
            PreparedStatement preparedStatement =
                    connection.getConnection()
                            .prepareStatement("DROP TABLE IF EXISTS " + tableName + " ;");
            preparedStatement.executeUpdate();

            connection.commit();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("can not DELETE table");
            e.printStackTrace();
            try {
                connection.rollback(savepoint);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            connection = new Util();

            PreparedStatement preparedStatement = connection.getConnection()
                    .prepareStatement("INSERT INTO " + tableName + " (name, lastName, age) VALUES (?, ?, ?);");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);

            preparedStatement.executeUpdate();
            System.out.println("User ?? ???????????? ??? " + name + " ???????????????? ?? ???????? ????????????");

            connection.commit();

            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("can not ADD User to the table");
            e.printStackTrace();
            try {
                connection.rollback(savepoint);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {

        try {
            connection = new Util();
            PreparedStatement preparedStatement =
                    connection.getConnection()
                            .prepareStatement("DELETE FROM " + tableName + " WHERE id = ? ;");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

            connection.commit();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println(" can not REMOVE USER");
            e.printStackTrace();
            try {
                connection.rollback(savepoint);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }


    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try {
            connection = new Util();
            Statement statement = connection.getConnection().createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName + " ;");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                userList.add(user);
            }

            connection.commit();

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println(" can not REMOVE USER");
            e.printStackTrace();
            try {
                connection.rollback(savepoint);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return userList;
    }


    public void cleanUsersTable() {
        try {
            connection = new Util();
            Statement statement = connection.getConnection().createStatement();
            statement.executeUpdate("DELETE FROM " + tableName + " ;");

            connection.commit();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("can not CLEAN table");
            e.printStackTrace();
            try {
                connection.rollback(savepoint);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
