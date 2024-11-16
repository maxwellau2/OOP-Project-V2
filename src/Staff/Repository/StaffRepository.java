package Staff.Repository;

import Abstract.Repository;
import Administrator.Model.Admin;
import Doctor.Model.Doctor;
import Pharmacist.Model.Pharmacist;
import Staff.Model.Staff;

import java.util.ArrayList;
import java.util.List;

import static Util.RepositoryGetter.*;

public class StaffRepository extends Repository<Staff> {
    private static StaffRepository instance = null;
    private StaffRepository(String csvPath) {
        super(csvPath);
        this.entities = new ArrayList<Staff>();
        this.load();
    }

    public static StaffRepository getInstance(String csvPath){
        if (instance == null){
            instance = new StaffRepository(csvPath);
        }
        return instance;
    }

    @Override
    public Staff delete(Staff item) {
        if (deleteStaffById(item.getId()))
            return item;
        return null;
    }

    @Override
    protected Staff fromCSV(String csv) {
        String[] values = csv.split(",");
        int age = Integer.parseInt(values[4]);
        return new Staff(values[0], values[1], values[2], values[3], age);
    }

    @Override
    protected String getHeader() {
        return "id,name,role,gender,age";
    }

    public Staff createStaffFromDoctor(Doctor doctor) {
        return new Staff(doctor.getId(), doctor.getName(), "doctor", doctor.getGender(), doctor.getAge());
    }

    public Staff createStaffFromPharmacist(Pharmacist pharmacist) {
        return new Staff(pharmacist.getId(),pharmacist.getName(),"pharmacist", pharmacist.getGender(), pharmacist.getAge());
    }

    public Staff createStaffFromAdmin(Admin admin) {
        return new Staff(admin.getId(),admin.getName(),"admin", admin.getGender(), admin.getAge());
    }

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
