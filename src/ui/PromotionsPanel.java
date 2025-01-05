package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PromotionsPanel extends JPanel {
    private JLabel imageLabel;
    private ImageIcon promoImage;

    public PromotionsPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245)); // Light background color

        // Load the promotional image
        promoImage = new ImageIcon("C:\\Users\\Admin\\Documents\\GYM management system\\GymManagementSystem\\src\\PromotionPic.jpg");

        // Create a label for the image
        imageLabel = new JLabel();
        imageLabel.setIcon(promoImage);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);

        // Create a scroll pane to allow scrolling if the image is larger than the panel
        JScrollPane scrollPane = new JScrollPane(imageLabel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null); // Remove border from the scroll pane

        // Add the scroll pane to the panel
        add(scrollPane, BorderLayout.CENTER);

        // Set the size of the panel
        setPreferredSize(new Dimension(600, 400)); // Adjust this size as needed

        // Start fade-in animation
        startFadeInAnimation();
    }

    private void startFadeInAnimation() {
        // Create a timer to gradually change the opacity
        Timer timer = new Timer(50, new ActionListener() {
            float opacity = 0.0f;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (opacity < 1.0f) {
                    opacity += 0.05f; // Increase opacity
                    imageLabel.setOpaque(true);
                    imageLabel.setBackground(new Color(255, 255, 255, (int) (opacity * 255))); // Set background with alpha
                } else {
                    ((Timer) e.getSource()).stop(); // Stop the timer when fully opaque
                }
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Scale the image to fill the entire panel
        if (promoImage != null) {
            Image img = promoImage.getImage();
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
