package ui;

import controller.HospitalController;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * Modern Java main navigation dashboard for HospiTron with demonstration data seeder.
 */
public final class DashboardFrame extends JFrame {

    private final HospitalController controller;

    public DashboardFrame(HospitalController controller) {
        this.controller = controller;

        setTitle("Dashboard - HOSPITRON");
        setSize(500, 440);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(245, 248, 252));

        var lblTitle = new JLabel("HOSPITRON DASHBOARD");
        lblTitle.setBounds(135, 18, 250, 30);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(lblTitle);

        var btnPatient = new JButton("Patient Module");
        btnPatient.setBounds(150, 68, 200, 30);
        add(btnPatient);

        var btnDoctor = new JButton("Doctor Module");
        btnDoctor.setBounds(150, 108, 200, 30);
        add(btnDoctor);

        var btnAppointment = new JButton("Appointment Module");
        btnAppointment.setBounds(150, 148, 200, 30);
        add(btnAppointment);

        var btnEmergency = new JButton("Emergency Triage");
        btnEmergency.setBounds(150, 188, 200, 30);
        add(btnEmergency);

        var btnBilling = new JButton("Billing & Invoices");
        btnBilling.setBounds(150, 228, 200, 30);
        add(btnBilling);

        var btnOpt = new JButton("⚡ Optimization Suite (DSA)");
        btnOpt.setBounds(130, 272, 240, 34);
        btnOpt.setBackground(new Color(39, 174, 96));
        btnOpt.setForeground(Color.WHITE);
        add(btnOpt);

        var btnDemo = new JButton("🎓 Load Academic Demonstration Data");
        btnDemo.setBounds(110, 320, 280, 34);
        btnDemo.setBackground(new Color(142, 68, 173));
        btnDemo.setForeground(Color.WHITE);
        add(btnDemo);

        // ---------------- ACTIONS ----------------
        btnPatient.addActionListener(e -> new PatientFrame(controller));
        btnDoctor.addActionListener(e -> new DoctorFrame(controller));
        btnAppointment.addActionListener(e -> new AppointmentFrame(controller));
        btnEmergency.addActionListener(e -> new EmergencyFrame(controller));
        btnBilling.addActionListener(e -> new BillingFrame(controller));
        btnOpt.addActionListener(e -> new OptimizationFrame(controller));
        btnDemo.addActionListener(e -> seedDemoData());

        setVisible(true);
    }

    private void seedDemoData() {
        int confirm = JOptionPane.showConfirmDialog(this,
                """
                Load Academic Demonstration Dataset?
                ====================================
                This will automatically populate:
                • 5 Specialized Doctors across hospital wings
                • 5 Patients (including compliance-flagged record)
                • 7 Appointments with deliberate conflicts to demonstrate Interval Scheduling
                • 3 Emergency cases with varying severities (4, 7, 9) for PriorityQueue triage
                """,
                "Demo Dataset Seeder",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            controller.loadAcademicDemoData();
            JOptionPane.showMessageDialog(this,
                    """
                    DEMONSTRATION DATASET LOADED & PERSISTED
                    ========================================
                    ✔ 5 Doctors Registered
                    ✔ 5 Patients Registered
                    ✔ 7 Appointments Scheduled (with conflicts)
                    ✔ 3 Emergency Cases in PriorityQueue
                    
                    You can now open any module to demonstrate the algorithms!
                    """,
                    "Seeder Complete",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}