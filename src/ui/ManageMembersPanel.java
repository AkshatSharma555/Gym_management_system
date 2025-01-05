package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import dao.MemberDAO;
import model.Member;

public class ManageMembersPanel extends JPanel {
    private JTable membersTable;
    private DefaultTableModel tableModel;
    private MemberDAO memberDAO;

    public ManageMembersPanel(Connection connection) {
        this.memberDAO = new MemberDAO(connection);
        setLayout(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel("Manage Members", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Create the table with column names
        String[] columnNames = {
                "ID", "Name", "Email", "Phone", "Membership Type",
                "Join Date", "Status", "Address", "Date of Birth", "Gender"
        };
        tableModel = new DefaultTableModel(columnNames, 0);
        membersTable = new JTable(tableModel);
        membersTable.setFillsViewportHeight(true); // Make table fill the viewport

        // Add the table to a scroll pane
        add(new JScrollPane(membersTable), BorderLayout.CENTER);

        // Create a button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, 10, 0)); // 3 buttons with 10px spacing
        JButton btnAdd = createButton("Add Member");
        JButton btnUpdate = createButton("Update Member");
        JButton btnDelete = createButton("Delete Member");

        // Add action listeners
        btnAdd.addActionListener(e -> addMember());
        btnUpdate.addActionListener(e -> updateMember());
        btnDelete.addActionListener(e -> deleteMember());

        // Add buttons to the panel
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);

        // Add button panel to the bottom of the main panel
        add(buttonPanel, BorderLayout.SOUTH);

        // Set background color for aesthetics
        setBackground(Color.LIGHT_GRAY);
        buttonPanel.setBackground(Color.GRAY);

        loadMembers(); // Load existing members into the table
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(Color.BLUE);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEtchedBorder());
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(120, 40)); // Set a larger button size
        return button;
    }

    private void loadMembers() {
        try {
            List<Member> members = memberDAO.getAllMembers();
            for (Member member : members) {
                tableModel.addRow(new Object[]{
                        member.getId(),
                        member.getName(),
                        member.getEmail(),
                        member.getPhone(),
                        member.getMembershipType(),
                        member.getJoinDate(),
                        member.getStatus(),
                        member.getAddress(),
                        member.getDateOfBirth(),
                        member.getGender()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading members: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addMember() {
        // Create input fields for member details
        JTextField nameField = new JTextField(15);
        JTextField emailField = new JTextField(15);
        JTextField phoneField = new JTextField(15);
        String[] membershipTypes = {"monthly", "yearly", "lifetime"};
        JComboBox<String> membershipComboBox = new JComboBox<>(membershipTypes);
        JTextField joinDateField = new JTextField(15);
        String[] statuses = {"active", "inactive"};
        JComboBox<String> statusComboBox = new JComboBox<>(statuses);
        JTextField addressField = new JTextField(15);
        JTextField dobField = new JTextField(15);
        String[] genders = {"male", "female", "other"};
        JComboBox<String> genderComboBox = new JComboBox<>(genders);

        // Create a panel for input fields
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Email:"));
        inputPanel.add(emailField);
        inputPanel.add(new JLabel("Phone:"));
        inputPanel.add(phoneField);
        inputPanel.add(new JLabel("Membership Type:"));
        inputPanel.add(membershipComboBox);
        inputPanel.add(new JLabel("Join Date (YYYY-MM-DD):"));
        inputPanel.add(joinDateField);
        inputPanel.add(new JLabel("Status:"));
        inputPanel.add(statusComboBox);
        inputPanel.add(new JLabel("Address:"));
        inputPanel.add(addressField);
        inputPanel.add(new JLabel("Date of Birth (YYYY-MM-DD):"));
        inputPanel.add(dobField);
        inputPanel.add(new JLabel("Gender:"));
        inputPanel.add(genderComboBox);

        int result = JOptionPane.showConfirmDialog(this, inputPanel, "Enter Member Details", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                String email = emailField.getText();
                String phone = phoneField.getText();
                String membershipType = (String) membershipComboBox.getSelectedItem();
                Date joinDate = Date.valueOf(joinDateField.getText());
                String status = (String) statusComboBox.getSelectedItem();
                String address = addressField.getText();
                Date dateOfBirth = Date.valueOf(dobField.getText());
                String gender = (String) genderComboBox.getSelectedItem();

                Member member = new Member(0, name, email, phone, membershipType, joinDate, status, address, dateOfBirth, gender);
                memberDAO.addMember(member);

                tableModel.setRowCount(0);
                loadMembers();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error adding member: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateMember() {
        int selectedRow = membersTable.getSelectedRow();
        if (selectedRow >= 0) {
            int memberId = (int) membersTable.getValueAt(selectedRow, 0);

            JTextField nameField = new JTextField((String) membersTable.getValueAt(selectedRow, 1));
            JTextField emailField = new JTextField((String) membersTable.getValueAt(selectedRow, 2));
            JTextField phoneField = new JTextField((String) membersTable.getValueAt(selectedRow, 3));
            String[] membershipTypes = {"monthly", "yearly", "lifetime"};
            JComboBox<String> membershipComboBox = new JComboBox<>(membershipTypes);
            membershipComboBox.setSelectedItem(membersTable.getValueAt(selectedRow, 4));
            JTextField joinDateField = new JTextField(membersTable.getValueAt(selectedRow, 5).toString());
            String[] statuses = {"active", "inactive"};
            JComboBox<String> statusComboBox = new JComboBox<>(statuses);
            statusComboBox.setSelectedItem(membersTable.getValueAt(selectedRow, 6));
            JTextField addressField = new JTextField((String) membersTable.getValueAt(selectedRow, 7));
            JTextField dobField = new JTextField(membersTable.getValueAt(selectedRow, 8).toString());
            String[] genders = {"male", "female", "other"};
            JComboBox<String> genderComboBox = new JComboBox<>(genders);
            genderComboBox.setSelectedItem(membersTable.getValueAt(selectedRow, 9));

            JPanel inputPanel = new JPanel(new GridLayout(0, 2, 10, 10));
            inputPanel.add(new JLabel("Name:"));
            inputPanel.add(nameField);
            inputPanel.add(new JLabel("Email:"));
            inputPanel.add(emailField);
            inputPanel.add(new JLabel("Phone:"));
            inputPanel.add(phoneField);
            inputPanel.add(new JLabel("Membership Type:"));
            inputPanel.add(membershipComboBox);
            inputPanel.add(new JLabel("Join Date (YYYY-MM-DD):"));
            inputPanel.add(joinDateField);
            inputPanel.add(new JLabel("Status:"));
            inputPanel.add(statusComboBox);
            inputPanel.add(new JLabel("Address:"));
            inputPanel.add(addressField);
            inputPanel.add(new JLabel("Date of Birth (YYYY-MM-DD):"));
            inputPanel.add(dobField);
            inputPanel.add(new JLabel("Gender:"));
            inputPanel.add(genderComboBox);

            int result = JOptionPane.showConfirmDialog(this, inputPanel, "Update Member Details", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    String name = nameField.getText();
                    String email = emailField.getText();
                    String phone = phoneField.getText();
                    String membershipType = (String) membershipComboBox.getSelectedItem();
                    Date joinDate = Date.valueOf(joinDateField.getText());
                    String status = (String) statusComboBox.getSelectedItem();
                    String address = addressField.getText();
                    Date dateOfBirth = Date.valueOf(dobField.getText());
                    String gender = (String) genderComboBox.getSelectedItem();

                    Member member = new Member(memberId, name, email, phone, membershipType, joinDate, status, address, dateOfBirth, gender);
                    memberDAO.updateMember(member);

                    tableModel.setRowCount(0);
                    loadMembers();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error updating member: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a member to update.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteMember() {
        int selectedRow = membersTable.getSelectedRow();
        if (selectedRow >= 0) {
            int memberId = (int) membersTable.getValueAt(selectedRow, 0);
            int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this member?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                try {
                    memberDAO.deleteMember(memberId);
                    tableModel.setRowCount(0);
                    loadMembers();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error deleting member: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a member to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }
}
