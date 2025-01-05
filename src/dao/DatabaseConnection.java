package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() throws SQLException {
        // Replace with your actual database credentials
        String url = "jdbc:mysql://localhost:3306/gym_management";
        String username = "root"; // Replace with your actual username
        String password = "Akshat@555"; // Replace with your actual password

        return DriverManager.getConnection(url, username, password);
    }
}
