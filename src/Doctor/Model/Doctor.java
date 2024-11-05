package Doctor.Model;

import Interfaces.IEntity;

public class Doctor implements IEntity {
    private String id;
    private String name;
    private String specialization;
    public Doctor(String id, String name, String specialization) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toCSV() {
        return id + "," + name + "," + specialization;
    }

    public void setId(String id) {
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

    @Override
    public String toString() {
        return String.format("Doctor{ID: %s, Name: %s, Specialization: %s}", id, name, specialization);
    }
}
