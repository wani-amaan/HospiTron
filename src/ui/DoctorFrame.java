package ui;

import controller.HospitalController;
import model.Doctor;
import util.UITheme;
import util.ValidationUtil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
 * Modern, classy Doctor Management Interface with live JTable synchronization and smooth transitions.
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

        setTitle("Medical Faculty & Clinician Roster - HOSPITRON");
        setSize(840, 520);
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

        var lblTag = new JLabel("CLINICAL SPECIALISTS & ATTENDING PHYSICIANS");
        lblTag.setFont(UITheme.FONT_SECTION_TAG);
        lblTag.setForeground(new Color(45, 212, 191)); // Teal 400

        var lblTitle = new JLabel("🩺 MEDICAL FACULTY & DOCTOR ROSTER");
        lblTitle.setFont(UITheme.FONT_HEADER_TITLE);
        lblTitle.setForeground(Color.WHITE);

        var lblSub = new JLabel("Departmental Directory, Specialist Allocations & Ward Availability");
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
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        pnlForm.setPreferredSize(new Dimension(310, 420));

        var gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 5, 4, 5);

        gbc.gridx = 0; gbc.gridy = 0;
        var lblId = new JLabel("DOCTOR ID (Numeric)");
        lblId.setFont(UITheme.FONT_FORM_LABEL);
        lblId.setForeground(UITheme.SLATE_700);
        pnlForm.add(lblId, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        txtId = new JTextField();
        UITheme.styleInputField(txtId);
        txtId.setPreferredSize(new Dimension(250, 32));
        pnlForm.add(txtId, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        var lblName = new JLabel("DOCTOR FULL NAME");
        lblName.setFont(UITheme.FONT_FORM_LABEL);
        lblName.setForeground(UITheme.SLATE_700);
        pnlForm.add(lblName, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        txtName = new JTextField();
        UITheme.styleInputField(txtName);
        txtName.setPreferredSize(new Dimension(250, 32));
        pnlForm.add(txtName, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        var lblSpec = new JLabel("SPECIALIZATION / CLINICAL DEPT");
        lblSpec.setFont(UITheme.FONT_FORM_LABEL);
        lblSpec.setForeground(UITheme.SLATE_700);
        pnlForm.add(lblSpec, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        txtSpec = new JTextField();
        UITheme.styleInputField(txtSpec);
        txtSpec.setPreferredSize(new Dimension(250, 32));
        pnlForm.add(txtSpec, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        gbc.insets = new Insets(16, 5, 6, 5);
        var btnAdd = new JButton("Register Medical Staff");
        UITheme.attachButtonTransitions(btnAdd, UITheme.SURGICAL_TEAL, UITheme.TEAL_HOVER, UITheme.TEAL_CLICK);
        btnAdd.setPreferredSize(new Dimension(250, 36));
        pnlForm.add(btnAdd, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        gbc.insets = new Insets(6, 5, 5, 5);
        var btnRefresh = new JButton("Refresh Roster");
        UITheme.attachButtonTransitions(btnRefresh, UITheme.SLATE_800, UITheme.SLATE_700, UITheme.SLATE_900);
        btnRefresh.setPreferredSize(new Dimension(250, 32));
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
        UITheme.styleTable(table);

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
                    "Physician Registered",
                    JOptionPane.INFORMATION_MESSAGE);

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
                    "DOC-" + d.id(),
                    "Dr. " + d.name(),
                    d.specialization(),
                    d.available() ? "● Available on Duty" : "○ On Leave"
            });
        }
    }
}
