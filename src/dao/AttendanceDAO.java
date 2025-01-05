package dao;

import model.Attendance;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {
    private Connection connection;

    public AttendanceDAO(Connection connection) {
        this.connection = connection;
    }

    // Method to add attendance
    public boolean addAttendance(Attendance attendance) {
        String sql = "INSERT INTO attendance (member_id, class_id, attendance_date) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, attendance.getMemberId());
            stmt.setInt(2, attendance.getClassId());
            stmt.setDate(3, Date.valueOf(attendance.getAttendanceDate()));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to get all attendance records
    public List<Attendance> getAllAttendance() {
        List<Attendance> attendanceList = new ArrayList<>();
        String sql = "SELECT * FROM attendance";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Attendance attendance = new Attendance(rs.getInt("member_id"), rs.getInt("class_id"), rs.getDate("attendance_date").toLocalDate());
                attendance.setId(rs.getInt("id"));
                attendanceList.add(attendance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attendanceList;
    }
}
