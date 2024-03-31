/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // URL for the MySQL database connection
    private static final String URL = "jdbc:mysql://localhost:3306/cms";
    
    // Username for the MySQL database connection
    private static final String USERNAME = "admin";
    
    // Password for the MySQL database connection
    private static final String PASSWORD = "java";
    
    // Connection object to hold the database connection
    private Connection connection;
    try {
    /* load the MySQL JDBC driver
    this is necessary to establish a connection to the MySQL database */
    Class.forName("com.mysql.cj.jdbc.Driver");

    /* establish the database connection
    create a connection to the database using the provided URL, username, and password */
    connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			
    // print a message indicating successful connection
    System.out.println("Connected to the database.");
    } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        }
}

// method to get the database connection
    public Connection getConnection() {
        return connection;
    }

// method to close the database connection
    public void closeConnection() {
        try {
			// check if the connection is not null and not already closed
            if (connection != null && !connection.isClosed()) {
				// close the database connection
                connection.close();
                System.out.println("Disconnected from the database.");
            }
        } catch (SQLException e) {
			// handle the exception if there is an error closing the connection
            System.out.println("Failed to close the database connection.");
            e.printStackTrace();
        }
    }