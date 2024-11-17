package Administrator.Controller;

import Administrator.Model.Admin;
import Appointment.Model.Appointment;
import Doctor.Model.Doctor;
import Inventory.Model.Inventory;
import Inventory.Repository.InventoryRepository;
import Pharmacist.Model.Pharmacist;
import Staff.Model.Staff;
import Staff.Repository.StaffRepository;
import User.Model.User;

import java.util.List;

import static Util.RepositoryGetter.*;
/**
 * A controller class that provides various administrative actions for managing staff, appointments, inventory, and admin-related operations.
 */
public class AdminController {

    /**
     * Retrieves a list of all staff members.
     *
     * @return A list of all {@link Staff} entities.
     */
    public static List<Staff> getAllStaff(){
        StaffRepository repo = getStaffRepoInstance();
        return repo.getAll();
    }
    /**
     * Adds a new staff member to the repository.
     *
     * @param staff The {@link Staff} entity to be added.
     * @return The created {@link Staff} entity.
     */
    public static Staff addStaff(Staff staff){
        StaffRepository repo = getStaffRepoInstance();
        return repo.create(staff);
    }

    /**
     * Updates the information of an existing staff member.
     *
     * @param staff The {@link Staff} entity to be updated.
     * @return The updated {@link Staff} entity.
     */
    public static Staff updateStaff(Staff staff){
        StaffRepository repo = getStaffRepoInstance();
        staff = repo.update(staff);
        // update according to the role
        switch(staff.getRole().toLowerCase()){
            case "admin":
                Admin a = getAdminRepository().read(staff.getId());
                a.setAge(staff.getAge());
                a.setName(staff.getName());
                a.setGender(staff.getGender());
                getAdminRepository().update(a);
                break;
            case "doctor":
                Doctor d = getDoctorRepository().read(staff.getId());
                d.setAge(staff.getAge());
                d.setName(staff.getName());
                d.setGender(staff.getGender());
                getDoctorRepository().update(d);
                break;
            case "pharmacist":
                Pharmacist p = getPharmacistRepository().read(staff.getId());
                p.setAge(staff.getAge());
                p.setName(staff.getName());
                p.setGender(staff.getGender());
                getPharmacistRepository().update(p);
                break;
            default:
                break;
        }
        return staff;
    }

    /**
     * Deletes a staff member from the repository.
     *
     * @param staff The {@link Staff} entity to be deleted.
     * @return {@code true} if the staff member was successfully deleted; otherwise {@code false}.
     */
    public static boolean deleteStaff(Staff staff) {
        StaffRepository repo = getStaffRepoInstance();
        if (repo.delete(staff) == null){
            return false;
        }
        switch(staff.getRole().toLowerCase()){
            case "admin":
                Admin a = getAdminRepository().read(staff.getId());
                if (a == null){
                    return true;
                }
                return getAdminRepository().delete(a) != null;
            case "doctor":
                Doctor d = getDoctorRepository().read(staff.getId());
                if (d == null){
                    return true;
                }
                return getDoctorRepository().delete(d) != null;
            case "pharmacist":
                Pharmacist p = getPharmacistRepository().read(staff.getId());
                if (p == null){
                    return true;
                }
                return getPharmacistRepository().delete(p) != null;
        }
        return true;
    }

    /**
     * Retrieves a list of all appointments.
     *
     * @return A list of all {@link Appointment} entities.
     */
    public static List<Appointment> getAllAppointments() {
        return getAppointmentRepository().getAll();
    }

    /**
     * Updates the stock information for a specific inventory item.
     *
     * @param item The {@link Inventory} item to be updated.
     * @return {@code true} if the stock was successfully updated; otherwise {@code false}.
     */
    public static boolean updateStock(Inventory item){
        InventoryRepository repo = getInventoryRepoInstance();
        if (repo.update(item) == null){
            return false;
        }
        return true;
    }

    /**
     * Creates an {@link Admin} entity from a {@link User}.
     *
     * @param user The {@link User} entity to create the admin from.
     * @return The created {@link Admin} entity, or {@code null} if no admin is found.
     */
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

    /**
     * Displays a list of all appointments.
     */
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

    /**
     * Deletes an admin by their unique ID.
     *
     * @param id The unique ID of the admin to delete.
     * @return {@code true} if the admin was successfully deleted; otherwise {@code false}.
     */
    public static boolean deleteAdminById(String id) {
        return getAdminRepository().deleteById(id);
    }

}