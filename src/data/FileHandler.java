package data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import model.Appointment;
import model.Doctor;
import model.Patient;

/**
 * Modern Java NIO-based data persistence layer for HospiTron.
 */
public final class FileHandler {

    private static final Path DATA_DIR = Path.of("data");
    private static final Path PATIENT_FILE = DATA_DIR.resolve("patients.txt");
    private static final Path DOCTOR_FILE = DATA_DIR.resolve("doctors.txt");
    private static final Path APPOINTMENT_FILE = DATA_DIR.resolve("appointments.txt");

    private FileHandler() {}

    public static void ensureDataDirectoryExists() {
        try {
            if (!Files.exists(DATA_DIR)) {
                Files.createDirectories(DATA_DIR);
            }
        } catch (IOException e) {
            System.err.println("Failed to create data directory: " + e.getMessage());
        }
    }

    // ---------------- PATIENT ----------------

    public static void savePatients(List<Patient> list) {
        ensureDataDirectoryExists();
        var lines = list.stream()
                .map(p -> p.id() + "," + p.name() + "," + p.age())
                .toList();

        try {
            Files.write(PATIENT_FILE, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error saving patients: " + e.getMessage());
        }
    }

    public static List<Patient> loadPatients() {
        ensureDataDirectoryExists();
        if (!Files.exists(PATIENT_FILE)) return new ArrayList<>();

        try (var lines = Files.lines(PATIENT_FILE)) {
            return lines.filter(line -> !line.isBlank())
                    .map(line -> line.split(","))
                    .filter(parts -> parts.length >= 3)
                    .map(parts -> new Patient(
                            Integer.parseInt(parts[0].strip()),
                            parts[1].strip(),
                            Integer.parseInt(parts[2].strip())
                    ))
                    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        } catch (Exception e) {
            System.err.println("Error loading patients: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // ---------------- DOCTOR ----------------

    public static void saveDoctors(List<Doctor> list) {
        ensureDataDirectoryExists();
        var lines = list.stream()
                .map(d -> d.id() + "," + d.name() + "," + d.specialization() + "," + d.available())
                .toList();

        try {
            Files.write(DOCTOR_FILE, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error saving doctors: " + e.getMessage());
        }
    }

    public static List<Doctor> loadDoctors() {
        ensureDataDirectoryExists();
        if (!Files.exists(DOCTOR_FILE)) return new ArrayList<>();

        try (var lines = Files.lines(DOCTOR_FILE)) {
            return lines.filter(line -> !line.isBlank())
                    .map(line -> line.split(","))
                    .filter(parts -> parts.length >= 3)
                    .map(parts -> {
                        boolean available = parts.length > 3 ? Boolean.parseBoolean(parts[3].strip()) : true;
                        return new Doctor(
                                Integer.parseInt(parts[0].strip()),
                                parts[1].strip(),
                                parts[2].strip(),
                                available
                        );
                    })
                    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        } catch (Exception e) {
            System.err.println("Error loading doctors: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // ---------------- APPOINTMENTS ----------------

    public static void saveAppointments(List<Appointment> list) {
        ensureDataDirectoryExists();
        var lines = list.stream()
                .map(a -> a.appointmentId() + "," +
                          a.patientId() + "," +
                          a.doctorId() + "," +
                          a.status() + "," +
                          a.date() + "," +
                          a.time())
                .toList();

        try {
            Files.write(APPOINTMENT_FILE, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error saving appointments: " + e.getMessage());
        }
    }

    public static List<Appointment> loadAppointments() {
        ensureDataDirectoryExists();
        if (!Files.exists(APPOINTMENT_FILE)) return new ArrayList<>();

        try (var lines = Files.lines(APPOINTMENT_FILE)) {
            return lines.filter(line -> !line.isBlank())
                    .map(line -> line.split(","))
                    .filter(parts -> parts.length >= 4)
                    .map(parts -> {
                        String date = parts.length >= 6 ? parts[4].strip() : "31-10-2026";
                        String time = parts.length >= 6 ? parts[5].strip() : "10:00";
                        return new Appointment(
                                Integer.parseInt(parts[0].strip()),
                                Integer.parseInt(parts[1].strip()),
                                Integer.parseInt(parts[2].strip()),
                                parts[3].strip(),
                                "NORMAL",
                                date,
                                time
                        );
                    })
                    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        } catch (Exception e) {
            System.err.println("Error loading appointments: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}