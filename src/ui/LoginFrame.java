package ui;

import controller.HospitalController;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import security.AuthService;

/**
 * Modern Java login window with authentication verification.
 */
public final class LoginFrame extends JFrame {

    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final AuthService auth = new AuthService();

    public LoginFrame() {
        setTitle("HospiTron Security Login");
        setSize(360, 240);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(245, 248, 252));

        var title = new JLabel("HOSPITRON LOGIN");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setBounds(95, 12, 200, 30);
        add(title);

        var userLabel = new JLabel("Username:");
        userLabel.setBounds(35, 55, 90, 25);
        add(userLabel);

        usernameField = new JTextField("AMAAN");
        usernameField.setBounds(130, 55, 170, 26);
        add(usernameField);

        var passLabel = new JLabel("Password:");
        passLabel.setBounds(35, 95, 90, 25);
        add(passLabel);

        passwordField = new JPasswordField("12345");
        passwordField.setBounds(130, 95, 170, 26);
        add(passwordField);

        var loginBtn = new JButton("LOGIN");
        loginBtn.setBounds(130, 145, 110, 32);
        loginBtn.setBackground(new Color(41, 128, 185));
        loginBtn.setForeground(Color.WHITE);
        add(loginBtn);

        // Bind both button click and Enter key
        loginBtn.addActionListener(e -> attemptLogin());
        passwordField.addActionListener(e -> attemptLogin());

        setVisible(true);
    }

    private void attemptLogin() {
        var u = usernameField.getText();
        var p = new String(passwordField.getPassword());

        if (auth.login(u, p)) {
            JOptionPane.showMessageDialog(this, "Authentication Successful. Welcome, " + u + "!", "HospiTron Security", JOptionPane.INFORMATION_MESSAGE);
            new DashboardFrame(new HospitalController());
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials. Please verify your username and password.", "Authentication Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}