package MedicalRecord.Controller;

import MedicalRecord.Model.MedicalRecord;

import java.time.LocalDateTime;
import java.util.List;

import static Util.RepositoryGetter.getMedicalRecordRepository;

/**
 * Controller class for managing `MedicalRecord` entities.
 * Provides methods for creating and retrieving medical records.
 */
public class MedicalRecordController {

    /**
     * Creates a new `MedicalRecord` with the current date and time.
     *
     * @param patientId  The ID of the patient associated with the medical record.
     * @param doctorId   The ID of the doctor creating the medical record.
     * @param diagnosis  The diagnosis details for the medical record.
     * @param treatment  The treatment details for the medical record.
     * @return A newly created `MedicalRecord` object.
     */
    public static MedicalRecord createNewMedicalRecordToday(String patientId, String doctorId, String diagnosis, String treatment) {
        return new MedicalRecord(getMedicalRecordRepository().generateId(), patientId, doctorId, diagnosis, treatment, LocalDateTime.now());
    }

    /**
     * Retrieves all medical records.
     *
     * @return A list of all `MedicalRecord` objects.
     */
    public static List<MedicalRecord> getAllMedicalRecords() {
        return getMedicalRecordRepository().getAll();
    }


    /**
     * Retrieves all medical records for a specific patient by their ID.
     *
     * @param patientId The ID of the patient whose medical records are to be retrieved.
     * @return A list of `MedicalRecord` objects associated with the specified patient ID.
     */
    public static List<MedicalRecord> getAllMedicalRecordsByPatientId(String patientId) {
        return getMedicalRecordRepository().filterById(patientId);
    }
}
