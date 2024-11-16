package Staff.Repository;

import Abstract.Repository;
import Administrator.Model.Admin;
import Doctor.Model.Doctor;
import Pharmacist.Model.Pharmacist;
import Staff.Model.Staff;

import java.util.ArrayList;
import java.util.List;

import static Util.RepositoryGetter.*;


/**
 * Repository class for managing {@link Staff} entities.
 * Handles CRUD operations and maintains a unified staff list derived from multiple repositories
 * such as Doctor, Pharmacist, and Admin repositories.
 */
public class StaffRepository extends Repository<Staff> {
    private static StaffRepository instance = null;

    /**
     * Private constructor to initialize the repository with a CSV file path.
     *
     * @param csvPath The file path for storing staff data.
     */
    private StaffRepository(String csvPath) {
        super(csvPath);
        this.entities = new ArrayList<Staff>();
        this.load();
    }

    /**
     * Singleton pattern to retrieve the single instance of StaffRepository.
     *
     * @param csvPath The file path for storing staff data.
     * @return The single instance of {@link StaffRepository}.
     */
    public static StaffRepository getInstance(String csvPath){
        if (instance == null){
            instance = new StaffRepository(csvPath);
        }
        return instance;
    }


    /**
     * Deletes a staff member by their ID.
     *
     * @param item The {@link Staff} entity to be deleted.
     * @return The deleted {@link Staff} object if successful, otherwise null.
     */
    @Override
    public Staff delete(Staff item) {
        if (deleteStaffById(item.getId()))
            return item;
        return null;
    }

    /**
     * Parses a CSV line to create a {@link Staff} object.
     *
     * @param csv The CSV-formatted string representing a staff member.
     * @return A {@link Staff} object populated with the parsed data.
     */
    @Override
    protected Staff fromCSV(String csv) {
        String[] values = csv.split(",");
        int age = Integer.parseInt(values[4]);
        return new Staff(values[0], values[1], values[2], values[3], age);
    }


    /**
     * Provides the header for the CSV file storing staff data.
     *
     * @return The CSV header string.
     */
    @Override
    protected String getHeader() {
        return "id,name,role,gender,age";
    }


    /**
     * Creates a {@link Staff} object from a {@link Doctor} object.
     *
     * @param doctor The {@link Doctor} object.
     * @return A {@link Staff} object representing the doctor.
     */
    public Staff createStaffFromDoctor(Doctor doctor) {
        return new Staff(doctor.getId(), doctor.getName(), "doctor", doctor.getGender(), doctor.getAge());
    }

    /**
     * Creates a {@link Staff} object from a {@link Pharmacist} object.
     *
     * @param pharmacist The {@link Pharmacist} object.
     * @return A {@link Staff} object representing the pharmacist.
     */
    public Staff createStaffFromPharmacist(Pharmacist pharmacist) {
        return new Staff(pharmacist.getId(),pharmacist.getName(),"pharmacist", pharmacist.getGender(), pharmacist.getAge());
    }

    /**
     * Creates a {@link Staff} object from an {@link Admin} object.
     *
     * @param admin The {@link Admin} object.
     * @return A {@link Staff} object representing the admin.
     */
    public Staff createStaffFromAdmin(Admin admin) {
        return new Staff(admin.getId(),admin.getName(),"admin", admin.getGender(), admin.getAge());
    }


    /**
     * Loads the staff list by combining data from Doctor, Pharmacist, and Admin repositories.
     *
     * @return True if the load is successful.
     */
    @Override
    public boolean load(){
        entities.clear();
        List<Doctor> doctors = getDoctorRepository().getAll();
        List<Pharmacist> pharmacists = getPharmacistRepository().getAll();
        List<Admin> admins = getAdminRepository().getAll();
        for (Doctor doctor : doctors) {
            entities.add(createStaffFromDoctor(doctor));
        }
        for (Pharmacist pharmacist : pharmacists) {
            entities.add(createStaffFromPharmacist(pharmacist));
        }
        for (Admin admin : admins) {
            entities.add(createStaffFromAdmin(admin));
        }
        return true;
    }


    /**
     * Deletes a staff member by their ID.
     *
     * @param id The ID of the staff member to delete.
     * @return True if deletion is successful, false otherwise.
     */
    public boolean deleteStaffById(String id) {
        // Find the staff by ID
        Staff staffToDelete = entities.stream()
                .filter(staff -> staff.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (staffToDelete == null) {
            System.out.println("No staff found with ID: " + id);
            return false;
        }

        // Determine the role and delete from the appropriate repository
        switch (staffToDelete.getRole().toLowerCase()) {
            case "doctor":
                boolean doctorDeleted = getDoctorRepository().deleteById(id);
                if (!doctorDeleted) {
                    System.out.println("Failed to delete doctor with ID: " + id);
                    return false;
                }
                break;

            case "pharmacist":
                boolean pharmacistDeleted = getPharmacistRepository().deleteById(id);
                if (!pharmacistDeleted) {
                    System.out.println("Failed to delete pharmacist with ID: " + id);
                    return false;
                }
                break;

            case "admin":
                boolean adminDeleted = getAdminRepository().deleteById(id);
                if (!adminDeleted) {
                    System.out.println("Failed to delete admin with ID: " + id);
                    return false;
                }
                break;

            default:
                System.out.println("Invalid staff role: " + staffToDelete.getRole());
                return false;
        }

        // Reload the staff table
        this.store();
        // delete user by ID oso
        getUserRepository().deleteById(staffToDelete.getId());
        return true;
    }

}
