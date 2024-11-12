package Patient.Model;

import Interfaces.IEntity;
import User.Model.User;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Patient implements IEntity {
    private String phoneNumber;
    private String id;
    private String name;
    private LocalDateTime dob;
    private String gender;
    private String email;
    private String bloodType;
    private User user;

    public Patient(String id, String name, LocalDateTime dob, String gender, String email, String phoneNumber, String bloodType) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;
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
        return this.id + "," + this.name + "," + formattedDob + "," + this.gender + "," + this.email + "," + this.phoneNumber + "," + this.bloodType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
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
                ", Email='" + email + '\'' +
                ", Phone Number='" + phoneNumber + '\'' +
                ", Blood Type='" + bloodType + '\'' +
                ", User=" + (user != null ? user.toString() : "None") +
                '}';
    }

}
