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

    public List<Appointment> getSlotDeduplicatedAppointments() {
        var scheduler = new algorithm.GreedyScheduler();
        return scheduler.schedule(new ArrayList<>(appointments));
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

    public List<EmergencyCase> getEmergencyQueueSnapshot() {
        return emergencyQueue.stream()
                .sorted((a, b) -> Integer.compare(b.severity(), a.severity()))
                .toList();
    }

    public int getEmergencyQueueSize() {
        return emergencyQueue.size();
    }

    // ---------------- ACADEMIC DEMONSTRATION DATA SEEDER ----------------

    public void loadAcademicDemoData() {
        patients.clear();
        patients.addAll(List.of(
                new Patient(101, "Zubair Ahmad", 34),
                new Patient(102, "Aamina Jan", 28),
                new Patient(103, "Tariq Shah", 52),
                new Patient(104, "Fatima Bano", 65),
                new Patient(105, "John Doe (Foreign National)", 41)
        ));
        FileHandler.savePatients(patients);

        doctors.clear();
        doctors.addAll(List.of(
                new Doctor(201, "Shahid Sultan", "Chief of Surgery / Trauma", true),
                new Doctor(202, "Sobia Rashid", "Cardiology", true),
                new Doctor(203, "Farooq Mir", "Neurology", true),
                new Doctor(204, "Nadia Malik", "Emergency Medicine", true),
                new Doctor(205, "Hilal Lone", "Orthopedics", true)
        ));
        FileHandler.saveDoctors(doctors);

        appointments.clear();
        appointments.addAll(List.of(
                new Appointment(301, 101, 201, "CONFIRMED", "NORMAL", "31-10-2026", "10:00"),
                new Appointment(302, 102, 201, "CONFIRMED", "NORMAL", "31-10-2026", "10:10"), // Overlaps 301
                new Appointment(303, 103, 202, "CONFIRMED", "NORMAL", "31-10-2026", "10:30"),
                new Appointment(304, 104, 203, "CONFIRMED", "NORMAL", "31-10-2026", "10:40"), // Overlaps 303
                new Appointment(305, 105, 204, "CONFIRMED", "NORMAL", "31-10-2026", "11:00"),
                new Appointment(306, 101, 205, "CONFIRMED", "NORMAL", "31-10-2026", "11:10"), // Overlaps 305
                new Appointment(307, 102, 201, "CONFIRMED", "NORMAL", "31-10-2026", "11:30")
        ));
        FileHandler.saveAppointments(appointments);

        emergencyQueue.clear();
        emergencyQueue.offer(new EmergencyCase(103, "Tariq Shah (Mild Pain)", 4));
        emergencyQueue.offer(new EmergencyCase(104, "Fatima Bano (Acute Trauma)", 9));
        emergencyQueue.offer(new EmergencyCase(101, "Zubair Ahmad (Compound Fracture)", 7));
    }
}