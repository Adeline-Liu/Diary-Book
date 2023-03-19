package ui;

import javax.swing.*;
import java.awt.*;

// the welcome page of the app
public class WelcomePage {
    JFrame welcomeFrame;

    public static final int WIDTH = 360;
    public static final int HEIGHT = 240;

    // EFFECTS: constructs the welcome page of the ui
    public WelcomePage() {
        welcomeFrame = new JFrame("Welcome");
        initialize();
    }

    // MODIFIES: this
    // EFFECTS: initializes the frame of the welcome page
    public void initialize() {
        welcomeFrame.setLayout(new BorderLayout());
        welcomeFrame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomeFrame.getContentPane().setLayout(new BorderLayout(10, 10));
        welcomeFrame.setLocationRelativeTo(null);

        addBackground();

        welcomeFrame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: adds visual components of the welcome page
    public void addBackground() {
        welcomeFrame.add(new JLabel(new ImageIcon("data/WelcomeImage.jpg")), BorderLayout.CENTER);

        JButton enter = new JButton("Enter");
        enter.setBackground(new Color(20, 82, 100));
        enter.setForeground(new Color(255, 255, 255));
        enter.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 25));
        welcomeFrame.add(enter, BorderLayout.PAGE_END);

        enter.addActionListener(e -> {
            welcomeFrame.dispose();
            new OptionsPage();
        });
    }
}
