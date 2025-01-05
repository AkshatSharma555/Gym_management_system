package dao;

import model.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {
    private Connection connection;

    public PaymentDAO(Connection connection) {
        this.connection = connection;
    }

    // Method to add a new payment
    public boolean addPayment(Payment payment) {
        String sql = "INSERT INTO payments (member_id, amount, payment_date, payment_method, transaction_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, payment.getMemberId());
            statement.setDouble(2, payment.getAmount());
            statement.setDate(3, Date.valueOf(payment.getPaymentDate())); // Convert LocalDate to SQL Date
            statement.setString(4, payment.getPaymentMethod());
            statement.setString(5, payment.getTransactionId());

            return statement.executeUpdate() > 0; // Return true if the insert was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to get all payments
    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payments";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Payment payment = new Payment();
                payment.setId(resultSet.getInt("id"));
                payment.setMemberId(resultSet.getInt("member_id"));
                payment.setAmount(resultSet.getDouble("amount"));
                payment.setPaymentDate(resultSet.getDate("payment_date").toLocalDate()); // Convert SQL Date to LocalDate
                payment.setPaymentMethod(resultSet.getString("payment_method"));
                payment.setTransactionId(resultSet.getString("transaction_id"));

                payments.add(payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }
}
