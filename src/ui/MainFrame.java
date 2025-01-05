package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class MainFrame extends JFrame {
    private JLabel quoteLabel;
    private JLabel welcomeLabel;
    private String[] motivationalQuotes = {
            "Push yourself, because no one else is going to do it for you.",
            "Success starts with self-discipline.",
            "Don’t stop until you’re proud.",
            "Train like a beast, look like a beauty.",
            "The body achieves what the mind believes.",
            "Hustle for that muscle!",
            "Sweat, Smile, Repeat."
    };
    private int quoteIndex = 0;
    private JPanel mainPanel;
    private Connection connection;
    private BufferedImage backgroundImage;

    public MainFrame() {
        setTitle("OP Gym - Gym Management System");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Establish database connection
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym_management", "root", "Akshat@555");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error connecting to the database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1); // Exit if connection fails
        }

        // Load the background image
        try {
            backgroundImage = ImageIO.read(new File("C:\\Users\\Admin\\Documents\\GYM management system\\GymManagementSystem\\src\\GYMbg.jpg")); // Specify the path to your image
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading background image: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setBackground(new Color(40, 40, 40));
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));

        // Create buttons for each functionality
        JButton btnHome = createStyledButton("Home");
        JButton btnManageMembers = createStyledButton("Manage Members");
        JButton btnManageInstructors = createStyledButton("Manage Instructors");
        JButton btnManageClasses = createStyledButton("Manage Classes");
        JButton btnAttendance = createStyledButton("Attendance");
        JButton btnPayments = createStyledButton("Payments");
        JButton btnFeedback = createStyledButton("Feedback");
        JButton btnPromotions = createStyledButton("Promotions");

        // Add action listeners for buttons
        btnHome.addActionListener(e -> showHomePanel());
        btnManageMembers.addActionListener(e -> showManageMembersPanel());
        btnManageInstructors.addActionListener(e -> showManageInstructorsPanel());
        btnManageClasses.addActionListener(e -> showManageClassesPanel());
        btnAttendance.addActionListener(e -> showManageAttendancePanel());
        btnPayments.addActionListener(e -> showManagePaymentsPanel());
        btnFeedback.addActionListener(e -> showManageFeedbackPanel());
        btnPromotions.addActionListener(e -> showPromotionsPanel());

        // Add buttons to the sidebar panel
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        sidebarPanel.add(btnHome);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        sidebarPanel.add(btnManageMembers);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        sidebarPanel.add(btnManageInstructors);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        sidebarPanel.add(btnManageClasses);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        sidebarPanel.add(btnAttendance);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        sidebarPanel.add(btnPayments);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        sidebarPanel.add(btnFeedback);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        sidebarPanel.add(btnPromotions);

        // Main panel with background image
        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the background image
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setOpaque(false); // Make the main panel transparent

        // Welcome and quote labels
        welcomeLabel = new JLabel("Welcome to OP Gym - Your Fitness Journey Starts Here!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 30)); // Increased font size
        welcomeLabel.setForeground(Color.WHITE); // Change text color to white for better visibility
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        quoteLabel = new JLabel(motivationalQuotes[0], JLabel.CENTER);
        quoteLabel.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 28)); // Increased font size
        quoteLabel.setForeground(Color.WHITE); // Change text color to white for better visibility
        quoteLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        // Add components to main panel
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);
        mainPanel.add(quoteLabel, BorderLayout.CENTER);

        // Quote timer to cycle through motivational quotes
        Timer quoteTimer = new Timer(5000, e -> {
            quoteIndex = (quoteIndex + 1) % motivationalQuotes.length;
            quoteLabel.setText(motivationalQuotes[quoteIndex]);
            animateQuoteLabel(quoteLabel); // Animate the quote change
        });
        quoteTimer.start();

        // Add sidebar and main panel to the frame
        add(sidebarPanel, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);

        // Initial display of the home panel
        showHomePanel();
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 128, 128));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(180, 50));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 150, 150));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 128, 128));
            }
        });

        return button;
    }

    private void animateQuoteLabel(JLabel label) {
        // Fade in effect
        Timer fadeInTimer = new Timer(30, new ActionListener() {
            private float alpha = 0f;

            public void actionPerformed(ActionEvent e) {
                alpha += 0.05f; // Increase alpha
                if (alpha > 1f) {
                    alpha = 1f;
                    ((Timer) e.getSource()).stop();
                }
                label.setForeground(new Color(255, 255, 255, (int) (alpha * 255))); // Set alpha for color
            }
        });
        fadeInTimer.start();
    }

    private void showHomePanel() {
        mainPanel.removeAll();
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);
        mainPanel.add(quoteLabel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showManageMembersPanel() {
        mainPanel.removeAll();
        mainPanel.add(new ManageMembersPanel(connection), BorderLayout.CENTER); // Pass the connection to the panel
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showManageInstructorsPanel() {
        mainPanel.removeAll();
        mainPanel.add(new ManageInstructorPanel(connection), BorderLayout.CENTER); // Pass the connection to the panel
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showManageClassesPanel() {
        mainPanel.removeAll();
        mainPanel.add(new ManageClassesPanel(connection), BorderLayout.CENTER); // Pass the connection to the panel
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showManageAttendancePanel() {
        mainPanel.removeAll();
        mainPanel.add(new ManageAttendancePanel(connection), BorderLayout.CENTER); // Pass the connection to the panel
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showManagePaymentsPanel() {
        mainPanel.removeAll();
        mainPanel.add(new ManagePaymentsPanel(connection), BorderLayout.CENTER); // Pass the connection to the panel
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showManageFeedbackPanel() {
        mainPanel.removeAll();
        mainPanel.add(new ManageFeedbackPanel(connection), BorderLayout.CENTER); // Pass the connection to the panel
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showPromotionsPanel() {
        mainPanel.removeAll();
        mainPanel.add(new PromotionsPanel(), BorderLayout.CENTER); // No argument
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}
