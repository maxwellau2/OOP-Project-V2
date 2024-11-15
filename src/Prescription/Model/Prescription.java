package Prescription.Model;
import Interfaces.IEntity;


public class Prescription implements IEntity {

    private String id;
    private String doctorId;
    private String patientId;
    private String medicationName;
    private String dosage;
    private String status; // e.g., "pending", "dispensed", etc.
    private String appointmentId;

    public Prescription(String id, String doctorId, String patientId, String medicationName, String dosage, String status, String appointmentId) {
        this.id = id;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.status = status;
        this.appointmentId = appointmentId;
    }
    public Prescription(String doctorId, String patientId, String medicationName, String dosage, String status, String appointmentId) {
        this.id = null;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.status = status;
        this.appointmentId = appointmentId;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Prescription{prescriptionId='" + id + "', doctorId='" + doctorId + "', patientId='" + patientId +
                "', medicationName='" + medicationName + "', dosage='" + dosage + "', status='" + status + "', appointmentId='" + appointmentId + "'}";
    }

    @Override
    public String toCSV() {
        return String.join(",", id, doctorId, patientId, medicationName, dosage, status, appointmentId);
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void prettyPrint() {
        System.out.println("=====================================");
        System.out.println("Prescription ID  : " + (id != null ? id : "Not Assigned"));
        System.out.println("Appointment ID   : " + appointmentId);
        System.out.println("Doctor ID        : " + doctorId);
        System.out.println("Patient ID       : " + patientId);
        System.out.println("Medication Name  : " + medicationName);
        System.out.println("Dosage           : " + dosage);
        System.out.println("Status           : " + status);
        System.out.println("=====================================");
    }

}
