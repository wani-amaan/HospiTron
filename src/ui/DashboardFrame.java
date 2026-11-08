package ui;

import controller.HospitalController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Modern, executive clinical dashboard for HospiTron.
 */
public final class DashboardFrame extends JFrame {

    private final HospitalController controller;

    public DashboardFrame(HospitalController controller) {
        this.controller = controller;

        setTitle("HospiTron - Executive Healthcare Dashboard");
        setSize(700, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top Navigation Header
        var pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(new Color(15, 23, 42)); // Slate 900
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));

        var pnlTitle = new JPanel();
        pnlTitle.setLayout(new BoxLayout(pnlTitle, BoxLayout.Y_AXIS));
        pnlTitle.setOpaque(false);

        var lblBrand = new JLabel("🏥 HOSPITRON CLINICAL SUITE");
        lblBrand.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblBrand.setForeground(new Color(56, 189, 248)); // Sky Blue

        var lblSub = new JLabel("Intelligent Hospital Operations & Algorithmic Optimization");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(new Color(148, 163, 184)); // Slate 400

        pnlTitle.add(lblBrand);
        pnlTitle.add(Box.createVerticalStrut(2));
        pnlTitle.add(lblSub);

        var lblStatus = new JLabel("🟢 Java 27 Engine • Active");
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblStatus.setForeground(new Color(52, 211, 153)); // Emerald 400

        pnlHeader.add(pnlTitle, BorderLayout.WEST);
        pnlHeader.add(lblStatus, BorderLayout.EAST);
        add(pnlHeader, BorderLayout.NORTH);

        // Center Grid Cards Panel
        var pnlGrid = new JPanel(new GridLayout(3, 2, 14, 14));
        pnlGrid.setBackground(new Color(241, 245, 249)); // Slate 100
        pnlGrid.setBorder(BorderFactory.createEmptyBorder(18, 22, 18, 22));

        var btnPatient = createModuleCard("🧑‍⚕️ Patient Management", "Admissions, Demographics & Compliance", new Color(2, 132, 199));
        var btnDoctor = createModuleCard("🩺 Doctor & Staff Directory", "Specialist Registry & Ward Availability", new Color(14, 116, 144));
        var btnAppointment = createModuleCard("📅 Appointment Scheduler", "Time-Slots & Greedy Conflict Resolution", new Color(79, 70, 229));
        var btnEmergency = createModuleCard("🚨 Emergency Triage System", "Max-Heap PriorityQueue Severity Dispatch", new Color(225, 29, 72));
        var btnBilling = createModuleCard("🧾 Invoicing & Medical Bills", "Itemized Receipts, Surcharges & File Export", new Color(217, 119, 6));
        var btnOpt = createModuleCard("⚡ Optimization Suite (DSA)", "Dijkstra Emergency Routes & Knapsack DP", new Color(16, 185, 129));

        pnlGrid.add(btnPatient);
        pnlGrid.add(btnDoctor);
        pnlGrid.add(btnAppointment);
        pnlGrid.add(btnEmergency);
        pnlGrid.add(btnBilling);
        pnlGrid.add(btnOpt);

        add(pnlGrid, BorderLayout.CENTER);

        // Bottom Demonstration Seeder Bar
        var pnlFooter = new JPanel(new BorderLayout());
        pnlFooter.setBackground(new Color(248, 250, 252));
        pnlFooter.setBorder(BorderFactory.createEmptyBorder(10, 22, 14, 22));

        var btnDemo = new JButton("🎓 Load Academic Demonstration Dataset (1-Click Viva Setup)");
        btnDemo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnDemo.setBackground(new Color(126, 34, 206)); // Royal Purple
        btnDemo.setForeground(Color.WHITE);
        btnDemo.setPreferredSize(new Dimension(650, 38));
        btnDemo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDemo.setFocusPainted(false);
        pnlFooter.add(btnDemo, BorderLayout.CENTER);

        add(pnlFooter, BorderLayout.SOUTH);

        // Event Listeners
        btnPatient.addActionListener(e -> new PatientFrame(controller));
        btnDoctor.addActionListener(e -> new DoctorFrame(controller));
        btnAppointment.addActionListener(e -> new AppointmentFrame(controller));
        btnEmergency.addActionListener(e -> new EmergencyFrame(controller));
        btnBilling.addActionListener(e -> new BillingFrame(controller));
        btnOpt.addActionListener(e -> new OptimizationFrame(controller));
        btnDemo.addActionListener(e -> seedDemoData());

        setVisible(true);
    }

    private JButton createModuleCard(String title, String subtitle, Color accentColor) {
        var btn = new JButton();
        btn.setLayout(new BorderLayout(5, 5));
        btn.setBackground(Color.WHITE);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(12, 16, 12, 16)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);

        var lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitle.setForeground(accentColor);

        var lblSub = new JLabel(subtitle);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblSub.setForeground(new Color(100, 116, 139));

        btn.add(lblTitle, BorderLayout.NORTH);
        btn.add(lblSub, BorderLayout.SOUTH);

        return btn;
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

                    Open any module to demonstrate the live algorithms!
                    """,
                    "Seeder Complete",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
