package Prescription.Model;
import Interfaces.IEntity;


public class Prescription implements IEntity {

    private String id;
    private String doctorId;
    private String patientId;
    private String medicationName;
    private String dosage;
    private String status; // e.g., "pending", "dispensed", etc.


    public Prescription(String id, String doctorId, String patientId, String medicationName, String dosage, String status) {
        this.id = id;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.status = status;
    }
    public Prescription(String doctorId, String patientId, String medicationName, String dosage, String status) {
        this.id = null;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.status = status;
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
                "', medicationName='" + medicationName + "', dosage='" + dosage + "', status='" + status + "'}";
    }

    @Override
    public String toCSV() {
        return String.join(",", id, doctorId, patientId, medicationName, dosage, status);
    }
}
