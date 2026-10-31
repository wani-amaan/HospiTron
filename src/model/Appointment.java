package model;

public class Appointment {

    private int appointmentId;
    private int patientId;
    private int doctorId;

    private String status;
    private String type;

    // NEW FIELDS (REQUIRED BY YOUR ALGORITHMS)
    private String date;
    private String time;

    // FULL CONSTRUCTOR (UPDATED)
    public Appointment(int appointmentId,
                       int patientId,
                       int doctorId,
                       String status,
                       String type,
                       String date,
                       String time) {

        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.status = status;
        this.type = type;
        this.date = date;
        this.time = time;
    }

    // OLD COMPATIBLE CONSTRUCTOR (SAFE FOR EXISTING CALLS)
    public Appointment(int appointmentId,
                       int patientId,
                       int doctorId,
                       String status,
                       String type) {

        this(appointmentId, patientId, doctorId, status, type, "NA", "NA");
    }

    // GETTERS

    public int getAppointmentId() {
        return appointmentId;
    }

    public int getPatientId() {
        return patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    // SETTERS (optional but useful)

    public void setStatus(String status) {
        this.status = status;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }
}