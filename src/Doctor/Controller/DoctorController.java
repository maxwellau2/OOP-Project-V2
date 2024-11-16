package Doctor.Controller;

import Appointment.Model.Appointment;
import Doctor.Model.Doctor;
import MedicalRecord.Model.MedicalRecord;
import Prescription.Model.Prescription;
import Prescription.Repository.PrescriptionRepository;
import User.Model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static Util.RepositoryGetter.*;


/**
 * DoctorController class provides operations for managing doctor-related functionality.
 * Includes methods for managing doctors, accessing medical records, generating prescriptions,
 * and interacting with the repositories.
 */
public class DoctorController {

    /**
     * Retrieves a list of all doctors.
     *
     * @return A list of all Doctor entities.
     */
    public static List<Doctor> getAllDoctors(){
        return getDoctorRepository().getAll();
    }


    /**
     * Creates a Doctor object from the given User object by retrieving the doctor information.
     *
     * @param user The User object representing the doctor.
     * @return The created Doctor object, or null if the doctor is not found.
     */
    public static Doctor createDoctorFromUser(User user){
        Doctor doctor = getDoctorRepository().read(user.getId());
        if (doctor == null) {
            System.out.println("Doctor not found");
            return null;
        }
        doctor.setUser(user);
        return doctor;
    }


    /**
     * Retrieves a list of all medical records associated with a specific doctor.
     *
     * @param doctor The Doctor entity whose records are being retrieved.
     * @return A list of MedicalRecord entities, or null if the doctor is null.
     */
    public static List<MedicalRecord> viewPatientRecord(Doctor doctor){
        if (doctor == null) {
//            System.out.println("Doctor cannot be null.");
            return null;
        }
        return getMedicalRecordRepository().getByFilter((MedicalRecord record) -> record.getDoctorId().equals(doctor.getId()));
    }

    /**
     * Retrieves medical records for a specific patient assigned to a specific doctor.
     *
     * @param doctor    The Doctor entity responsible for the patient.
     * @param patientId The ID of the patient.
     * @return A list of MedicalRecord entities, or null if no records are found.
     */
    public static List<MedicalRecord> viewSpecificPatientRecord(Doctor doctor, String patientId) {
        if (doctor == null || patientId == null) {
//            System.out.println("Doctor or Patient ID cannot be null.");
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

    /**
     * Adds a new medical record to the repository.
     *
     * @param record The MedicalRecord entity to be added.
     * @return The created MedicalRecord entity.
     */
    public static MedicalRecord addPatientRecord(MedicalRecord record) {
        return getMedicalRecordRepository().create(record);
    }

    /**
     * Updates an existing medical record in the repository.
     *
     * @param entity The MedicalRecord entity to update.
     */
    public static void updatePatientRecord(MedicalRecord entity){
        if (entity == null) {
            System.out.println("Medical record cannot be null.");
            return;
        }
        getMedicalRecordRepository().update(entity);
    }

    /**
     * Generates a new prescription for a given appointment and stores it in the repository.
     *
     * @param prescription The Prescription entity to be created.
     * @param appointment  The Appointment entity associated with the prescription.
     */
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


    /**
     * Generates a unique prescription ID based on the date of the associated appointment.
     *
     * @param appointment The Appointment entity used to generate the ID.
     * @return A unique prescription ID string, or null if the appointment date is null.
     */
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


    /**
     * Retrieves all prescriptions associated with a specific doctor.
     *
     * @param doctor The Doctor entity whose prescriptions are being retrieved.
     * @return A list of Prescription entities, or null if the doctor is null.
     */
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

    /**
     * Updates the status of a specific prescription.
     *
     * @param prescriptionId The ID of the prescription to update.
     * @param newStatus      The new status to set.
     */
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

    /**
     * Retrieves a Doctor entity by its unique ID.
     *
     * @param id The unique ID of the doctor.
     * @return The Doctor entity with the specified ID, or null if not found.
     */
    public static Doctor getDoctorById(String id){
        return getDoctorRepository().read(id);
    }

    /**
     * Deletes a doctor by its unique ID.
     *
     * @param id The unique ID of the doctor to delete.
     * @return True if the doctor was successfully deleted, otherwise false.
     */
    public static boolean deleteDoctorById(String id){
        return getDoctorRepository().deleteById(id);
    }

}
