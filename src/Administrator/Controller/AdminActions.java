package Administrator.Controller;

import Administrator.Model.Admin;
import Appointment.Model.Appointment;
import Appointment.Repository.AppointmentRepository;
import Inventory.Model.Inventory;
import Inventory.Repository.InventoryRepository;
import Staff.Model.Staff;
import Staff.Repository.StaffRepository;
import User.Model.User;
import static Util.RepositoryGetter.getAdminRepository;
import java.util.List;

public class AdminActions{
//    public static void main (String[] args){
//       System.out.println(AdminActions.getAllStaff());
////       Staff s = new Staff("A001","Sarah Lee","Administrator","Female",41);
//       Staff s = new Staff(getStaffRepoInstance().generateId(),"Carnegie","Administrator","Make",22);
////       updateStaff(s);
//        //addStaff(s);
//        getStaffRepoInstance().delete("A002");
//        getStaffRepoInstance().display();
//        System.out.println(getAllAppointments());
//        Inventory Inv = new Inventory("22222", "viagra", 5, 10, false);
//        boolean res = updateStock(Inv);
//        System.out.println(res);
//    }

    private static StaffRepository getStaffRepoInstance(){
        StaffRepository repo = StaffRepository.getInstance("src/Data/Staff_List.csv");
        return repo;
    }

    private static AppointmentRepository getAppointmentRepoInstance(){
        return AppointmentRepository.getInstance("src/Data/Appointment_List.csv");
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
    public static Staff updateStaff(Staff staff){
        StaffRepository repo = getStaffRepoInstance();
        repo.update(staff);
        return staff;
    }
    public static boolean deleteStaff(Staff staff) {
        StaffRepository repo = getStaffRepoInstance();
        return repo.delete(staff) != null;
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