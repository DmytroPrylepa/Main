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
public class CourseReport {
        private Connection connection;

    public CourseReport(Connection connection) {
        this.connection = connection;
    }

    public void generateReport(String format) {
        try {
            // fetch course data from the database
            String query = "SELECT c.course_name, c.programme, c.enrolled_students, c.lecturer, c.room " +
                           "FROM Course c";
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

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Failed to generate course report.");
            e.printStackTrace();
        }
    }

	// method to generate the course report in TXT format
    private void generateTxtReport(ResultSet resultSet) throws SQLException {
        try (FileWriter writer = new FileWriter("course_report.txt")) {
            while (resultSet.next()) {
                String courseName = resultSet.getString("course_name");
                String programme = resultSet.getString("programme");
                int enrolledStudents = resultSet.getInt("enrolled_students");
                String lecturer = resultSet.getString("lecturer");
                String room = resultSet.getString("room");

                writer.write("Course: " + courseName + "\n");
                writer.write("Programme: " + programme + "\n");
                writer.write("Enrolled Students: " + enrolledStudents + "\n");
                writer.write("Lecturer: " + lecturer + "\n");
                writer.write("Room: " + room + "\n");
                writer.write("------------------------\n");
            }
            System.out.println("Course report generated successfully (course_report.txt).");
        } catch (IOException e) {
            System.out.println("Failed to generate TXT report.");
            e.printStackTrace();
        }
    }
}
