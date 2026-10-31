package service;

import data.FileHandler;
import model.Appointment;
import model.Doctor;
import model.EmergencyCase;
import model.Patient;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Modern Java service layer for hospital business operations.
 */
public final class HospitalService {

    private List<Patient> patients = new ArrayList<>();
    private List<Doctor> doctors = new ArrayList<>();
    private List<Appointment> appointments = new ArrayList<>();
    private final PriorityQueue<EmergencyCase> emergencyQueue = new PriorityQueue<>(
            Comparator.comparingInt(EmergencyCase::severity).reversed() // Max-Heap
    );

    public HospitalService() {
        loadAllData();
    }

    public void loadAllData() {
        patients = new ArrayList<>(FileHandler.loadPatients());
        doctors = new ArrayList<>(FileHandler.loadDoctors());
        appointments = new ArrayList<>(FileHandler.loadAppointments());
    }

    // ---------------- PATIENT ----------------

    public void addPatient(int id, String name, int age) {
        patients.add(new Patient(id, name, age));
        FileHandler.savePatients(patients);
    }

    public List<Patient> getPatients() {
        return List.copyOf(patients);
    }

    // ---------------- DOCTOR ----------------

    public void addDoctor(int id, String name, String specialization) {
        doctors.add(new Doctor(id, name, specialization, true));
        FileHandler.saveDoctors(doctors);
    }

    public List<Doctor> getDoctors() {
        return List.copyOf(doctors);
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
        return List.copyOf(appointments);
    }

    public List<Appointment> getOptimizedAppointments() {
        var scheduler = new algorithm.IntervalScheduling();
        var validList = new ArrayList<Appointment>();
        for (var a : appointments) {
            if (a.date() != null && !a.date().equalsIgnoreCase("NA") &&
                a.time() != null && !a.time().equalsIgnoreCase("NA")) {
                validList.add(a);
            }
        }

        return scheduler.schedule(validList);
    }

    // ---------------- BILLING ----------------

    public double generateBill(int patientId, String patientName, boolean emergency) {
        var billing = new BillingService();
        return billing.calculateBill(2000, emergency, 0);
    }

    public String generateDetailedReceipt(int patientId, String patientName, int consultationFee, int testsCost, boolean emergency) {
        var billing = new BillingService();
        return billing.generateDetailedReceipt(patientId, patientName, consultationFee, testsCost, emergency);
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