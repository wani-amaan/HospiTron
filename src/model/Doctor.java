package model;

public class Doctor {

    private int id;
    private String name;
    private String specialization;
    private boolean available;

    public Doctor(int id, String name, String specialization, boolean available) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.available = available;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { 
        this.id = id; 
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { 
        this.name = name; 
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) { 
        this.specialization = specialization; 
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return id + "," + name + "," + specialization + "," + available;
    }
}