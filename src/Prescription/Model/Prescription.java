package Prescription.Model;
import Interfaces.IEntity;


/**
 * The Prescription class represents a medical prescription in the system.
 * It includes details about the prescription such as the doctor, patient,
 * medication, dosage, status, and the associated appointment.
 */
public class Prescription implements IEntity {

    private String id;
    private String doctorId;
    private String patientId;
    private String medicationName;
    private String dosage;
    private String status; // e.g., "pending", "dispensed", etc.
    private String appointmentId;


    /**
     * Constructor for creating a Prescription with all fields.
     *
     * @param id             Unique identifier for the prescription
     * @param doctorId       ID of the prescribing doctor
     * @param patientId      ID of the patient
     * @param medicationName Name of the prescribed medication
     * @param dosage         Dosage of the medication
     * @param status         Status of the prescription
     * @param appointmentId  ID of the associated appointment
     */
    public Prescription(String id, String doctorId, String patientId, String medicationName, String dosage, String status, String appointmentId) {
        this.id = id;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.status = status;
        this.appointmentId = appointmentId;
    }

    /**
     * Constructor for creating a Prescription without an ID.
     * Useful for creating new prescriptions before assigning an ID.
     *
     * @param doctorId       ID of the prescribing doctor
     * @param patientId      ID of the patient
     * @param medicationName Name of the prescribed medication
     * @param dosage         Dosage of the medication
     * @param status         Status of the prescription
     * @param appointmentId  ID of the associated appointment
     */
    public Prescription(String doctorId, String patientId, String medicationName, String dosage, String status, String appointmentId) {
        this.id = null;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.status = status;
        this.appointmentId = appointmentId;
    }


    /**
     * Gets the prescription ID.
     *
     * @return The prescription ID.
     */
    @Override
    public String getId() {
        return id;
    }


    /**
     * Sets the prescription ID.
     *
     * @param id The prescription ID.
     */

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the ID of the prescribing doctor.
     *
     * @return The doctor ID.
     */
    public String getDoctorId() {
        return doctorId;
    }

    /**
     * Sets the ID of the prescribing doctor.
     *
     * @param doctorId The doctor ID.
     */
    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    /**
     * Gets the ID of the patient.
     *
     * @return The patient ID.
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * Sets the ID of the patient.
     *
     * @param patientId The patient ID.
     */
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }


    /**
     * Gets the name of the prescribed medication.
     *
     * @return The medication name.
     */

    public String getMedicationName() {
        return medicationName;
    }


    /**
     * Sets the name of the prescribed medication.
     *
     * @param medicationName The medication name.
     */
    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }


    /**
     * Gets the dosage of the medication.
     *
     * @return The dosage.
     */
    public String getDosage() {
        return dosage;
    }


    /**
     * Sets the dosage of the medication.
     *
     * @param dosage The dosage.
     */
    public void setDosage(String dosage) {
        this.dosage = dosage;
    }


    /**
     * Gets the status of the prescription.
     *
     * @return The status.
     */
    public String getStatus() {
        return status;
    }


    /**
     * Sets the status of the prescription.
     *
     * @param status The status.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the ID of the associated appointment.
     *
     * @return The appointment ID.
     */
    public String getAppointmentId() {
        return appointmentId;
    }


    /**
     * Provides a string representation of the Prescription object.
     *
     * @return A string representation of the Prescription object.
     */
    @Override
    public String toString() {
        return "Prescription{prescriptionId='" + id + "', doctorId='" + doctorId + "', patientId='" + patientId +
                "', medicationName='" + medicationName + "', dosage='" + dosage + "', status='" + status + "', appointmentId='" + appointmentId + "'}";
    }


    /**
     * Converts the Prescription object to a CSV string.
     *
     * @return A CSV representation of the Prescription object.
     */
    @Override
    public String toCSV() {
        return String.join(",", id, doctorId, patientId, medicationName, dosage, status, appointmentId);
    }



    /**
     * Prints the prescription details in a readable format.
     */
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
