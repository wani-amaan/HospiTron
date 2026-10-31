package model;

/**
 * Modern Java record representing a booked hospital appointment.
 */
public record Appointment(
        int appointmentId,
        int patientId,
        int doctorId,
        String status,
        String type,
        String date,
        String time
) {

    public Appointment {
        status = (status != null && !status.isBlank()) ? status.strip() : "CONFIRMED";
        type = (type != null && !type.isBlank()) ? type.strip() : "NORMAL";
        date = (date != null && !date.isBlank()) ? date.strip() : "31-10-2026";
        time = (time != null && !time.isBlank()) ? time.strip() : "10:00";
    }

    // Compatibility constructor
    public Appointment(int appointmentId, int patientId, int doctorId, String status, String type) {
        this(appointmentId, patientId, doctorId, status, type, "31-10-2026", "10:00");
    }

    // JavaBean getter aliases
    public int getAppointmentId() { return appointmentId; }
    public int getPatientId() { return patientId; }
    public int getDoctorId() { return doctorId; }
    public String getStatus() { return status; }
    public String getType() { return type; }
    public String getDate() { return date; }
    public String getTime() { return time; }

    @Override
    public String toString() {
        return appointmentId + "," + patientId + "," + doctorId + "," + status + "," + date + "," + time;
    }
}