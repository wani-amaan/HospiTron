package ui;

import controller.HospitalController;

import javax.swing.*;
import java.awt.*;

public class EmergencyFrame extends JFrame {

    private JTextField txtPatientId;
    private JTextField txtName;
    private JTextField txtSeverity;

    private HospitalController controller;

    public EmergencyFrame(HospitalController controller) {
        this.controller = controller;

        setTitle("Emergency Module - HOSPITRON");
        setSize(450, 300);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        getContentPane().setBackground(new Color(245, 248, 252));

        JLabel lblTitle = new JLabel("Emergency Handling");
        lblTitle.setBounds(140, 20, 200, 25);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblTitle);

        JLabel lblId = new JLabel("Patient ID:");
        lblId.setBounds(50, 70, 100, 25);
        add(lblId);

        txtPatientId = new JTextField();
        txtPatientId.setBounds(160, 70, 200, 25);
        add(txtPatientId);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(50, 110, 100, 25);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(160, 110, 200, 25);
        add(txtName);

        JLabel lblSeverity = new JLabel("Severity:");
        lblSeverity.setBounds(50, 150, 100, 25);
        add(lblSeverity);

        txtSeverity = new JTextField();
        txtSeverity.setBounds(160, 150, 200, 25);
        add(txtSeverity);

        JButton btnAdd = new JButton("Add Emergency");
        btnAdd.setBounds(60, 200, 140, 30);
        add(btnAdd);

        JButton btnProcess = new JButton("Process Next");
        btnProcess.setBounds(210, 200, 140, 30);
        add(btnProcess);

        btnAdd.addActionListener(e -> addEmergency());
        btnProcess.addActionListener(e -> processEmergency());

        setVisible(true);
    }

    private void addEmergency() {
        try {
            int patientId = Integer.parseInt(txtPatientId.getText().trim());
            String name = txtName.getText().trim();
            int severity = Integer.parseInt(txtSeverity.getText().trim());

            util.ValidationUtil.validateId(patientId);
            util.ValidationUtil.validateName(name);
            util.ValidationUtil.validateSeverity(severity);

            controller.addEmergency(patientId, name, severity);

            JOptionPane.showMessageDialog(this,
                    "Emergency Added Successfully!\nTriaged into PriorityQueue with Severity " + severity + "/10",
                    "Emergency Registered",
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
            model.EmergencyCase ec = controller.handleEmergency();

            if (ec != null) {
                JOptionPane.showMessageDialog(this,
                        "Triage Processing (Highest Priority First):\n\n" +
                        "Patient Name   : " + ec.getName() + "\n" +
                        "Patient ID     : " + ec.getPatientId() + "\n" +
                        "Severity Level : " + ec.getSeverity() + " / 10\n\n" +
                        "Status: Dispatched to Emergency Response Unit",
                        "Priority Case Processed",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "No emergency cases currently waiting in queue.",
                        "Queue Empty",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No Emergency Found: " + e.getMessage());
        }
    }
}