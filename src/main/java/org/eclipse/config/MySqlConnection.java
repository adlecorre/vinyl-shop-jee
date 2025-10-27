package org.eclipse.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/bd_vinyle?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    private static final String USER = "app";
    private static final String PASSWORD = "app";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Pilote JDBC MySQL introuvable : " + e.getMessage());
        }
    }

    private MySqlConnection() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
