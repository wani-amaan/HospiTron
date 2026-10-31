package ui;

import controller.HospitalController;
import model.Doctor;
import util.ValidationUtil;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Modern Java Doctor Management Frame.
 */
public final class DoctorFrame extends JFrame {

    private final JTextField txtId;
    private final JTextField txtName;
    private final JTextField txtSpec;
    private final HospitalController controller;

    public DoctorFrame(HospitalController controller) {
        this.controller = controller;

        setTitle("Doctor Module - HOSPITRON");
        setSize(500, 360);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(240, 248, 255));

        var title = new JLabel("DOCTOR MANAGEMENT");
        title.setBounds(150, 20, 250, 30);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        add(title);

        var lblId = new JLabel("ID:");
        lblId.setBounds(50, 70, 100, 25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(160, 70, 220, 26);
        add(txtId);

        var lblName = new JLabel("Name:");
        lblName.setBounds(50, 110, 100, 25);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(160, 110, 220, 26);
        add(txtName);

        var lblSpec = new JLabel("Specialization:");
        lblSpec.setBounds(50, 150, 120, 25);
        add(lblSpec);

        txtSpec = new JTextField();
        txtSpec.setBounds(160, 150, 220, 26);
        add(txtSpec);

        var btnAdd = new JButton("Add Doctor");
        btnAdd.setBounds(50, 210, 140, 32);
        add(btnAdd);

        var btnView = new JButton("View Doctors");
        btnView.setBounds(210, 210, 140, 32);
        add(btnView);

        btnAdd.addActionListener(e -> addDoctor());
        btnView.addActionListener(e -> viewDoctors());

        setVisible(true);
    }

    private void addDoctor() {
        try {
            int id = Integer.parseInt(txtId.getText().strip());
            String name = txtName.getText().strip();
            String spec = txtSpec.getText().strip();

            ValidationUtil.validateId(id);
            ValidationUtil.validateName(name);
            if (spec.isBlank()) {
                throw new IllegalArgumentException("Specialization cannot be blank.");
            }

            controller.addDoctor(id, name, spec);

            JOptionPane.showMessageDialog(this, "Dr. " + name + " (" + spec + ") added successfully.", "Doctor Registered", JOptionPane.INFORMATION_MESSAGE);

            txtId.setText("");
            txtName.setText("");
            txtSpec.setText("");

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric ID.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewDoctors() {
        var list = controller.getAllDoctors();

        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No doctors registered in the system.", "Doctor Records", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        var sb = new StringBuilder("""
                =================================================================
                                  HOSPITRON MEDICAL FACULTY
                =================================================================
                """);

        for (Doctor d : list) {
            sb.append(String.format("• ID: %-5d | Dr. %-20s | Spec: %-18s | Available: %s\n",
                    d.id(), d.name(), d.specialization(), (d.available() ? "YES" : "NO")));
        }

        sb.append("=================================================================\n");
        sb.append("Total Medical Staff: ").append(list.size());

        JOptionPane.showMessageDialog(this, sb.toString(), "Hospital Doctors", JOptionPane.INFORMATION_MESSAGE);
    }
}