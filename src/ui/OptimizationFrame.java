package ui;

import algorithm.DijkstraRouting;
import algorithm.KnapsackAllocator;
import controller.HospitalController;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
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

        setTitle("Optimization Suite - HOSPITRON (DSA)");
        setSize(640, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        var tabbedPane = new JTabbedPane();

        // ---------------- TAB 1: DIJKSTRA ROUTING ----------------
        var pnlDijkstra = new JPanel(new BorderLayout(10, 10));
        pnlDijkstra.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        pnlDijkstra.setBackground(new Color(245, 248, 252));

        var pnlControls = new JPanel(new GridLayout(3, 2, 10, 10));
        pnlControls.setOpaque(false);

        pnlControls.add(new JLabel("Source Location:"));
        cmbSource = new JComboBox<>(ZONES);
        cmbSource.setSelectedIndex(0);
        pnlControls.add(cmbSource);

        pnlControls.add(new JLabel("Destination Location:"));
        cmbDest = new JComboBox<>(ZONES);
        cmbDest.setSelectedIndex(3); // ICU by default
        pnlControls.add(cmbDest);

        var btnRoute = new JButton("⚡ Find Fastest Emergency Route (Dijkstra)");
        btnRoute.setBackground(new Color(41, 128, 185));
        btnRoute.setForeground(Color.WHITE);
        btnRoute.addActionListener(e -> calculateShortestRoute());
        pnlControls.add(new JLabel(""));
        pnlControls.add(btnRoute);

        pnlDijkstra.add(pnlControls, BorderLayout.NORTH);

        txtRouteOutput = new JTextArea();
        txtRouteOutput.setEditable(false);
        txtRouteOutput.setFont(new Font("Consolas", Font.PLAIN, 12));
        txtRouteOutput.setText("""
                Select Source and Destination, then click 'Find Fastest Emergency Route'.
                Uses Dijkstra's Shortest Path Algorithm to compute optimal transit time across hospital wings.
                """);
        pnlDijkstra.add(new JScrollPane(txtRouteOutput), BorderLayout.CENTER);

        tabbedPane.addTab("Emergency Route (Dijkstra)", pnlDijkstra);

        // ---------------- TAB 2: KNAPSACK ALLOCATOR ----------------
        var pnlKnapsack = new JPanel(new BorderLayout(10, 10));
        pnlKnapsack.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        pnlKnapsack.setBackground(new Color(245, 248, 252));

        var pnlKControls = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        pnlKControls.setOpaque(false);

        pnlKControls.add(new JLabel("Resource Budget / Capacity Limit:"));
        txtCapacity = new JTextField("45", 8);
        pnlKControls.add(txtCapacity);

        var btnAllocate = new JButton("⚡ Allocate Resources (0/1 Knapsack DP)");
        btnAllocate.setBackground(new Color(46, 125, 50));
        btnAllocate.setForeground(Color.WHITE);
        btnAllocate.addActionListener(e -> calculateKnapsackAllocation());
        pnlKControls.add(btnAllocate);

        pnlKnapsack.add(pnlKControls, BorderLayout.NORTH);

        txtResourceOutput = new JTextArea();
        txtResourceOutput.setEditable(false);
        txtResourceOutput.setFont(new Font("Consolas", Font.PLAIN, 12));
        txtResourceOutput.setText("""
                Available Medical Equipment Pool:
                1. ICU Ventilator (Cost: 15, Benefit: 85)
                2. Defibrillator Unit (Cost: 10, Benefit: 60)
                3. Vital Signs Monitor (Cost: 8, Benefit: 50)
                4. Hemodialysis Machine (Cost: 25, Benefit: 95)
                5. Portable X-Ray Unit (Cost: 20, Benefit: 70)
                6. Oxygen Concentrator Station (Cost: 12, Benefit: 65)

                Enter budget capacity and click 'Allocate Resources' to run Dynamic Programming 0/1 Knapsack.
                """);
        pnlKnapsack.add(new JScrollPane(txtResourceOutput), BorderLayout.CENTER);

        tabbedPane.addTab("Resource Allocation (Knapsack DP)", pnlKnapsack);

        add(tabbedPane);
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
                    ====================================================
                       DIJKSTRA EMERGENCY ROUTING OPTIMIZATION
                    ====================================================
                    From: %s
                    To  : %s

                    """.formatted(ZONES[src], ZONES[dest]));

            if (path.isEmpty() || dist >= DijkstraRouting.INF) {
                sb.append("Status: No direct path available.\n");
            } else {
                sb.append(String.format("Estimated Transit Time : %d minutes\n", dist));
                sb.append("Optimal Navigation Path:\n");
                for (int i = 0; i < path.size(); i++) {
                    int node = path.get(i);
                    sb.append("  [").append(i + 1).append("] ").append(ZONES[node]);
                    if (i < path.size() - 1) sb.append("\n        ↓ (proceed)\n");
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
                    ====================================================
                       0/1 KNAPSACK DYNAMIC PROGRAMMING ALLOCATION
                    ====================================================
                    Total Capacity / Budget Allocated : %d units
                    Maximum Achievable Clinical Benefit: %d points

                    Optimally Selected Medical Equipment:
                    """.formatted(capacity, res.maxBenefit()));

            int usedWeight = 0;
            for (int idx : res.selectedItems()) {
                sb.append(String.format("  ✔ %-28s (Cost: %2d, Benefit: %2d)\n",
                        ITEMS.get(idx), WEIGHTS[idx], VALUES[idx]));
                usedWeight += WEIGHTS[idx];
            }

            sb.append("""

                    Total Resources Utilized : %d / %d units
                    Budget Efficiency        : %.1f%%
                    """.formatted(usedWeight, capacity, (usedWeight * 100.0 / capacity)));

            txtResourceOutput.setText(sb.toString());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid positive number for Capacity.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }
}
