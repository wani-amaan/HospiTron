package ui;

import controller.HospitalController;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {

    private HospitalController controller;

    public DashboardFrame(HospitalController controller) {
        this.controller = controller;

        setTitle("Dashboard - HOSPITRON");
        setSize(500, 390);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(245, 248, 252));

        JLabel lblTitle = new JLabel("HOSPITRON DASHBOARD");
        lblTitle.setBounds(135, 20, 250, 30);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitle);

        JButton btnPatient = new JButton("Patient Module");
        btnPatient.setBounds(150, 75, 200, 30);
        add(btnPatient);

        JButton btnDoctor = new JButton("Doctor Module");
        btnDoctor.setBounds(150, 115, 200, 30);
        add(btnDoctor);

        JButton btnAppointment = new JButton("Appointment Module");
        btnAppointment.setBounds(150, 155, 200, 30);
        add(btnAppointment);

        JButton btnEmergency = new JButton("Emergency Triage");
        btnEmergency.setBounds(150, 195, 200, 30);
        add(btnEmergency);

        JButton btnBilling = new JButton("Generate Bill");
        btnBilling.setBounds(150, 235, 200, 30);
        add(btnBilling);

        JButton btnOpt = new JButton("⚡ Optimization Suite (DSA)");
        btnOpt.setBounds(130, 280, 240, 34);
        btnOpt.setBackground(new Color(39, 174, 96));
        btnOpt.setForeground(Color.WHITE);
        add(btnOpt);

        // ---------------- ACTIONS ----------------

        btnPatient.addActionListener(e -> new PatientFrame(controller));

        btnDoctor.addActionListener(e -> new DoctorFrame(controller));

        btnAppointment.addActionListener(e -> new AppointmentFrame(controller));

        btnEmergency.addActionListener(e -> new EmergencyFrame(controller));

        btnBilling.addActionListener(e -> generateBill());

        btnOpt.addActionListener(e -> new OptimizationFrame(controller));

        setVisible(true);
    }

    private void generateBill() {
        try {
            String inputId = JOptionPane.showInputDialog(this, "Enter Patient ID:");
            String name = JOptionPane.showInputDialog(this, "Enter Patient Name:");
            String emergencyInput = JOptionPane.showInputDialog(this, "Emergency? (true/false)");

            int patientId = Integer.parseInt(inputId);
            boolean emergency = Boolean.parseBoolean(emergencyInput);

            double bill = controller.generateBill(patientId, name, emergency);

            JOptionPane.showMessageDialog(this,
                    "Total Bill: " + bill);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error generating bill");
        }
    }
}