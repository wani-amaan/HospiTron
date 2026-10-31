package ui;

import controller.HospitalController;
import model.Doctor;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DoctorFrame extends JFrame {

    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtSpec;

    private HospitalController controller;

    public DoctorFrame(HospitalController controller) {
        this.controller = controller;

        setTitle("Doctor Module - HOSPITRON");
        setSize(500, 350);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        getContentPane().setBackground(new Color(240, 248, 255));

        JLabel title = new JLabel("DOCTOR MANAGEMENT");
        title.setBounds(150, 20, 250, 30);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        add(title);

        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(50, 70, 100, 25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(150, 70, 200, 25);
        add(txtId);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(50, 110, 100, 25);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(150, 110, 200, 25);
        add(txtName);

        JLabel lblSpec = new JLabel("Specialization:");
        lblSpec.setBounds(50, 150, 120, 25);
        add(lblSpec);

        txtSpec = new JTextField();
        txtSpec.setBounds(150, 150, 200, 25);
        add(txtSpec);

        JButton btnAdd = new JButton("Add Doctor");
        btnAdd.setBounds(50, 200, 130, 30);
        add(btnAdd);

        JButton btnView = new JButton("View Doctors");
        btnView.setBounds(200, 200, 130, 30);
        add(btnView);

        btnAdd.addActionListener(e -> addDoctor());
        btnView.addActionListener(e -> viewDoctors());

        setVisible(true);
    }

    private void addDoctor() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String name = txtName.getText().trim();
            String spec = txtSpec.getText().trim();

            util.ValidationUtil.validateId(id);
            util.ValidationUtil.validateName(name);
            if (spec.isEmpty()) {
                throw new IllegalArgumentException("Specialization cannot be empty.");
            }

            controller.addDoctor(id, name, spec);

            JOptionPane.showMessageDialog(this, "Doctor Added Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);

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
        List<Doctor> list = controller.getAllDoctors();

        StringBuilder sb = new StringBuilder();

        for (Doctor d : list) {
            sb.append("ID: ").append(d.getId())
              .append(" | Name: ").append(d.getName())
              .append(" | Spec: ").append(d.getSpecialization())
              .append("\n");
        }

        JOptionPane.showMessageDialog(this,
                sb.length() == 0 ? "No Doctors Found" : sb.toString());
    }
}