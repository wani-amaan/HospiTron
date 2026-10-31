package model;

/**
 * Modern Java record representing a hospital patient entity.
 * Provides compact constructor validation, immutability, and legacy getter compatibility.
 */
public record Patient(int id, String name, int age) {

    public Patient {
        if (id < 0) throw new IllegalArgumentException("Patient ID cannot be negative.");
        name = name != null ? name.strip() : "";
    }

    // JavaBean getter aliases for backward compatibility
    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }

    @Override
    public String toString() {
        return id + "," + name + "," + age;
    }
}