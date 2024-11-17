package Patient.Controller;

import MedicalRecord.Model.MedicalRecord;
import MedicalRecord.Repository.MedicalRecordRepository;
import Patient.Model.Patient;
import Patient.Repository.PatientRepository;
import User.Model.User;

import java.util.List;

import static Util.RepositoryGetter.getMedicalRecordRepository;
import static Util.RepositoryGetter.getPatientRepository;

/**
 * Controller class for managing `Patient` entities and their interactions.
 * Provides methods for retrieving, updating, and creating `Patient` objects,
 * as well as accessing associated medical records.
 */
public class PatientController {

    /**
     * Retrieves the medical records associated with a specific patient.
     *
     * @param patient The `Patient` object whose medical records are to be retrieved.
     * @return A list of `MedicalRecord` objects for the specified patient.
     */
    public static List<MedicalRecord> getMedicalRecords(Patient patient){
        MedicalRecordRepository repo = getMedicalRecordRepository();
        return repo.getByFilter((MedicalRecord record) -> record.getPatientId().equals(patient.getId()));
    }

    /**
     * Creates a `Patient` object from a given `User` object.
     * Associates the `Patient` with the provided `User`.
     *
     * @param user The `User` object to create the `Patient` object from.
     * @return The created `Patient` object, or `null` if no patient is found in the repository.
     */
    public static Patient createPatientFromUser(User user){
        PatientRepository repo = getPatientRepository();
//        repo.display();
        Patient patient = repo.read(user.getId());
        if (patient == null) {
            System.out.println("Patient not found");
            return null;
        }
        patient.setUser(user);
        return patient;
    }

    /**
     * Updates the details of a specific `Patient` in the repository.
     *
     * @param patient The `Patient` object with updated details.
     * @return The updated `Patient` object after saving changes to the repository.
     */
    public static Patient updatePatient(Patient patient){
        PatientRepository repo = getPatientRepository();
        return repo.update(patient);
    }

    /**
     * Retrieves a `Patient` object by its unique ID.
     *
     * @param patientId The unique ID of the patient.
     * @return The `Patient` object corresponding to the given ID, or `null` if not found.
     */
    public static Patient getPatientById(String patientId) {
        return getPatientRepository().read(patientId);
    }
}
