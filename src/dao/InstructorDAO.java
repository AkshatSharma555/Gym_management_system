package dao;

import model.Instructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InstructorDAO {
    private Connection connection;

    public InstructorDAO(Connection connection) {
        this.connection = connection;
    }

    // Method to add a new instructor
    public void addInstructor(Instructor instructor) throws SQLException {
        String sql = "INSERT INTO instructors (name, email, phone, hire_date, specialization) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, instructor.getName());
            pstmt.setString(2, instructor.getEmail());
            pstmt.setString(3, instructor.getPhone());
            pstmt.setDate(4, instructor.getHireDate());
            pstmt.setString(5, instructor.getSpecialization());
            pstmt.executeUpdate();
        }
    }

    // Method to update an instructor
    public void updateInstructor(Instructor instructor) throws SQLException {
        String sql = "UPDATE instructors SET name=?, email=?, phone=?, hire_date=?, specialization=? WHERE id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, instructor.getName());
            pstmt.setString(2, instructor.getEmail());
            pstmt.setString(3, instructor.getPhone());
            pstmt.setDate(4, instructor.getHireDate());
            pstmt.setString(5, instructor.getSpecialization());
            pstmt.setInt(6, instructor.getId());
            pstmt.executeUpdate();
        }
    }

    // Method to delete an instructor
    public void deleteInstructor(int instructorId) throws SQLException {
        String sql = "DELETE FROM instructors WHERE id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, instructorId);
            pstmt.executeUpdate();
        }
    }

    // Method to get all instructors
    public List<Instructor> getAllInstructors() throws SQLException {
        List<Instructor> instructors = new ArrayList<>();
        String sql = "SELECT * FROM instructors";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Instructor instructor = new Instructor(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getDate("hire_date"),
                        rs.getString("specialization")
                );
                instructors.add(instructor);
            }
        }
        return instructors;
    }
}
