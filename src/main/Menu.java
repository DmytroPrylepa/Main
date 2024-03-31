/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.util.Scanner;

/**
 *
 * @author upgra
 */
public class Menu {
    private UserManager userManager;
    private AccessControl accessControl;
    private Scanner scanner;

    public Menu(UserManager userManager, AccessControl accessControl) {
        this.userManager = userManager;
        this.accessControl = accessControl;
        this.scanner = new Scanner(System.in);
    }
	
	// method to display the menu and handle user interactions
    public void displayMenu() {
        String username = null;
        boolean authenticated = false;

        while (true) {
            if (!authenticated) {
                System.out.println("=== Course Management System ===");
                System.out.println("1. Login");
                System.out.println("2. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        username = loginUser();
                        authenticated = username != null;
                        break;
                    case 2:
                        System.out.println("Exiting the program...");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } else {
                System.out.println("=== Course Management System ===");
                System.out.println("Logged in as: " + username);
                System.out.println("1. Generate Reports");
                System.out.println("2. Manage Users");
                System.out.println("3. Change Password");
                System.out.println("4. Logout");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        generateReports(username);
                        break;
                    case 2:
                        manageUsers(username);
                        break;
                    case 3:
                        changePassword(username);
                        break;
                    case 4:
                        System.out.println("Logging out...");
                        authenticated = false;
                        username = null;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }
    
    // method to handle user login
    private String loginUser() {
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        if (accessControl.authenticateUser(username, password)) {
            System.out.println("Login successful.");
            return username;
        } else {
            System.out.println("Invalid username or password.");
            return null;
        }
    }
	
	// method to handle report generation based on user permissions
    private void generateReports(String username) {
        if (accessControl.canGenerateReport(username)) {
            System.out.println("=== Generate Reports ===");
            System.out.println("1. Course Report");
            System.out.println("2. Student Report");
            System.out.println("3. Lecturer Report");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {	
                case 1:
                    System.out.print("Enter report format (txt/csv/console): ");
                    String courseFormat = scanner.next();
                    CourseReport courseReport = new CourseReport(userManager.getConnection());
                    courseReport.generateReport(courseFormat);
                    break;
                case 2:
                    System.out.print("Enter report format (txt/csv/console): ");
                    String studentFormat = scanner.next();
                    StudentReport studentReport = new StudentReport(userManager.getConnection());
                    studentReport.generateReport(studentFormat);
                    break;
                case 3:
                    if (accessControl.canGenerateLecturerReport(username)) {
                        System.out.print("Enter report format (txt/csv/console): ");
                        String lecturerFormat = scanner.next();
                        LecturerReport lecturerReport = new LecturerReport(userManager.getConnection());
                        lecturerReport.generateReport(lecturerFormat, username);
                    } else {
                        System.out.println("You don't have permission to generate lecturer reports.");
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } else {
            System.out.println("You don't have permission to generate reports.");
        }
    }
}
