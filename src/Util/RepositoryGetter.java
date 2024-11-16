package Util;

import Administrator.Repository.AdminRepository;
import Appointment.Repository.AppointmentRepository;
import AppointmentOutcome.Repository.AppointmentOutcomeRepository;
import Doctor.Repository.DoctorRepository;
import Inventory.Repository.InventoryRepository;
import Leaves.Repository.LeavesRepository;
import MedicalRecord.Repository.MedicalRecordRepository;
import Patient.Repository.PatientRepository;
import Pharmacist.Repository.PharmacistRepository;
import Prescription.Repository.PrescriptionRepository;
import Staff.Repository.StaffRepository;
import User.Repository.UserRepository;


/**
 * Utility class for retrieving singleton instances of various repositories.
 * Each method provides a way to access the singleton instance of a repository,
 * ensuring centralized and consistent access to data storage.
 */
public class RepositoryGetter {


    /**
     * @return Singleton instance of the UserRepository.
     */
    public static UserRepository getUserRepository() {
        return UserRepository.getInstance("src/Data/User_List.csv");
    }


    /**
     * @return Singleton instance of the PatientRepository.
     */
    public static PatientRepository getPatientRepository() {
        return PatientRepository.getInstance("src/Data/Patient_List.csv");
    }

    /**
     * @return Singleton instance of the DoctorRepository.
     */
    public static DoctorRepository getDoctorRepository() {
        return DoctorRepository.getInstance("src/Data/Doctor_List.csv");
    }


    /**
     * Initializes and retrieves the singleton instance of the PrescriptionRepository.
     *
     * @return Singleton instance of the PrescriptionRepository.
     */
    public static PrescriptionRepository initPrescriptionRepository() {
        return PrescriptionRepository.getInstance("src/Data/Prescription_List.csv");
    }

    /**
     * @return Singleton instance of the AppointmentRepository.
     */
    public static AppointmentRepository getAppointmentRepository() {
        return AppointmentRepository.getInstance("src/Data/Appointment_List.csv");
    }

    /**
     * @return Singleton instance of the AppointmentOutcomeRepository.
     */
    public static AppointmentOutcomeRepository getAppointmentOutcomeRepository() {
        return AppointmentOutcomeRepository.getInstance("src/Data/AppointmentOutcome_List.csv");
    }

    /**
     * @return Singleton instance of the MedicalRecordRepository.
     */
    public static MedicalRecordRepository getMedicalRecordRepository(){
        return MedicalRecordRepository.getInstance("src/Data/MedicalRecords_List.csv");
    }

    /**
     * @return Singleton instance of the PrescriptionRepository.
     */
    public static PrescriptionRepository getPrescriptionRepository(){
        return PrescriptionRepository.getInstance("src/Data/Prescription_List.csv");
    }


    /**
     * @return Singleton instance of the LeavesRepository.
     */
    public static LeavesRepository getLeavesRepository() {
        return LeavesRepository.getInstance("src/Data/Leaves_List.csv");
    }

    /**
     * @return Singleton instance of the PharmacistRepository.
     */
    public static PharmacistRepository getPharmacistRepository() {
        return PharmacistRepository.getInstance("src/Data/Pharmacist_List.csv");
    }

    /**
     * @return Singleton instance of the AdminRepository.
     */
    public static AdminRepository getAdminRepository() {
        return AdminRepository.getInstance("src/Data/Administrator_List.csv");
    }

    /**
     * @return Singleton instance of the StaffRepository.
     */
    public static StaffRepository getStaffRepoInstance() {
        return StaffRepository.getInstance("src/Data/Staff_List.csv");
    }

    /**
     * @return Singleton instance of the InventoryRepository.
     */
    public static InventoryRepository getInventoryRepoInstance(){
        return InventoryRepository.getInstance("src/Data/Medicine_List.csv");
    }



}

