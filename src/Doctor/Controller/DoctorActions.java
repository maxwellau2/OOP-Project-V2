package Doctor.Controller;

import Doctor.Model.Doctor;
import Doctor.Repository.DoctorRepository;
import MedicalRecord.Model.MedicalRecord;
import MedicalRecord.Repository.MedicalRecordRepository;
import Prescription.Model.Prescription;
import Prescription.Repository.PrescriptionRepository;
import java.util.List;

public class DoctorActions {


    public static DoctorRepository getDoctorRepository() {
        return DoctorRepository.getInstance("src/Data/Doctor_List.csv");
    }

    public static List<Doctor> getAllDoctors(){
        return getDoctorRepository().getAll();
    }

    public static List<MedicalRecord> viewPatientRecord(Doctor doctor){
        if (doctor == null) {
            System.out.println("Doctor cannot be null.");
            return null;
        }
        MedicalRecordRepository medRepo = MedicalRecordRepository.getInstance("src/Data/MedicalRecord_List.csv");
        return medRepo.getByFilter((MedicalRecord record) -> record.getDoctorId().equals(doctor.getId()));
    }
    public static MedicalRecord viewSpecificPatientRecord(Doctor doctor, String patientId) {
        if (doctor == null || patientId == null) {
            System.out.println("Doctor or Patient ID cannot be null.");
            return null;
        }

        MedicalRecordRepository medRepo = MedicalRecordRepository.getInstance("src/Data/MedicalRecord_List.csv");
        List<MedicalRecord> records = medRepo.getByFilter((MedicalRecord record) -> record.getPatientId().equals(patientId) && record.getDoctorId().equals(doctor.getId()));

        if (records.isEmpty()) {
            System.out.println("No records found for patient with ID: " + patientId + " assigned to doctor with ID: " + doctor.getId());
            return null;
        } else {
            System.out.println("Found record for patient with ID: " + patientId);
            return records.get(0); // Assuming each patient has one record per doctor
        }
    }   
    public static void addPatientRecord(MedicalRecord record) {
        if (record == null) {
            System.out.println("Medical record cannot be null.");
            return;
        }

        MedicalRecordRepository repo = MedicalRecordRepository.getInstance("src/Data/MedicalRecord_List.csv");
        MedicalRecord added = repo.create(record);

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
        MedicalRecordRepository repo = MedicalRecordRepository.getInstance("src/Data/MedicalRecord_List.csv");
        repo.update(entity);
    }
    public static void generatePrescription(Prescription prescription) {
        if (prescription == null) {
            System.out.println("Prescription cannot be null.");
            return;
        }
        PrescriptionRepository prescriptionRepo = PrescriptionRepository.getInstance("src/Data/Prescription_List.csv");
        prescriptionRepo.update(prescription); //insert id of the prescription
    }

    public static List<Prescription> viewPrescriptionsByDoctor(Doctor doctor) {
        if (doctor == null) {
            System.out.println("Doctor cannot be null.");
            return null;
        }

        PrescriptionRepository prescriptionRepo = PrescriptionRepository.getInstance("src/Data/Prescription_List.csv");
        List<Prescription> prescriptions = prescriptionRepo.getByFilter(prescription -> prescription.getDoctorId().equals(doctor.getId()));

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

        PrescriptionRepository prescriptionRepo = PrescriptionRepository.getInstance("src/Data/Prescription_List.csv");
        Prescription prescription = prescriptionRepo.read(prescriptionId);

        if (prescription != null) {
            prescription.setStatus(newStatus);
            prescriptionRepo.update(prescription);
            System.out.println("Prescription status updated to: " + newStatus);
        } else {
            System.out.println("Prescription with ID " + prescriptionId + " not found.");
        }
    }

    public static Doctor getDoctorById(String id){
        return getDoctorRepository().read(id);
    }

}
