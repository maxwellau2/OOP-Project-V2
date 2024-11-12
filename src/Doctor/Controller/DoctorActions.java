package Doctor.Controller;

import Doctor.Model.Doctor;
import Doctor.Repository.DoctorRepository;
import MedicalRecord.Model.MedicalRecord;
import MedicalRecord.Repository.MedicalRecordRepository;
import Prescription.Model.Prescription;
import Prescription.Repository.PrescriptionRepository;
import User.Model.User;
import java.util.List;

import static Util.RepositoryGetter.*;

public class DoctorActions {

    public static List<Doctor> getAllDoctors(){
        return getDoctorRepository().getAll();
    }

    public static Doctor createDoctorFromUser(User user){
        Doctor doctor = getDoctorRepository().read(user.getId());
        if (doctor == null) {
            return null;
        }
        doctor.setUser(user);
        return doctor;
    }


    public static List<MedicalRecord> viewPatientRecord(Doctor doctor){
        if (doctor == null) {
            System.out.println("Doctor cannot be null.");
            return null;
        }
        return getMedicalRecordRepository().getByFilter((MedicalRecord record) -> record.getDoctorId().equals(doctor.getId()));
    }
    public static MedicalRecord viewSpecificPatientRecord(Doctor doctor, String patientId) {
        if (doctor == null || patientId == null) {
            System.out.println("Doctor or Patient ID cannot be null.");
            return null;
        }

        List<MedicalRecord> records = getMedicalRecordRepository().getByFilter((MedicalRecord record) -> record.getPatientId().equals(patientId) && record.getDoctorId().equals(doctor.getId()));

        if (records.isEmpty()) {
            System.out.println("No records found for patient with ID: " + patientId + " assigned to doctor with ID: " + doctor.getId());
            return null;
        } else {
            System.out.println("Found record for patient with ID: " + patientId);
            return records.getFirst(); // Assuming each patient has one record per doctor
        }
    }   
    public static void addPatientRecord(MedicalRecord record) {
        if (record == null) {
            System.out.println("Medical record cannot be null.");
            return;
        }

        MedicalRecord added = getMedicalRecordRepository().create(record);

        if (added != null) {
            System.out.println("New medical record added successfully for patient with ID: " + record.getPatientId());
        } else {
            System.out.println("Failed to add new medical record for patient with ID: " + record.getPatientId());
        }
    }

    public static void updatePatientRecord(MedicalRecord entity){
        if (entity == null) {
            System.out.println("Medical record cannot be null.");
            return;
        }
        getMedicalRecordRepository().update(entity);
    }
    public static void generatePrescription(Prescription prescription) {
        if (prescription == null) {
            System.out.println("Prescription cannot be null.");
            return;
        }
        PrescriptionRepository prescriptionRepo = getPrescriptionRepository();
        prescriptionRepo.update(prescription); //insert id of the prescription
    }

    public static List<Prescription> viewPrescriptionsByDoctor(Doctor doctor) {
        if (doctor == null) {
            System.out.println("Doctor cannot be null.");
            return null;
        }

        List<Prescription> prescriptions = getPrescriptionRepository().getByFilter(prescription -> prescription.getDoctorId().equals(doctor.getId()));

        if (prescriptions.isEmpty()) {
            System.out.println("No prescriptions found for doctor with ID: " + doctor.getId());
        } else {
            System.out.println("Found " + prescriptions.size() + " prescription(s) for doctor with ID: " + doctor.getId());
        }

        return prescriptions;
    }
    public static void updatePrescriptionStatus(String prescriptionId, String newStatus) {
        if (prescriptionId == null || newStatus == null) {
            System.out.println("Prescription ID and status cannot be null.");
            return;
        }

        Prescription prescription = getPrescriptionRepository().read(prescriptionId);

        if (prescription != null) {
            prescription.setStatus(newStatus);
            getPrescriptionRepository().update(prescription);
            System.out.println("Prescription status updated to: " + newStatus);
        } else {
            System.out.println("Prescription with ID " + prescriptionId + " not found.");
        }
    }

    public static Doctor getDoctorById(String id){
        return getDoctorRepository().read(id);
    }

}
