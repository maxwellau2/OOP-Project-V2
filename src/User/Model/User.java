package User.Model;

import Interfaces.IEntity;
import User.Repository.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class User implements IEntity{
    private String hospitalID;
    private String passwordHash;
    private String role;
    private boolean isBlacklist;
    private LocalDateTime lastLogin;

    // Constructor
    public User(String hospitalID, String passwordHash, String role) {
        this.hospitalID = hospitalID;
        this.passwordHash = passwordHash;
        this.role = role;
        this.isBlacklist = false; // Default to not blacklisted
        this.lastLogin = null;    // Default to no login initially
    }

    // Getters and Setters
    public String getHospitalID() {
        return hospitalID;
    }

    public void setHospitalID(String hospitalID) {
        this.hospitalID = hospitalID;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isAdmin(){
        return this.role.equals("Administrator");
    }

    public boolean isBlacklist() {
        return isBlacklist;
    }

    public void setBlacklist(boolean isBlacklist) {
        this.isBlacklist = isBlacklist;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }


    // Login method: verifies if provided credentials are correct
    public boolean login(String hospitalID, String passwordHash) {
        if (this.isBlacklist) {
            System.out.println("User is blacklisted and cannot log in.");
            return false;
        }

        if (this.hospitalID.equals(hospitalID) && this.passwordHash.equals(passwordHash)) {
            this.lastLogin = LocalDateTime.now();
            System.out.println("Login successful.");
            return true;
        } else {
            System.out.println("Invalid credentials.");
            return false;
        }
    }

    // Change password method
    public void changePassword(String oldPasswordHash, String newPasswordHash) {
        if (this.passwordHash.equals(oldPasswordHash)) {
            this.passwordHash = newPasswordHash;
            System.out.println("Password changed successfully.");
        } else {
            System.out.println("Old password is incorrect.");
        }
    }

    @Override
    public String toString() {
        return "User{hospitalID='" + hospitalID + "', role='" + role + "', isBlacklist=" + isBlacklist +
                ", lastLogin=" + (lastLogin != null ? lastLogin : "Never") + "}";
    }

    @Override
    public String getId() {
        return hospitalID;
    }

    @Override
    public String toCSV() {
        String lastLoginStr = (lastLogin != null) ? lastLogin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "";
        return String.join(",", hospitalID, passwordHash, role, Boolean.toString(isBlacklist), lastLoginStr);
    }
}

