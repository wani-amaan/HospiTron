package model;

/**
 * Modern Java record representing an emergency hospital patient case.
 */
public record EmergencyCase(int patientId, String name, int severity) {

    public EmergencyCase {
        if (patientId < 0) throw new IllegalArgumentException("Patient ID cannot be negative.");
        name = name != null ? name.strip() : "";
        if (severity < 1 || severity > 10) throw new IllegalArgumentException("Severity must be 1-10.");
    }

    // JavaBean getter aliases
    public int getPatientId() { return patientId; }
    public String getName() { return name; }
    public int getSeverity() { return severity; }

    @Override
    public String toString() {
        return patientId + "," + name + "," + severity;
    }
}