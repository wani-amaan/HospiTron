package model;

public class EmergencyCase {

    private int patientId;
    private String name;
    private int severity;

    public EmergencyCase(int patientId, String name, int severity) {
        this.patientId = patientId;
        this.name = name;
        this.severity = severity;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) { 
        this.patientId = patientId; 
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { 
        this.name = name; 
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) { 
        this.severity = severity; 
    }

    @Override
    public String toString() {
        return patientId + "," + name + "," + severity;
    }
}