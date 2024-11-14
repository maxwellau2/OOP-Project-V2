package Doctor.Model;

import Interfaces.IEntity;
import User.Model.User;

public class Doctor implements IEntity {
    private String id;
    private String name;
    private Integer age;
    private String gender;
    private String specialization;
    private User user = null;
    public Doctor(String id, String name, Integer age, String gender, String specialization) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.specialization = specialization;
    }

    @Override
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
        return String.format("Doctor{ID: %s, Name: %s, Gender: %s, Age: %s, Specialization: %s}", id, name, gender, age, specialization);
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
