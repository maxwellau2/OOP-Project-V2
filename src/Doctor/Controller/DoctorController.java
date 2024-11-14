package Doctor.Controller;

import Appointment.Model.Appointment;
import Doctor.Model.Doctor;
import MedicalRecord.Model.MedicalRecord;
import Prescription.Model.Prescription;
import Prescription.Repository.PrescriptionRepository;
import User.Model.User;
import static Util.RepositoryGetter.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DoctorController {

    public static List<Doctor> getAllDoctors(){
        return getDoctorRepository().getAll();
    }

    public static Doctor createDoctorFromUser(User user){
        Doctor doctor = getDoctorRepository().read(user.getId());
        if (doctor == null) {
            System.out.println("Doctor not found");
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
    public static List<MedicalRecord> viewSpecificPatientRecord(Doctor doctor, String patientId) {
        if (doctor == null || patientId == null) {
            System.out.println("Doctor or Patient ID cannot be null.");
            return null;
        }
    
        List<MedicalRecord> records = getMedicalRecordRepository().getByFilter(
            record -> record.getPatientId().equals(patientId) && record.getDoctorId().equals(doctor.getId())
        );
    
        if (records.isEmpty()) {
            System.out.println("No records found for patient with ID: " + patientId + " assigned to doctor with ID: " + doctor.getId());
            return null;
        } else {
            System.out.println("Found " + records.size() + " record(s) for patient with ID: " + patientId);
            return records; 
        }
    }   
    public static MedicalRecord addPatientRecord(MedicalRecord record) {
        return getMedicalRecordRepository().create(record);
    }

    public static void updatePatientRecord(MedicalRecord entity){
        if (entity == null) {
            System.out.println("Medical record cannot be null.");
            return;
        }
        getMedicalRecordRepository().update(entity);
    }
    public static void generatePrescription(Prescription prescription, Appointment appointment) {
        if (prescription == null || appointment == null) {
            System.out.println("Prescription or Appointment cannot be null.");
            return;
        }

        // Generate Prescription ID based on the appointment time
        String prescriptionId = generatePrescriptionIdFromAppointment(appointment);
        prescription.setId(prescriptionId);

        PrescriptionRepository prescriptionRepo = getPrescriptionRepository();
        Prescription addedPrescription = prescriptionRepo.create(prescription);  

        if (addedPrescription != null) {
            System.out.println("Prescription created successfully with ID: " + addedPrescription.getId());
        } else {
            System.out.println("Failed to create prescription.");
        }
    }

    private static String generatePrescriptionIdFromAppointment(Appointment appointment) {
        // Use the appointment's date to generate a unique prescription ID 
        LocalDateTime appointmentDate = appointment.getDate();
        if (appointmentDate == null) {
            System.out.println("Appointment date is null.");
            return null;
        }

        // Format the appointment time as a string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        return appointmentDate.format(formatter);  // Format to "YYYYMMDD-HHMMSS"
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
