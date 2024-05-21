package com.example.todolist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dbconnect {

    private static String Host = "127.0.0.1";
    private static int port = 3306;
    private static String DB_Name = "todolist";
    private static String username = "root";
    private static String password = "";
    private static Connection connection;

    public static Connection getConnection() {
        try {
            String url = String.format("jdbc:mysql://%s:%d/%s", Host, port, DB_Name);
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
