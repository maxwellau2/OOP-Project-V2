package Doctor.Repository;
import Abstract.Repository;
import Doctor.Model.Doctor;
import java.util.ArrayList;

import static Util.RepositoryGetter.getDoctorRepository;

/**
 * Repository class for managing Doctor entities.
 * Provides CRUD operations and handles data persistence via a CSV file.
 */
public class DoctorRepository extends Repository<Doctor> {
    private static DoctorRepository instance = null;

    /**
     * Private constructor for initializing the repository with the specified CSV file path.
     *
     * @param csvPath Path to the CSV file used for data persistence.
     */

    private DoctorRepository(String csvPath) {
        super(csvPath);
        this.entities = new ArrayList<>();
    }

    /**
     * Retrieves the singleton instance of the DoctorRepository.
     *
     * @param csvPath Path to the CSV file for data persistence.
     * @return The singleton instance of DoctorRepository.
     */
    public static DoctorRepository getInstance(String csvPath) {
        if (instance == null) {
            instance = new DoctorRepository(csvPath);
            instance.load();
        }
        return instance;
    }

    /**
     * Converts a CSV line into a Doctor object.
     *
     * @param csvLine A single line from the CSV file representing a doctor's data.
     * @return A Doctor object created from the CSV line.
     */
    @Override
    protected Doctor fromCSV(String csvLine){
        String[] data = csvLine.split(",");
        return new Doctor(data[0], data[1], Integer.valueOf(data[2]), data[3], data[4]);
    }

    /**
     * Retrieves the header line for the CSV file.
     *
     * @return A string representing the CSV header.
     */
    @Override
    protected String getHeader(){
        return "id,name,age,gender,specialization";
    }


    /**
     * Creates a new Doctor entity and adds it to the repository.
     *
     * @param name           Name of the doctor.
     * @param age            Age of the doctor.
     * @param gender         Gender of the doctor.
     * @param specialization Medical specialization of the doctor.
     * @return The newly created Doctor object.
     */
    public Doctor createDoctor(String name, Integer age, String gender, String specialization){
        Doctor newDoctor = new Doctor(getDoctorRepository().generateId(), name, age, gender, specialization);
        return getDoctorRepository().create(newDoctor);
    }
}
