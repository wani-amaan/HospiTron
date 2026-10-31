package main;

import java.awt.*;
import javax.swing.*;
import ui.LoginFrame;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (Exception ignored) {}

            // ================= GLOBAL THEME =================

            UIManager.put("control", new Color(245, 248, 252)); // background
            UIManager.put("nimbusBase", new Color(52, 152, 219)); // blue
            UIManager.put("nimbusFocus", new Color(41, 128, 185));

            UIManager.put("OptionPane.background", new Color(245, 248, 252));
            UIManager.put("Panel.background", new Color(245, 248, 252));

            UIManager.put("Button.background", new Color(41, 128, 185));
            UIManager.put("Button.foreground", Color.WHITE);

            UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 13));
            UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 13));
            UIManager.put("TextField.font", new Font("Segoe UI", Font.PLAIN, 13));

            new LoginFrame();
        });
    }
}
