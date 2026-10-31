package ui;

import controller.HospitalController;
import model.Patient;
import util.ValidationUtil;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class BillingFrame extends JFrame {

    private JTextField txtPID;
    private JTextField txtName;
    private JTextField txtConsultation;
    private JTextField txtTests;
    private JCheckBox chkEmergency;
    private JTextArea txtReceipt;

    private HospitalController controller;

    public BillingFrame(HospitalController controller) {
        this.controller = controller;

        setTitle("Billing & Invoicing - HOSPITRON");
        setSize(650, 560);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Header Panel
        JPanel pnlHeader = new JPanel();
        pnlHeader.setBackground(new Color(41, 128, 185));
        JLabel lblTitle = new JLabel("HOSPITRON BILLING & INVOICE SYSTEM");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitle.setForeground(Color.WHITE);
        pnlHeader.add(lblTitle);
        add(pnlHeader, BorderLayout.NORTH);

        // Main Center Panel with Left Controls and Right Receipt
        JPanel pnlCenter = new JPanel(new GridLayout(1, 2, 12, 10));
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        pnlCenter.setBackground(new Color(245, 248, 252));

        // Form Panel
        JPanel pnlForm = new JPanel();
        pnlForm.setLayout(new BoxLayout(pnlForm, BoxLayout.Y_AXIS));
        pnlForm.setOpaque(false);

        pnlForm.add(new JLabel("Patient ID:"));
        txtPID = new JTextField();
        pnlForm.add(txtPID);
        pnlForm.add(Box.createVerticalStrut(8));

        JButton btnAutoFill = new JButton("🔍 Auto-Lookup Patient Name");
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

        JButton btnGenerate = new JButton("⚡ Generate Detailed Invoice");
        btnGenerate.setBackground(new Color(41, 128, 185));
        btnGenerate.setForeground(Color.WHITE);
        btnGenerate.addActionListener(e -> generateInvoice());
        pnlForm.add(btnGenerate);
        pnlForm.add(Box.createVerticalStrut(8));

        JButton btnExport = new JButton("💾 Export Invoice to File");
        btnExport.setBackground(new Color(39, 174, 96));
        btnExport.setForeground(Color.WHITE);
        btnExport.addActionListener(e -> exportInvoice());
        pnlForm.add(btnExport);

        pnlCenter.add(pnlForm);

        // Receipt Output Panel
        txtReceipt = new JTextArea();
        txtReceipt.setEditable(false);
        txtReceipt.setFont(new Font("Consolas", Font.PLAIN, 11));
        txtReceipt.setBackground(new Color(255, 255, 255));
        txtReceipt.setText("Enter billing parameters on the left and click 'Generate Detailed Invoice'.\n\n" +
                "Itemized breakdown will display:\n" +
                "• Consultation charges\n" +
                "• Diagnostic/Laboratory test costs\n" +
                "• Critical care emergency surcharges (50%)\n" +
                "• Final calculated total");

        JScrollPane scrollPane = new JScrollPane(txtReceipt);
        pnlCenter.add(scrollPane);

        add(pnlCenter, BorderLayout.CENTER);

        setVisible(true);
    }

    private void lookupPatientName() {
        try {
            int pid = Integer.parseInt(txtPID.getText().trim());
            List<Patient> patients = controller.getAllPatients();
            for (Patient p : patients) {
                if (p.getId() == pid) {
                    txtName.setText(p.getName());
                    JOptionPane.showMessageDialog(this, "Found patient: " + p.getName(), "Patient Match", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "No registered patient with ID " + pid + ". You can enter the name manually.", "Lookup", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric Patient ID first.", "Invalid ID", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void generateInvoice() {
        try {
            int pid = Integer.parseInt(txtPID.getText().trim());
            String name = txtName.getText().trim();
            int consultation = Integer.parseInt(txtConsultation.getText().trim());
            int tests = Integer.parseInt(txtTests.getText().trim());
            boolean emergency = chkEmergency.isSelected();

            ValidationUtil.validateId(pid);
            ValidationUtil.validateName(name);

            if (consultation < 0 || tests < 0) {
                throw new IllegalArgumentException("Cost values cannot be negative.");
            }

            String receipt = controller.generateDetailedReceipt(pid, name, consultation, tests, emergency);
            txtReceipt.setText(receipt);

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for fees and ID.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportInvoice() {
        try {
            String content = txtReceipt.getText().trim();
            if (content.isEmpty() || !content.contains("OFFICIAL MEDICAL INVOICE")) {
                JOptionPane.showMessageDialog(this, "Please generate an invoice first before exporting.", "No Invoice", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int pid = Integer.parseInt(txtPID.getText().trim());
            File dataDir = new File("data");
            if (!dataDir.exists()) dataDir.mkdirs();

            File outFile = new File(dataDir, "invoice_patient_" + pid + ".txt");
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(outFile))) {
                bw.write(content);
            }

            JOptionPane.showMessageDialog(this, "Invoice exported successfully to:\n" + outFile.getAbsolutePath(), "Invoice Saved", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error exporting invoice: " + ex.getMessage(), "Export Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
