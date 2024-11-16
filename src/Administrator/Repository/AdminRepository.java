package Administrator.Repository;

import Abstract.Repository;
import Administrator.Model.Admin;

import java.util.ArrayList;

import static Util.RepositoryGetter.getAdminRepository;

/**
 * Repository class for managing {@link Admin} entities.
 * Provides CRUD operations and utility methods for handling admin-related data.
 * Extends the generic {@link Repository} class.
 */
public class AdminRepository extends Repository<Admin> {
    private static AdminRepository instance = null;

    /**
     * Singleton pattern to get a single instance of {@code AdminRepository}.
     *
     * @param csvPath The path to the CSV file used for storing admin data.
     * @return The single instance of {@code AdminRepository}.
     */
    public static AdminRepository getInstance(String csvPath) {
        if (instance == null) {
            instance = new AdminRepository(csvPath);
            instance.load(); // Load data from CSV on initialization
        }
        return instance;
    }

    /**
     * Private constructor to enforce singleton pattern.
     *
     * @param csvPath The path to the CSV file used for storing admin data.
     */
    private AdminRepository(String csvPath) {
        super(csvPath);
        this.entities = new ArrayList<>(); // Initialize the entity list
    }

    /**
     * Parses a CSV line into an {@link Admin} entity.
     *
     * @param csv The CSV line containing admin details.
     * @return An {@link Admin} object constructed from the CSV data.
     */
    @Override
    protected Admin fromCSV(String csv) {
        String[] values = csv.split(",");
        String id = values[0];
        String name = values[1];
        String gender = values[2];
        int age = Integer.parseInt(values[3]);
        return new Admin(id, name, gender, age);
    }

    /**
     * Provides the header line for the CSV file.
     *
     * @return A string representing the CSV header.
     */
    @Override
    protected String getHeader() {
        return "id,name,gender,age";
    }

    /**
     * Creates a new {@link Admin} entity and stores it in the repository.
     *
     * @param name   The name of the admin.
     * @param gender The gender of the admin.
     * @param age    The age of the admin.
     * @return The created {@link Admin} entity.
     */
    public Admin createAdmin(String name, String gender, int age) {
        Admin admin = new Admin(getAdminRepository().generateId(), name, gender, age);
        return getAdminRepository().create(admin);
    }
}
