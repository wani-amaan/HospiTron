package service;

import data.FileHandler;
import model.*;

import java.util.*;

public class HospitalService {

    private List<Patient> patients = new ArrayList<>();
    private List<Doctor> doctors = new ArrayList<>();
    private List<Appointment> appointments = new ArrayList<>();
    private PriorityQueue<EmergencyCase> emergencyQueue = new PriorityQueue<>(
            (a, b) -> Integer.compare(b.getSeverity(), a.getSeverity()) // Max-heap: highest severity first
    );

    public HospitalService() {
        loadAllData();
    }

    public void loadAllData() {
        patients = FileHandler.loadPatients();
        doctors = FileHandler.loadDoctors();
        appointments = FileHandler.loadAppointments();
    }

    // ---------------- PATIENT ----------------

    public void addPatient(int id, String name, int age) {
        patients.add(new Patient(id, name, age));
        FileHandler.savePatients(patients);
    }

    public List<Patient> getPatients() {
        return patients;
    }

    // ---------------- DOCTOR ----------------

    public void addDoctor(int id, String name, String specialization) {
        doctors.add(new Doctor(id, name, specialization, true));
        FileHandler.saveDoctors(doctors);
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    // ---------------- APPOINTMENT ----------------

    public void bookAppointment(int patientId, int doctorId, int appointmentId, String date, String time) {
        appointments.add(new Appointment(
                appointmentId,
                patientId,
                doctorId,
                "CONFIRMED",
                "NORMAL",
                date,
                time
        ));
        FileHandler.saveAppointments(appointments);
    }

    public void bookAppointment(int patientId, int doctorId, int appointmentId) {
        bookAppointment(patientId, doctorId, appointmentId, "31-10-2026", "10:00");
    }

    public List<Appointment> getAllAppointments() {
        return appointments;
    }

    public List<Appointment> getOptimizedAppointments() {
        algorithm.IntervalScheduling scheduler = new algorithm.IntervalScheduling();
        ArrayList<Appointment> validList = new ArrayList<>();
        for (Appointment a : appointments) {
            if (a.getDate() != null && !a.getDate().equalsIgnoreCase("NA") &&
                a.getTime() != null && !a.getTime().equalsIgnoreCase("NA")) {
                validList.add(a);
            }
        }
        return scheduler.schedule(validList);
    }

    // ---------------- BILLING ----------------

    public double generateBill(int patientId, String patientName, boolean emergency) {
        BillingService billing = new BillingService();
        return billing.calculateBill(2000, emergency, 0);
    }

    // ---------------- EMERGENCY (PRIORITY QUEUE TRIAGE) ----------------

    public void addEmergency(int patientId, String name, int severity) {
        emergencyQueue.offer(new EmergencyCase(patientId, name, severity));
    }

    public EmergencyCase handleEmergency() {
        return emergencyQueue.poll();
    }

    public int getEmergencyQueueSize() {
        return emergencyQueue.size();
    }
}