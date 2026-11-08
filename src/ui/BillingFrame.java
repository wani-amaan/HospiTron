package ui;

import controller.HospitalController;
import model.Patient;
import util.UITheme;
import util.ValidationUtil;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.Box;
import javax.swing.BoxLayout;
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
 * Modern, classy Invoicing & Billing Interface with 50% Emergency Surcharge Calculation and smooth transitions.
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
        setSize(880, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Header Panel - Vertical Stack
        var pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(UITheme.SLATE_900);
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(15, 24, 15, 24));

        var pnlTitleGroup = new JPanel();
        pnlTitleGroup.setLayout(new BoxLayout(pnlTitleGroup, BoxLayout.Y_AXIS));
        pnlTitleGroup.setOpaque(false);

        var lblTag = new JLabel("HOSPITAL TARIFFS & FINANCIAL AUDIT");
        lblTag.setFont(UITheme.FONT_SECTION_TAG);
        lblTag.setForeground(new Color(251, 191, 36)); // Amber 400

        var lblTitle = new JLabel("💳 INVOICE GENERATION & PATIENT BILLING");
        lblTitle.setFont(UITheme.FONT_HEADER_TITLE);
        lblTitle.setForeground(Color.WHITE);

        var lblSub = new JLabel("Itemized Tariff Engine (+50% Emergency Critical Care Surcharge Calculation)");
        lblSub.setFont(UITheme.FONT_HEADER_SUB);
        lblSub.setForeground(UITheme.SLATE_500);

        pnlTitleGroup.add(lblTag);
        pnlTitleGroup.add(Box.createVerticalStrut(3));
        pnlTitleGroup.add(lblTitle);
        pnlTitleGroup.add(Box.createVerticalStrut(3));
        pnlTitleGroup.add(lblSub);

        pnlHeader.add(pnlTitleGroup, BorderLayout.WEST);
        add(pnlHeader, BorderLayout.NORTH);

        // Left Form Panel
        var pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBackground(UITheme.SLATE_50);
        pnlForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, UITheme.SLATE_200),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));
        pnlForm.setPreferredSize(new Dimension(320, 460));

        var gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(3, 5, 3, 5);

        gbc.gridx = 0; gbc.gridy = 0;
        var l1 = new JLabel("PATIENT ID");
        l1.setFont(UITheme.FONT_FORM_LABEL);
        l1.setForeground(UITheme.SLATE_700);
        pnlForm.add(l1, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        txtPID = new JTextField();
        UITheme.styleInputField(txtPID);
        txtPID.setPreferredSize(new Dimension(260, 30));
        pnlForm.add(txtPID, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.insets = new Insets(2, 5, 6, 5);
        var btnAutoFill = new JButton("🔍 Auto-Lookup Patient Name");
        UITheme.attachButtonTransitions(btnAutoFill, UITheme.SLATE_800, UITheme.SLATE_700, UITheme.SLATE_900);
        btnAutoFill.setFont(UITheme.FONT_BTN_SM);
        btnAutoFill.setPreferredSize(new Dimension(260, 28));
        btnAutoFill.addActionListener(e -> lookupPatientName());
        pnlForm.add(btnAutoFill, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.insets = new Insets(3, 5, 3, 5);
        var l2 = new JLabel("PATIENT FULL NAME");
        l2.setFont(UITheme.FONT_FORM_LABEL);
        l2.setForeground(UITheme.SLATE_700);
        pnlForm.add(l2, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        txtName = new JTextField();
        UITheme.styleInputField(txtName);
        txtName.setPreferredSize(new Dimension(260, 30));
        pnlForm.add(txtName, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        var l3 = new JLabel("DOCTOR CONSULTATION FEE (₹)");
        l3.setFont(UITheme.FONT_FORM_LABEL);
        l3.setForeground(UITheme.SLATE_700);
        pnlForm.add(l3, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        txtConsultation = new JTextField("2000");
        UITheme.styleInputField(txtConsultation);
        txtConsultation.setPreferredSize(new Dimension(260, 30));
        pnlForm.add(txtConsultation, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        var l4 = new JLabel("DIAGNOSTICS & LAB TESTS COST (₹)");
        l4.setFont(UITheme.FONT_FORM_LABEL);
        l4.setForeground(UITheme.SLATE_700);
        pnlForm.add(l4, gbc);

        gbc.gridx = 0; gbc.gridy = 8;
        txtTests = new JTextField("500");
        UITheme.styleInputField(txtTests);
        txtTests.setPreferredSize(new Dimension(260, 30));
        pnlForm.add(txtTests, gbc);

        gbc.gridx = 0; gbc.gridy = 9;
        gbc.insets = new Insets(8, 5, 8, 5);
        chkEmergency = new JCheckBox("🚨 Emergency Case (+50% Surcharge)");
        chkEmergency.setOpaque(false);
        chkEmergency.setFont(UITheme.FONT_FORM_LABEL);
        chkEmergency.setForeground(UITheme.TRIAGE_RED);
        pnlForm.add(chkEmergency, gbc);

        gbc.gridx = 0; gbc.gridy = 10;
        gbc.insets = new Insets(8, 5, 4, 5);
        var btnGenerate = new JButton("⚡ Generate Itemized Invoice");
        UITheme.attachButtonTransitions(btnGenerate, UITheme.TARIFF_AMBER, UITheme.AMBER_HOVER, UITheme.AMBER_CLICK);
        btnGenerate.setPreferredSize(new Dimension(260, 34));
        btnGenerate.addActionListener(e -> generateInvoice());
        pnlForm.add(btnGenerate, gbc);

        gbc.gridx = 0; gbc.gridy = 11;
        var btnExport = new JButton("💾 Export Invoice to File");
        UITheme.attachButtonTransitions(btnExport, UITheme.SUCCESS_EMERALD, UITheme.EMERALD_HOVER, UITheme.EMERALD_CLICK);
        btnExport.setPreferredSize(new Dimension(260, 34));
        btnExport.addActionListener(e -> exportInvoice());
        pnlForm.add(btnExport, gbc);

        // Right Invoice Receipt Panel
        var pnlReceipt = new JPanel(new BorderLayout());
        pnlReceipt.setBackground(Color.WHITE);

        var pnlBar = new JPanel(new BorderLayout());
        pnlBar.setBackground(UITheme.SLATE_100);
        pnlBar.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        var lblBar = new JLabel("Official Hospital Tax Invoice & Audit Receipt");
        lblBar.setFont(UITheme.FONT_BADGE);
        lblBar.setForeground(UITheme.SLATE_700);
        pnlBar.add(lblBar, BorderLayout.WEST);
        pnlReceipt.add(pnlBar, BorderLayout.NORTH);

        txtReceipt = new JTextArea();
        txtReceipt.setEditable(false);
        txtReceipt.setFont(UITheme.FONT_MONO_DATA);
        txtReceipt.setBackground(Color.WHITE);
        txtReceipt.setForeground(new Color(15, 23, 42));
        txtReceipt.setBorder(BorderFactory.createEmptyBorder(14, 16, 14, 16));
        txtReceipt.setText("""
                ========================================================================
                                     HOSPITRON INVOICE PREVIEW CONSOLE
                ========================================================================
                Fill in the patient and tariff details on the left, then click
                'Generate Itemized Invoice' to calculate the total payable balance.

                Features:
                • Automated patient directory lookup (O(1) in-memory filter)
                • Itemized tariff breakdown
                • 50% critical emergency care surcharge
                • One-click export to data/invoice_patient_<id>.txt
                """);

        var scrollReceipt = new JScrollPane(txtReceipt);
        scrollReceipt.setBorder(BorderFactory.createEmptyBorder());
        pnlReceipt.add(scrollReceipt, BorderLayout.CENTER);

        var splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlForm, pnlReceipt);
        splitPane.setDividerLocation(320);
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
