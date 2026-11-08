package ui;

import controller.HospitalController;
import model.Patient;
import util.ValidationUtil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
 * Modern, classy Patient Management Interface with live JTable synchronization.
 */
public final class PatientFrame extends JFrame {

    private final JTextField txtId;
    private final JTextField txtName;
    private final JTextField txtAge;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final HospitalController controller;

    public PatientFrame(HospitalController controller) {
        this.controller = controller;

        setTitle("Patient Directory & Admission - HOSPITRON");
        setSize(780, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header Panel
        var pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(new Color(2, 132, 199)); // Modern Sky 600
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));

        var lblTitle = new JLabel("🧑‍⚕️ PATIENT REGISTRATION & DIRECTORY");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblTitle.setForeground(Color.WHITE);

        var lblSub = new JLabel("Central Inpatient & Outpatient Records");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(new Color(224, 242, 254));

        pnlHeader.add(lblTitle, BorderLayout.WEST);
        pnlHeader.add(lblSub, BorderLayout.EAST);
        add(pnlHeader, BorderLayout.NORTH);

        // Main Split Pane: Left Form, Right Table
        var pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBackground(new Color(248, 250, 252));
        pnlForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(226, 232, 240)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        pnlForm.setPreferredSize(new Dimension(300, 400));

        var gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0;
        var lblId = new JLabel("Patient ID");
        lblId.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblId.setForeground(new Color(51, 65, 85));
        pnlForm.add(lblId, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        txtId = new JTextField();
        txtId.setPreferredSize(new Dimension(240, 30));
        pnlForm.add(txtId, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        var lblName = new JLabel("Full Name");
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblName.setForeground(new Color(51, 65, 85));
        pnlForm.add(lblName, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        txtName = new JTextField();
        txtName.setPreferredSize(new Dimension(240, 30));
        pnlForm.add(txtName, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        var lblAge = new JLabel("Age (Years)");
        lblAge.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblAge.setForeground(new Color(51, 65, 85));
        pnlForm.add(lblAge, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        txtAge = new JTextField();
        txtAge.setPreferredSize(new Dimension(240, 30));
        pnlForm.add(txtAge, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        gbc.insets = new Insets(16, 5, 6, 5);
        var btnAdd = new JButton("Register Patient");
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnAdd.setBackground(new Color(2, 132, 199));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setPreferredSize(new Dimension(240, 34));
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdd.setFocusPainted(false);
        pnlForm.add(btnAdd, gbc);

        // Right Table Panel
        var pnlTable = new JPanel(new BorderLayout());
        pnlTable.setBackground(Color.WHITE);

        String[] cols = {"Patient ID", "Full Name", "Age", "Status"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(tableModel);
        table.setRowHeight(26);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(241, 245, 249));

        var scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        pnlTable.add(scrollPane, BorderLayout.CENTER);

        var splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlForm, pnlTable);
        splitPane.setDividerLocation(290);
        splitPane.setDividerSize(1);
        add(splitPane, BorderLayout.CENTER);

        // Event Actions
        btnAdd.addActionListener(e -> addPatient());

        // Initial Data Load
        refreshTable();

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
            refreshTable();

            JOptionPane.showMessageDialog(this, "Patient #" + id + " registered successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

            txtId.setText("");
            txtName.setText("");
            txtAge.setText("");

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Warning", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for ID and Age.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Patient p : controller.getAllPatients()) {
            tableModel.addRow(new Object[]{p.id(), p.name(), p.age() + " yrs", "Active Inpatient"});
        }
    }
}
