package Doctor.Model;

import Interfaces.IEntity;
import User.Model.User;

/**
 * Represents a Doctor entity with basic personal and professional details.
 * Implements the IEntity interface to provide a standardized structure for ID-based entities.
 */
public class Doctor implements IEntity {
    private String id;                // Unique identifier for the doctor
    private String name;              // Name of the doctor
    private Integer age;              // Age of the doctor
    private String gender;            // Gender of the doctor
    private String specialization;    // Medical specialization of the doctor
    private User user = null;         // Associated User object, if any

    /**
     * Constructor to initialize a Doctor object.
     *
     * @param id             Unique identifier for the doctor.
     * @param name           Name of the doctor.
     * @param age            Age of the doctor.
     * @param gender         Gender of the doctor.
     * @param specialization Medical specialization of the doctor.
     */
    public Doctor(String id, String name, Integer age, String gender, String specialization) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.specialization = specialization;
    }

    /**
     * Retrieves the unique ID of the doctor.
     *
     * @return The doctor's unique ID.
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Converts the doctor's details into a CSV-compatible format.
     *
     * @return A string representing the doctor's details in CSV format.
     */
    @Override
    public String toCSV() {
        return id + "," + name + "," + age + "," + gender + "," + specialization;
    }

    /**
     * Sets the doctor's unique ID.
     *
     * @param id The unique ID to set for the doctor.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Retrieves the name of the doctor.
     *
     * @return The doctor's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the doctor.
     *
     * @param name The name to set for the doctor.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the doctor's medical specialization.
     *
     * @return The doctor's specialization.
     */
    public String getSpecialization() {
        return specialization;
    }

    /**
     * Sets the doctor's medical specialization.
     *
     * @param specialization The specialization to set for the doctor.
     */
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    /**
     * Converts the doctor's details into a formatted string for display.
     *
     * @return A string representing the doctor's details.
     */
    @Override
    public String toString() {
        return String.format("Doctor{ID: %s, Name: %s, Gender: %s, Age: %s, Specialization: %s}",
                id, name, gender, age, specialization);
    }

    /**
     * Retrieves the doctor's gender.
     *
     * @return The doctor's gender.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the doctor's gender.
     *
     * @param gender The gender to set for the doctor.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Retrieves the doctor's age.
     *
     * @return The doctor's age.
     */
    public Integer getAge() {
        return age;
    }

    /**
     * Sets the doctor's age.
     *
     * @param age The age to set for the doctor.
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * Retrieves the associated User object for the doctor, if any.
     *
     * @return The associated User object, or null if not set.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the associated User object for the doctor.
     *
     * @param user The User object to associate with the doctor.
     */
    public void setUser(User user) {
        this.user = user;
    }
}
