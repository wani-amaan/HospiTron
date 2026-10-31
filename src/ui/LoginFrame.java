package ui;

import controller.HospitalController;
import java.awt.*;
import javax.swing.*;
import security.AuthService;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private final AuthService auth = new AuthService();

    public LoginFrame() {

        setTitle("HospiTron Login");
        setSize(350, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel title = new JLabel("HOSPITRON LOGIN");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setBounds(90, 10, 200, 30);
        add(title);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(30, 50, 100, 25);
        add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(120, 50, 160, 25);
        add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(30, 85, 100, 25);
        add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(120, 85, 160, 25);
        add(passwordField);

        JButton loginBtn = new JButton("LOGIN");
        loginBtn.setBounds(120, 130, 100, 30);
        add(loginBtn);

        loginBtn.addActionListener(e -> {

            String u = usernameField.getText();
            String p = new String(passwordField.getPassword());

            if (auth.login(u, p)) {

                JOptionPane.showMessageDialog(this, "Login Successful");

                new DashboardFrame(new HospitalController());

                dispose();

            } else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials");
            }
        });

        setVisible(true);
    }
}