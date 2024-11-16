package Util;

import Administrator.Repository.AdminRepository;
import Appointment.Repository.AppointmentRepository;
import AppointmentOutcome.Repository.AppointmentOutcomeRepository;
import Doctor.Repository.DoctorRepository;
import Inventory.Repository.InventoryRepository;
import Leaves.Repository.LeavesRepository;
import MedicalRecord.Repository.MedicalRecordRepository;
import Pharmacist.Repository.PharmacistRepository;
import Prescription.Repository.PrescriptionRepository;
import Staff.Repository.StaffRepository;
import User.Repository.UserRepository;

public class RepositoryGetter {

    public static UserRepository getUserRepository() {
        return UserRepository.getInstance("src/Data/User_List.csv");
    }

    public static DoctorRepository getDoctorRepository() {
        return DoctorRepository.getInstance("src/Data/Doctor_List.csv");
    }

    public static PrescriptionRepository initPrescriptionRepository() {
        return PrescriptionRepository.getInstance("src/Data/Prescription_List.csv");
    }


    public static AppointmentRepository getAppointmentRepository() {
        return AppointmentRepository.getInstance("src/Data/Appointment_List.csv");
    }


    public static AppointmentOutcomeRepository getAppointmentOutcomeRepository() {
        return AppointmentOutcomeRepository.getInstance("src/Data/AppointmentOutcome_List.csv");
    }


    public static MedicalRecordRepository getMedicalRecordRepository(){
        return MedicalRecordRepository.getInstance("src/Data/MedicalRecords_List.csv");
    }

    public static PrescriptionRepository getPrescriptionRepository(){
        return PrescriptionRepository.getInstance("src/Data/Prescription_List.csv");
    }


    public static LeavesRepository getLeavesRepository() {
        return LeavesRepository.getInstance("src/Data/Leaves_List.csv");
    }

    public static PharmacistRepository getPharmacistRepository() {
        return PharmacistRepository.getInstance("src/Data/Pharmacist_List.csv");
    }
    
    public static AdminRepository getAdminRepository() {
        return AdminRepository.getInstance("src/Data/Administrator_List.csv");
    }

    public static StaffRepository getStaffRepoInstance() {
        return StaffRepository.getInstance("src/Data/Staff_List.csv");
    }

    public static InventoryRepository getInventoryRepoInstance(){
        return InventoryRepository.getInstance("src/Data/Inventory_List.csv");
    }

}

