/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



/**
 *
 * @author upgra
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Database connection details
       
        String url = "jdbc:mysql://localhost:3306/cms";
        String username = "admin";
        String password = "java";

        try {
        /* establish the database connection
	use the DriverManager to get a connection to the database with the provided details */
        Connection connection = DriverManager.getConnection(url, username, password);

        /* create instances of UserManager, AccessControl, and Menu
        UserManager handles user-related operations and interacts with the database */
            UserManager userManager = new UserManager(connection);
            AccessControl accessControl = new AccessControl(userManager);
            Menu menu = new Menu(userManager, accessControl);

            /* display the menu
            start the interactive menu system for the user to navigate and perform actions */
            menu.displayMenu();

            /* close the database connection
            release the resources and close the connection to the database */
            connection.close();
        } catch (SQLException e) {
			// handle any SQLException that may occur during database operations
            System.out.println("Database connection error.");
			 // print the stack trace for debugging purposes
            e.printStackTrace();
        }
    }
    }
    

