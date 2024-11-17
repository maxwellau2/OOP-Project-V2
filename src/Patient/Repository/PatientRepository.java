package Patient.Repository;
import Patient.Model.Patient;
import Abstract.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


/**
 * Repository class for managing Patient entities.
 * Provides methods to interact with the underlying storage (CSV file) for Patient data.
 */
public class PatientRepository extends Repository<Patient> {
    private static PatientRepository instance = null;

    /**
     * Retrieves the singleton instance of the PatientRepository.
     * If the instance does not exist, it initializes it with the specified CSV path and loads data.
     *
     * @param csvPath The file path of the CSV file.
     * @return The singleton instance of PatientRepository.
     */
    public static PatientRepository getInstance(String csvPath){
        if(instance == null){
            instance = new PatientRepository(csvPath);
            instance.load();
        }
        return instance;
    }


    /**
     * Private constructor to initialize the `PatientRepository`.
     * Loads patient data from the specified CSV file.
     *
     * @param csvPath The file path of the CSV file containing patient data.
     */
    private PatientRepository(String csvPath) {
        super(csvPath);
        this.entities = new ArrayList<Patient>();
    }


    /**
     * Converts a CSV line into a `Patient` object.
     *
     * @param csv The CSV line containing patient data.
     *            Format: id,name,dob,gender,email,phoneNumber,bloodType
     * @return A `Patient` object populated with data from the CSV line.
     */
    @Override
    protected Patient fromCSV(String csv) {
        String[] values = csv.split(",");
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE; // yyyy-MM-dd format
        LocalDateTime dateTime = LocalDate.parse(values[2], formatter).atStartOfDay();
        return new Patient(values[0], values[1], dateTime, values[3], values[4], values[5], values[6]);
    }


    /**
     * Retrieves the CSV header line for patient data.
     *
     * @return A string representing the header of the CSV file.
     *         Format: id,name,dob,gender,email,phoneNumber,bloodType
     */
    @Override
    protected String getHeader() {
        return "id,name,dob,gender,email,phoneNumber,bloodType";
    }
}
