package Administrator.Controller;

import Administrator.Model.Admin;
import Appointment.Model.Appointment;
import Inventory.Model.Inventory;
import Inventory.Repository.InventoryRepository;
import Staff.Model.Staff;
import Staff.Repository.StaffRepository;
import User.Model.User;

import java.util.List;

import static Util.RepositoryGetter.*;

public class AdminActions{

    public static List<Staff> getAllStaff(){
        StaffRepository repo = getStaffRepoInstance();
        return repo.getAll();
    }
    public static Staff addStaff(Staff staff){
        StaffRepository repo = getStaffRepoInstance();
        return repo.create(staff);
    }
    public static Staff updateStaff(Staff staff){
        StaffRepository repo = getStaffRepoInstance();
        repo.update(staff);
        return staff;
    }
    public static boolean deleteStaff(Staff staff) {
        StaffRepository repo = getStaffRepoInstance();
        if (repo.delete(staff) == null){
            return false;
        }
        return true;
    }
    public static List<Appointment> getAllAppointments() {
        return getAppointmentRepository().getAll();
    }
    public static boolean updateStock(Inventory item){
        InventoryRepository repo = getInventoryRepoInstance();
        if (repo.update(item) == null){
            return false;
        }
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

    public static boolean deleteAdminById(String id) {
        return getAdminRepository().deleteById(id);
    }
}