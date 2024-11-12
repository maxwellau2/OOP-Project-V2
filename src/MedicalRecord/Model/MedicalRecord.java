package MedicalRecord.Model;

import Interfaces.IEntity;
import MedicalRecord.Repository.MedicalRecordRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static Util.RepositoryGetter.getMedicalRecordRepository;

public class MedicalRecord implements IEntity {
    private String recordId;
    private String patientId;
    private String doctorId;
    private String diagnosis;
    private String treatment;
    private LocalDateTime date;

    public MedicalRecord(String recordId, String patientId, String doctorId, String diagnosis, String treatment, LocalDateTime date) {
        this.recordId = recordId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.date = date;
    }

    public static MedicalRecord createNewMedicalRecordToday(String patientId, String doctorId, String diagnosis, String treatment) {
        return new MedicalRecord(getMedicalRecordRepository().generateId(), patientId, doctorId, diagnosis, treatment, LocalDateTime.now());
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
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

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void prettyPrintMedicalRecord(){
        System.out.println("Record ID: " + recordId);
        System.out.println("\t\tPatient ID: " + patientId);
        System.out.println("\t\tDoctor ID: " + doctorId);
        System.out.println("\t\tDiagnosis: " + diagnosis);
        System.out.println("\t\tTreatment: " + treatment);
        System.out.println("\t\tDate: " + date);
    }

    @Override
    public String getId() {
        return recordId;
    }

    public String toCSV() {
        String dateString = (date != null) ? date.format(DateTimeFormatter.ISO_DATE_TIME) : "";
        return recordId + "," + patientId + "," + doctorId + "," + diagnosis + "," + treatment + "," + dateString;
    }

    @Override
    public String toString() {
        return "MedicalRecord {recordId=" + recordId + ", patientId=" + patientId + ", doctorId=" + doctorId
                + ", diagnosis=" + diagnosis + ", treatment=" + treatment + ", date=" + date + "}";
    }
}
