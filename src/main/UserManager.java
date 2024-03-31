/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author upgra
 */
public class UserManager {
    private Connection connection;

    public UserManager(Connection connection) {
        this.connection = connection;
    }
	
	
	
	// method to add a new user to the database
    public void addUser(String username, String password, String role) {
        try {
            String query = "INSERT INTO User (username, password, role) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, role);
            statement.executeUpdate();
            System.out.println("User added successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to add user.");
            e.printStackTrace();
        }
    }
}
