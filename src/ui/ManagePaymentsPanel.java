package ui;

import dao.PaymentDAO;
import model.Payment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class ManagePaymentsPanel extends JPanel {
    private Connection connection;
    private PaymentDAO paymentDAO;
    private JTextField txtMemberId, txtAmount, txtTransactionId;
    private JComboBox<String> cbPaymentMethod;
    private JTextField txtPaymentDate;
    private JTextArea textAreaPayments;

    public ManagePaymentsPanel(Connection connection) {
        this.connection = connection;
        this.paymentDAO = new PaymentDAO(connection);
        setLayout(new BorderLayout());
        setBackground(new Color(230, 230, 250)); // Light lavender background

        // Header
        JLabel headerLabel = new JLabel("Manage Payments", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(72, 61, 139)); // Dark slate blue
        add(headerLabel, BorderLayout.NORTH);

        // Form panel for adding payments
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(6, 2, 10, 10)); // Add gaps between rows and columns
        formPanel.setBackground(new Color(255, 255, 255)); // White background
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the form

        formPanel.add(new JLabel("Member ID:"));
        txtMemberId = new JTextField();
        formPanel.add(txtMemberId);

        formPanel.add(new JLabel("Amount:"));
        txtAmount = new JTextField();
        formPanel.add(txtAmount);

        formPanel.add(new JLabel("Payment Method:"));
        cbPaymentMethod = new JComboBox<>(new String[]{"credit_card", "cash", "bank_transfer", "digital_wallet"});
        formPanel.add(cbPaymentMethod);

        formPanel.add(new JLabel("Transaction ID:"));
        txtTransactionId = new JTextField();
        formPanel.add(txtTransactionId);

        formPanel.add(new JLabel("Payment Date (yyyy-MM-dd):"));
        txtPaymentDate = new JTextField(LocalDate.now().toString());
        formPanel.add(txtPaymentDate);

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton btnAddPayment = new JButton("Add Payment");
        btnAddPayment.setBackground(new Color(50, 205, 50)); // Lime green
        btnAddPayment.setForeground(Color.WHITE);
        btnAddPayment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPayment();
            }
        });
        buttonPanel.add(btnAddPayment);

        JButton btnViewPayments = new JButton("View Payments");
        btnViewPayments.setBackground(new Color(70, 130, 180)); // Steel blue
        btnViewPayments.setForeground(Color.WHITE);
        btnViewPayments.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewPayments();
            }
        });
        buttonPanel.add(btnViewPayments);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Text area to display payment records
        textAreaPayments = new JTextArea();
        textAreaPayments.setEditable(false);
        textAreaPayments.setFont(new Font("Arial", Font.PLAIN, 14));
        textAreaPayments.setLineWrap(true);
        textAreaPayments.setWrapStyleWord(true);
        textAreaPayments.setBackground(new Color(240, 255, 255)); // Light cyan background

        JScrollPane scrollPane = new JScrollPane(textAreaPayments);
        scrollPane.setPreferredSize(new Dimension(400, 300)); // Adjusting size for better visibility
        add(scrollPane, BorderLayout.EAST);
    }

    private void addPayment() {
        try {
            Payment payment = new Payment();
            payment.setMemberId(Integer.parseInt(txtMemberId.getText()));
            payment.setAmount(Double.parseDouble(txtAmount.getText()));
            payment.setPaymentMethod(cbPaymentMethod.getSelectedItem().toString());
            payment.setTransactionId(txtTransactionId.getText());
            payment.setPaymentDate(LocalDate.parse(txtPaymentDate.getText()));

            if (paymentDAO.addPayment(payment)) {
                JOptionPane.showMessageDialog(this, "Payment added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                viewPayments(); // Refresh the list of payment records
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add payment.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please check your entries.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewPayments() {
        List<Payment> paymentRecords = paymentDAO.getAllPayments();
        textAreaPayments.setText("");
        for (Payment payment : paymentRecords) {
            textAreaPayments.append("ID: " + payment.getId() +
                    ", Member ID: " + payment.getMemberId() +
                    ", Amount: " + payment.getAmount() +
                    ", Method: " + payment.getPaymentMethod() +
                    ", Transaction ID: " + payment.getTransactionId() +
                    ", Date: " + payment.getPaymentDate() + "\n");
        }
    }

    private void clearFields() {
        txtMemberId.setText("");
        txtAmount.setText("");
        txtTransactionId.setText("");
        txtPaymentDate.setText(LocalDate.now().toString());
    }
}
