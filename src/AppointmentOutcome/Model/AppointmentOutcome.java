package AppointmentOutcome.Model;

import Interfaces.IEntity;


/**
 * Represents the outcome of an appointment, including details such as services provided,
 * medication prescribed, and consultation notes.
 */
public class AppointmentOutcome implements IEntity {
    private String appointmentId;
    private String patientId;
    private String doctorId;
    private String services;
    private String medication;
    private String consultationNotes;

    /**
     * Constructs an AppointmentOutcome with the specified details.
     *
     * @param appointmentId       Unique ID of the appointment.
     * @param patientId           ID of the patient.
     * @param doctorId            ID of the doctor.
     * @param services            Services provided during the appointment.
     * @param medication          Medication prescribed.
     * @param consultationNotes   Notes from the consultation.
     */

    public AppointmentOutcome(String appointmentId, String patientId, String doctorId, String services, String medication, String consultationNotes) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.services = services;
        this.medication = medication;
        this.consultationNotes = consultationNotes;
    }

    /**
     * Gets the ID of the appointment.
     *
     * @return The appointment ID.
     */
    @Override
    public String getId() {
        return this.appointmentId;
    }


    /**
     * Converts the AppointmentOutcome to a CSV string.
     *
     * @return A CSV representation of the AppointmentOutcome.
     */
    @Override
    public String toCSV() {
        return appointmentId + "," + patientId + "," + doctorId + "," + services + "," + medication + "," + consultationNotes;
    }


    /**
     * Gets the appointment ID.
     *
     * @return The appointment ID.
     */
    public String getAppointmentId() {
        return appointmentId;
    }

    /**
     * Sets the appointment ID.
     *
     * @param appointmentId The new appointment ID.
     */
    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
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
     * Gets the services provided.
     *
     * @return The services provided.
     */
    public String getServices() {
        return services;
    }


    /**
     * Sets the services provided.
     *
     * @param services The new services provided.
     */
    public void setServices(String services) {
        this.services = services;
    }


    /**
     * Gets the consultation notes.
     *
     * @return The consultation notes.
     */
    public String getConsultationNotes() {
        return consultationNotes;
    }


    /**
     * Sets the consultation notes.
     *
     * @param consultationNotes The new consultation notes.
     */
    public void setConsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }


    /**
     * Gets the medication prescribed.
     *
     * @return The medication prescribed.
     */
    public String getMedication() {
        return medication;
    }


    /**
     * Sets the medication prescribed.
     *
     * @param medication The new medication prescribed.
     */
    public void setMedication(String medication) {
        this.medication = medication;
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
     * Displays the details of the AppointmentOutcome in a user-friendly format.
     */
    public void prettyPrint() {
        System.out.println("=== Appointment Outcome ===");
        System.out.println("Appointment ID: " + appointmentId);
        System.out.println("Patient ID    : " + patientId);
        System.out.println("Doctor ID     : " + doctorId);
        System.out.println("Services      : " + services);
        System.out.println("Medication    : " + medication);
        System.out.println("Notes         : " + consultationNotes);
        System.out.println("===========================");
    }

}
