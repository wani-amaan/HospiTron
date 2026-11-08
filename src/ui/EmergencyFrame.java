package ui;

import controller.HospitalController;
import model.EmergencyCase;
import util.ValidationUtil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Modern, classy Emergency Triage Interface with PriorityQueue Max-Heap live tracking.
 */
public final class EmergencyFrame extends JFrame {

    private final JTextField txtPatientId;
    private final JTextField txtName;
    private final JTextField txtSeverity;
    private final JLabel lblQueueCount;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final HospitalController controller;

    public EmergencyFrame(HospitalController controller) {
        this.controller = controller;

        setTitle("Emergency Triage Center - HOSPITRON (PriorityQueue Heap)");
        setSize(860, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header Panel
        var pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(new Color(220, 38, 38)); // Red 600
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));

        var lblTitle = new JLabel("🚨 EMERGENCY TRIAGE & TRAUMA DISPATCH");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblTitle.setForeground(Color.WHITE);

        var lblSub = new JLabel("PriorityQueue Max-Heap (O(log N) Enqueue & Dequeue)");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(new Color(254, 226, 226)); // Red 100

        pnlHeader.add(lblTitle, BorderLayout.WEST);
        pnlHeader.add(lblSub, BorderLayout.EAST);
        add(pnlHeader, BorderLayout.NORTH);

        // Main Split Pane: Left Form, Right Table
        var pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBackground(new Color(254, 242, 242)); // Red 50
        pnlForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(254, 202, 202)),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));
        pnlForm.setPreferredSize(new Dimension(320, 400));

        var gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 5, 4, 5);

        gbc.gridx = 0; gbc.gridy = 0;
        var lblId = new JLabel("Patient ID");
        lblId.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblId.setForeground(new Color(69, 10, 10));
        pnlForm.add(lblId, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        txtPatientId = new JTextField();
        txtPatientId.setPreferredSize(new Dimension(260, 28));
        pnlForm.add(txtPatientId, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        var lblName = new JLabel("Patient Name");
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblName.setForeground(new Color(69, 10, 10));
        pnlForm.add(lblName, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        txtName = new JTextField();
        txtName.setPreferredSize(new Dimension(260, 28));
        pnlForm.add(txtName, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        var lblSev = new JLabel("Severity Score (1 to 10)");
        lblSev.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblSev.setForeground(new Color(69, 10, 10));
        pnlForm.add(lblSev, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        txtSeverity = new JTextField();
        txtSeverity.setPreferredSize(new Dimension(260, 28));
        pnlForm.add(txtSeverity, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        gbc.insets = new Insets(14, 5, 5, 5);
        var btnAdd = new JButton("🚨 Enqueue Emergency Case");
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnAdd.setBackground(new Color(220, 38, 38));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setPreferredSize(new Dimension(260, 34));
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdd.setFocusPainted(false);
        pnlForm.add(btnAdd, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        gbc.insets = new Insets(5, 5, 10, 5);
        var btnProcess = new JButton("⚡ Dispatch Highest Priority (Poll)");
        btnProcess.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnProcess.setBackground(new Color(30, 41, 59)); // Slate 800
        btnProcess.setForeground(Color.WHITE);
        btnProcess.setPreferredSize(new Dimension(260, 34));
        btnProcess.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnProcess.setFocusPainted(false);
        pnlForm.add(btnProcess, gbc);

        // Acuity Legend Box
        gbc.gridx = 0; gbc.gridy = 8;
        var pnlLegend = new JPanel(new GridLayout(3, 1, 2, 2));
        pnlLegend.setBackground(new Color(254, 226, 226));
        pnlLegend.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(252, 165, 165)),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));

        var lA = new JLabel("• Level 8-10: CRITICAL (Resuscitation Bay)");
        lA.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lA.setForeground(new Color(185, 28, 28));
        var lB = new JLabel("• Level 5-7:  URGENT (Trauma Center)");
        lB.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lB.setForeground(new Color(194, 65, 12));
        var lC = new JLabel("• Level 1-4:  STABLE (Sub-acute Care)");
        lC.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lC.setForeground(new Color(71, 85, 105));

        pnlLegend.add(lA);
        pnlLegend.add(lB);
        pnlLegend.add(lC);
        pnlForm.add(pnlLegend, gbc);

        // Right Table Panel
        var pnlRight = new JPanel(new BorderLayout());
        pnlRight.setBackground(Color.WHITE);

        var pnlBar = new JPanel(new BorderLayout());
        pnlBar.setBackground(new Color(241, 245, 249));
        pnlBar.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));

        lblQueueCount = new JLabel("Active Triage Queue: 0 Patients Waiting");
        lblQueueCount.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblQueueCount.setForeground(new Color(71, 85, 105));
        pnlBar.add(lblQueueCount, BorderLayout.WEST);
        pnlRight.add(pnlBar, BorderLayout.NORTH);

        String[] cols = {"Priority Rank", "Patient ID", "Patient Name", "Severity Score", "Triage Code"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(tableModel);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(248, 250, 252));

        var scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        pnlRight.add(scrollPane, BorderLayout.CENTER);

        var splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlForm, pnlRight);
        splitPane.setDividerLocation(320);
        splitPane.setDividerSize(1);
        add(splitPane, BorderLayout.CENTER);

        // Actions
        btnAdd.addActionListener(e -> addEmergency());
        btnProcess.addActionListener(e -> processEmergency());

        // Initial Load
        refreshQueueTable();
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
            refreshQueueTable();

            JOptionPane.showMessageDialog(this,
                    """
                    EMERGENCY CASE ENQUEUED
                    Patient placed into PriorityQueue Max-Heap.
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
            refreshQueueTable();

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

    private void refreshQueueTable() {
        tableModel.setRowCount(0);
        List<EmergencyCase> snapshot = controller.getEmergencyQueueSnapshot();

        int rank = 1;
        for (EmergencyCase ec : snapshot) {
            String triageCode;
            if (ec.severity() >= 8) {
                triageCode = "🔴 CODE RED (Critical)";
            } else if (ec.severity() >= 5) {
                triageCode = "🟠 CODE YELLOW (Urgent)";
            } else {
                triageCode = "🟢 CODE GREEN (Standard)";
            }

            tableModel.addRow(new Object[]{
                    "Priority #" + rank,
                    ec.patientId(),
                    ec.name(),
                    ec.severity() + " / 10",
                    triageCode
            });
            rank++;
        }

        lblQueueCount.setText("Live Max-Heap Queue: " + snapshot.size() + " Patients Awaiting Dispatch");
    }
}
