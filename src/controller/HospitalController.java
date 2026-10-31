package controller;

import service.HospitalService;
import model.*;

import java.util.List;

public class HospitalController {

    private HospitalService service = new HospitalService();

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

    // ---------------- BILLING ----------------

    public double generateBill(int patientId, String name, boolean emergency) {
        return service.generateBill(patientId, name, emergency);
    }
}