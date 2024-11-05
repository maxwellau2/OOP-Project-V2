package Doctor.Model;

import Interfaces.IEntity;

public class Doctor implements IEntity {
    private String id;
    private String name;
    private Integer age;
    private String gender;
    private String specialization;
    public Doctor(String id, String name, Integer age, String gender, String specialization) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.specialization = specialization;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toCSV() {
        return id + "," + name + "," + age + "," + gender + "," + specialization;
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
        return String.format("Doctor{ID: %s, Name: %s, Gender: %s, Specialization: %s}", id, name, gender, specialization);
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
