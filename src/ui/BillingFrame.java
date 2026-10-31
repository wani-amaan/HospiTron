package ui;

import controller.HospitalController;
import model.Patient;
import util.ValidationUtil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Modern Java Billing & Invoice Frame.
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

        setTitle("Billing & Invoicing - HOSPITRON");
        setSize(650, 560);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Header Panel
        var pnlHeader = new JPanel();
        pnlHeader.setBackground(new Color(41, 128, 185));
        var lblTitle = new JLabel("HOSPITRON BILLING & INVOICE SYSTEM");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitle.setForeground(Color.WHITE);
        pnlHeader.add(lblTitle);
        add(pnlHeader, BorderLayout.NORTH);

        // Main Center Panel
        var pnlCenter = new JPanel(new GridLayout(1, 2, 12, 10));
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        pnlCenter.setBackground(new Color(245, 248, 252));

        // Form Panel
        var pnlForm = new JPanel();
        pnlForm.setLayout(new BoxLayout(pnlForm, BoxLayout.Y_AXIS));
        pnlForm.setOpaque(false);

        pnlForm.add(new JLabel("Patient ID:"));
        txtPID = new JTextField();
        pnlForm.add(txtPID);
        pnlForm.add(Box.createVerticalStrut(8));

        var btnAutoFill = new JButton("🔍 Auto-Lookup Patient Name");
        btnAutoFill.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        btnAutoFill.addActionListener(e -> lookupPatientName());
        pnlForm.add(btnAutoFill);
        pnlForm.add(Box.createVerticalStrut(10));

        pnlForm.add(new JLabel("Patient Name:"));
        txtName = new JTextField();
        pnlForm.add(txtName);
        pnlForm.add(Box.createVerticalStrut(10));

        pnlForm.add(new JLabel("Doctor Consultation Fee (₹):"));
        txtConsultation = new JTextField("2000");
        pnlForm.add(txtConsultation);
        pnlForm.add(Box.createVerticalStrut(10));

        pnlForm.add(new JLabel("Diagnostics / Lab Tests Cost (₹):"));
        txtTests = new JTextField("500");
        pnlForm.add(txtTests);
        pnlForm.add(Box.createVerticalStrut(10));

        chkEmergency = new JCheckBox("Emergency Case (+50% Surcharge)");
        chkEmergency.setOpaque(false);
        chkEmergency.setFont(new Font("Segoe UI", Font.BOLD, 12));
        chkEmergency.setForeground(new Color(192, 57, 43));
        pnlForm.add(chkEmergency);
        pnlForm.add(Box.createVerticalStrut(15));

        var btnGenerate = new JButton("⚡ Generate Detailed Invoice");
        btnGenerate.setBackground(new Color(41, 128, 185));
        btnGenerate.setForeground(Color.WHITE);
        btnGenerate.addActionListener(e -> generateInvoice());
        pnlForm.add(btnGenerate);
        pnlForm.add(Box.createVerticalStrut(8));

        var btnExport = new JButton("💾 Export Invoice to File");
        btnExport.setBackground(new Color(39, 174, 96));
        btnExport.setForeground(Color.WHITE);
        btnExport.addActionListener(e -> exportInvoice());
        pnlForm.add(btnExport);

        pnlCenter.add(pnlForm);

        // Receipt Output Panel
        txtReceipt = new JTextArea();
        txtReceipt.setEditable(false);
        txtReceipt.setFont(new Font("Consolas", Font.PLAIN, 11));
        txtReceipt.setBackground(Color.WHITE);
        txtReceipt.setText("""
                Enter billing parameters on the left and click 'Generate Detailed Invoice'.

                Itemized breakdown will display:
                • Doctor Consultation charges
                • Diagnostic / Laboratory test costs
                • Critical care emergency surcharges (50%)
                • Final calculated total amount
                """);

        pnlCenter.add(new JScrollPane(txtReceipt));
        add(pnlCenter, BorderLayout.CENTER);

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
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for fees and ID.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportInvoice() {
        try {
            var content = txtReceipt.getText().strip();
            if (content.isEmpty() || !content.contains("OFFICIAL MEDICAL INVOICE")) {
                JOptionPane.showMessageDialog(this, "Please generate an invoice first before exporting.", "No Invoice", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int pid = Integer.parseInt(txtPID.getText().strip());
            var dataDir = Path.of("data");
            if (!Files.exists(dataDir)) Files.createDirectories(dataDir);

            var outFile = dataDir.resolve("invoice_patient_" + pid + ".txt");
            Files.writeString(outFile, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            JOptionPane.showMessageDialog(this, "Invoice exported successfully to:\n" + outFile.toAbsolutePath(), "Invoice Saved", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error exporting invoice: " + ex.getMessage(), "Export Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
