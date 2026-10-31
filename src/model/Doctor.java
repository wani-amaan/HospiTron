package model;

/**
 * Modern Java record representing a hospital doctor entity.
 */
public record Doctor(int id, String name, String specialization, boolean available) {

    public Doctor {
        if (id < 0) throw new IllegalArgumentException("Doctor ID cannot be negative.");
        name = name != null ? name.strip() : "";
        specialization = specialization != null ? specialization.strip() : "";
    }

    public Doctor withAvailable(boolean newAvailability) {
        return new Doctor(id, name, specialization, newAvailability);
    }

    // JavaBean getter aliases
    public int getId() { return id; }
    public String getName() { return name; }
    public String getSpecialization() { return specialization; }
    public boolean isAvailable() { return available; }

    @Override
    public String toString() {
        return id + "," + name + "," + specialization + "," + available;
    }
}