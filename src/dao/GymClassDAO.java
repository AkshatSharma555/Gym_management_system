package dao;

import model.GymClass;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GymClassDAO {
    private Connection connection;

    // Constructor to initialize the connection
    public GymClassDAO(Connection connection) {
        this.connection = connection;
    }

    // Method to add a new class record to the database
    public boolean addClass(GymClass gymClass) {
        String sql = "INSERT INTO classes (class_name, instructor_id, schedule, duration, max_participants, description) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, gymClass.getClassName());
            pstmt.setInt(2, gymClass.getInstructorId());
            pstmt.setTimestamp(3, Timestamp.valueOf(gymClass.getSchedule())); // Convert LocalDateTime to Timestamp
            pstmt.setInt(4, gymClass.getDuration());
            pstmt.setInt(5, gymClass.getMaxParticipants());
            pstmt.setString(6, gymClass.getDescription());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Return true if insertion was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to retrieve all class records from the database
    public List<GymClass> getAllClasses() {
        List<GymClass> classesList = new ArrayList<>();
        String sql = "SELECT * FROM classes";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                GymClass gymClass = new GymClass();
                gymClass.setId(rs.getInt("id"));
                gymClass.setClassName(rs.getString("class_name"));
                gymClass.setInstructorId(rs.getInt("instructor_id"));
                gymClass.setSchedule(rs.getTimestamp("schedule").toLocalDateTime());
                gymClass.setDuration(rs.getInt("duration"));
                gymClass.setMaxParticipants(rs.getInt("max_participants"));
                gymClass.setDescription(rs.getString("description"));
                classesList.add(gymClass);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classesList;
    }

    // Method to update an existing class record in the database
    public boolean updateClass(GymClass gymClass) {
        String sql = "UPDATE classes SET class_name = ?, instructor_id = ?, schedule = ?, duration = ?, max_participants = ?, description = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, gymClass.getClassName());
            pstmt.setInt(2, gymClass.getInstructorId());
            pstmt.setTimestamp(3, Timestamp.valueOf(gymClass.getSchedule())); // Convert LocalDateTime to Timestamp
            pstmt.setInt(4, gymClass.getDuration());
            pstmt.setInt(5, gymClass.getMaxParticipants());
            pstmt.setString(6, gymClass.getDescription());
            pstmt.setInt(7, gymClass.getId());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Return true if update was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to delete a class record by its ID
    public boolean deleteClass(int classId) {
        String sql = "DELETE FROM classes WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, classId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Return true if deletion was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
