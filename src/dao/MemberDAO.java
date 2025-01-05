package dao;

import model.Member;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
    private Connection connection;

    public MemberDAO(Connection connection) {
        this.connection = connection;
    }

    // Method to add a new member
    public void addMember(Member member) throws SQLException {
        String sql = "INSERT INTO members (name, email, phone, membership_type, join_date, status, address, date_of_birth, gender) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, member.getName());
            pstmt.setString(2, member.getEmail());
            pstmt.setString(3, member.getPhone());
            pstmt.setString(4, member.getMembershipType());
            pstmt.setDate(5, member.getJoinDate());
            pstmt.setString(6, member.getStatus());
            pstmt.setString(7, member.getAddress());
            pstmt.setDate(8, member.getDateOfBirth());
            pstmt.setString(9, member.getGender());
            pstmt.executeUpdate();
        }
    }

    // Method to update a member
    public void updateMember(Member member) throws SQLException {
        String sql = "UPDATE members SET name=?, email=?, phone=?, membership_type=?, join_date=?, status=?, address=?, date_of_birth=?, gender=? WHERE id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, member.getName());
            pstmt.setString(2, member.getEmail());
            pstmt.setString(3, member.getPhone());
            pstmt.setString(4, member.getMembershipType());
            pstmt.setDate(5, member.getJoinDate());
            pstmt.setString(6, member.getStatus());
            pstmt.setString(7, member.getAddress());
            pstmt.setDate(8, member.getDateOfBirth());
            pstmt.setString(9, member.getGender());
            pstmt.setInt(10, member.getId());
            pstmt.executeUpdate();
        }
    }

    // Method to delete a member
    public void deleteMember(int memberId) throws SQLException {
        String sql = "DELETE FROM members WHERE id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, memberId);
            pstmt.executeUpdate();
        }
    }

    // Method to get all members
    public List<Member> getAllMembers() throws SQLException {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM members";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Member member = new Member(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("membership_type"),
                        rs.getDate("join_date"),
                        rs.getString("status"),
                        rs.getString("address"),
                        rs.getDate("date_of_birth"),
                        rs.getString("gender")
                );
                members.add(member);
            }
        }
        return members;
    }
}
