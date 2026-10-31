package ui;

import controller.HospitalController;
import model.Patient;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PatientFrame extends JFrame {

    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtAge;

    private HospitalController controller;

    public PatientFrame(HospitalController controller) {
        this.controller = controller;

        setTitle("Patient Module - HOSPITRON");
        setSize(500, 350);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        getContentPane().setBackground(new Color(240, 248, 255));

        JLabel title = new JLabel("PATIENT MANAGEMENT");
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

        JLabel lblAge = new JLabel("Age:");
        lblAge.setBounds(50, 150, 100, 25);
        add(lblAge);

        txtAge = new JTextField();
        txtAge.setBounds(150, 150, 200, 25);
        add(txtAge);

        JButton btnAdd = new JButton("Add Patient");
        btnAdd.setBounds(50, 200, 130, 30);
        add(btnAdd);

        JButton btnView = new JButton("View Patients");
        btnView.setBounds(200, 200, 130, 30);
        add(btnView);

        btnAdd.addActionListener(e -> addPatient());
        btnView.addActionListener(e -> viewPatients());

        setVisible(true);
    }

    private void addPatient() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String name = txtName.getText().trim();
            int age = Integer.parseInt(txtAge.getText().trim());

            util.ValidationUtil.validateId(id);
            util.ValidationUtil.validateName(name);
            util.ValidationUtil.validateAge(age);
            util.ValidationUtil.foreignerCheck(name);

            controller.addPatient(id, name, age);

            JOptionPane.showMessageDialog(this, "Patient Added Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);

            txtId.setText("");
            txtName.setText("");
            txtAge.setText("");

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for ID and Age.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewPatients() {
        List<Patient> list = controller.getAllPatients();

        StringBuilder sb = new StringBuilder();

        for (Patient p : list) {
            sb.append("ID: ").append(p.getId())
              .append(" | Name: ").append(p.getName())
              .append(" | Age: ").append(p.getAge())
              .append("\n");
        }

        JOptionPane.showMessageDialog(this,
                sb.length() == 0 ? "No Patients Found" : sb.toString());
    }
}