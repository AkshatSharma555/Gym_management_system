package dao;

import model.Feedback;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAO {
    private Connection connection;

    public FeedbackDAO(Connection connection) {
        this.connection = connection;
    }

    // Method to add feedback
    public boolean addFeedback(Feedback feedback) {
        String sql = "INSERT INTO feedback (member_id, feedback_text, rating, feedback_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, feedback.getMemberId());
            pstmt.setString(2, feedback.getFeedbackText());
            pstmt.setInt(3, feedback.getRating());
            pstmt.setDate(4, feedback.getFeedbackDate()); // Using java.sql.Date directly
            return pstmt.executeUpdate() > 0; // Returns true if the row was added
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false in case of error
        }
    }

    // Method to retrieve all feedback records
    public List<Feedback> getAllFeedback() {
        List<Feedback> feedbackList = new ArrayList<>();
        String sql = "SELECT * FROM feedback";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Feedback feedback = new Feedback(
                        rs.getInt("id"),
                        rs.getInt("member_id"),
                        rs.getString("feedback_text"),
                        rs.getInt("rating"),
                        rs.getDate("feedback_date") // Using java.sql.Date
                );
                feedbackList.add(feedback);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedbackList; // Return the list of feedback records
    }
}
