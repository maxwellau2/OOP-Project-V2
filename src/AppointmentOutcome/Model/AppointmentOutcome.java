package AppointmentOutcome.Model;

import Interfaces.IEntity;

public class AppointmentOutcome implements IEntity {
    private String appointmentId;
    private String patientId;
    private String doctorId;
    private String services;
    private String medication;
    private String consultationNotes;
    public AppointmentOutcome(String appointmentId, String patientId, String doctorId, String services, String medication, String consultationNotes) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.services = services;
        this.medication = medication;
        this.consultationNotes = consultationNotes;
    }

    @Override
    public String getId() {
        return this.appointmentId;
    }

    @Override
    public String toCSV() {
        return appointmentId + "," + patientId + "," + doctorId + "," + services + "," + medication + "," + consultationNotes;
    }

    public String getAppointmentId() {
        return appointmentId;
    }
    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }
    public String getPatientId() {
        return patientId;
    }
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getConsultationNotes() {
        return consultationNotes;
    }

    public void setConsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

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
