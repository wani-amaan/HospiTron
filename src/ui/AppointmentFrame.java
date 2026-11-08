package ui;

import controller.HospitalController;
import model.Appointment;
import util.ValidationUtil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
 * Modern, classy Appointment Management Interface with Greedy Interval Scheduling.
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
        setSize(860, 560);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header Panel
        var pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(new Color(37, 99, 235)); // Blue 600
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));

        var lblTitle = new JLabel("📅 APPOINTMENT MANAGEMENT & SCHEDULING");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblTitle.setForeground(Color.WHITE);

        var lblSub = new JLabel("Greedy Interval Scheduling Engine (O(N log N))");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(new Color(219, 234, 254)); // Blue 100

        pnlHeader.add(lblTitle, BorderLayout.WEST);
        pnlHeader.add(lblSub, BorderLayout.EAST);
        add(pnlHeader, BorderLayout.NORTH);

        // Main Split Pane: Left Form, Right Table
        var pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBackground(new Color(248, 250, 252));
        pnlForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(226, 232, 240)),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));
        pnlForm.setPreferredSize(new Dimension(320, 420));

        var gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 5, 4, 5);

        gbc.gridx = 0; gbc.gridy = 0;
        var l1 = new JLabel("Appointment ID");
        l1.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l1.setForeground(new Color(51, 65, 85));
        pnlForm.add(l1, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        txtAID = new JTextField();
        txtAID.setPreferredSize(new Dimension(260, 28));
        pnlForm.add(txtAID, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        var l2 = new JLabel("Patient ID");
        l2.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l2.setForeground(new Color(51, 65, 85));
        pnlForm.add(l2, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        txtPID = new JTextField();
        txtPID.setPreferredSize(new Dimension(260, 28));
        pnlForm.add(txtPID, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        var l3 = new JLabel("Doctor ID");
        l3.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l3.setForeground(new Color(51, 65, 85));
        pnlForm.add(l3, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        txtDID = new JTextField();
        txtDID.setPreferredSize(new Dimension(260, 28));
        pnlForm.add(txtDID, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        var l4 = new JLabel("Date (dd-MM-yyyy)");
        l4.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l4.setForeground(new Color(51, 65, 85));
        pnlForm.add(l4, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        txtDate = new JTextField("31-10-2026");
        txtDate.setPreferredSize(new Dimension(260, 28));
        pnlForm.add(txtDate, gbc);

        gbc.gridx = 0; gbc.gridy = 8;
        var l5 = new JLabel("Time (HH:mm)");
        l5.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l5.setForeground(new Color(51, 65, 85));
        pnlForm.add(l5, gbc);

        gbc.gridx = 0; gbc.gridy = 9;
        txtTime = new JTextField("10:00");
        txtTime.setPreferredSize(new Dimension(260, 28));
        pnlForm.add(txtTime, gbc);

        gbc.gridx = 0; gbc.gridy = 10;
        gbc.insets = new Insets(12, 5, 4, 5);
        var btnBook = new JButton("Book Appointment");
        btnBook.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnBook.setBackground(new Color(37, 99, 235));
        btnBook.setForeground(Color.WHITE);
        btnBook.setPreferredSize(new Dimension(260, 32));
        btnBook.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBook.setFocusPainted(false);
        pnlForm.add(btnBook, gbc);

        gbc.gridx = 0; gbc.gridy = 11;
        gbc.insets = new Insets(4, 5, 4, 5);
        var btnOptimize = new JButton("⚡ Run Interval Optimizer");
        btnOptimize.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnOptimize.setBackground(new Color(16, 185, 129)); // Emerald 500
        btnOptimize.setForeground(Color.WHITE);
        btnOptimize.setPreferredSize(new Dimension(260, 32));
        btnOptimize.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnOptimize.setFocusPainted(false);
        pnlForm.add(btnOptimize, gbc);

        gbc.gridx = 0; gbc.gridy = 12;
        var btnAll = new JButton("Show All Bookings");
        btnAll.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnAll.setPreferredSize(new Dimension(260, 28));
        btnAll.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pnlForm.add(btnAll, gbc);

        // Right Table Panel
        var pnlRight = new JPanel(new BorderLayout());
        pnlRight.setBackground(Color.WHITE);

        var pnlTableBar = new JPanel(new BorderLayout());
        pnlTableBar.setBackground(new Color(241, 245, 249));
        pnlTableBar.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));

        lblFilterStatus = new JLabel("Showing all registered appointments");
        lblFilterStatus.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblFilterStatus.setForeground(new Color(71, 85, 105));
        pnlTableBar.add(lblFilterStatus, BorderLayout.WEST);
        pnlRight.add(pnlTableBar, BorderLayout.NORTH);

        String[] cols = {"Appt ID", "Patient ID", "Doctor ID", "Date", "Time", "Status"};
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
                    "#" + a.appointmentId(),
                    "Patient " + a.patientId(),
                    "Doctor " + a.doctorId(),
                    a.date(),
                    a.time(),
                    isOptimized ? "✔ COMPATIBLE (Optimized)" : a.status()
            });
        }

        if (isOptimized) {
            lblFilterStatus.setText("⚡ Filtered View: " + list.size() + " Conflict-Free Appointments (Interval Scheduling)");
            lblFilterStatus.setForeground(new Color(16, 185, 129));
        } else {
            lblFilterStatus.setText("Showing all " + list.size() + " registered appointments");
            lblFilterStatus.setForeground(new Color(71, 85, 105));
        }
    }
}
