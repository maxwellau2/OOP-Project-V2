package Appointment.Model;

import Interfaces.IEntity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment implements IEntity {
    private String id;
    private String patientId;
    private String doctorId;
    private LocalDateTime date;
    private String status;
    public Appointment(String id, String patientId, String doctorId, LocalDateTime date, String status) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toCSV() {
        String dateString = (date != null) ? date.format(DateTimeFormatter.ISO_DATE_TIME) : "";
        return id + "," + patientId + "," + doctorId + "," + dateString + "," + status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

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

    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public String toString() {
        return "Appointment {id=" + id + ", patientId=" + patientId + ", doctorId="
                + doctorId + ", date=" + date + ", status=" + status + "}";
    }
}
