package ui;

import dao.InstructorDAO;
import model.Instructor;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ManageInstructorPanel extends JPanel {
    private InstructorDAO instructorDAO;

    // GUI components
    private JTextField idField;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField hireDateField;
    private JTextField specializationField;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton displayButton;
    private JTextArea displayArea;

    public ManageInstructorPanel(Connection connection) {
        instructorDAO = new InstructorDAO(connection);
        setLayout(new GridBagLayout()); // Set layout for the panel
        setBackground(new Color(245, 245, 245)); // Light gray background

        initializeComponents();
        layoutComponents();
    }

    private void initializeComponents() {
        idField = new JTextField(5);
        nameField = new JTextField(20);
        emailField = new JTextField(20);
        phoneField = new JTextField(15);
        hireDateField = new JTextField(15); // For hire date input
        specializationField = new JTextField(20);

        addButton = new JButton("Add Instructor");
        updateButton = new JButton("Update Instructor");
        deleteButton = new JButton("Delete Instructor");
        displayButton = new JButton("Display Instructors");
        displayArea = new JTextArea(10, 40);
        displayArea.setEditable(false); // Make display area non-editable

        // Add action listeners for buttons
        addButton.addActionListener(e -> addInstructor());
        updateButton.addActionListener(e -> updateInstructor());
        deleteButton.addActionListener(e -> deleteInstructor());
        displayButton.addActionListener(e -> displayInstructors());

        // Button styling
        styleButtons();
    }

    private void styleButtons() {
        Color buttonColor = new Color(70, 130, 180); // Steel blue color
        Color textColor = Color.WHITE;

        for (JButton button : new JButton[]{addButton, updateButton, deleteButton, displayButton}) {
            button.setBackground(buttonColor);
            button.setForeground(textColor);
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        }
    }

    private void layoutComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding

        // ID Field
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        add(idField, gbc);

        // Name Field
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        add(nameField, gbc);

        // Email Field
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        add(emailField, gbc);

        // Phone Field
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        add(phoneField, gbc);

        // Hire Date Field
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Hire Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        add(hireDateField, gbc);

        // Specialization Field
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Specialization:"), gbc);
        gbc.gridx = 1;
        add(specializationField, gbc);

        // Buttons
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 245)); // Light gray background for buttons
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(displayButton);
        add(buttonPanel, gbc);

        // Display Area
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        add(new JScrollPane(displayArea), gbc);
    }

    private void addInstructor() {
        try {
            Instructor instructor = new Instructor();
            instructor.setName(nameField.getText());
            instructor.setEmail(emailField.getText());
            instructor.setPhone(phoneField.getText());
            instructor.setSpecialization(specializationField.getText());
            instructor.setHireDate(java.sql.Date.valueOf(hireDateField.getText())); // Use hire date from input

            instructorDAO.addInstructor(instructor);
            JOptionPane.showMessageDialog(this, "Instructor added successfully!");

            clearFields(); // Clear the input fields

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error adding instructor: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use YYYY-MM-DD.");
        }
    }

    private void updateInstructor() {
        try {
            int id = Integer.parseInt(idField.getText());
            Instructor instructor = new Instructor();
            instructor.setId(id);
            instructor.setName(nameField.getText());
            instructor.setEmail(emailField.getText());
            instructor.setPhone(phoneField.getText());
            instructor.setSpecialization(specializationField.getText());
            instructor.setHireDate(java.sql.Date.valueOf(hireDateField.getText())); // Use hire date from input

            instructorDAO.updateInstructor(instructor);
            JOptionPane.showMessageDialog(this, "Instructor updated successfully!");

            clearFields(); // Clear the input fields

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error updating instructor: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid ID.");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use YYYY-MM-DD.");
        }
    }

    private void deleteInstructor() {
        try {
            int id = Integer.parseInt(idField.getText());
            instructorDAO.deleteInstructor(id);
            JOptionPane.showMessageDialog(this, "Instructor deleted successfully!");

            clearFields(); // Clear the input fields

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error deleting instructor: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid ID.");
        }
    }

    private void displayInstructors() {
        try {
            List<Instructor> instructors = instructorDAO.getAllInstructors();
            StringBuilder displayText = new StringBuilder("Instructors:\n\n");

            for (Instructor instructor : instructors) {
                displayText.append("ID: ").append(instructor.getId()).append(", ")
                        .append("Name: ").append(instructor.getName()).append(", ")
                        .append("Email: ").append(instructor.getEmail()).append(", ")
                        .append("Phone: ").append(instructor.getPhone()).append(", ")
                        .append("Specialization: ").append(instructor.getSpecialization()).append(", ")
                        .append("Hire Date: ").append(instructor.getHireDate()).append("\n");
            }

            displayArea.setText(displayText.toString());

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error displaying instructors: " + e.getMessage());
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        hireDateField.setText("");
        specializationField.setText("");
    }
}
