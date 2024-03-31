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
public class StudentReport {
    private Connection connection;

    public StudentReport(Connection connection) {
        this.connection = connection;
    }

    public void generateReport(String format) {
        try {
            // fetch student data from the database
            String query = "SELECT s.student_name, s.student_number, s.programme, " +
                           "GROUP_CONCAT(e.course_id) AS enrolled_modules, " +
                           "GROUP_CONCAT(g.course_id, ':', g.grade_value) AS completed_modules, " +
                           "GROUP_CONCAT(DISTINCT r.course_id) AS modules_to_repeat " +
                           "FROM Student s " +
                           "LEFT JOIN Enrollment e ON s.student_id = e.student_id " +
                           "LEFT JOIN Grade g ON s.student_id = g.student_id " +
                           "LEFT JOIN (SELECT student_id, course_id FROM Grade WHERE grade_value < 50) r ON s.student_id = r.student_id " +
                           "GROUP BY s.student_id";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            // generate the report based on the selected format
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
			
			// close the result set and statement
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Failed to generate student report.");
            e.printStackTrace();
        }
    }

	// method to generate the student report in TXT format
    private void generateTxtReport(ResultSet resultSet) throws SQLException {
        try (FileWriter writer = new FileWriter("student_report.txt")) {
            while (resultSet.next()) {
                String studentName = resultSet.getString("student_name");
                String studentNumber = resultSet.getString("student_number");
                String programme = resultSet.getString("programme");
                String enrolledModules = resultSet.getString("enrolled_modules");
                String completedModules = resultSet.getString("completed_modules");
                String modulesToRepeat = resultSet.getString("modules_to_repeat");

                writer.write("Student: " + studentName + "\n");
                writer.write("Student Number: " + studentNumber + "\n");
                writer.write("Programme: " + programme + "\n");
                writer.write("Enrolled Modules: " + enrolledModules + "\n");
                writer.write("Completed Modules: " + completedModules + "\n");
                writer.write("Modules to Repeat: " + modulesToRepeat + "\n");
                writer.write("------------------------\n");
            }
            System.out.println("Student report generated successfully (student_report.txt).");
        } catch (IOException e) {
            System.out.println("Failed to generate TXT report.");
            e.printStackTrace();
        }
    }
	
	// method to generate the student report in CSV format
    private void generateCsvReport(ResultSet resultSet) throws SQLException {
        try (FileWriter writer = new FileWriter("student_report.csv")) {
            writer.write("Student,Student Number,Programme,Enrolled Modules,Completed Modules,Modules to Repeat\n");
            while (resultSet.next()) {
                String studentName = resultSet.getString("student_name");
                String studentNumber = resultSet.getString("student_number");
                String programme = resultSet.getString("programme");
                String enrolledModules = resultSet.getString("enrolled_modules");
                String completedModules = resultSet.getString("completed_modules");
                String modulesToRepeat = resultSet.getString("modules_to_repeat");

                writer.write(studentName + "," + studentNumber + "," + programme + "," +
                             enrolledModules + "," + completedModules + "," + modulesToRepeat + "\n");
            }
            System.out.println("Student report generated successfully (student_report.csv).");
        } catch (IOException e) {
            System.out.println("Failed to generate CSV report.");
            e.printStackTrace();
        }
    }


	// method to generate the course report and display it in the console
    private void generateConsoleReport(ResultSet resultSet) throws SQLException {
        System.out.println("Student Report:");
        System.out.println("------------------------");
        while (resultSet.next()) {
            String studentName = resultSet.getString("student_name");
            String studentNumber = resultSet.getString("student_number");
            String programme = resultSet.getString("programme");
            String enrolledModules = resultSet.getString("enrolled_modules");
            String completedModules = resultSet.getString("completed_modules");
            String modulesToRepeat = resultSet.getString("modules_to_repeat");

            System.out.println("Student: " + studentName);
            System.out.println("Student Number: " + studentNumber);
            System.out.println("Programme: " + programme);
            System.out.println("Enrolled Modules: " + enrolledModules);
            System.out.println("Completed Modules: " + completedModules);
            System.out.println("Modules to Repeat: " + modulesToRepeat);
            System.out.println("------------------------");
        }
    }
}
