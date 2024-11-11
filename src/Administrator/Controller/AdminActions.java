package Administrator.Controller;

import Appointment.Model.Appointment;
import Appointment.Repository.AppointmentRepository;
import Inventory.Model.Inventory;
import Inventory.Repository.InventoryRepository;
import Staff.Repository.StaffRepository;

import java.util.List;

import Staff.Model.Staff;

public class AdminActions{
    public static void main (String[] args){
//       System.out.println(AdminActions.getAllStaff());
////       Staff s = new Staff("A001","Sarah Lee","Administrator","Female",41);
//       Staff s = new Staff(getStaffRepoInstance().generateId(),"Carnegie","Administrator","Make",22);
////       updateStaff(s);
//        //addStaff(s);
//        getStaffRepoInstance().delete("A002");
//        getStaffRepoInstance().display();
//        System.out.println(getAllAppointments());
        Inventory Inv = new Inventory("1", "New Medication", 5, 10, false);
        updateStock(Inv);
    }

    private static StaffRepository getStaffRepoInstance(){
        StaffRepository repo = StaffRepository.getInstance("src/Data/Staff_List.csv");
        return repo;
    }

    private static AppointmentRepository getAppointmentRepoInstance(){
        return AppointmentRepository.getInstance("src/Data/Appointment_List.csv");
    }

    private static InventoryRepository getInventoryRepoInstance(){
        return InventoryRepository.getInstance("src/Data/Medicine_List.csv");
    }

    public static List<Staff> getAllStaff(){
        StaffRepository repo = getStaffRepoInstance();
        return repo.getAll();
    }
    public static boolean addStaff(Staff staff){
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
        repo.delete(staff.getId());
        return true;
    }
    public static List<Appointment> getAllAppointments() {
        return getAppointmentRepoInstance().getAll();
    }
    public static boolean updateStock(Inventory item){
        InventoryRepository repo = getInventoryRepoInstance();
        repo.update(item);
        return true;
    }
}