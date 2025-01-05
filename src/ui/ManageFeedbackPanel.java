package ui;

import dao.FeedbackDAO;
import model.Feedback;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public class ManageFeedbackPanel extends JPanel {
    private Connection connection;
    private FeedbackDAO feedbackDAO;
    private JTextField memberIdField;
    private JTextArea feedbackTextArea;
    private JComboBox<Integer> ratingComboBox;
    private JTextArea feedbackDisplayArea;

    public ManageFeedbackPanel(Connection connection) {
        this.connection = connection;
        this.feedbackDAO = new FeedbackDAO(connection);
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(30, 30, 30));
        JLabel titleLabel = new JLabel("Manage Feedback");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBackground(new Color(245, 245, 245));
        inputPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0, 128, 128)),
                "Add Feedback",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Serif", Font.BOLD, 18),
                new Color(0, 128, 128)));

        // Member ID Field
        JLabel memberIdLabel = new JLabel("Member ID:");
        memberIdField = new JTextField(10);
        memberIdField.setPreferredSize(new Dimension(150, 30));
        memberIdField.setBackground(Color.WHITE);
        memberIdField.setBorder(BorderFactory.createLineBorder(new Color(0, 128, 128)));

        // Feedback Text Area
        JLabel feedbackLabel = new JLabel("Feedback:");
        feedbackTextArea = new JTextArea(5, 20);
        feedbackTextArea.setLineWrap(true);
        feedbackTextArea.setWrapStyleWord(true);
        feedbackTextArea.setBackground(Color.WHITE);
        feedbackTextArea.setBorder(BorderFactory.createLineBorder(new Color(0, 128, 128)));

        // Rating Combo Box
        JLabel ratingLabel = new JLabel("Rating (1-5):");
        ratingComboBox = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
        ratingComboBox.setBackground(Color.WHITE);
        ratingComboBox.setBorder(BorderFactory.createLineBorder(new Color(0, 128, 128)));

        // Submit Button
        JButton submitButton = new JButton("Submit Feedback");
        submitButton.setBackground(new Color(0, 128, 128));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setBorderPainted(false);
        submitButton.addActionListener(new SubmitFeedbackAction());

        // Layout for Input Panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(memberIdLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(memberIdField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(feedbackLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(new JScrollPane(feedbackTextArea), gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(ratingLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(ratingComboBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(submitButton, gbc);

        add(inputPanel, BorderLayout.CENTER);

        // Feedback Display Area
        feedbackDisplayArea = new JTextArea(10, 40);
        feedbackDisplayArea.setEditable(false);
        feedbackDisplayArea.setBackground(new Color(220, 220, 220));
        feedbackDisplayArea.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0, 128, 128)),
                "Existing Feedback",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Serif", Font.BOLD, 18),
                new Color(0, 128, 128)));

        // Load existing feedback on panel initialization
        loadFeedback();

        add(new JScrollPane(feedbackDisplayArea), BorderLayout.SOUTH);
    }

    private class SubmitFeedbackAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int memberId = Integer.parseInt(memberIdField.getText());
                String feedbackText = feedbackTextArea.getText();
                int rating = (int) ratingComboBox.getSelectedItem();
                Date feedbackDate = new Date(System.currentTimeMillis()); // Current date

                Feedback feedback = new Feedback(0, memberId, feedbackText, rating, feedbackDate);
                boolean success = feedbackDAO.addFeedback(feedback);
                if (success) {
                    JOptionPane.showMessageDialog(ManageFeedbackPanel.this, "Feedback submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadFeedback(); // Reload feedback after submission
                    memberIdField.setText(""); // Clear the input fields
                    feedbackTextArea.setText("");
                } else {
                    JOptionPane.showMessageDialog(ManageFeedbackPanel.this, "Failed to submit feedback.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ManageFeedbackPanel.this, "Please enter a valid Member ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadFeedback() {
        List<Feedback> feedbackList = feedbackDAO.getAllFeedback();
        StringBuilder feedbackDisplay = new StringBuilder();
        for (Feedback feedback : feedbackList) {
            feedbackDisplay.append("ID: ").append(feedback.getId()).append("\n")
                    .append("Member ID: ").append(feedback.getMemberId()).append("\n")
                    .append("Feedback: ").append(feedback.getFeedbackText()).append("\n")
                    .append("Rating: ").append(feedback.getRating()).append("\n")
                    .append("Date: ").append(feedback.getFeedbackDate()).append("\n")
                    .append("------------------------------------\n");
        }
        feedbackDisplayArea.setText(feedbackDisplay.toString());
    }
}
