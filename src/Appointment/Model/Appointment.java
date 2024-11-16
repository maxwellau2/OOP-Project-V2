package Appointment.Model;

import Interfaces.IEntity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Represents an Appointment entity in the system, containing details about the patient, doctor, date, and status.
 */
public class Appointment implements IEntity {
    private String id;
    private String patientId;
    private String doctorId;
    private LocalDateTime date;
    private String status;

    /**
     * Constructor to create an Appointment instance.
     *
     * @param id         Unique identifier for the appointment.
     * @param patientId  ID of the patient associated with the appointment.
     * @param doctorId   ID of the doctor associated with the appointment.
     * @param date       Date and time of the appointment.
     * @param status     Status of the appointment (e.g., Confirmed, Completed, Pending).
     */
    public Appointment(String id, String patientId, String doctorId, LocalDateTime date, String status) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.status = status;
    }


    /**
     * Retrieves the unique identifier for the appointment.
     *
     * @return The appointment ID.
     */

    @Override
    public String getId() {
        return id;
    }


    /**
     * Converts the appointment details into a CSV-compatible string.
     *
     * @return A CSV representation of the appointment.
     */
    @Override
    public String toCSV() {
        String dateString = (date != null) ? date.format(DateTimeFormatter.ISO_DATE_TIME) : "";
        return id + "," + patientId + "," + doctorId + "," + dateString + "," + status;
    }


    /**
     * Sets the unique identifier for the appointment.
     *
     * @param id The new appointment ID.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Retrieves the patient ID associated with the appointment.
     *
     * @return The patient ID.
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * Updates the patient ID associated with the appointment.
     *
     * @param patientId The new patient ID.
     */
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    /**
     * Retrieves the doctor ID associated with the appointment.
     *
     * @return The doctor ID.
     */
    public String getDoctorId() {
        return doctorId;
    }


    /**
     * Updates the doctor ID associated with the appointment.
     *
     * @param doctorId The new doctor ID.
     */
    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    /**
     * Retrieves the date and time of the appointment.
     *
     * @return The appointment date and time.
     */
    public LocalDateTime getDate() {
        return date;
    }


    /**
     * Updates the date and time of the appointment.
     *
     * @param date The new appointment date and time.
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }


    /**
     * Retrieves the current status of the appointment.
     *
     * @return The appointment status.
     */
    public String getStatus() {
        return status;
    }


    /**
     * Updates the status of the appointment.
     *
     * @param status The new status for the appointment.
     */
    public void setStatus(String status) {
        this.status = status;
    }


    /**
     * Prints the details of the appointment in a human-readable format.
     */
    public void prettyPrint() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

        System.out.println("Appointment Details:");
        System.out.println("----------------------------");
        System.out.println("Appointment ID: " + id);
        System.out.println("Patient ID    : " + patientId);
        System.out.println("Doctor ID     : " + doctorId);
        System.out.println("Date          : " + date.format(dateFormatter));
        System.out.println("Time          : " + date.format(timeFormatter));
        System.out.println("Status        : " + status);
        System.out.println("----------------------------");
    }

    /**
     * Converts the appointment details into a string representation.
     *
     * @return A string representation of the appointment.
     */
    @Override
    public String toString() {
        return "Appointment {id=" + id + ", patientId=" + patientId + ", doctorId="
                + doctorId + ", date=" + date + ", status=" + status + "}";
    }
}
