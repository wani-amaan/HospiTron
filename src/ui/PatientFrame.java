package ui;

import controller.HospitalController;
import model.Patient;
import util.ValidationUtil;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Modern Java Patient Management Frame.
 */
public final class PatientFrame extends JFrame {

    private final JTextField txtId;
    private final JTextField txtName;
    private final JTextField txtAge;
    private final HospitalController controller;

    public PatientFrame(HospitalController controller) {
        this.controller = controller;

        setTitle("Patient Module - HOSPITRON");
        setSize(500, 360);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(240, 248, 255));

        var title = new JLabel("PATIENT MANAGEMENT");
        title.setBounds(150, 20, 250, 30);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        add(title);

        var lblId = new JLabel("ID:");
        lblId.setBounds(50, 70, 100, 25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(150, 70, 220, 26);
        add(txtId);

        var lblName = new JLabel("Name:");
        lblName.setBounds(50, 110, 100, 25);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(150, 110, 220, 26);
        add(txtName);

        var lblAge = new JLabel("Age:");
        lblAge.setBounds(50, 150, 100, 25);
        add(lblAge);

        txtAge = new JTextField();
        txtAge.setBounds(150, 150, 220, 26);
        add(txtAge);

        var btnAdd = new JButton("Add Patient");
        btnAdd.setBounds(50, 210, 140, 32);
        add(btnAdd);

        var btnView = new JButton("View Patients");
        btnView.setBounds(210, 210, 140, 32);
        add(btnView);

        btnAdd.addActionListener(e -> addPatient());
        btnView.addActionListener(e -> viewPatients());

        setVisible(true);
    }

    private void addPatient() {
        try {
            int id = Integer.parseInt(txtId.getText().strip());
            String name = txtName.getText().strip();
            int age = Integer.parseInt(txtAge.getText().strip());

            ValidationUtil.validateId(id);
            ValidationUtil.validateName(name);
            ValidationUtil.validateAge(age);
            ValidationUtil.foreignerCheck(name);

            controller.addPatient(id, name, age);

            JOptionPane.showMessageDialog(this, "Patient #" + id + " (" + name + ") registered successfully.", "Registration Complete", JOptionPane.INFORMATION_MESSAGE);

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
        var list = controller.getAllPatients();

        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No patients currently registered in system.", "Patient Records", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        var sb = new StringBuilder("""
                =================================================
                            HOSPITRON REGISTERED PATIENTS
                =================================================
                """);

        for (Patient p : list) {
            sb.append(String.format("• ID: %-5d | Name: %-20s | Age: %d\n", p.id(), p.name(), p.age()));
        }

        sb.append("=================================================\n");
        sb.append("Total Registered Patients: ").append(list.size());

        JOptionPane.showMessageDialog(this, sb.toString(), "Registered Patients", JOptionPane.INFORMATION_MESSAGE);
    }
}