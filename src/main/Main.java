package main;

import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import ui.LoginFrame;

/**
 * HospiTron Application Entry Point - Target Java 27 LTS Runtime.
 */
public final class Main {

    private Main() {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (Exception ignored) {}

            // ================= GLOBAL MODERN HEALTHCARE THEME =================
            final var bgLight = new Color(245, 248, 252);
            final var primaryBlue = new Color(52, 152, 219);
            final var focusBlue = new Color(41, 128, 185);

            UIManager.put("control", bgLight);
            UIManager.put("nimbusBase", primaryBlue);
            UIManager.put("nimbusFocus", focusBlue);

            UIManager.put("OptionPane.background", bgLight);
            UIManager.put("Panel.background", bgLight);

            UIManager.put("Button.background", focusBlue);
            UIManager.put("Button.foreground", Color.WHITE);

            UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 13));
            UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 13));
            UIManager.put("TextField.font", new Font("Segoe UI", Font.PLAIN, 13));

            new LoginFrame();
        });
    }
}
