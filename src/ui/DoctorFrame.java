package ui;

import controller.HospitalController;
import model.Doctor;
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
 * Modern, classy Doctor Management Interface with live JTable synchronization.
 */
public final class DoctorFrame extends JFrame {

    private final JTextField txtId;
    private final JTextField txtName;
    private final JTextField txtSpec;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final HospitalController controller;

    public DoctorFrame(HospitalController controller) {
        this.controller = controller;

        setTitle("Medical Faculty & Doctor Directory - HOSPITRON");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header Panel
        var pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(new Color(13, 148, 136)); // Teal 600
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));

        var lblTitle = new JLabel("🩺 MEDICAL FACULTY & SPECIALISTS");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblTitle.setForeground(Color.WHITE);

        var lblSub = new JLabel("Clinician Registry & Roster Management");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(new Color(204, 251, 241)); // Teal 100

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
        pnlForm.setPreferredSize(new Dimension(310, 400));

        var gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0;
        var lblId = new JLabel("Doctor ID");
        lblId.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblId.setForeground(new Color(51, 65, 85));
        pnlForm.add(lblId, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        txtId = new JTextField();
        txtId.setPreferredSize(new Dimension(250, 30));
        pnlForm.add(txtId, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        var lblName = new JLabel("Doctor Full Name");
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblName.setForeground(new Color(51, 65, 85));
        pnlForm.add(lblName, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        txtName = new JTextField();
        txtName.setPreferredSize(new Dimension(250, 30));
        pnlForm.add(txtName, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        var lblSpec = new JLabel("Specialization / Department");
        lblSpec.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblSpec.setForeground(new Color(51, 65, 85));
        pnlForm.add(lblSpec);

        gbc.gridx = 0; gbc.gridy = 5;
        txtSpec = new JTextField();
        txtSpec.setPreferredSize(new Dimension(250, 30));
        pnlForm.add(txtSpec, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        gbc.insets = new Insets(16, 5, 6, 5);
        var btnAdd = new JButton("Register Doctor");
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnAdd.setBackground(new Color(13, 148, 136));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setPreferredSize(new Dimension(250, 34));
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdd.setFocusPainted(false);
        pnlForm.add(btnAdd, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        gbc.insets = new Insets(6, 5, 5, 5);
        var btnRefresh = new JButton("Refresh Roster");
        btnRefresh.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnRefresh.setPreferredSize(new Dimension(250, 30));
        btnRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pnlForm.add(btnRefresh, gbc);

        // Right Table Panel
        var pnlTable = new JPanel(new BorderLayout());
        pnlTable.setBackground(Color.WHITE);

        String[] cols = {"Doctor ID", "Doctor Name", "Specialization", "Availability Status"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(tableModel);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(241, 245, 249));

        var scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        pnlTable.add(scrollPane, BorderLayout.CENTER);

        var splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlForm, pnlTable);
        splitPane.setDividerLocation(310);
        splitPane.setDividerSize(1);
        add(splitPane, BorderLayout.CENTER);

        // Event Actions
        btnAdd.addActionListener(e -> addDoctor());
        btnRefresh.addActionListener(e -> refreshTable());

        // Initial Data Load
        refreshTable();
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
            refreshTable();

            JOptionPane.showMessageDialog(this,
                    "Dr. " + name + " (" + spec + ") registered successfully.",
                    "Doctor Registered", JOptionPane.INFORMATION_MESSAGE);

            txtId.setText("");
            txtName.setText("");
            txtSpec.setText("");

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric ID.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Doctor d : controller.getAllDoctors()) {
            tableModel.addRow(new Object[]{
                    d.id(),
                    "Dr. " + d.name(),
                    d.specialization(),
                    d.available() ? "● Available on Duty" : "○ On Leave"
            });
        }
    }
}
