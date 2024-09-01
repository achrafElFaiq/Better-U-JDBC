package com.example.geniustask1;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private Connection connection;
    private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jjdbc:mysql://localhost:3306/geniusTask";
    private static final String USERNAME = "achraf";
    private static final String PASSWORD = "achrafA123*";

    public Connection getConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/geniusTask", "achraf", "achrafA123*");
            System.out.println("Connection to database successful!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return connection;
    }
}