package Administrator.Controller;

import Administrator.Model.Admin;
import Administrator.Repository.AdminRepository;
import Appointment.Model.Appointment;
import Appointment.Repository.AppointmentRepository;
import Doctor.Model.Doctor;
import Doctor.Repository.DoctorRepository;
import Inventory.Model.Inventory;
import Inventory.Repository.InventoryRepository;
import Pharmacist.Model.Pharmacist;
import Pharmacist.Repository.PharmacistRepository;
import Staff.Model.Staff;
import Staff.Repository.StaffRepository;
import User.Model.User;
import static Util.RepositoryGetter.getAdminRepository;
import java.util.List;

public class AdminActions{

    private static StaffRepository getStaffRepoInstance(){
        StaffRepository repo = StaffRepository.getInstance("src/Data/Staff_List.csv");
        return repo;
    }

    private static AppointmentRepository getAppointmentRepoInstance(){
        return AppointmentRepository.getInstance("src/Data/Appointment_List.csv");
    }

    private static DoctorRepository getDoctorRepoInstance(){
        return DoctorRepository.getInstance("src/Data/Doctor_List.csv");
    }

    private static PharmacistRepository getPharmacistRepoInstance(){
        return PharmacistRepository.getInstance("src/Data/Pharmacist_List.csv");
    }

    private static AdminRepository getAdminRepoInstance(){
        return AdminRepository.getInstance("src/Data/Administrator_List.csv");
    }

    public static InventoryRepository getInventoryRepoInstance(){
        return InventoryRepository.getInstance("src/Data/Medicine_List.csv");
    }

    public static List<Staff> getAllStaff(){
        StaffRepository repo = getStaffRepoInstance();
        return repo.getAll();
    }

    public static Staff addStaff(Staff staff){
        StaffRepository repo = getStaffRepoInstance();
        return repo.create(staff);
    }

    public static Doctor addDoctor(Doctor doctor){
        DoctorRepository repo = getDoctorRepoInstance();
        return repo.create(doctor);
    }

    public static Pharmacist addPharmacist(Pharmacist pharmacist){
        PharmacistRepository repo = getPharmacistRepoInstance();
        return repo.create(pharmacist);
    }

    public static Admin addAdmin(Admin admin){
        AdminRepository repo = getAdminRepoInstance();
        return repo.create(admin);
    }

    public static Staff updateStaff(Staff staff){
        StaffRepository repo = getStaffRepoInstance();
        repo.update(staff);
        return staff;
    }

    public static boolean deleteStaff(Staff staff) {
        StaffRepository repo = getStaffRepoInstance();
        return repo.delete(staff) != null;
    }

    public static boolean deleteDoctor(String staffId) {
        Doctor doctorToDelete = getDoctorRepoInstance().read(staffId);
        if (doctorToDelete != null) {
            Doctor deletedDoctor = getDoctorRepoInstance().delete(doctorToDelete);  
            return deletedDoctor != null;
        }
        return false;
    }

    public static boolean deletePharmacist(String staffId) {
        Pharmacist pharmacistToDelete = getPharmacistRepoInstance().read(staffId);
        if (pharmacistToDelete != null) {
            Pharmacist deletedPharmacist = getPharmacistRepoInstance().delete(pharmacistToDelete);  
            return deletedPharmacist != null;
        }
        return false;
    }

    public static boolean deleteAdmin(String staffId) {
        Admin adminToDelete = getAdminRepoInstance().read(staffId);
        if (adminToDelete != null) {
            Admin deletedAdmin = getAdminRepoInstance().delete(adminToDelete);  
            return deletedAdmin != null;
        }
        return false;
    }

    public static List<Appointment> getAllAppointments() {
        return getAppointmentRepoInstance().getAll();
    }

    public static boolean updateStock(String medicationId, int newQuantity) {
        InventoryRepository repo = getInventoryRepoInstance();

        Inventory existingItem = repo.getById(medicationId);

        if (existingItem != null) {
            existingItem.setQuantity(newQuantity);
            repo.update(existingItem);
            return true;
        } else {
            System.out.println("No inventory item found with ID: " + medicationId);
            return false;
        }
    }

    public static boolean addNewInventoryItem(Inventory newItem) {
        InventoryRepository repo = getInventoryRepoInstance();
        if (repo.getById(newItem.getId()) != null) {
            System.out.println("An item with this ID already exists. Cannot add a duplicate.");
            return false;
        }
        repo.create(newItem);
        return true;
    }
    
    public static Admin createAdminFromUser(User user) {
        // Create an Admin from the User
        Admin admin = getAdminRepository().read(user.getId());
        if (admin == null) {
            System.out.println("Admin not found.");
            return null;
        }
        admin.setUser(user);
        return admin;
    }
    
    public static void viewAppointments() {
        List<Appointment> appointments = getAllAppointments();
        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");
        } else {
            for (Appointment appointment : appointments) {
                System.out.println(appointment);
            }
        }
    }
    
    public static void manageInventory(String medicationName) {
        InventoryRepository inventoryRepo = getInventoryRepoInstance();
        Inventory item = inventoryRepo.getItemByName(medicationName);
        if (item != null) {
            System.out.println("Inventory for " + medicationName + ": " + item);
        } else {
            System.out.println("Medication not found.");
        }
    }
}