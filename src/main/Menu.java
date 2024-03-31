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
    
    // method to handle user management based on user permissions
    private void manageUsers(String username) {
        if (accessControl.canManageUsers(username)) {
            System.out.println("=== Manage Users ===");
            System.out.println("1. Add User");
            System.out.println("2. Update User");
            System.out.println("3. Delete User");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String newUsername = scanner.next();
                    System.out.print("Enter password: ");
                    String newPassword = scanner.next();
                    System.out.print("Enter role (admin/office/lecturer): ");
                    String newRole = scanner.next();
                    userManager.addUser(newUsername, newPassword, newRole);
                    break;
                case 2:
                    System.out.print("Enter username: ");
                    String updateUsername = scanner.next();
                    System.out.print("Enter new password: ");
                    String updatePassword = scanner.next();
                    System.out.print("Enter new role (admin/office/lecturer): ");
                    String updateRole = scanner.next();
                    userManager.updateUser(updateUsername, updatePassword, updateRole);
                    break;
                case 3:
                    System.out.print("Enter username: ");
                    String deleteUsername = scanner.next();
                    userManager.deleteUser(deleteUsername);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } else {
            System.out.println("You don't have permission to manage users.");
        }
    }
	
	// method to handle password change for the logged-in user
    private void changePassword(String username) {
        if (accessControl.canModifyOwnDetails(username)) {
            System.out.print("Enter new password: ");
            String newPassword = scanner.next();
            userManager.changePassword(username, newPassword);
        } else {
            System.out.println("You don't have permission to change your password.");
        }
    
 }
}
