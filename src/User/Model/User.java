package User.Model;

import Interfaces.IEntity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Represents a User entity within the system.
 * Implements {@link IEntity} and includes attributes such as hospital ID, role, blacklist status, and last login timestamp.
 * Provides methods for login, password change, and conversion to CSV format.
 */
public class User implements IEntity{
    private String hospitalID;
    private String passwordHash;
    private String role;
    private boolean isBlacklist;
    private LocalDateTime lastLogin;

    // Constructor
    /**
     * Constructs a {@link User} object with the specified hospital ID, password hash, and role.
     * By default, the user is not blacklisted, and no login has been recorded initially.
     *
     * @param hospitalID   The unique hospital ID for the user.
     * @param passwordHash The hashed password for the user.
     * @param role         The role assigned to the user (e.g., Administrator, Doctor, etc.).
     */
    public User(String hospitalID, String passwordHash, String role) {
        this.hospitalID = hospitalID;
        this.passwordHash = passwordHash;
        this.role = role;
        this.isBlacklist = false; // Default to not blacklisted
        this.lastLogin = null;    // Default to no login initially
    }

    // Getters and Setters
    /**
     * Gets the hospital ID of the user.
     *
     * @return The hospital ID.
     */
    public String getHospitalID() {
        return hospitalID;
    }

    /**
     * Sets the hospital ID of the user.
     *
     * @param hospitalID The new hospital ID.
     */
    public void setHospitalID(String hospitalID) {
        this.hospitalID = hospitalID;
    }


    /**
     * Gets the hashed password of the user.
     *
     * @return The password hash.
     */
    public String getPasswordHash() {
        return passwordHash;
    }


    /**
     * Sets the hashed password of the user.
     *
     * @param passwordHash The new password hash.
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }


    /**
     * Gets the role of the user.
     *
     * @return The role (e.g., Administrator, Doctor, etc.).
     */
    public String getRole() {
        return role;
    }


    /**
     * Sets the role of the user.
     *
     * @param role The new role.
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Checks if the user has the "Administrator" role.
     *
     * @return True if the role is "Administrator", false otherwise.
     */
    public boolean isAdmin(){
        return this.role.equals("Administrator");
    }


    /**
     * Checks if the user is blacklisted.
     *
     * @return True if blacklisted, false otherwise.
     */
    public boolean isBlacklist() {
        return isBlacklist;
    }


    /**
     * Sets the blacklist status of the user.
     *
     * @param isBlacklist True to blacklist the user, false otherwise.
     */
    public void setBlacklist(boolean isBlacklist) {
        this.isBlacklist = isBlacklist;
    }


    /**
     * Gets the last login timestamp of the user.
     *
     * @return The last login timestamp, or null if no login has occurred.
     */
    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    /**
     * Sets the last login timestamp of the user.
     *
     * @param lastLogin The new last login timestamp.
     */
    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }


    // Login method: verifies if provided credentials are correct
    /**
     * Verifies the login credentials provided by the user.
     *
     * @param hospitalID   The hospital ID entered by the user.
     * @param passwordHash The password hash entered by the user.
     * @return True if the credentials match and the user is not blacklisted, false otherwise.
     */
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

    /**
     * Returns a string representation of the user.
     *
     * @return A string containing the hospital ID, role, blacklist status, and last login timestamp.
     */
    @Override
    public String toString() {
        return "User{hospitalID='" + hospitalID + "', role='" + role + "', isBlacklist=" + isBlacklist +
                ", lastLogin=" + (lastLogin != null ? lastLogin : "Never") + "}";
    }

    /**
     * Gets the unique ID of the user (hospital ID).
     *
     * @return The hospital ID.
     */
    @Override
    public String getId() {
        return hospitalID;
    }


    /**
     * Converts the user object to a CSV-formatted string.
     *
     * @return A CSV string representation of the user.
     */
    @Override
    public String toCSV() {
        String lastLoginStr = (lastLogin != null) ? lastLogin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "";
        return String.join(",", hospitalID, passwordHash, role, Boolean.toString(isBlacklist), lastLoginStr);
    }
}

