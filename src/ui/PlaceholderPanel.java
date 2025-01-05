package ui;

import javax.swing.*;
import java.awt.*;

// PlaceholderPanel is a simple panel to show when a feature is not yet implemented.
public class PlaceholderPanel extends JPanel {
    private JLabel messageLabel;

    // Constructor
    public PlaceholderPanel(String message) {
        setLayout(new BorderLayout());
        messageLabel = new JLabel(message, JLabel.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(messageLabel, BorderLayout.CENTER);
    }
}
