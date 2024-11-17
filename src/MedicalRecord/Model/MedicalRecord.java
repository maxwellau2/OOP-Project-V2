package MedicalRecord.Model;

import Interfaces.IEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



/**
 * Represents a medical record in the healthcare system.
 * The `MedicalRecord` class contains details about a patient's medical history, including the diagnosis, treatment, and the associated doctor and patient.
 */
public class MedicalRecord implements IEntity {
    private String recordId;      // Unique identifier for the medical record
    private String patientId;     // ID of the patient associated with the record
    private String doctorId;      // ID of the doctor who created the record
    private String diagnosis;     // Diagnosis details
    private String treatment;     // Treatment details
    private LocalDateTime date;   // Date and time the record was created

    /**
     * Constructs a new `MedicalRecord` object.
     *
     * @param recordId  The unique ID of the medical record.
     * @param patientId The ID of the patient.
     * @param doctorId  The ID of the doctor.
     * @param diagnosis The diagnosis details.
     * @param treatment The treatment details.
     * @param date      The date and time the record was created.
     */
    public MedicalRecord(String recordId, String patientId, String doctorId, String diagnosis, String treatment, LocalDateTime date) {
        this.recordId = recordId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.date = date;
    }

    /**
     * Gets the record ID.
     *
     * @return The record ID.
     */
    public String getRecordId() {
        return recordId;
    }

    /**
     * Sets the record ID.
     *
     * @param recordId The new record ID.
     */
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    /**
     * Gets the patient ID.
     *
     * @return The patient ID.
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * Sets the patient ID.
     *
     * @param patientId The new patient ID.
     */
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    /**
     * Gets the doctor ID.
     *
     * @return The doctor ID.
     */
    public String getDoctorId() {
        return doctorId;
    }

    /**
     * Sets the doctor ID.
     *
     * @param doctorId The new doctor ID.
     */
    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    /**
     * Gets the diagnosis details.
     *
     * @return The diagnosis details.
     */
    public String getDiagnosis() {
        return diagnosis;
    }

    /**
     * Sets the diagnosis details.
     *
     * @param diagnosis The new diagnosis details.
     */
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    /**
     * Gets the treatment details.
     *
     * @return The treatment details.
     */
    public String getTreatment() {
        return treatment;
    }

    /**
     * Sets the treatment details.
     *
     * @param treatment The new treatment details.
     */
    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    /**
     * Gets the date and time the record was created.
     *
     * @return The creation date and time.
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Sets the date and time the record was created.
     *
     * @param date The new date and time.
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Prints the medical record details in a formatted manner.
     */
    public void prettyPrintMedicalRecord() {
        System.out.println("Record ID: " + recordId);
        System.out.println("\t\tPatient ID: " + patientId);
        System.out.println("\t\tDoctor ID: " + doctorId);
        System.out.println("\t\tDiagnosis: " + diagnosis);
        System.out.println("\t\tTreatment: " + treatment);
        System.out.println("\t\tDate: " + date);
    }

    /**
     * Gets the ID of the medical record (used for IEntity interface).
     *
     * @return The record ID.
     */
    @Override
    public String getId() {
        return recordId;
    }

    /**
     * Converts the medical record to a CSV-formatted string.
     *
     * @return A CSV-formatted string representation of the medical record.
     */
    @Override
    public String toCSV() {
        String dateString = (date != null) ? date.format(DateTimeFormatter.ISO_DATE_TIME) : "";
        return recordId + "," + patientId + "," + doctorId + "," + diagnosis + "," + treatment + "," + dateString;
    }

    /**
     * Returns a string representation of the medical record.
     *
     * @return A string containing the details of the medical record.
     */
    @Override
    public String toString() {
        return "MedicalRecord {recordId=" + recordId + ", patientId=" + patientId + ", doctorId=" + doctorId
                + ", diagnosis=" + diagnosis + ", treatment=" + treatment + ", date=" + date + "}";
    }
}
