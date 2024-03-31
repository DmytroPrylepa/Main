/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

/**
 *
 * @author upgra
 */
public class User {
    private int userId;
    private String username;
    private String password;
    private String role;

    // constructor to initialize the User object with user details
    public User(int userId, String username, String password, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // getter method to retrieve the user ID
    public int getUserId() {
        return userId;
    }
	
	// setter method to set the user ID
    public void setUserId(int userId) {
        this.userId = userId;
    }

	// getter method to retrieve the username
    public String getUsername() {
        return username;
    }
	
	// setter method to set the username
    public void setUsername(String username) {
        this.username = username;
    }
	
	// getter method to retrieve the password
    public String getPassword() {
        return password;
    }

	// setter method to set the password
    public void setPassword(String password) {
        this.password = password;
    }

	 // getter method to retrieve the user role
    public String getRole() {
        return role;
    }
	
	//method to set the user role
    public void setRole(String role) {
        this.role = role;
    }

    // override the toString method to provide a string representation of the User object
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
