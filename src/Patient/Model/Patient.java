package Patient.Model;

import Interfaces.IEntity;
import User.Model.User;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



/**
 * Represents a Patient entity in the system, encapsulating personal details and medical information.
 */
public class Patient implements IEntity {
    private String phoneNumber;
    private String id;
    private String name;
    private LocalDateTime dob;
    private String gender;
    private String email;
    private String bloodType;
    private User user;

    /**
     * Constructor to create a Patient instance.
     *
     * @param id          Unique identifier for the patient.
     * @param name        Name of the patient.
     * @param dob         Date of birth of the patient.
     * @param gender      Gender of the patient.
     * @param email       Email address of the patient.
     * @param phoneNumber Phone number of the patient.
     * @param bloodType   Blood type of the patient.
     */
    public Patient(String id, String name, LocalDateTime dob, String gender, String email, String phoneNumber, String bloodType) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.bloodType = bloodType;
    }

    /**
     * Retrieves the unique identifier for the patient.
     *
     * @return The patient ID.
     */
    @Override
    public String getId() {
        return this.id;
    }


    /**
     * Converts the patient details into a CSV-compatible string.
     *
     * @return A CSV representation of the patient.
     */
    @Override
    public String toCSV() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE; // yyyy-MM-dd format
        String formattedDob = dob.toLocalDate().format(formatter); // Format the LocalDateTime as yyyy-MM-dd
        return this.id + "," + this.name + "," + formattedDob + "," + this.gender + "," + this.email + "," + this.phoneNumber + "," + this.bloodType;
    }

    /**
     * Retrieves the phone number of the patient.
     *
     * @return The patient's phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Updates the phone number of the patient.
     *
     * @param phoneNumber The new phone number for the patient.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Retrieves the email address of the patient.
     *
     * @return The patient's email address.
     */
    public String getEmail(){
        return email;
    }

    /**
     * Updates the email address of the patient.
     *
     * @param email The new email address for the patient.
     */
    public void setEmail(String email){
        this.email = email;
    }


    /**
     * Updates the unique identifier for the patient.
     *
     * @param id The new patient ID.
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * Retrieves the name of the patient.
     *
     * @return The patient's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the name of the patient.
     *
     * @param name The new name for the patient.
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Retrieves the date of birth of the patient.
     *
     * @return The patient's date of birth.
     */
    public LocalDateTime getDob() {
        return dob;
    }


    /**
     * Updates the date of birth of the patient.
     *
     * @param dob The new date of birth for the patient.
     */
    public void setDob(LocalDateTime dob) {
        this.dob = dob;
    }


    /**
     * Retrieves the gender of the patient.
     *
     * @return The patient's gender.
     */
    public String getGender() {
        return gender;
    }


    /**
     * Updates the gender of the patient.
     *
     * @param gender The new gender for the patient.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }


    /**
     * Retrieves the blood type of the patient.
     *
     * @return The patient's blood type.
     */
    public String getBloodType() {
        return bloodType;
    }


    /**
     * Updates the blood type of the patient.
     *
     * @param bloodType The new blood type for the patient.
     */
    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }



    /**
     * Retrieves the User object associated with the patient.
     *
     * @return The User object linked to the patient, or null if none.
     */
    public User getUser() {
        return user;
    }


    /**
     * Associates a User object with the patient.
     *
     * @param user The User object to be linked to the patient.
     */
    public void setUser(User user) {
        this.user = user;
    }


    /**
     * Converts the patient details into a string representation.
     *
     * @return A string representation of the patient.
     */
    @Override
    public String toString() {
        return "Patient {" +
                "ID='" + id + '\'' +
                ", Name='" + name + '\'' +
                ", DOB=" + dob +
                ", Gender='" + gender + '\'' +
                ", Email='" + email + '\'' +
                ", Phone Number='" + phoneNumber + '\'' +
                ", Blood Type='" + bloodType + '\'' +
                ", User=" + (user != null ? user.toString() : "None") +
                '}';
    }

}
