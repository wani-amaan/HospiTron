package ui;

import algorithm.DijkstraRouting;
import algorithm.KnapsackAllocator;
import controller.HospitalController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class OptimizationFrame extends JFrame {

    private HospitalController controller;

    private JComboBox<String> cmbSource;
    private JComboBox<String> cmbDest;
    private JTextArea txtRouteOutput;

    private JTextField txtCapacity;
    private JTextArea txtResourceOutput;

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

    private static final String[] ITEMS = {
            "ICU Ventilator",
            "Defibrillator Unit",
            "Vital Signs Monitor",
            "Hemodialysis Machine",
            "Portable X-Ray Unit",
            "Oxygen Concentrator Station"
    };
    private static final int[] WEIGHTS = {15, 10, 8, 25, 20, 12};
    private static final int[] VALUES  = {85, 60, 50, 95, 70, 65};

    public OptimizationFrame(HospitalController controller) {
        this.controller = controller;

        setTitle("Optimization Suite - HOSPITRON (DSA)");
        setSize(620, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // ---------------- TAB 1: DIJKSTRA ROUTING ----------------
        JPanel pnlDijkstra = new JPanel(new BorderLayout(10, 10));
        pnlDijkstra.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        pnlDijkstra.setBackground(new Color(245, 248, 252));

        JPanel pnlControls = new JPanel(new GridLayout(3, 2, 10, 10));
        pnlControls.setOpaque(false);

        pnlControls.add(new JLabel("Source Location:"));
        cmbSource = new JComboBox<>(ZONES);
        cmbSource.setSelectedIndex(0);
        pnlControls.add(cmbSource);

        pnlControls.add(new JLabel("Destination Location:"));
        cmbDest = new JComboBox<>(ZONES);
        cmbDest.setSelectedIndex(3); // ICU by default
        pnlControls.add(cmbDest);

        JButton btnRoute = new JButton("⚡ Find Fastest Emergency Route (Dijkstra)");
        btnRoute.setBackground(new Color(41, 128, 185));
        btnRoute.setForeground(Color.WHITE);
        btnRoute.addActionListener(e -> calculateShortestRoute());
        pnlControls.add(new JLabel(""));
        pnlControls.add(btnRoute);

        pnlDijkstra.add(pnlControls, BorderLayout.NORTH);

        txtRouteOutput = new JTextArea();
        txtRouteOutput.setEditable(false);
        txtRouteOutput.setFont(new Font("Consolas", Font.PLAIN, 12));
        txtRouteOutput.setText("Select Source and Destination, then click 'Find Fastest Emergency Route'.\n" +
                "Uses Dijkstra's Shortest Path Algorithm to compute optimal transit time across hospital wings.");
        pnlDijkstra.add(new JScrollPane(txtRouteOutput), BorderLayout.CENTER);

        tabbedPane.addTab("Emergency Route (Dijkstra)", pnlDijkstra);

        // ---------------- TAB 2: KNAPSACK ALLOCATOR ----------------
        JPanel pnlKnapsack = new JPanel(new BorderLayout(10, 10));
        pnlKnapsack.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        pnlKnapsack.setBackground(new Color(245, 248, 252));

        JPanel pnlKControls = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        pnlKControls.setOpaque(false);

        pnlKControls.add(new JLabel("Resource Budget / Capacity Limit:"));
        txtCapacity = new JTextField("45", 8);
        pnlKControls.add(txtCapacity);

        JButton btnAllocate = new JButton("⚡ Allocate Resources (0/1 Knapsack DP)");
        btnAllocate.setBackground(new Color(46, 125, 50));
        btnAllocate.setForeground(Color.WHITE);
        btnAllocate.addActionListener(e -> calculateKnapsackAllocation());
        pnlKControls.add(btnAllocate);

        pnlKnapsack.add(pnlKControls, BorderLayout.NORTH);

        txtResourceOutput = new JTextArea();
        txtResourceOutput.setEditable(false);
        txtResourceOutput.setFont(new Font("Consolas", Font.PLAIN, 12));
        txtResourceOutput.setText("Available Medical Equipment Pool:\n" +
                "1. ICU Ventilator (Cost: 15, Benefit: 85)\n" +
                "2. Defibrillator Unit (Cost: 10, Benefit: 60)\n" +
                "3. Vital Signs Monitor (Cost: 8, Benefit: 50)\n" +
                "4. Hemodialysis Machine (Cost: 25, Benefit: 95)\n" +
                "5. Portable X-Ray Unit (Cost: 20, Benefit: 70)\n" +
                "6. Oxygen Concentrator Station (Cost: 12, Benefit: 65)\n\n" +
                "Enter budget capacity and click 'Allocate Resources' to run Dynamic Programming 0/1 Knapsack.");
        pnlKnapsack.add(new JScrollPane(txtResourceOutput), BorderLayout.CENTER);

        tabbedPane.addTab("Resource Allocation (Knapsack DP)", pnlKnapsack);

        add(tabbedPane);
        setVisible(true);
    }

    private void calculateShortestRoute() {
        try {
            int src = cmbSource.getSelectedIndex();
            int dest = cmbDest.getSelectedIndex();

            DijkstraRouting router = new DijkstraRouting();
            DijkstraRouting.Result result = router.shortestPath(HOSPITAL_GRAPH, src);

            int dist = result.getDistanceTo(dest);
            List<Integer> path = result.getPathTo(dest);

            StringBuilder sb = new StringBuilder();
            sb.append("====================================================\n");
            sb.append("   DIJKSTRA EMERGENCY ROUTING OPTIMIZATION\n");
            sb.append("====================================================\n\n");
            sb.append("From: ").append(ZONES[src]).append("\n");
            sb.append("To  : ").append(ZONES[dest]).append("\n\n");

            if (path.isEmpty() || dist >= DijkstraRouting.INF) {
                sb.append("Status: No direct path available.\n");
            } else {
                sb.append("Estimated Transit Time : ").append(dist).append(" minutes\n");
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
            int capacity = Integer.parseInt(txtCapacity.getText().trim());
            if (capacity < 0) {
                throw new IllegalArgumentException("Capacity must be positive.");
            }

            KnapsackAllocator allocator = new KnapsackAllocator();
            KnapsackAllocator.Result res = allocator.maximizeBenefitWithSelection(capacity, WEIGHTS, VALUES);

            StringBuilder sb = new StringBuilder();
            sb.append("====================================================\n");
            sb.append("   0/1 KNAPSACK DYNAMIC PROGRAMMING ALLOCATION\n");
            sb.append("====================================================\n\n");
            sb.append("Total Capacity / Budget Allocated : ").append(capacity).append(" units\n");
            sb.append("Maximum Achievable Clinical Benefit: ").append(res.getMaxBenefit()).append(" points\n\n");
            sb.append("Optimally Selected Medical Equipment:\n");

            int usedWeight = 0;
            for (int idx : res.getSelectedItems()) {
                sb.append("  ✔ ").append(ITEMS[idx])
                  .append(" (Cost: ").append(WEIGHTS[idx])
                  .append(", Benefit: ").append(VALUES[idx]).append(")\n");
                usedWeight += WEIGHTS[idx];
            }

            sb.append("\nTotal Resources Utilized : ").append(usedWeight).append(" / ").append(capacity).append(" units\n");
            sb.append("Budget Efficiency        : ").append(String.format("%.1f", (usedWeight * 100.0 / capacity))).append("%\n");

            txtResourceOutput.setText(sb.toString());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid positive number for Capacity.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }
}
