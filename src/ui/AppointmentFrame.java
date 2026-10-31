package ui;

import controller.HospitalController;
import model.Appointment;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AppointmentFrame extends JFrame {

    private JTextField txtAID;
    private JTextField txtPID;
    private JTextField txtDID;
    private JTextField txtDate;
    private JTextField txtTime;

    private HospitalController controller;

    public AppointmentFrame(HospitalController controller) {
        this.controller = controller;

        setTitle("Appointment Module - HOSPITRON");
        setSize(520, 440);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        getContentPane().setBackground(new Color(235, 245, 255));

        JLabel title = new JLabel("APPOINTMENT MANAGEMENT");
        title.setBounds(130, 15, 300, 25);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        add(title);

        JLabel l1 = new JLabel("Appointment ID:");
        l1.setBounds(50, 60, 120, 25);
        add(l1);

        txtAID = new JTextField();
        txtAID.setBounds(180, 60, 250, 25);
        add(txtAID);

        JLabel l2 = new JLabel("Patient ID:");
        l2.setBounds(50, 100, 120, 25);
        add(l2);

        txtPID = new JTextField();
        txtPID.setBounds(180, 100, 250, 25);
        add(txtPID);

        JLabel l3 = new JLabel("Doctor ID:");
        l3.setBounds(50, 140, 120, 25);
        add(l3);

        txtDID = new JTextField();
        txtDID.setBounds(180, 140, 250, 25);
        add(txtDID);

        JLabel l4 = new JLabel("Date (dd-MM-yyyy):");
        l4.setBounds(50, 180, 130, 25);
        add(l4);

        txtDate = new JTextField("31-10-2026");
        txtDate.setBounds(180, 180, 250, 25);
        add(txtDate);

        JLabel l5 = new JLabel("Time (HH:mm):");
        l5.setBounds(50, 220, 120, 25);
        add(l5);

        txtTime = new JTextField("10:00");
        txtTime.setBounds(180, 220, 250, 25);
        add(txtTime);

        JButton btnBook = new JButton("Book Appointment");
        btnBook.setBounds(50, 275, 180, 32);
        add(btnBook);

        JButton btnView = new JButton("View Appointments");
        btnView.setBounds(250, 275, 180, 32);
        add(btnView);

        JButton btnOptimize = new JButton("⚡ Optimize Schedule (Interval Scheduling)");
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
            int aid = Integer.parseInt(txtAID.getText().trim());
            int pid = Integer.parseInt(txtPID.getText().trim());
            int did = Integer.parseInt(txtDID.getText().trim());
            String date = txtDate.getText().trim();
            String time = txtTime.getText().trim();

            util.ValidationUtil.validateId(aid);
            util.ValidationUtil.validateId(pid);
            util.ValidationUtil.validateId(did);

            if (date.isEmpty() || time.isEmpty()) {
                throw new IllegalArgumentException("Date and Time cannot be empty.");
            }

            controller.bookAppointment(pid, did, aid, date, time);

            JOptionPane.showMessageDialog(this,
                    "Appointment #" + aid + " Booked Successfully for " + date + " at " + time,
                    "Success", JOptionPane.INFORMATION_MESSAGE);

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
        List<Appointment> list = controller.getAllAppointments();

        StringBuilder sb = new StringBuilder();

        for (Appointment a : list) {
            sb.append("AID: ").append(a.getAppointmentId())
              .append(" | PID: ").append(a.getPatientId())
              .append(" | DID: ").append(a.getDoctorId())
              .append(" | Date: ").append(a.getDate())
              .append(" ").append(a.getTime())
              .append(" | Status: ").append(a.getStatus())
              .append("\n");
        }

        JOptionPane.showMessageDialog(this,
                sb.length() == 0 ? "No Appointments Found" : sb.toString(),
                "All Appointments", JOptionPane.INFORMATION_MESSAGE);
    }

    private void optimizeSchedule() {
        try {
            List<Appointment> optimized = controller.getOptimizedAppointments();

            if (optimized.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No appointments available with valid Date & Time to optimize.",
                        "Schedule Optimizer", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            StringBuilder sb = new StringBuilder();
            sb.append("INTERVAL SCHEDULING (GREEDY OPTIMIZATION)\n");
            sb.append("Mutually compatible, conflict-free appointment schedule:\n");
            sb.append("====================================================\n\n");

            for (Appointment a : optimized) {
                sb.append("• Slot: ").append(a.getDate()).append(" ").append(a.getTime())
                  .append(" -> Appointment #").append(a.getAppointmentId())
                  .append(" (Patient ID: ").append(a.getPatientId())
                  .append(", Doctor ID: ").append(a.getDoctorId()).append(")\n");
            }

            sb.append("\nTotal Optimized Non-Overlapping Slots: ").append(optimized.size());

            JOptionPane.showMessageDialog(this, sb.toString(), "Optimal Conflict-Free Schedule", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error optimizing schedule: " + ex.getMessage(), "Optimization Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}