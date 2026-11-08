package controller;

import model.Appointment;
import model.Doctor;
import model.EmergencyCase;
import model.Patient;
import service.HospitalService;

import java.util.List;

/**
 * Modern Java controller orchestrating UI requests to service layer.
 */
public final class HospitalController {

    private final HospitalService service;

    public HospitalController() {
        this(new HospitalService());
    }

    public HospitalController(HospitalService service) {
        this.service = service;
    }

    // ---------------- PATIENT ----------------

    public void addPatient(int id, String name, int age) {
        service.addPatient(id, name, age);
    }

    public List<Patient> getAllPatients() {
        return service.getPatients();
    }

    // ---------------- DOCTOR ----------------

    public void addDoctor(int id, String name, String specialization) {
        service.addDoctor(id, name, specialization);
    }

    public List<Doctor> getAllDoctors() {
        return service.getDoctors();
    }

    // ---------------- APPOINTMENT ----------------

    public void bookAppointment(int patientId, int doctorId, int appointmentId, String date, String time) {
        service.bookAppointment(patientId, doctorId, appointmentId, date, time);
    }

    public void bookAppointment(int patientId, int doctorId, int appointmentId) {
        service.bookAppointment(patientId, doctorId, appointmentId);
    }

    public List<Appointment> getAllAppointments() {
        return service.getAllAppointments();
    }

    public List<Appointment> getOptimizedAppointments() {
        return service.getOptimizedAppointments();
    }

    // ---------------- EMERGENCY ----------------

    public void addEmergency(int patientId, String name, int severity) {
        service.addEmergency(patientId, name, severity);
    }

    public EmergencyCase handleEmergency() {
        return service.handleEmergency();
    }

    public List<EmergencyCase> getEmergencyQueueSnapshot() {
        return service.getEmergencyQueueSnapshot();
    }

    // ---------------- BILLING ----------------

    public double generateBill(int patientId, String name, boolean emergency) {
        return service.generateBill(patientId, name, emergency);
    }

    public String generateDetailedReceipt(int patientId, String name, int consultationFee, int testsCost, boolean emergency) {
        return service.generateDetailedReceipt(patientId, name, consultationFee, testsCost, emergency);
    }

    // ---------------- DEMO SEEDER ----------------

    public void loadAcademicDemoData() {
        service.loadAcademicDemoData();
    }
}