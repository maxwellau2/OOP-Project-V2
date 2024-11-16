package Prescription.Repository;

import Abstract.Repository;
import Prescription.Model.Prescription;

import java.util.ArrayList;
import java.util.List;


/**
 * The PrescriptionRepository class is responsible for managing prescription data.
 * It provides methods to load, save, and query prescription data stored in a CSV file.
 * The repository ensures unique prescription entries and facilitates retrieval by various attributes.
 */
public class PrescriptionRepository extends Repository<Prescription> {
    private static PrescriptionRepository instance;


    /**
     * Private constructor to prevent direct instantiation.
     * Loads prescription data from the specified CSV file.
     *
     * @param csvPath The path to the CSV file.
     */

    private PrescriptionRepository(String csvPath) { // make sure constructor is private
        super(csvPath); 
        this.entities = new ArrayList<Prescription>();
        this.load();
    }

    /**
     * Returns the singleton instance of the PrescriptionRepository.
     * If the instance does not exist, it creates a new one.
     *
     * @param csvPath The path to the CSV file.
     * @return The singleton instance of the PrescriptionRepository.
     */
    public static PrescriptionRepository getInstance(String csvPath) {
        if (instance == null) {
            instance = new PrescriptionRepository(csvPath);
            instance.load();
        }
        return instance;
    }


    /**
     * Creates a new prescription in the repository if it is unique.
     * Generates a new ID for the prescription if it does not have one.
     *
     * @param prescription The Prescription object to create.
     * @return The created Prescription object if unique, otherwise null.
     */
    public Prescription createPrescription(Prescription prescription){
        // returns a prescription if is unique, else none
        if (prescription.getId() == null){
            prescription.setId(this.generateId());
        }
        if (this.create(prescription) != null){
            return prescription;
        }
        return null;
    }


    /**
     * Converts a CSV line to a Prescription object.
     *
     * @param csvLine The CSV line containing prescription data.
     * @return The Prescription object created from the CSV line, or null if the line is invalid.
     */
    @Override
    protected Prescription fromCSV(String csvLine) {
        String[] data = csvLine.split(",");
        if (data.length == 7) {
            return new Prescription(data[0], data[1], data[2], data[3], data[4], data[5], data[6]);
        }
        return null;
    }

    /**
     * Returns the header string for the CSV file.
     *
     * @return The header string.
     */
    @Override
    protected String getHeader() {
        return "prescriptionId,doctorId,patientId,medicationName,dosage,status,appointmentId";
    }


    // Find prescriptions by patientId
    /**
     * Retrieves a list of prescriptions for a specific patient.
     *
     * @param patientId The ID of the patient.
     * @return A list of Prescription objects for the specified patient.
     */

    public List<Prescription> getByPatientId(String patientId) {
        return this.getByFilter(prescription -> prescription.getPatientId().equals(patientId));
    }



    // Find prescriptions by doctorId
    /**
     * Retrieves a list of prescriptions written by a specific doctor.
     *
     * @param doctorId The ID of the doctor.
     * @return A list of Prescription objects written by the specified doctor.
     */
    public List<Prescription> getByDoctorId(String doctorId) {
        return this.getByFilter(prescription -> prescription.getDoctorId().equals(doctorId));
    }

    // Find prescriptions by status (e.g., pending, dispensed)
    /**
     * Retrieves a list of prescriptions with a specific status.
     *
     * @param status The status of the prescriptions (e.g., "pending", "dispensed").
     * @return A list of Prescription objects with the specified status.
     */
    public List<Prescription> getByStatus(String status) {
        return this.getByFilter(prescription -> prescription.getStatus().equalsIgnoreCase(status));
    }

    // update prescription status
    /**
     * Updates the status of a given prescription.
     *
     * @param prescription The Prescription object to update.
     * @param status       The new status to set.
     * @return The updated Prescription object.
     */
    public Prescription updatePrescriptionStatus(Prescription prescription, String status) {
        prescription.setStatus(status);
        return this.update(prescription);
    }
}
