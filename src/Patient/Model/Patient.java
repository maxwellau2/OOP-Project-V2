package Patient.Model;

import Interfaces.IEntity;
import User.Model.User;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Patient implements IEntity {
    private String id;
    private String name;
    private LocalDateTime dob;
    private String gender;
    private String contactInfo;
    private String bloodType;
    private User user;

    public Patient(String id, String name, LocalDateTime dob, String gender, String contactInfo, String bloodType) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.contactInfo = contactInfo;
        this.bloodType = bloodType;
    }

    @Override
    public String getId() {
        return this.id;
    }


    @Override
    public String toCSV() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE; // yyyy-MM-dd format
        String formattedDob = dob.toLocalDate().format(formatter); // Format the LocalDateTime as yyyy-MM-dd
        return this.id + "," + this.name + "," + formattedDob + "," + this.gender + "," + this.contactInfo + "," + this.bloodType;
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

    public LocalDateTime getDob() {
        return dob;
    }

    public void setDob(LocalDateTime dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Patient {" +
                "ID='" + id + '\'' +
                ", Name='" + name + '\'' +
                ", DOB=" + dob +
                ", Gender='" + gender + '\'' +
                ", Contact Info='" + contactInfo + '\'' +
                ", Blood Type='" + bloodType + '\'' +
                ", User=" + (user != null ? user.toString() : "None") +
                '}';
    }

}
