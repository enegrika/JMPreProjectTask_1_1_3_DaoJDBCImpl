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

    public Util() throws SQLException {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            System.out.println("can not register DbDriver");
//        }
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        connection.setAutoCommit(false);// USING TRANSACTIONS
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() throws SQLException {
        connection.close();
    }

    public void commit() throws SQLException {
        connection.commit();
    }

    public Savepoint setSavepoint() throws SQLException {
        savepoint = connection.setSavepoint();
        return savepoint;
    }

    public void rollback(Savepoint savepoint) throws SQLException{
        connection.rollback(savepoint);
    }
}
