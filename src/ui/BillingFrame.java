package ui;

import controller.HospitalController;
import model.Patient;
import util.ValidationUtil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Modern, classy Invoicing & Billing Interface with 50% Emergency Surcharge Calculation.
 */
public final class BillingFrame extends JFrame {

    private final JTextField txtPID;
    private final JTextField txtName;
    private final JTextField txtConsultation;
    private final JTextField txtTests;
    private final JCheckBox chkEmergency;
    private final JTextArea txtReceipt;
    private final HospitalController controller;

    public BillingFrame(HospitalController controller) {
        this.controller = controller;

        setTitle("Billing & Invoicing Suite - HOSPITRON");
        setSize(860, 580);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Header Panel
        var pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(new Color(15, 23, 42)); // Slate 900
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));

        var lblTitle = new JLabel("💳 INVOICE GENERATION & PATIENT BILLING");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblTitle.setForeground(Color.WHITE);

        var lblSub = new JLabel("Itemized Tariff Engine (+50% Emergency Critical Care Surcharge)");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(new Color(148, 163, 184)); // Slate 400

        pnlHeader.add(lblTitle, BorderLayout.WEST);
        pnlHeader.add(lblSub, BorderLayout.EAST);
        add(pnlHeader, BorderLayout.NORTH);

        // Left Form Panel
        var pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBackground(new Color(248, 250, 252));
        pnlForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(226, 232, 240)),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));
        pnlForm.setPreferredSize(new Dimension(320, 440));

        var gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(3, 5, 3, 5);

        gbc.gridx = 0; gbc.gridy = 0;
        var l1 = new JLabel("Patient ID");
        l1.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l1.setForeground(new Color(51, 65, 85));
        pnlForm.add(l1, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        txtPID = new JTextField();
        txtPID.setPreferredSize(new Dimension(260, 28));
        pnlForm.add(txtPID, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.insets = new Insets(2, 5, 6, 5);
        var btnAutoFill = new JButton("🔍 Auto-Lookup Patient Name");
        btnAutoFill.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        btnAutoFill.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAutoFill.addActionListener(e -> lookupPatientName());
        pnlForm.add(btnAutoFill, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.insets = new Insets(3, 5, 3, 5);
        var l2 = new JLabel("Patient Full Name");
        l2.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l2.setForeground(new Color(51, 65, 85));
        pnlForm.add(l2, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        txtName = new JTextField();
        txtName.setPreferredSize(new Dimension(260, 28));
        pnlForm.add(txtName, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        var l3 = new JLabel("Doctor Consultation Fee (₹)");
        l3.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l3.setForeground(new Color(51, 65, 85));
        pnlForm.add(l3, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        txtConsultation = new JTextField("2000");
        txtConsultation.setPreferredSize(new Dimension(260, 28));
        pnlForm.add(txtConsultation, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        var l4 = new JLabel("Diagnostics / Lab Tests Cost (₹)");
        l4.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l4.setForeground(new Color(51, 65, 85));
        pnlForm.add(l4, gbc);

        gbc.gridx = 0; gbc.gridy = 8;
        txtTests = new JTextField("500");
        txtTests.setPreferredSize(new Dimension(260, 28));
        pnlForm.add(txtTests, gbc);

        gbc.gridx = 0; gbc.gridy = 9;
        gbc.insets = new Insets(8, 5, 8, 5);
        chkEmergency = new JCheckBox("🚨 Emergency Case (+50% Surcharge)");
        chkEmergency.setOpaque(false);
        chkEmergency.setFont(new Font("Segoe UI", Font.BOLD, 12));
        chkEmergency.setForeground(new Color(220, 38, 38));
        pnlForm.add(chkEmergency, gbc);

        gbc.gridx = 0; gbc.gridy = 10;
        gbc.insets = new Insets(8, 5, 4, 5);
        var btnGenerate = new JButton("⚡ Generate Itemized Invoice");
        btnGenerate.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnGenerate.setBackground(new Color(2, 132, 199)); // Sky 600
        btnGenerate.setForeground(Color.WHITE);
        btnGenerate.setPreferredSize(new Dimension(260, 32));
        btnGenerate.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGenerate.setFocusPainted(false);
        btnGenerate.addActionListener(e -> generateInvoice());
        pnlForm.add(btnGenerate, gbc);

        gbc.gridx = 0; gbc.gridy = 11;
        var btnExport = new JButton("💾 Export Invoice to File");
        btnExport.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnExport.setBackground(new Color(16, 185, 129)); // Emerald 500
        btnExport.setForeground(Color.WHITE);
        btnExport.setPreferredSize(new Dimension(260, 32));
        btnExport.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnExport.setFocusPainted(false);
        btnExport.addActionListener(e -> exportInvoice());
        pnlForm.add(btnExport, gbc);

        // Right Invoice Receipt Panel
        var pnlReceipt = new JPanel(new BorderLayout());
        pnlReceipt.setBackground(Color.WHITE);

        var pnlBar = new JPanel(new BorderLayout());
        pnlBar.setBackground(new Color(241, 245, 249));
        pnlBar.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        var lblBar = new JLabel("Official Hospital Tax Invoice Preview");
        lblBar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblBar.setForeground(new Color(71, 85, 105));
        pnlBar.add(lblBar, BorderLayout.WEST);
        pnlReceipt.add(pnlBar, BorderLayout.NORTH);

        txtReceipt = new JTextArea();
        txtReceipt.setEditable(false);
        txtReceipt.setFont(new Font("Consolas", Font.PLAIN, 12));
        txtReceipt.setBackground(new Color(248, 250, 252));
        txtReceipt.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));
        txtReceipt.setText("""
                ========================================================================
                                     HOSPITRON INVOICE PREVIEW
                ========================================================================
                Fill in the patient and tariff details on the left, then click
                'Generate Itemized Invoice' to calculate the total payable balance.

                Features:
                • Automated patient directory lookup
                • Itemized tariff breakdown
                • 50% critical emergency care surcharge
                • One-click export to data/invoice_patient_<id>.txt
                """);

        pnlReceipt.add(new JScrollPane(txtReceipt), BorderLayout.CENTER);

        var splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlForm, pnlReceipt);
        splitPane.setDividerLocation(330);
        splitPane.setDividerSize(1);
        add(splitPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void lookupPatientName() {
        try {
            int pid = Integer.parseInt(txtPID.getText().strip());
            var match = controller.getAllPatients().stream()
                    .filter(p -> p.id() == pid)
                    .findFirst();

            if (match.isPresent()) {
                txtName.setText(match.get().name());
                JOptionPane.showMessageDialog(this, "Found patient: " + match.get().name(), "Patient Match", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No registered patient with ID " + pid + ". You can enter the name manually.", "Lookup", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric Patient ID first.", "Invalid ID", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void generateInvoice() {
        try {
            int pid = Integer.parseInt(txtPID.getText().strip());
            String name = txtName.getText().strip();
            int consultation = Integer.parseInt(txtConsultation.getText().strip());
            int tests = Integer.parseInt(txtTests.getText().strip());
            boolean emergency = chkEmergency.isSelected();

            ValidationUtil.validateId(pid);
            ValidationUtil.validateName(name);

            if (consultation < 0 || tests < 0) {
                throw new IllegalArgumentException("Cost values cannot be negative.");
            }

            var receipt = controller.generateDetailedReceipt(pid, name, consultation, tests, emergency);
            txtReceipt.setText(receipt);

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for fees and ID.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportInvoice() {
        String receipt = txtReceipt.getText();
        if (receipt.isBlank() || receipt.contains("Fill in the patient and tariff details")) {
            JOptionPane.showMessageDialog(this, "Please generate an invoice before exporting.", "No Invoice Data", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int pid = Integer.parseInt(txtPID.getText().strip());
            Path dir = Path.of("data");
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }

            Path file = dir.resolve("invoice_patient_" + pid + ".txt");
            Files.writeString(file, receipt, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            JOptionPane.showMessageDialog(this,
                    "Invoice exported successfully to:\n" + file.toAbsolutePath(),
                    "Export Confirmed",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Failed to save invoice file: " + ex.getMessage(), "Export Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Please ensure Patient ID is valid before exporting.", "Invalid Patient ID", JOptionPane.ERROR_MESSAGE);
        }
    }
}
