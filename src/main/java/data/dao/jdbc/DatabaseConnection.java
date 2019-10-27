package data.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection conn;

    public static DatabaseConnection getInstance() throws ClassNotFoundException, SQLException {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    private DatabaseConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        final String URL = "jdbc:mysql://localhost:3306/mdvsp";
        final String USER = "root";
        final String PASS = "admin";
        conn = DriverManager.getConnection(URL, USER, PASS);
    }

    public Connection getConn() {
        return conn;
    }
}
