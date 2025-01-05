package ui;

import dao.AttendanceDAO;
import model.Attendance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class ManageAttendancePanel extends JPanel {
    private Connection connection;
    private AttendanceDAO attendanceDAO;
    private JTextField txtMemberId, txtClassId;
    private JTextField txtAttendanceDate;
    private JTextArea textAreaAttendance;

    public ManageAttendancePanel(Connection connection) {
        this.connection = connection;
        this.attendanceDAO = new AttendanceDAO(connection);
        setLayout(new BorderLayout());
        setBackground(Color.LIGHT_GRAY); // Set background color

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.DARK_GRAY);
        JLabel headerLabel = new JLabel("Manage Attendance");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Form panel for adding attendance
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding

        gbc.gridx = 0; gbc.gridy = 0; // Member ID
        formPanel.add(new JLabel("Member ID:"), gbc);
        txtMemberId = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 0;
        formPanel.add(txtMemberId, gbc);

        gbc.gridx = 0; gbc.gridy = 1; // Class ID
        formPanel.add(new JLabel("Class ID:"), gbc);
        txtClassId = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 1;
        formPanel.add(txtClassId, gbc);

        gbc.gridx = 0; gbc.gridy = 2; // Attendance Date
        formPanel.add(new JLabel("Attendance Date (yyyy-MM-dd):"), gbc);
        txtAttendanceDate = new JTextField(LocalDate.now().toString(), 15);
        gbc.gridx = 1; gbc.gridy = 2;
        formPanel.add(txtAttendanceDate, gbc);

        // Buttons
        JButton btnAddAttendance = new JButton("Add Attendance");
        btnAddAttendance.setBackground(Color.GREEN);
        btnAddAttendance.setForeground(Color.BLACK);
        btnAddAttendance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAttendance();
            }
        });
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(btnAddAttendance, gbc);

        JButton btnViewAttendance = new JButton("View Attendance");
        btnViewAttendance.setBackground(Color.BLUE);
        btnViewAttendance.setForeground(Color.WHITE);
        btnViewAttendance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAttendance();
            }
        });
        gbc.gridx = 1; gbc.gridy = 3;
        formPanel.add(btnViewAttendance, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Text area to display attendance records
        textAreaAttendance = new JTextArea();
        textAreaAttendance.setEditable(false);
        textAreaAttendance.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(textAreaAttendance);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Attendance Records"));

        // Set preferred size for the scroll pane to make it larger
        scrollPane.setPreferredSize(new Dimension(600, 300)); // Adjust width and height as needed

        add(scrollPane, BorderLayout.SOUTH);
    }

    private void addAttendance() {
        Attendance attendance = new Attendance();
        attendance.setMemberId(Integer.parseInt(txtMemberId.getText()));
        attendance.setClassId(Integer.parseInt(txtClassId.getText()));
        attendance.setAttendanceDate(LocalDate.parse(txtAttendanceDate.getText()));

        if (attendanceDAO.addAttendance(attendance)) {
            JOptionPane.showMessageDialog(this, "Attendance added successfully!");
            clearFields();
            viewAttendance(); // Refresh the list of attendance records
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add attendance.");
        }
    }

    private void viewAttendance() {
        List<Attendance> attendanceRecords = attendanceDAO.getAllAttendance();
        textAreaAttendance.setText("");
        for (Attendance attendance : attendanceRecords) {
            textAreaAttendance.append("ID: " + attendance.getId() +
                    ", Member ID: " + attendance.getMemberId() +
                    ", Class ID: " + attendance.getClassId() +
                    ", Date: " + attendance.getAttendanceDate() + "\n");
        }
    }

    private void clearFields() {
        txtMemberId.setText("");
        txtClassId.setText("");
        txtAttendanceDate.setText(LocalDate.now().toString());
    }
}
