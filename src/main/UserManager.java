/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    
    // method to update an existing user in the database
    public void updateUser(String username, String newPassword, String newRole) {
        try {
            String query = "UPDATE User SET password = ?, role = ? WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newPassword);
            statement.setString(2, newRole);
            statement.setString(3, username);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User updated successfully.");
            } else {
                System.out.println("User not found.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to update user.");
            e.printStackTrace();
        }
    }
    
    // method to delete a user from the database
    public void deleteUser(String username) {
        try {
            String query = "DELETE FROM User WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User deleted successfully.");
            } else {
                System.out.println("User not found.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to delete user.");
            e.printStackTrace();
        }
    }
    // method to find a user by their username
    public User findUserByUsername(String username) {
        try {
            String query = "SELECT * FROM User WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                return new User(userId, username, password, role);
            }
        } catch (SQLException e) {
            System.out.println("Failed to find user.");
            e.printStackTrace();
        }
        return null;
    }
	
	// method to change the password of a user
    public void changePassword(String username, String newPassword) {
        try {
            String query = "UPDATE User SET password = ? WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newPassword);
            statement.setString(2, username);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Password changed successfully.");
            } else {
                System.out.println("User not found.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to change password.");
            e.printStackTrace();
        }
    }
	
	// method to authenticate a user based on username and password
    public boolean authenticateUser(String username, String password) {
        try {
            String query = "SELECT * FROM User WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            System.out.println("Failed to authenticate user.");
            e.printStackTrace();
        }
        return false;
    }
	
	// method to get the database connection object
    public Connection getConnection() {
        return connection;
    }
}
