package ui;

import controller.HospitalController;
import model.Appointment;
import util.UITheme;
import util.ValidationUtil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
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
 * Modern, classy Appointment Management Interface with Greedy Interval Scheduling and smooth transitions.
 */
public final class AppointmentFrame extends JFrame {

    private final JTextField txtAID;
    private final JTextField txtPID;
    private final JTextField txtDID;
    private final JTextField txtDate;
    private final JTextField txtTime;
    private final JLabel lblFilterStatus;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final HospitalController controller;

    public AppointmentFrame(HospitalController controller) {
        this.controller = controller;

        setTitle("Outpatient Appointments & Timetable Optimizer - HOSPITRON");
        setSize(880, 580);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header Panel - Vertical Stack
        var pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(UITheme.SLATE_900);
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(15, 24, 15, 24));

        var pnlTitleGroup = new JPanel();
        pnlTitleGroup.setLayout(new BoxLayout(pnlTitleGroup, BoxLayout.Y_AXIS));
        pnlTitleGroup.setOpaque(false);

        var lblTag = new JLabel("OUTPATIENT CLINIC & SCHEDULING ENGINE");
        lblTag.setFont(UITheme.FONT_SECTION_TAG);
        lblTag.setForeground(new Color(129, 140, 248)); // Indigo 400

        var lblTitle = new JLabel("📅 APPOINTMENT MANAGEMENT & INTERVAL OPTIMIZER");
        lblTitle.setFont(UITheme.FONT_HEADER_TITLE);
        lblTitle.setForeground(Color.WHITE);

        var lblSub = new JLabel("Greedy Interval Scheduling Engine (Earliest Finish Time First • O(N log N))");
        lblSub.setFont(UITheme.FONT_HEADER_SUB);
        lblSub.setForeground(UITheme.SLATE_500);

        pnlTitleGroup.add(lblTag);
        pnlTitleGroup.add(Box.createVerticalStrut(3));
        pnlTitleGroup.add(lblTitle);
        pnlTitleGroup.add(Box.createVerticalStrut(3));
        pnlTitleGroup.add(lblSub);

        pnlHeader.add(pnlTitleGroup, BorderLayout.WEST);
        add(pnlHeader, BorderLayout.NORTH);

        // Main Split Pane: Left Form, Right Table
        var pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBackground(UITheme.SLATE_50);
        pnlForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, UITheme.SLATE_200),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));
        pnlForm.setPreferredSize(new Dimension(320, 440));

        var gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 5, 4, 5);

        gbc.gridx = 0; gbc.gridy = 0;
        var l1 = new JLabel("APPOINTMENT ID");
        l1.setFont(UITheme.FONT_FORM_LABEL);
        l1.setForeground(UITheme.SLATE_700);
        pnlForm.add(l1, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        txtAID = new JTextField();
        UITheme.styleInputField(txtAID);
        txtAID.setPreferredSize(new Dimension(260, 30));
        pnlForm.add(txtAID, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        var l2 = new JLabel("PATIENT ID");
        l2.setFont(UITheme.FONT_FORM_LABEL);
        l2.setForeground(UITheme.SLATE_700);
        pnlForm.add(l2, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        txtPID = new JTextField();
        UITheme.styleInputField(txtPID);
        txtPID.setPreferredSize(new Dimension(260, 30));
        pnlForm.add(txtPID, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        var l3 = new JLabel("DOCTOR ID");
        l3.setFont(UITheme.FONT_FORM_LABEL);
        l3.setForeground(UITheme.SLATE_700);
        pnlForm.add(l3, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        txtDID = new JTextField();
        UITheme.styleInputField(txtDID);
        txtDID.setPreferredSize(new Dimension(260, 30));
        pnlForm.add(txtDID, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        var l4 = new JLabel("DATE (dd-MM-yyyy)");
        l4.setFont(UITheme.FONT_FORM_LABEL);
        l4.setForeground(UITheme.SLATE_700);
        pnlForm.add(l4, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        txtDate = new JTextField("31-10-2026");
        UITheme.styleInputField(txtDate);
        txtDate.setPreferredSize(new Dimension(260, 30));
        pnlForm.add(txtDate, gbc);

        gbc.gridx = 0; gbc.gridy = 8;
        var l5 = new JLabel("TIME (HH:mm)");
        l5.setFont(UITheme.FONT_FORM_LABEL);
        l5.setForeground(UITheme.SLATE_700);
        pnlForm.add(l5, gbc);

        gbc.gridx = 0; gbc.gridy = 9;
        txtTime = new JTextField("10:00");
        UITheme.styleInputField(txtTime);
        txtTime.setPreferredSize(new Dimension(260, 30));
        pnlForm.add(txtTime, gbc);

        gbc.gridx = 0; gbc.gridy = 10;
        gbc.insets = new Insets(14, 5, 4, 5);
        var btnBook = new JButton("Book Clinical Appointment");
        UITheme.attachButtonTransitions(btnBook, UITheme.SCHED_BLUE, UITheme.SCHED_HOVER, UITheme.SCHED_CLICK);
        btnBook.setPreferredSize(new Dimension(260, 34));
        pnlForm.add(btnBook, gbc);

        gbc.gridx = 0; gbc.gridy = 11;
        gbc.insets = new Insets(4, 5, 4, 5);
        var btnOptimize = new JButton("⚡ Run Interval Optimizer (Greedy)");
        UITheme.attachButtonTransitions(btnOptimize, UITheme.SUCCESS_EMERALD, UITheme.EMERALD_HOVER, UITheme.EMERALD_CLICK);
        btnOptimize.setPreferredSize(new Dimension(260, 34));
        pnlForm.add(btnOptimize, gbc);

        gbc.gridx = 0; gbc.gridy = 12;
        var btnAll = new JButton("Show All Bookings");
        UITheme.attachButtonTransitions(btnAll, UITheme.SLATE_800, UITheme.SLATE_700, UITheme.SLATE_900);
        btnAll.setPreferredSize(new Dimension(260, 30));
        pnlForm.add(btnAll, gbc);

        // Right Table Panel
        var pnlRight = new JPanel(new BorderLayout());
        pnlRight.setBackground(Color.WHITE);

        var pnlTableBar = new JPanel(new BorderLayout());
        pnlTableBar.setBackground(UITheme.SLATE_100);
        pnlTableBar.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));

        lblFilterStatus = new JLabel("Showing all registered appointments");
        lblFilterStatus.setFont(UITheme.FONT_BADGE);
        lblFilterStatus.setForeground(UITheme.SLATE_700);
        pnlTableBar.add(lblFilterStatus, BorderLayout.WEST);
        pnlRight.add(pnlTableBar, BorderLayout.NORTH);

        String[] cols = {"Appt ID", "Patient ID", "Doctor ID", "Date", "Time", "Schedule Status"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(tableModel);
        UITheme.styleTable(table);

        var scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        pnlRight.add(scrollPane, BorderLayout.CENTER);

        var splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlForm, pnlRight);
        splitPane.setDividerLocation(320);
        splitPane.setDividerSize(1);
        add(splitPane, BorderLayout.CENTER);

        // Actions
        btnBook.addActionListener(e -> bookAppointment());
        btnOptimize.addActionListener(e -> optimizeSchedule());
        btnAll.addActionListener(e -> populateTable(controller.getAllAppointments(), false));

        // Initial Load
        populateTable(controller.getAllAppointments(), false);
        setVisible(true);
    }

    private void bookAppointment() {
        try {
            int aid = Integer.parseInt(txtAID.getText().strip());
            int pid = Integer.parseInt(txtPID.getText().strip());
            int did = Integer.parseInt(txtDID.getText().strip());
            String date = txtDate.getText().strip();
            String time = txtTime.getText().strip();

            ValidationUtil.validateId(aid);
            ValidationUtil.validateId(pid);
            ValidationUtil.validateId(did);

            if (date.isBlank() || time.isBlank()) {
                throw new IllegalArgumentException("Date and Time cannot be blank.");
            }

            controller.bookAppointment(pid, did, aid, date, time);
            populateTable(controller.getAllAppointments(), false);

            JOptionPane.showMessageDialog(this,
                    "Appointment #" + aid + " booked successfully for " + date + " at " + time + ".",
                    "Booking Confirmed", JOptionPane.INFORMATION_MESSAGE);

            txtAID.setText("");
            txtPID.setText("");
            txtDID.setText("");

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Please enter valid numeric IDs and proper Date/Time format (dd-MM-yyyy HH:mm).",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void optimizeSchedule() {
        var opt = controller.getOptimizedAppointments();
        populateTable(opt, true);

        JOptionPane.showMessageDialog(this,
                """
                GREEDY INTERVAL SCHEDULING COMPLETE
                =================================================
                Total Confirmed Slots Selected : %d
                Algorithm : Earliest Finish Time First (O(N log N))
                Conflict Elimination : Non-overlapping appointments scheduled.
                """.formatted(opt.size()),
                "Optimization Result",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void populateTable(List<Appointment> list, boolean isOptimized) {
        tableModel.setRowCount(0);
        for (Appointment a : list) {
            tableModel.addRow(new Object[]{
                    "APT-" + a.appointmentId(),
                    "PID-" + a.patientId(),
                    "DOC-" + a.doctorId(),
                    a.date(),
                    a.time(),
                    isOptimized ? "✔ OPTIMAL (Conflict-Free)" : a.status()
            });
        }

        if (isOptimized) {
            lblFilterStatus.setText("⚡ Filtered View: " + list.size() + " Conflict-Free Slots (Earliest Finish Time First)");
            lblFilterStatus.setForeground(UITheme.SUCCESS_EMERALD);
        } else {
            lblFilterStatus.setText("Showing all " + list.size() + " registered appointments");
            lblFilterStatus.setForeground(UITheme.SLATE_700);
        }
    }
}
