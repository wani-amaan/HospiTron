package ui;

import controller.HospitalController;
import model.Appointment;
import util.ValidationUtil;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Modern Java Appointment Management & Interval Scheduling Frame.
 */
public final class AppointmentFrame extends JFrame {

    private final JTextField txtAID;
    private final JTextField txtPID;
    private final JTextField txtDID;
    private final JTextField txtDate;
    private final JTextField txtTime;
    private final HospitalController controller;

    public AppointmentFrame(HospitalController controller) {
        this.controller = controller;

        setTitle("Appointment Module - HOSPITRON");
        setSize(520, 440);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(235, 245, 255));

        var title = new JLabel("APPOINTMENT MANAGEMENT");
        title.setBounds(130, 15, 300, 25);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        add(title);

        var l1 = new JLabel("Appointment ID:");
        l1.setBounds(50, 60, 120, 25);
        add(l1);

        txtAID = new JTextField();
        txtAID.setBounds(180, 60, 250, 26);
        add(txtAID);

        var l2 = new JLabel("Patient ID:");
        l2.setBounds(50, 100, 120, 25);
        add(l2);

        txtPID = new JTextField();
        txtPID.setBounds(180, 100, 250, 26);
        add(txtPID);

        var l3 = new JLabel("Doctor ID:");
        l3.setBounds(50, 140, 120, 25);
        add(l3);

        txtDID = new JTextField();
        txtDID.setBounds(180, 140, 250, 26);
        add(txtDID);

        var l4 = new JLabel("Date (dd-MM-yyyy):");
        l4.setBounds(50, 180, 130, 25);
        add(l4);

        txtDate = new JTextField("31-10-2026");
        txtDate.setBounds(180, 180, 250, 26);
        add(txtDate);

        var l5 = new JLabel("Time (HH:mm):");
        l5.setBounds(50, 220, 120, 25);
        add(l5);

        txtTime = new JTextField("10:00");
        txtTime.setBounds(180, 220, 250, 26);
        add(txtTime);

        var btnBook = new JButton("Book Appointment");
        btnBook.setBounds(50, 275, 180, 32);
        add(btnBook);

        var btnView = new JButton("View Appointments");
        btnView.setBounds(250, 275, 180, 32);
        add(btnView);

        var btnOptimize = new JButton("⚡ Optimize Schedule (Interval Scheduling)");
        btnOptimize.setBounds(70, 325, 340, 34);
        btnOptimize.setBackground(new Color(46, 125, 50));
        btnOptimize.setForeground(Color.WHITE);
        add(btnOptimize);

        btnBook.addActionListener(e -> bookAppointment());
        btnView.addActionListener(e -> viewAppointments());
        btnOptimize.addActionListener(e -> optimizeSchedule());

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
                    "Please enter valid numeric IDs and proper Date/Time format (dd-MM-yyyy HH:mm).", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewAppointments() {
        var list = controller.getAllAppointments();

        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No appointments currently on record.", "Appointment Records", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        var sb = new StringBuilder("""
                ========================================================================
                                     HOSPITRON BOOKED APPOINTMENTS
                ========================================================================
                """);

        for (Appointment a : list) {
            sb.append(String.format("• Appt #%-5d | Patient ID: %-5d | Doctor ID: %-5d | Slot: %s %s | Status: %s\n",
                    a.appointmentId(), a.patientId(), a.doctorId(), a.date(), a.time(), a.status()));
        }

        sb.append("========================================================================\n");
        sb.append("Total Appointments: ").append(list.size());

        JOptionPane.showMessageDialog(this, sb.toString(), "All Appointments", JOptionPane.INFORMATION_MESSAGE);
    }

    private void optimizeSchedule() {
        try {
            var optimized = controller.getOptimizedAppointments();

            if (optimized.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No appointments available with valid Date & Time to optimize.",
                        "Schedule Optimizer", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            var sb = new StringBuilder("""
                    ========================================================================
                                    INTERVAL SCHEDULING (GREEDY OPTIMIZATION)
                    ========================================================================
                    Mutually compatible, conflict-free appointment schedule:

                    """);

            for (Appointment a : optimized) {
                sb.append(String.format("✔ Slot: %s %-5s -> Appt #%-5d (Patient ID: %-5d, Doctor ID: %-5d)\n",
                        a.date(), a.time(), a.appointmentId(), a.patientId(), a.doctorId()));
            }

            sb.append("""

                    ------------------------------------------------------------------------
                    Algorithm: Greedy Activity/Interval Selection (O(N log N) Sorting by finish time)
                    Optimal Non-Overlapping Slots Scheduled: %d
                    ========================================================================
                    """.formatted(optimized.size()));

            JOptionPane.showMessageDialog(this, sb.toString(), "Optimal Conflict-Free Schedule", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error optimizing schedule: " + ex.getMessage(), "Optimization Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}