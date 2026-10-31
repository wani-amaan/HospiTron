package util;

import javax.swing.*;

public class ValidationUtil {

    public static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
    }

    public static void validateId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be greater than 0");
        }
    }

    public static void validateAge(int age) {
        if (age <= 0 || age > 120) {
            throw new IllegalArgumentException("Invalid age");
        }
    }

    public static void validateSeverity(int severity) {
        if (severity < 1 || severity > 10) {
            throw new IllegalArgumentException("Severity must be 1-10");
        }
    }

    public static void foreignerCheck(String name) {

        if (name.toLowerCase().contains("foreign") ||
            name.toLowerCase().contains("foreigner")) {

            JOptionPane.showMessageDialog(
                    null,
                    "FOREIGN PATIENT FLAGGED\nMay be reported for compliance review.",
                    "Legal Compliance Alert",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }
}