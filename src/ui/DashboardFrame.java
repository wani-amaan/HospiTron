package ui;

import controller.HospitalController;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Modern Java main navigation dashboard for HospiTron.
 */
public final class DashboardFrame extends JFrame {

    private final HospitalController controller;

    public DashboardFrame(HospitalController controller) {
        this.controller = controller;

        setTitle("Dashboard - HOSPITRON");
        setSize(500, 395);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(245, 248, 252));

        var lblTitle = new JLabel("HOSPITRON DASHBOARD");
        lblTitle.setBounds(135, 20, 250, 30);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(lblTitle);

        var btnPatient = new JButton("Patient Module");
        btnPatient.setBounds(150, 75, 200, 30);
        add(btnPatient);

        var btnDoctor = new JButton("Doctor Module");
        btnDoctor.setBounds(150, 115, 200, 30);
        add(btnDoctor);

        var btnAppointment = new JButton("Appointment Module");
        btnAppointment.setBounds(150, 155, 200, 30);
        add(btnAppointment);

        var btnEmergency = new JButton("Emergency Triage");
        btnEmergency.setBounds(150, 195, 200, 30);
        add(btnEmergency);

        var btnBilling = new JButton("Billing & Invoices");
        btnBilling.setBounds(150, 235, 200, 30);
        add(btnBilling);

        var btnOpt = new JButton("⚡ Optimization Suite (DSA)");
        btnOpt.setBounds(130, 280, 240, 34);
        btnOpt.setBackground(new Color(39, 174, 96));
        btnOpt.setForeground(Color.WHITE);
        add(btnOpt);

        // ---------------- ACTIONS ----------------
        btnPatient.addActionListener(e -> new PatientFrame(controller));
        btnDoctor.addActionListener(e -> new DoctorFrame(controller));
        btnAppointment.addActionListener(e -> new AppointmentFrame(controller));
        btnEmergency.addActionListener(e -> new EmergencyFrame(controller));
        btnBilling.addActionListener(e -> new BillingFrame(controller));
        btnOpt.addActionListener(e -> new OptimizationFrame(controller));

        setVisible(true);
    }
}