package Prescription.Controller;

import Prescription.Model.Prescription;
import Prescription.Repository.PrescriptionRepository;

import java.util.List;

import static Util.RepositoryGetter.*;


/**
 * The PrescriptionActions class provides static methods to manage prescriptions,
 * including creating, updating, dispensing, and retrieving prescription details.
 */
public class PrescriptionActions {

    /**
     * Creates a new prescription object with the specified details.
     * The ID is generated automatically.
     *
     * @param doctorId      The ID of the doctor.
     * @param patientId     The ID of the patient.
     * @param medicationName The name of the medication.
     * @param dosage        The dosage of the medication.
     * @param status        The status of the prescription (e.g., "pending", "dispensed").
     * @return A new Prescription object.
     */
    public static Prescription createNewPrescription(String doctorId, String patientId, String medicationName, String dosage, String status){
        return new Prescription(getPrescriptionRepository().generateId(), doctorId, patientId, medicationName, dosage, status);
    }


    /**
     * Updates an existing prescription in the repository.
     *
     * @param prescription The Prescription object to update.
     * @return The updated Prescription object.
     */
    public static Prescription updatePrescription(Prescription prescription){
        return getPrescriptionRepository().update(prescription);
    }


    /**
     * Dispenses a prescription and updates the inventory based on the medication dosage.
     *
     * @param prescription The Prescription object to dispense.
     * @return The updated Prescription object after dispensing.
     */
    public static Prescription dispensePrescription(Prescription prescription){
        // look up the inventory list
        String numericDosage = prescription.getDosage().replaceAll("[^0-9]", "");
        int numeric = 0;
        if (!numericDosage.isEmpty()) numeric = Integer.parseInt(numericDosage);
        getInventoryRepoInstance().decreaseQuantity(prescription.getMedicationName(),numeric);
        return updatePrescription(prescription);
    }


    /**
     * Retrieves a prescription by its unique ID.
     *
     * @param id The ID of the prescription.
     * @return The Prescription object if found, or null otherwise.
     */
    public static Prescription getPrescriptionById(String id){
        return getPrescriptionRepository().read(id);
    }


    /**
     * Creates a new prescription with a default "pending" status.
     *
     * @param doctorId       The ID of the doctor.
     * @param patientId      The ID of the patient.
     * @param medicationName The name of the medication.
     * @param dosage         The dosage of the medication.
     * @param appointmentId  The ID of the related appointment.
     * @return A new Prescription object with "pending" status.
     */
    public static Prescription createNewPendingPrescription(String doctorId, String patientId, String medicationName, String dosage, String appointmentId){
        return new Prescription(getPrescriptionRepository().generateId(), doctorId, patientId, medicationName, dosage, "pending", appointmentId);
    }

    /**
     * Retrieves all prescriptions stored in the repository.
     *
     * @return A list of all Prescription objects.
     */
    public static List<Prescription> getAllPrescriptions() {
        return getPrescriptionRepository().getAll();
    }


    /**
     * Retrieves all prescriptions with a "pending" status.
     *
     * @return A list of pending Prescription objects.
     */
    public static List<Prescription> getAllPendingPrescriptions() {
        return getPrescriptionRepository().getByFilter(prescription -> prescription.getStatus().equals("pending"));
    }


    /**
     * Retrieves all prescriptions with a "dispensed" status.
     *
     * @return A list of dispensed Prescription objects.
     */
    public static List<Prescription> getAllDispensedPrescriptions() {
        return getPrescriptionRepository().getByFilter(prescription -> prescription.getStatus().equals("dispensed"));
    }


    /**
     * Adds a new prescription to the repository.
     *
     * @param prescription The Prescription object to add.
     * @return The added Prescription object if successful, or null otherwise.
     */
    public static Prescription addPrescription(Prescription prescription){
        PrescriptionRepository prescriptionRepository = initPrescriptionRepository();
        return prescriptionRepository.createPrescription(prescription);
    }
}
