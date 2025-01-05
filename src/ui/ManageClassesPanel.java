package ui;

import dao.GymClassDAO;
import model.GymClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ManageClassesPanel extends JPanel {
    private Connection connection;
    private GymClassDAO gymClassDAO;
    private JTextField txtClassName, txtInstructorId, txtDuration, txtMaxParticipants, txtDescription;
    private JFormattedTextField txtSchedule;
    private JTextArea textAreaClasses;

    public ManageClassesPanel(Connection connection) {
        this.connection = connection;
        this.gymClassDAO = new GymClassDAO(connection);
        setLayout(new BorderLayout());

        // Form for adding/updating classes
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(7, 2, 10, 10)); // Improved grid layout with gaps

        formPanel.add(new JLabel("Class Name:"));
        txtClassName = new JTextField();
        formPanel.add(txtClassName);

        formPanel.add(new JLabel("Instructor ID:"));
        txtInstructorId = new JTextField();
        formPanel.add(txtInstructorId);

        formPanel.add(new JLabel("Schedule (yyyy-MM-dd HH:mm):"));
        txtSchedule = new JFormattedTextField();
        txtSchedule.setValue(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        formPanel.add(txtSchedule);

        formPanel.add(new JLabel("Duration (minutes):"));
        txtDuration = new JTextField();
        formPanel.add(txtDuration);

        formPanel.add(new JLabel("Max Participants:"));
        txtMaxParticipants = new JTextField();
        formPanel.add(txtMaxParticipants);

        formPanel.add(new JLabel("Description:"));
        txtDescription = new JTextField();
        formPanel.add(txtDescription);

        JButton btnAddClass = new JButton("Add Class");
        btnAddClass.addActionListener(e -> addClass());
        formPanel.add(btnAddClass);

        JButton btnViewClasses = new JButton("View Classes");
        btnViewClasses.addActionListener(e -> viewClasses());
        formPanel.add(btnViewClasses);

        // Add padding and borders
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(formPanel, BorderLayout.NORTH);

        // Text area to display classes
        textAreaClasses = new JTextArea();
        textAreaClasses.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textAreaClasses);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void addClass() {
        GymClass gymClass = new GymClass();
        gymClass.setClassName(txtClassName.getText());
        gymClass.setInstructorId(Integer.parseInt(txtInstructorId.getText()));
        gymClass.setSchedule(LocalDateTime.parse(txtSchedule.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        gymClass.setDuration(Integer.parseInt(txtDuration.getText()));
        gymClass.setMaxParticipants(Integer.parseInt(txtMaxParticipants.getText()));
        gymClass.setDescription(txtDescription.getText());

        if (gymClassDAO.addClass(gymClass)) {
            JOptionPane.showMessageDialog(this, "Class added successfully!");
            clearFields();
            viewClasses(); // Refresh the list of classes
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add class.");
        }
    }

    private void viewClasses() {
        List<GymClass> classes = gymClassDAO.getAllClasses();
        textAreaClasses.setText("");
        for (GymClass gymClass : classes) {
            textAreaClasses.append(gymClass.getId() + ": " + gymClass.getClassName() + " (Instructor ID: " + gymClass.getInstructorId() + ")\n");
        }
    }

    private void clearFields() {
        txtClassName.setText("");
        txtInstructorId.setText("");
        txtDuration.setText("");
        txtMaxParticipants.setText("");
        txtDescription.setText("");
        txtSchedule.setValue(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }
}
