package MedicalRecord.Repository;

import Abstract.Repository;
import MedicalRecord.Model.MedicalRecord;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static Util.RepositoryGetter.getMedicalRecordRepository;

/**
 * Repository class for managing `MedicalRecord` entities.
 * Handles CRUD operations and CSV persistence for medical records.
 */
public class MedicalRecordRepository extends Repository<MedicalRecord> {

    private static MedicalRecordRepository instance;

    /**
     * Private constructor to initialize the repository with a CSV path.
     *
     * @param csvPath The file path for the CSV file storing medical records.
     */
    private MedicalRecordRepository(String csvPath) {
        super(csvPath);
        this.entities = new ArrayList<>();
    }

    /**
     * Factory method to create a new medical record for today.
     *
     * @param patientId  The ID of the patient associated with the record.
     * @param doctorId   The ID of the doctor who created the record.
     * @param diagnosis  The diagnosis details.
     * @param treatment  The treatment details.
     * @return A newly created `MedicalRecord` object.
     */
    public static MedicalRecord createNewMedicalRecordToday(String patientId, String doctorId, String diagnosis, String treatment) {
        return new MedicalRecord(
                getMedicalRecordRepository().generateId(),
                patientId,
                doctorId,
                diagnosis,
                treatment,
                LocalDateTime.now()
        );
    }

    /**
     * Retrieves the singleton instance of the `MedicalRecordRepository`.
     *
     * @param csvPath The file path for the CSV file storing medical records.
     * @return The singleton instance of the repository.
     */
    public static MedicalRecordRepository getInstance(String csvPath) {
        if (instance == null) {
            instance = new MedicalRecordRepository(csvPath);
            instance.load();
        }
        return instance;
    }

    /**
     * Converts a CSV line to a `MedicalRecord` object.
     *
     * @param csvLine The CSV line representing a medical record.
     * @return A `MedicalRecord` object.
     * @throws IllegalArgumentException if the CSV line is invalid or incomplete.
     */
    @Override
    protected MedicalRecord fromCSV(String csvLine) {
        String[] data = csvLine.split(",");
        if (data.length < 6) {
            throw new IllegalArgumentException("Invalid CSV line for MedicalRecord: " + csvLine);
        }

        String recordId = data[0];
        String patientId = data[1];
        String doctorId = data[2];
        String diagnosis = data[3];
        String treatment = data[4];
        LocalDateTime date = data[5].isEmpty() ? null : LocalDateTime.parse(data[5], DateTimeFormatter.ISO_DATE_TIME);

        return new MedicalRecord(recordId, patientId, doctorId, diagnosis, treatment, date);
    }

    /**
     * Provides the header line for the CSV file.
     *
     * @return A string representing the CSV header.
     */
    @Override
    protected String getHeader() {
        return "recordId,patientId,doctorId,diagnosis,treatment,date";
    }
}
