package jm.task.core.jdbc.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Savepoint;


public class Util {
    // реализуйте настройку соединения с БД
    private final String URL = "jdbc:mysql://localhost:3306/JMDatabaseTask_1_1_3";
    private final String USERNAME = "root";
    private final String PASSWORD = "bangkok7";
    private Savepoint savepoint;

    private Connection connection;

    public Util(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("can not register DbDriver");
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            connection.setAutoCommit(false);// USING TRANSACTIONS
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public Connection getConnection() {
        return connection;
    }

    public void close(){
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void commit(){
        try {
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Savepoint setSavepoint() {
        try {
            savepoint = connection.setSavepoint();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return savepoint;
    }

    public void rollback(Savepoint savepoint) throws SQLException{
        connection.rollback(savepoint);
    }
}
