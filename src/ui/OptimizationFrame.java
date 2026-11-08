package ui;

import algorithm.DijkstraRouting;
import algorithm.KnapsackAllocator;
import controller.HospitalController;
import util.UITheme;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Modern Java Optimization Suite Frame connecting Dijkstra and 0/1 Knapsack algorithms.
 * Completely eliminates header text clipping with vertical stacked layout and smooth transitions.
 */
public final class OptimizationFrame extends JFrame {

    private final HospitalController controller;
    private final JComboBox<String> cmbSource;
    private final JComboBox<String> cmbDest;
    private final JTextArea txtRouteOutput;
    private final JTextField txtCapacity;
    private final JTextArea txtResourceOutput;

    private static final String[] ZONES = {
            "0: Ambulance Bay / Main Gate",
            "1: Triage & Emergency Reception",
            "2: Trauma Center & OT",
            "3: Intensive Care Unit (ICU)",
            "4: Radiology & Diagnostics",
            "5: Inpatient Ward Block"
    };

    private static final int[][] HOSPITAL_GRAPH = {
            {0, 2, 6, 8, 4, 10},
            {2, 0, 3, 5, 3, 7},
            {6, 3, 0, 2, 4, 6},
            {8, 5, 2, 0, 5, 4},
            {4, 3, 4, 5, 0, 5},
            {10, 7, 6, 4, 5, 0}
    };

    private static final List<String> ITEMS = List.of(
            "ICU Ventilator",
            "Defibrillator Unit",
            "Vital Signs Monitor",
            "Hemodialysis Machine",
            "Portable X-Ray Unit",
            "Oxygen Concentrator Station"
    );
    private static final int[] WEIGHTS = {15, 10, 8, 25, 20, 12};
    private static final int[] VALUES  = {85, 60, 50, 95, 70, 65};

    public OptimizationFrame(HospitalController controller) {
        this.controller = controller;

        setTitle("Optimization Suite - HOSPITRON (Advanced DSA)");
        setSize(880, 640);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header Banner - Vertical Stack (Prevents ANY text overlap)
        var pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(UITheme.SLATE_900);
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));

        var pnlTitleGroup = new JPanel();
        pnlTitleGroup.setLayout(new BoxLayout(pnlTitleGroup, BoxLayout.Y_AXIS));
        pnlTitleGroup.setOpaque(false);

        var lblTag = new JLabel("DATA STRUCTURES & ALGORITHMS");
        lblTag.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblTag.setForeground(new Color(129, 140, 248)); // Indigo 400

        var lblTitle = new JLabel("⚡ CLINICAL OPERATIONS OPTIMIZATION SUITE");
        lblTitle.setFont(UITheme.FONT_HEADER_TITLE);
        lblTitle.setForeground(Color.WHITE);

        var lblSub = new JLabel("Graph Routing Engine (Dijkstra) & Resource Allocation (0/1 Knapsack DP)");
        lblSub.setFont(UITheme.FONT_HEADER_SUB);
        lblSub.setForeground(UITheme.SLATE_500);

        pnlTitleGroup.add(lblTag);
        pnlTitleGroup.add(Box.createVerticalStrut(3));
        pnlTitleGroup.add(lblTitle);
        pnlTitleGroup.add(Box.createVerticalStrut(3));
        pnlTitleGroup.add(lblSub);

        var lblBadge = new JLabel(" O((V+E)log V)  •  O(N·W) ");
        lblBadge.setFont(UITheme.FONT_MONO_CODE);
        lblBadge.setForeground(new Color(165, 180, 252)); // Indigo 200
        lblBadge.setBackground(new Color(30, 41, 59));
        lblBadge.setOpaque(true);
        lblBadge.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(67, 56, 202), 1),
                BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));

        pnlHeader.add(pnlTitleGroup, BorderLayout.WEST);
        pnlHeader.add(lblBadge, BorderLayout.EAST);
        add(pnlHeader, BorderLayout.NORTH);

        var tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabbedPane.setBackground(UITheme.SLATE_100);

        // ---------------- TAB 1: DIJKSTRA ROUTING ----------------
        var pnlDijkstra = new JPanel(new BorderLayout(14, 14));
        pnlDijkstra.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));
        pnlDijkstra.setBackground(UITheme.SLATE_50);

        var pnlControls = new JPanel(new GridBagLayout());
        pnlControls.setBackground(Color.WHITE);
        pnlControls.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.SLATE_200, 1),
                BorderFactory.createEmptyBorder(14, 18, 14, 18)
        ));

        var gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 8, 6, 8);

        gbc.gridx = 0; gbc.gridy = 0;
        var lblSrc = new JLabel("ORIGIN ZONE (Source):");
        lblSrc.setFont(UITheme.FONT_FORM_LABEL);
        lblSrc.setForeground(UITheme.SLATE_700);
        pnlControls.add(lblSrc, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        cmbSource = new JComboBox<>(ZONES);
        cmbSource.setFont(UITheme.FONT_INPUT);
        cmbSource.setPreferredSize(new Dimension(280, 32));
        cmbSource.setSelectedIndex(0);
        pnlControls.add(cmbSource, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        var lblDst = new JLabel("TARGET ZONE (Destination):");
        lblDst.setFont(UITheme.FONT_FORM_LABEL);
        lblDst.setForeground(UITheme.SLATE_700);
        pnlControls.add(lblDst, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        cmbDest = new JComboBox<>(ZONES);
        cmbDest.setFont(UITheme.FONT_INPUT);
        cmbDest.setPreferredSize(new Dimension(280, 32));
        cmbDest.setSelectedIndex(3); // ICU by default
        pnlControls.add(cmbDest, gbc);

        gbc.gridx = 2; gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.insets = new Insets(6, 16, 6, 8);
        var btnRoute = new JButton("⚡ Calculate Fastest Route (Dijkstra)");
        UITheme.attachButtonTransitions(btnRoute, UITheme.OPTIM_INDIGO, UITheme.INDIGO_HOVER, UITheme.INDIGO_CLICK);
        btnRoute.setPreferredSize(new Dimension(260, 48));
        btnRoute.addActionListener(e -> calculateShortestRoute());
        pnlControls.add(btnRoute, gbc);

        pnlDijkstra.add(pnlControls, BorderLayout.NORTH);

        txtRouteOutput = new JTextArea();
        txtRouteOutput.setEditable(false);
        txtRouteOutput.setFont(UITheme.FONT_MONO_DATA);
        txtRouteOutput.setBackground(Color.WHITE);
        txtRouteOutput.setForeground(new Color(15, 23, 42));
        txtRouteOutput.setBorder(BorderFactory.createEmptyBorder(14, 16, 14, 16));
        txtRouteOutput.setText("""
                ========================================================================================
                                     DIJKSTRA HOSPITAL NAVIGATION OPTIMIZER CONSOLE
                ========================================================================================
                Select Origin and Target zones above, then click 'Calculate Fastest Route'.
                Computes single-source shortest path across weighted hospital navigation graph in O((V + E) log V).
                """);

        var scrollRoute = new JScrollPane(txtRouteOutput);
        scrollRoute.setBorder(BorderFactory.createLineBorder(UITheme.SLATE_200, 1));
        pnlDijkstra.add(scrollRoute, BorderLayout.CENTER);

        tabbedPane.addTab("Emergency Transit Routing (Dijkstra)", pnlDijkstra);

        // ---------------- TAB 2: KNAPSACK ALLOCATOR ----------------
        var pnlKnapsack = new JPanel(new BorderLayout(14, 14));
        pnlKnapsack.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));
        pnlKnapsack.setBackground(UITheme.SLATE_50);

        var pnlKControls = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 12));
        pnlKControls.setBackground(Color.WHITE);
        pnlKControls.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.SLATE_200, 1),
                BorderFactory.createEmptyBorder(8, 14, 8, 14)
        ));

        var lblBudget = new JLabel("EQUIPMENT CAPACITY BUDGET LIMIT:");
        lblBudget.setFont(UITheme.FONT_FORM_LABEL);
        lblBudget.setForeground(UITheme.SLATE_700);
        pnlKControls.add(lblBudget);

        txtCapacity = new JTextField("45", 8);
        UITheme.styleInputField(txtCapacity);
        txtCapacity.setPreferredSize(new Dimension(90, 32));
        pnlKControls.add(txtCapacity);

        var btnAllocate = new JButton("⚡ Maximize Clinical Value (0/1 Knapsack DP)");
        UITheme.attachButtonTransitions(btnAllocate, UITheme.SUCCESS_EMERALD, UITheme.EMERALD_HOVER, UITheme.EMERALD_CLICK);
        btnAllocate.setPreferredSize(new Dimension(320, 34));
        btnAllocate.addActionListener(e -> calculateKnapsackAllocation());
        pnlKControls.add(btnAllocate);

        pnlKnapsack.add(pnlKControls, BorderLayout.NORTH);

        txtResourceOutput = new JTextArea();
        txtResourceOutput.setEditable(false);
        txtResourceOutput.setFont(UITheme.FONT_MONO_DATA);
        txtResourceOutput.setBackground(Color.WHITE);
        txtResourceOutput.setForeground(new Color(15, 23, 42));
        txtResourceOutput.setBorder(BorderFactory.createEmptyBorder(14, 16, 14, 16));
        txtResourceOutput.setText("""
                ========================================================================================
                              AVAILABLE MEDICAL EQUIPMENT POOL (CANDIDATE RESOURCE POOL)
                ========================================================================================
                1. ICU Ventilator              (Cost Weight: 15, Clinical Value: 85 pts)
                2. Defibrillator Unit          (Cost Weight: 10, Clinical Value: 60 pts)
                3. Vital Signs Monitor         (Cost Weight:  8, Clinical Value: 50 pts)
                4. Hemodialysis Machine        (Cost Weight: 25, Clinical Value: 95 pts)
                5. Portable X-Ray Unit         (Cost Weight: 20, Clinical Value: 70 pts)
                6. Oxygen Concentrator Station (Cost Weight: 12, Clinical Value: 65 pts)

                Enter budget capacity limit above and click 'Maximize Clinical Value' to execute Dynamic Programming.
                Time Complexity: O(N * W) | Space Complexity: O(N * W) table memoization.
                """);

        var scrollKnap = new JScrollPane(txtResourceOutput);
        scrollKnap.setBorder(BorderFactory.createLineBorder(UITheme.SLATE_200, 1));
        pnlKnapsack.add(scrollKnap, BorderLayout.CENTER);

        tabbedPane.addTab("Medical Resource Allocation (Knapsack DP)", pnlKnapsack);

        add(tabbedPane, BorderLayout.CENTER);
        setVisible(true);
    }

    private void calculateShortestRoute() {
        try {
            int src = cmbSource.getSelectedIndex();
            int dest = cmbDest.getSelectedIndex();

            var router = new DijkstraRouting();
            var result = router.shortestPath(HOSPITAL_GRAPH, src);

            int dist = result.getDistanceTo(dest);
            var path = result.getPathTo(dest);

            var sb = new StringBuilder("""
                    ========================================================================================
                                       DIJKSTRA EMERGENCY ROUTING OPTIMIZATION RESULT
                    ========================================================================================
                    Origin Zone      : %s
                    Destination Zone : %s

                    """.formatted(ZONES[src], ZONES[dest]));

            if (path.isEmpty() || dist >= DijkstraRouting.INF) {
                sb.append("Status: No direct path available.\n");
            } else {
                sb.append(String.format("Estimated Transit Time : %d minutes\n", dist));
                sb.append("Optimal Navigation Path:\n");
                for (int i = 0; i < path.size(); i++) {
                    int node = path.get(i);
                    sb.append("  [").append(i + 1).append("] ").append(ZONES[node]);
                    if (i < path.size() - 1) sb.append("\n        ↓ (proceed through corridor)\n");
                }
            }

            txtRouteOutput.setText(sb.toString());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Routing error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calculateKnapsackAllocation() {
        try {
            int capacity = Integer.parseInt(txtCapacity.getText().strip());
            if (capacity < 0) {
                throw new IllegalArgumentException("Capacity must be positive.");
            }

            var allocator = new KnapsackAllocator();
            var res = allocator.maximizeBenefitWithSelection(capacity, WEIGHTS, VALUES);

            var sb = new StringBuilder("""
                    ========================================================================================
                               0/1 KNAPSACK DYNAMIC PROGRAMMING OPTIMAL RESOURCE ALLOCATION
                    ========================================================================================
                    Total Capacity / Budget Allocated : %d units
                    Maximum Achievable Clinical Value : %d points

                    Optimally Selected Medical Equipment:
                    """.formatted(capacity, res.maxBenefit()));

            int usedWeight = 0;
            for (int idx : res.selectedItems()) {
                sb.append(String.format("  ✔ %-30s (Cost: %2d units, Benefit: %2d pts)\n",
                        ITEMS.get(idx), WEIGHTS[idx], VALUES[idx]));
                usedWeight += WEIGHTS[idx];
            }

            sb.append("""

                    Total Resources Utilized : %d / %d units
                    Budget Efficiency        : %.1f%%
                    """.formatted(usedWeight, capacity, (capacity > 0 ? (usedWeight * 100.0 / capacity) : 0.0)));

            txtResourceOutput.setText(sb.toString());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid positive number for Capacity.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }
}
