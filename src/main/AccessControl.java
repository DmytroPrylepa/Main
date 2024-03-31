/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

/**
 *
 * @author upgra
 */
public class AccessControl {
    public static final String ADMIN_ROLE = "admin";
    public static final String OFFICE_ROLE = "office";
    public static final String LECTURER_ROLE = "lecturer";

    private UserManager userManager;

    public AccessControl(UserManager userManager) {
        this.userManager = userManager;
    }

    public boolean isAdmin(String username) {
        User user = userManager.findUserByUsername(username);
        return user != null && user.getRole().equals(ADMIN_ROLE);
    }

    public boolean isOffice(String username) {
        User user = userManager.findUserByUsername(username);
        return user != null && user.getRole().equals(OFFICE_ROLE);
    }

    public boolean isLecturer(String username) {
        User user = userManager.findUserByUsername(username);
        return user != null && user.getRole().equals(LECTURER_ROLE);
    }

    public boolean canGenerateReport(String username) {
        return isOffice(username);
    }

    public boolean canManageUsers(String username) {
        return isAdmin(username);
    }

    public boolean canModifyOwnDetails(String username) {
        return isAdmin(username) || isOffice(username) || isLecturer(username);
    }

    public boolean canGenerateLecturerReport(String username) {
        return isLecturer(username);
    }

    public boolean authenticateUser(String username, String password) {
        return userManager.authenticateUser(username, password);
    }
}
