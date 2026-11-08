package ui;

import controller.HospitalController;
import util.UITheme;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
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
 * Modern, classy healthcare security login interface with smooth interaction transitions.
 */
public final class LoginFrame extends JFrame {

    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final AuthService auth = new AuthService();

    public LoginFrame() {
        setTitle("HospiTron Security Gateway");
        setSize(440, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // Header Banner - Vertical Stack
        var pnlHeader = new JPanel();
        pnlHeader.setLayout(new BoxLayout(pnlHeader, BoxLayout.Y_AXIS));
        pnlHeader.setBackground(UITheme.SLATE_900);
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(22, 24, 20, 24));

        var lblTag = new JLabel("ENTERPRISE ACCESS GATEWAY");
        lblTag.setFont(UITheme.FONT_SECTION_TAG);
        lblTag.setForeground(new Color(56, 189, 248)); // Sky 400
        lblTag.setAlignmentX(CENTER_ALIGNMENT);

        var lblBrand = new JLabel("🏥 HOSPITRON CLINICAL SUITE");
        lblBrand.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblBrand.setForeground(Color.WHITE);
        lblBrand.setAlignmentX(CENTER_ALIGNMENT);

        var lblSub = new JLabel("Role-Based Cryptographic Access Verification");
        lblSub.setFont(UITheme.FONT_HEADER_SUB);
        lblSub.setForeground(UITheme.SLATE_500);
        lblSub.setAlignmentX(CENTER_ALIGNMENT);

        pnlHeader.add(lblTag);
        pnlHeader.add(Box.createVerticalStrut(4));
        pnlHeader.add(lblBrand);
        pnlHeader.add(Box.createVerticalStrut(4));
        pnlHeader.add(lblSub);
        add(pnlHeader, BorderLayout.NORTH);

        // Card Body
        var pnlBody = new JPanel(new GridBagLayout());
        pnlBody.setBackground(UITheme.SLATE_50);
        pnlBody.setBorder(BorderFactory.createEmptyBorder(20, 32, 20, 32));

        var gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 6, 5, 6);

        // Username
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
        var lblUser = new JLabel("USERNAME");
        lblUser.setFont(UITheme.FONT_FORM_LABEL);
        lblUser.setForeground(UITheme.SLATE_700);
        pnlBody.add(lblUser, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        usernameField = new JTextField("AMAAN");
        UITheme.styleInputField(usernameField);
        usernameField.setPreferredSize(new Dimension(300, 34));
        pnlBody.add(usernameField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
        var lblPass = new JLabel("PASSWORD");
        lblPass.setFont(UITheme.FONT_FORM_LABEL);
        lblPass.setForeground(UITheme.SLATE_700);
        pnlBody.add(lblPass, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        passwordField = new JPasswordField("12345");
        passwordField.setFont(UITheme.FONT_INPUT);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.SLATE_200, 1),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        passwordField.setPreferredSize(new Dimension(300, 34));
        pnlBody.add(passwordField, gbc);

        // Login Button
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.insets = new Insets(16, 6, 8, 6);
        var loginBtn = new JButton("SECURE SIGN IN  ➔");
        UITheme.attachButtonTransitions(loginBtn, UITheme.SURGICAL_TEAL, UITheme.TEAL_HOVER, UITheme.TEAL_CLICK);
        loginBtn.setPreferredSize(new Dimension(300, 38));
        pnlBody.add(loginBtn, gbc);

        // Footer helper text
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        var lblHelp = new JLabel("Default Admin Credentials: AMAAN / 12345", SwingConstants.CENTER);
        lblHelp.setFont(new Font("Consolas", Font.PLAIN, 11));
        lblHelp.setForeground(UITheme.SLATE_500);
        pnlBody.add(lblHelp, gbc);

        add(pnlBody, BorderLayout.CENTER);

        loginBtn.addActionListener(e -> performLogin());
        passwordField.addActionListener(e -> performLogin());

        setVisible(true);
    }

    private void performLogin() {
        String username = usernameField.getText().strip();
        String password = new String(passwordField.getPassword());

        if (auth.login(username, password)) {
            dispose();
            new DashboardFrame(new HospitalController());
        } else {
            JOptionPane.showMessageDialog(this,
                    """
                    AUTHENTICATION FAILED
                    Invalid credentials entered.
                    Default Admin Credentials: AMAAN / 12345
                    """,
                    "Security Verification Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
