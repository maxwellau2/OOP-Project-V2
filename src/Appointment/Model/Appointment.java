package Appointment.Model;

import Interfaces.IEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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

    public void setStatus(String status) {
        this.status = status;
    }
    public String toString() {
        return "Appointment {id=" + id + ", patientId=" + patientId + ", doctorId="
                + doctorId + ", date=" + date + ", status=" + status + "}";
    }
}
