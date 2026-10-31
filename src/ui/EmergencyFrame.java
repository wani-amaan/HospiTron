package ui;

import controller.HospitalController;
import model.EmergencyCase;
import util.ValidationUtil;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Modern Java Emergency Triage Management Frame.
 */
public final class EmergencyFrame extends JFrame {

    private final JTextField txtPatientId;
    private final JTextField txtName;
    private final JTextField txtSeverity;
    private final HospitalController controller;

    public EmergencyFrame(HospitalController controller) {
        this.controller = controller;

        setTitle("Emergency Module - HOSPITRON");
        setSize(460, 310);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(245, 248, 252));

        var lblTitle = new JLabel("EMERGENCY TRIAGE");
        lblTitle.setBounds(140, 18, 200, 25);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        add(lblTitle);

        var lblId = new JLabel("Patient ID:");
        lblId.setBounds(50, 70, 100, 25);
        add(lblId);

        txtPatientId = new JTextField();
        txtPatientId.setBounds(160, 70, 220, 26);
        add(txtPatientId);

        var lblName = new JLabel("Name:");
        lblName.setBounds(50, 110, 100, 25);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(160, 110, 220, 26);
        add(txtName);

        var lblSeverity = new JLabel("Severity (1-10):");
        lblSeverity.setBounds(50, 150, 100, 25);
        add(lblSeverity);

        txtSeverity = new JTextField();
        txtSeverity.setBounds(160, 150, 220, 26);
        add(txtSeverity);

        var btnAdd = new JButton("Add Emergency");
        btnAdd.setBounds(50, 205, 160, 32);
        add(btnAdd);

        var btnProcess = new JButton("⚡ Process Next");
        btnProcess.setBounds(230, 205, 160, 32);
        btnProcess.setBackground(new Color(192, 57, 43));
        btnProcess.setForeground(Color.WHITE);
        add(btnProcess);

        btnAdd.addActionListener(e -> addEmergency());
        btnProcess.addActionListener(e -> processEmergency());

        setVisible(true);
    }

    private void addEmergency() {
        try {
            int patientId = Integer.parseInt(txtPatientId.getText().strip());
            String name = txtName.getText().strip();
            int severity = Integer.parseInt(txtSeverity.getText().strip());

            ValidationUtil.validateId(patientId);
            ValidationUtil.validateName(name);
            ValidationUtil.validateSeverity(severity);

            controller.addEmergency(patientId, name, severity);

            JOptionPane.showMessageDialog(this,
                    """
                    EMERGENCY REGISTERED
                    Patient triaged into PriorityQueue Max-Heap.
                    Severity Level : %d / 10
                    """.formatted(severity),
                    "Triage Confirmed",
                    JOptionPane.INFORMATION_MESSAGE);

            txtPatientId.setText("");
            txtName.setText("");
            txtSeverity.setText("");

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric ID and Severity (1-10)", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void processEmergency() {
        try {
            EmergencyCase ec = controller.handleEmergency();

            if (ec != null) {
                JOptionPane.showMessageDialog(this,
                        """
                        PRIORITY EMERGENCY DISPATCH
                        ====================================
                        Patient Name   : %s
                        Patient ID     : %d
                        Severity Level : %d / 10 (Highest in Queue)
                        ====================================
                        Status: Dispatched immediately to ER Resuscitation Bay
                        """.formatted(ec.name(), ec.patientId(), ec.severity()),
                        "Emergency Case Dispatched",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "No emergency cases currently waiting in the PriorityQueue.",
                        "Queue Empty",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Emergency triage error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}