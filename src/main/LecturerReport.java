/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author upgra
 */
public class LecturerReport {
      private Connection connection;

    public LecturerReport(Connection connection) {
        this.connection = connection;
    }

    public void generateReport(String format, String lecturerName) {
        try {
            // Fetch lecturer data from the database
            String query = "SELECT l.lecturer_name, l.role, l.modules_teaching, " +
                           "SUM(c.enrolled_students) AS total_students, l.class_types " +
                           "FROM Lecturer l " +
                           "LEFT JOIN Course c ON FIND_IN_SET(c.course_id, REPLACE(l.modules_teaching, ' ', '')) " +
                           "WHERE l.lecturer_name = ? " +
                           "GROUP BY l.lecturer_id";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, lecturerName);
            ResultSet resultSet = statement.executeQuery();

            // Generate the report based on the selected format
            switch (format) {
                case "txt":
                    generateTxtReport(resultSet);
                    break;
                case "csv":
                    generateCsvReport(resultSet);
                    break;
                case "console":
                    generateConsoleReport(resultSet);
                    break;
                default:
                    System.out.println("Invalid report format.");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Failed to generate lecturer report.");
            e.printStackTrace();
        }
    }
	
	// method to generate the lecturer report in txt format
    private void generateTxtReport(ResultSet resultSet) throws SQLException {
        try (FileWriter writer = new FileWriter("lecturer_report.txt")) {
            while (resultSet.next()) {
                String lecturerName = resultSet.getString("lecturer_name");
                String role = resultSet.getString("role");
                String modulesTeaching = resultSet.getString("modules_teaching");
                int totalStudents = resultSet.getInt("total_students");
                String classTypes = resultSet.getString("class_types");

                writer.write("Lecturer: " + lecturerName + "\n");
                writer.write("Role: " + role + "\n");
                writer.write("Modules Teaching: " + modulesTeaching + "\n");
                writer.write("Total Students: " + totalStudents + "\n");
                writer.write("Class Types: " + classTypes + "\n");
                writer.write("------------------------\n");
            }
            System.out.println("Lecturer report generated successfully (lecturer_report.txt).");
        } catch (IOException e) {
            System.out.println("Failed to generate TXT report.");
            e.printStackTrace();
        }
    }
	
	// method to generate the lecturer report in CSV format
    private void generateCsvReport(ResultSet resultSet) throws SQLException {
        try (FileWriter writer = new FileWriter("lecturer_report.csv")) {
            writer.write("Lecturer,Role,Modules Teaching,Total Students,Class Types\n");
            while (resultSet.next()) {
                String lecturerName = resultSet.getString("lecturer_name");
                String role = resultSet.getString("role");
                String modulesTeaching = resultSet.getString("modules_teaching");
                int totalStudents = resultSet.getInt("total_students");
                String classTypes = resultSet.getString("class_types");

                writer.write(lecturerName + "," + role + "," + modulesTeaching + "," +
                             totalStudents + "," + classTypes + "\n");
            }
            System.out.println("Lecturer report generated successfully (lecturer_report.csv).");
        } catch (IOException e) {
            System.out.println("Failed to generate CSV report.");
            e.printStackTrace();
        }
    }


	// method to generate the course report and display it in the console
    private void generateConsoleReport(ResultSet resultSet) throws SQLException {
        System.out.println("Lecturer Report:");
        System.out.println("------------------------");
        while (resultSet.next()) {
            String lecturerName = resultSet.getString("lecturer_name");
            String role = resultSet.getString("role");
            String modulesTeaching = resultSet.getString("modules_teaching");
            int totalStudents = resultSet.getInt("total_students");
            String classTypes = resultSet.getString("class_types");

            System.out.println("Lecturer: " + lecturerName);
            System.out.println("Role: " + role);
            System.out.println("Modules Teaching: " + modulesTeaching);
            System.out.println("Total Students: " + totalStudents);
            System.out.println("Class Types: " + classTypes);
            System.out.println("------------------------");
        }
    }
}
