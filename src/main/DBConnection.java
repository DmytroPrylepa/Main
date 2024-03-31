/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



/**
 *
 * @author upgra
 */
public class DBConnection {
private static final String URL = "jdbc:mysql://localhost:3306/cms";
private static final String USERNAME = "admin";
private static final String PASSWORD = "java";

private Connection connection;

public DBConnection() {

	try {
	// load the MySQL JDBC driver
	Class.forName("com.mysql.cj.jdbc.Driver");
	// establish the database connection
	connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
	System.out.println("Connected to the database.");
	} catch (ClassNotFoundException e) {
	System.out.println("MySQL JDBC driver not found.");
	e.printStackTrace();
	} catch (SQLException e) {
	System.out.println("Failed to connect to the database.");
	e.printStackTrace();
	}
}

public Connection getConnection() {
return connection;
}

public void closeConnection() {
	try {
	if (connection != null && !connection.isClosed()) {
	connection.close();
	System.out.println("Disconnected from the database.");
	}
	} catch (SQLException e) {
	System.out.println("Failed to close the database connection.");
	e.printStackTrace();
    }
}
}
