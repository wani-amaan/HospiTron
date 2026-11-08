package ui;

import controller.HospitalController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import security.AuthService;

/**
 * Modern, classy healthcare security login interface.
 */
public final class LoginFrame extends JFrame {

    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final AuthService auth = new AuthService();

    public LoginFrame() {
        setTitle("HospiTron Security Gateway");
        setSize(420, 380);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // Header Banner
        var pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(new Color(15, 23, 42)); // Modern Slate 900
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(20, 20, 18, 20));

        var lblBrand = new JLabel("🏥 HOSPITRON", SwingConstants.CENTER);
        lblBrand.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblBrand.setForeground(new Color(56, 189, 248)); // Sky Blue

        var lblSub = new JLabel("Clinical Management & Resource Optimization", SwingConstants.CENTER);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(new Color(148, 163, 184)); // Slate 400

        pnlHeader.add(lblBrand, BorderLayout.CENTER);
        pnlHeader.add(lblSub, BorderLayout.SOUTH);
        add(pnlHeader, BorderLayout.NORTH);

        // Card Body
        var pnlBody = new JPanel(new GridBagLayout());
        pnlBody.setBackground(new Color(248, 250, 252));
        pnlBody.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        var gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 6, 6, 6);

        // Username
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
        var lblUser = new JLabel("Username");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblUser.setForeground(new Color(51, 65, 85));
        pnlBody.add(lblUser, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        usernameField = new JTextField("AMAAN");
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        usernameField.setPreferredSize(new Dimension(300, 32));
        pnlBody.add(usernameField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
        var lblPass = new JLabel("Password");
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblPass.setForeground(new Color(51, 65, 85));
        pnlBody.add(lblPass, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        passwordField = new JPasswordField("12345");
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        passwordField.setPreferredSize(new Dimension(300, 32));
        pnlBody.add(passwordField, gbc);

        // Login Button
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.insets = new Insets(14, 6, 8, 6);
        var loginBtn = new JButton("SECURE SIGN IN  ➔");
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        loginBtn.setBackground(new Color(14, 116, 144)); // Deep Cyan/Teal
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setPreferredSize(new Dimension(300, 36));
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginBtn.setFocusPainted(false);
        pnlBody.add(loginBtn, gbc);

        // Footer hint
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        gbc.insets = new Insets(4, 6, 4, 6);
        var lblHint = new JLabel("Default Admin Credentials: AMAAN / 12345", SwingConstants.CENTER);
        lblHint.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblHint.setForeground(new Color(100, 116, 139));
        pnlBody.add(lblHint, gbc);

        add(pnlBody, BorderLayout.CENTER);

        // Bind events
        loginBtn.addActionListener(e -> attemptLogin());
        passwordField.addActionListener(e -> attemptLogin());
        usernameField.addActionListener(e -> attemptLogin());

        setVisible(true);
    }

    private void attemptLogin() {
        var u = usernameField.getText();
        var p = new String(passwordField.getPassword());

        if (auth.login(u, p)) {
            new DashboardFrame(new HospitalController());
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Invalid Administrator Credentials. Please check username and password.",
                    "Authentication Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
