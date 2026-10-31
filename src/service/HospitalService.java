package service;

import model.*;

import java.io.*;
import java.util.*;

public class HospitalService {

    private List<Patient> patients = new ArrayList<>();
    private List<Doctor> doctors = new ArrayList<>();
    private List<Appointment> appointments = new ArrayList<>();
    private PriorityQueue<EmergencyCase> emergencyQueue = new PriorityQueue<>(
            (a, b) -> Integer.compare(b.getSeverity(), a.getSeverity()) // Max-heap: highest severity first
    );

    private final String DATA_DIR = "data";
    private final String PATIENT_FILE = "data/patients.txt";
    private final String DOCTOR_FILE = "data/doctors.txt";
    private final String APPOINTMENT_FILE = "data/appointments.txt";

    public HospitalService() {
        ensureDataDirectoryExists();
        loadAllData();
    }

    private void ensureDataDirectoryExists() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    // ---------------- PATIENT ----------------

    public void addPatient(int id, String name, int age) {
        patients.add(new Patient(id, name, age));
        savePatients();
    }

    public List<Patient> getPatients() {
        return patients;
    }

    private void savePatients() {
        ensureDataDirectoryExists();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PATIENT_FILE))) {
            for (Patient p : patients) {
                bw.write(p.getId() + "," + p.getName() + "," + p.getAge());
                bw.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---------------- DOCTOR ----------------

    public void addDoctor(int id, String name, String specialization) {
        doctors.add(new Doctor(id, name, specialization, true));
        saveDoctors();
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    private void saveDoctors() {
        ensureDataDirectoryExists();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DOCTOR_FILE))) {
            for (Doctor d : doctors) {
                bw.write(d.getId() + "," + d.getName() + "," + d.getSpecialization());
                bw.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        saveAppointments();
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

    private void saveAppointments() {
        ensureDataDirectoryExists();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(APPOINTMENT_FILE))) {

            for (Appointment a : appointments) {
                bw.write(
                        a.getAppointmentId() + "," +
                        a.getPatientId() + "," +
                        a.getDoctorId() + "," +
                        a.getStatus() + "," +
                        a.getDate() + "," +
                        a.getTime()
                );
                bw.newLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---------------- LOAD DATA ----------------

    public void loadAllData() {
        loadPatients();
        loadDoctors();
        loadAppointments();
    }

    private void loadPatients() {
        File file = new File(PATIENT_FILE);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length >= 3) {
                    patients.add(new Patient(
                            Integer.parseInt(p[0].trim()),
                            p[1].trim(),
                            Integer.parseInt(p[2].trim())
                    ));
                }
            }
        } catch (Exception ignored) {}
    }

    private void loadDoctors() {
        File file = new File(DOCTOR_FILE);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                if (d.length >= 3) {
                    doctors.add(new Doctor(
                            Integer.parseInt(d[0].trim()),
                            d[1].trim(),
                            d[2].trim(),
                            true
                    ));
                }
            }
        } catch (Exception ignored) {}
    }

    private void loadAppointments() {
        File file = new File(APPOINTMENT_FILE);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] a = line.split(",");
                if (a.length >= 6) {
                    appointments.add(new Appointment(
                            Integer.parseInt(a[0].trim()),
                            Integer.parseInt(a[1].trim()),
                            Integer.parseInt(a[2].trim()),
                            a[3].trim(),
                            "NORMAL",
                            a[4].trim(),
                            a[5].trim()
                    ));
                } else if (a.length >= 4) {
                    appointments.add(new Appointment(
                            Integer.parseInt(a[0].trim()),
                            Integer.parseInt(a[1].trim()),
                            Integer.parseInt(a[2].trim()),
                            a[3].trim(),
                            "NORMAL",
                            "31-10-2026",
                            "10:00"
                    ));
                }
            }
        } catch (Exception ignored) {}
    }

    // ---------------- BILLING ----------------

    public double generateBill(int patientId, String patientName, boolean emergency) {
        if (emergency) return 5000;
        return 2000;
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