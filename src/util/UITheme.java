package util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Enterprise Clinical UI Theme and Interaction Engine for HospiTron.
 * Provides professional color schemes, typographic hierarchies, and smooth click/hover transitions.
 */
public final class UITheme {

    private UITheme() {}

    // --- Enterprise Medical Palette ---
    public static final Color SLATE_900 = new Color(15, 23, 42);   // #0F172A - Executive Header
    public static final Color SLATE_800 = new Color(30, 41, 59);   // #1E293B - Secondary Controls
    public static final Color SLATE_700 = new Color(51, 65, 85);   // #334155 - Field Labels
    public static final Color SLATE_500 = new Color(100, 116, 139); // #64748B - Muted Subtitles
    public static final Color SLATE_200 = new Color(226, 232, 240); // #E2E8F0 - Subtle Borders
    public static final Color SLATE_100 = new Color(241, 245, 249); // #F1F5F9 - Table Headers & Pills
    public static final Color SLATE_50  = new Color(248, 250, 252); // #F8FAFC - Canvas Background

    // Clinical Accent Colors (Muted, Enterprise Tones)
    public static final Color SURGICAL_TEAL = new Color(15, 118, 110);   // #0F766E - Doctors & Clinicians
    public static final Color TEAL_HOVER    = new Color(13, 148, 136);   // #0D9488
    public static final Color TEAL_CLICK    = new Color(17, 94, 89);     // #115E59

    public static final Color PATIENT_SKY   = new Color(2, 132, 199);    // #0284C7 - Admissions & Demographics
    public static final Color SKY_HOVER     = new Color(14, 165, 233);   // #0EA5E9
    public static final Color SKY_CLICK     = new Color(3, 105, 161);    // #0369A1

    public static final Color SCHED_BLUE    = new Color(30, 64, 175);    // #1E40AF - Appointments
    public static final Color SCHED_HOVER   = new Color(37, 99, 235);    // #2563EB
    public static final Color SCHED_CLICK   = new Color(29, 78, 216);    // #1D4ED8

    public static final Color TRIAGE_RED    = new Color(185, 28, 28);    // #B91C1C - Emergency Max-Heap
    public static final Color RED_HOVER     = new Color(220, 38, 38);    // #DC2626
    public static final Color RED_CLICK     = new Color(153, 27, 27);    // #991B1B

    public static final Color OPTIM_INDIGO  = new Color(67, 56, 202);    // #4338CA - Algorithms & Optimization
    public static final Color INDIGO_HOVER  = new Color(79, 70, 229);    // #4F46E5
    public static final Color INDIGO_CLICK  = new Color(55, 48, 163);    // #3730A3

    public static final Color SUCCESS_EMERALD = new Color(16, 122, 87);  // #107A57 - Confirmed/Optimal
    public static final Color EMERALD_HOVER   = new Color(22, 163, 74);  // #16A34A
    public static final Color EMERALD_CLICK   = new Color(21, 128, 61);  // #15803D

    public static final Color TARIFF_AMBER  = new Color(180, 83, 9);     // #B45309 - Billing & Invoicing
    public static final Color AMBER_HOVER   = new Color(217, 119, 6);    // #D97706
    public static final Color AMBER_CLICK   = new Color(146, 64, 14);    // #92400E

    // --- Typographic Hierarchy ---
    public static final Font FONT_HEADER_TITLE = new Font("Segoe UI", Font.BOLD, 17);
    public static final Font FONT_HEADER_SUB   = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_FORM_LABEL   = new Font("Segoe UI", Font.BOLD, 11);
    public static final Font FONT_INPUT        = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_BTN_PRIMARY  = new Font("Segoe UI", Font.BOLD, 12);
    public static final Font FONT_BTN_SM       = new Font("Segoe UI", Font.PLAIN, 11);

    // Specialized Highlighting Fonts
    public static final Font FONT_MONO_CODE    = new Font("Consolas", Font.BOLD, 12);
    public static final Font FONT_MONO_DATA    = new Font("Consolas", Font.PLAIN, 12);
    public static final Font FONT_BADGE        = new Font("Segoe UI Semibold", Font.PLAIN, 11);
    public static final Font FONT_SECTION_TAG  = new Font("Segoe UI", Font.BOLD, 10);

    // --- Interactive Transition Engine ---

    /**
     * Attaches smooth hover and press transitions to an action button.
     */
    public static void attachButtonTransitions(JButton btn, Color base, Color hover, Color click) {
        btn.setBackground(base);
        btn.setForeground(Color.WHITE);
        btn.setFont(FONT_BTN_PRIMARY);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (btn.isEnabled()) btn.setBackground(hover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (btn.isEnabled()) btn.setBackground(base);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (btn.isEnabled()) btn.setBackground(click);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (btn.isEnabled()) {
                    btn.setBackground(btn.contains(e.getPoint()) ? hover : base);
                }
            }
        });
    }

    /**
     * Attaches smooth hover, border accent, and press transitions to dashboard module cards.
     */
    public static void attachCardTransitions(JButton card, Color normalBg, Color hoverBg, Color borderNormal, Color borderHover) {
        card.setBackground(normalBg);
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.setFocusPainted(false);
        Border normalBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderNormal, 1),
                BorderFactory.createEmptyBorder(12, 16, 12, 16)
        );
        Border hoverBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderHover, 1),
                BorderFactory.createEmptyBorder(12, 16, 12, 16)
        );
        card.setBorder(normalBorder);

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(hoverBg);
                card.setBorder(hoverBorder);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(normalBg);
                card.setBorder(normalBorder);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                card.setBackground(new Color(241, 245, 249));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                card.setBackground(card.contains(e.getPoint()) ? hoverBg : normalBg);
            }
        });
    }

    /**
     * Styles text fields with a crisp matte border and focus highlight transition.
     */
    public static void styleInputField(JTextField txt) {
        txt.setFont(FONT_INPUT);
        Border unselected = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(SLATE_200, 1),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
        );
        Border focused = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(14, 165, 233), 1), // Sky 500
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
        );
        txt.setBorder(unselected);
        txt.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                txt.setBorder(focused);
                txt.setBackground(Color.WHITE);
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                txt.setBorder(unselected);
                txt.setBackground(Color.WHITE);
            }
        });
    }

    /**
     * Standardizes JTable styling with breathable row heights and distinct monospaced highlighting for ID/code columns.
     */
    public static void styleTable(JTable table) {
        table.setRowHeight(30);
        table.setFont(FONT_INPUT);
        table.setGridColor(new Color(241, 245, 249));
        table.setSelectionBackground(new Color(224, 242, 254)); // Sky 100
        table.setSelectionForeground(SLATE_900);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);

        var header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 11));
        header.setBackground(SLATE_100);
        header.setForeground(SLATE_700);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, SLATE_200));

        // Center-align and monospace the primary ID column (column 0)
        table.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tbl, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                var c = super.getTableCellRendererComponent(tbl, value, isSelected, hasFocus, row, col);
                setFont(FONT_MONO_CODE);
                setForeground(isSelected ? SLATE_900 : new Color(30, 41, 59));
                setHorizontalAlignment(JLabel.CENTER);
                return c;
            }
        });
    }
}
