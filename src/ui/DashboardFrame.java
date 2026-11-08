package ui;

import controller.HospitalController;
import util.UITheme;

import java.awt.BorderLayout;
import java.awt.Color;
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

/**
 * Modern, executive clinical operations dashboard for HospiTron.
 * Features human-crafted typography, module badges, and smooth hover/click card transitions.
 */
public final class DashboardFrame extends JFrame {

    private final HospitalController controller;

    public DashboardFrame(HospitalController controller) {
        this.controller = controller;

        setTitle("HospiTron - Executive Clinical & Operations Suite");
        setSize(780, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top Navigation Header
        var pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(UITheme.SLATE_900);
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(18, 26, 18, 26));

        var pnlTitle = new JPanel();
        pnlTitle.setLayout(new BoxLayout(pnlTitle, BoxLayout.Y_AXIS));
        pnlTitle.setOpaque(false);

        var lblBrand = new JLabel("🏥 HOSPITRON CLINICAL SUITE");
        lblBrand.setFont(new Font("Segoe UI", Font.BOLD, 19));
        lblBrand.setForeground(new Color(248, 250, 252));

        var lblSub = new JLabel("Algorithmic Hospital Administration & Emergency Dispatch System");
        lblSub.setFont(UITheme.FONT_HEADER_SUB);
        lblSub.setForeground(UITheme.SLATE_500);

        pnlTitle.add(lblBrand);
        pnlTitle.add(Box.createVerticalStrut(3));
        pnlTitle.add(lblSub);

        var lblStatus = new JLabel(" ● Java 27 Engine Active ");
        lblStatus.setFont(UITheme.FONT_MONO_CODE);
        lblStatus.setForeground(new Color(52, 211, 153)); // Emerald 400
        lblStatus.setBackground(new Color(6, 78, 59, 140));
        lblStatus.setOpaque(true);
        lblStatus.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(16, 185, 129), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        pnlHeader.add(pnlTitle, BorderLayout.WEST);
        pnlHeader.add(lblStatus, BorderLayout.EAST);
        add(pnlHeader, BorderLayout.NORTH);

        // Center Grid Cards Panel
        var pnlGrid = new JPanel(new GridLayout(3, 2, 14, 14));
        pnlGrid.setBackground(UITheme.SLATE_50);
        pnlGrid.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        var btnPatient = createInteractiveCard(
                "🧑‍⚕️ Patient Management",
                "Admissions, Demographics & Compliance Tracking",
                "LIVE DIRECTORY",
                UITheme.PATIENT_SKY
        );
        var btnDoctor = createInteractiveCard(
                "🩺 Medical Faculty & Staff",
                "Specialist Registry, Rosters & Ward Availability",
                "FACULTY ROSTER",
                UITheme.SURGICAL_TEAL
        );
        var btnAppointment = createInteractiveCard(
                "📅 Appointment Scheduler",
                "Greedy Interval Scheduling & Conflict Elimination",
                "O(N log N) GREEDY",
                UITheme.SCHED_BLUE
        );
        var btnEmergency = createInteractiveCard(
                "🚨 Emergency Triage System",
                "Max-Heap PriorityQueue Severity Dispatch Bay",
                "PRIORITY QUEUE",
                UITheme.TRIAGE_RED
        );
        var btnBilling = createInteractiveCard(
                "🧾 Invoicing & Medical Tariffs",
                "Itemized Receipts, +50% Critical Care Surcharge",
                "TARIFF ENGINE",
                UITheme.TARIFF_AMBER
        );
        var btnOpt = createInteractiveCard(
                "⚡ Optimization Suite (DSA)",
                "Dijkstra Transit Routing & 0/1 Knapsack DP Allocation",
                "DIJKSTRA + DP",
                UITheme.OPTIM_INDIGO
        );

        pnlGrid.add(btnPatient);
        pnlGrid.add(btnDoctor);
        pnlGrid.add(btnAppointment);
        pnlGrid.add(btnEmergency);
        pnlGrid.add(btnBilling);
        pnlGrid.add(btnOpt);

        add(pnlGrid, BorderLayout.CENTER);

        // Bottom Demonstration Seeder Bar
        var pnlFooter = new JPanel(new BorderLayout());
        pnlFooter.setBackground(Color.WHITE);
        pnlFooter.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.SLATE_200),
                BorderFactory.createEmptyBorder(12, 24, 16, 24)
        ));

        var btnDemo = new JButton("🎓 Load Academic Demonstration Dataset (1-Click Viva Setup)");
        UITheme.attachButtonTransitions(
                btnDemo,
                new Color(109, 40, 217), // Violet 700
                new Color(124, 58, 237), // Violet 600 hover
                new Color(91, 33, 182)   // Violet 800 click
        );
        btnDemo.setPreferredSize(new Dimension(720, 38));
        pnlFooter.add(btnDemo, BorderLayout.CENTER);

        add(pnlFooter, BorderLayout.SOUTH);

        // Action Listeners
        btnPatient.addActionListener(e -> new PatientFrame(controller));
        btnDoctor.addActionListener(e -> new DoctorFrame(controller));
        btnAppointment.addActionListener(e -> new AppointmentFrame(controller));
        btnEmergency.addActionListener(e -> new EmergencyFrame(controller));
        btnBilling.addActionListener(e -> new BillingFrame(controller));
        btnOpt.addActionListener(e -> new OptimizationFrame(controller));
        btnDemo.addActionListener(e -> seedDemoData());

        setVisible(true);
    }

    private JButton createInteractiveCard(String title, String subtitle, String tag, Color accentColor) {
        var card = new JButton();
        card.setLayout(new BorderLayout(8, 4));

        var pnlTop = new JPanel(new BorderLayout());
        pnlTop.setOpaque(false);

        var lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTitle.setForeground(accentColor);

        var lblTag = new JLabel(" " + tag + " ");
        lblTag.setFont(UITheme.FONT_SECTION_TAG);
        lblTag.setForeground(UITheme.SLATE_700);
        lblTag.setBackground(UITheme.SLATE_100);
        lblTag.setOpaque(true);
        lblTag.setBorder(BorderFactory.createLineBorder(UITheme.SLATE_200, 1));

        pnlTop.add(lblTitle, BorderLayout.WEST);
        pnlTop.add(lblTag, BorderLayout.EAST);

        var lblSub = new JLabel(subtitle);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblSub.setForeground(UITheme.SLATE_500);

        card.add(pnlTop, BorderLayout.NORTH);
        card.add(lblSub, BorderLayout.SOUTH);

        // Attach smooth interactive transitions
        UITheme.attachCardTransitions(card, Color.WHITE, new Color(248, 250, 252), UITheme.SLATE_200, accentColor);

        return card;
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
                    ✔ 5 Patients Enrolled
                    ✔ 7 Appointments Scheduled (Interval Scheduling ready)
                    ✔ 3 Emergency Cases Triaged in PriorityQueue
                    """,
                    "Dataset Seeded Successfully",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
