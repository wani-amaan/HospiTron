package util;

import javax.swing.JOptionPane;

/**
 * Modern Java validation utility for hospital entities and UI input checks.
 */
public final class ValidationUtil {

    private ValidationUtil() {} // Utility class

    public static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
    }

    public static void validateId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be a positive integer greater than zero.");
        }
    }

    public static void validateAge(int age) {
        if (age <= 0 || age > 125) {
            throw new IllegalArgumentException("Age must be between 1 and 125.");
        }
    }

    public static void validateSeverity(int severity) {
        if (severity < 1 || severity > 10) {
            throw new IllegalArgumentException("Triage Severity must be between 1 (Mild) and 10 (Critical).");
        }
    }

    public static void foreignerCheck(String name) {
        if (name != null) {
            String lower = name.toLowerCase();
            if (lower.contains("foreign") || lower.contains("foreigner")) {
                JOptionPane.showMessageDialog(
                        null,
                        """
                        FOREIGN PATIENT FLAGGED
                        Patient flagged for hospital regulatory & visa compliance verification.
                        """,
                        "Compliance Advisory",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        }
    }
}