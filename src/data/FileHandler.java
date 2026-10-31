package data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import model.Appointment;
import model.Doctor;
import model.Patient;

public class FileHandler {

    private static final String DATA_DIR = "data";
    private static final String PATIENT_FILE = "data/patients.txt";
    private static final String DOCTOR_FILE = "data/doctors.txt";
    private static final String APPOINTMENT_FILE = "data/appointments.txt";

    public static void ensureDataDirectoryExists() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    // ---------------- PATIENT ----------------

    public static void savePatients(List<Patient> list) {
        ensureDataDirectoryExists();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PATIENT_FILE))) {
            for (Patient p : list) {
                bw.write(p.getId() + "," + p.getName() + "," + p.getAge());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving patients: " + e.getMessage());
        }
    }

    public static List<Patient> loadPatients() {
        List<Patient> list = new ArrayList<>();
        File file = new File(PATIENT_FILE);
        if (!file.exists()) return list;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    list.add(new Patient(
                            Integer.parseInt(parts[0].trim()),
                            parts[1].trim(),
                            Integer.parseInt(parts[2].trim())
                    ));
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading patients: " + e.getMessage());
        }

        return list;
    }

    // ---------------- DOCTOR ----------------

    public static void saveDoctors(List<Doctor> list) {
        ensureDataDirectoryExists();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DOCTOR_FILE))) {
            for (Doctor d : list) {
                bw.write(d.getId() + "," + d.getName() + "," + d.getSpecialization() + "," + d.isAvailable());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving doctors: " + e.getMessage());
        }
    }

    public static List<Doctor> loadDoctors() {
        List<Doctor> list = new ArrayList<>();
        File file = new File(DOCTOR_FILE);
        if (!file.exists()) return list;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    boolean available = parts.length > 3 ? Boolean.parseBoolean(parts[3].trim()) : true;
                    list.add(new Doctor(
                            Integer.parseInt(parts[0].trim()),
                            parts[1].trim(),
                            parts[2].trim(),
                            available
                    ));
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading doctors: " + e.getMessage());
        }

        return list;
    }

    // ---------------- APPOINTMENTS ----------------

    public static void saveAppointments(List<Appointment> list) {
        ensureDataDirectoryExists();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(APPOINTMENT_FILE))) {
            for (Appointment a : list) {
                bw.write(a.getAppointmentId() + "," +
                        a.getPatientId() + "," +
                        a.getDoctorId() + "," +
                        a.getStatus() + "," +
                        a.getDate() + "," +
                        a.getTime());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving appointments: " + e.getMessage());
        }
    }

    public static List<Appointment> loadAppointments() {
        List<Appointment> list = new ArrayList<>();
        File file = new File(APPOINTMENT_FILE);
        if (!file.exists()) return list;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length >= 6) {
                    list.add(new Appointment(
                            Integer.parseInt(p[0].trim()),
                            Integer.parseInt(p[1].trim()),
                            Integer.parseInt(p[2].trim()),
                            p[3].trim(),
                            "NORMAL",
                            p[4].trim(),
                            p[5].trim()
                    ));
                } else if (p.length >= 4) {
                    list.add(new Appointment(
                            Integer.parseInt(p[0].trim()),
                            Integer.parseInt(p[1].trim()),
                            Integer.parseInt(p[2].trim()),
                            p[3].trim(),
                            "NORMAL",
                            "31-10-2026",
                            "10:00"
                    ));
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading appointments: " + e.getMessage());
        }

        return list;
    }
}