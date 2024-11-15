package Prescription.Controller;

import Prescription.Model.Prescription;
import Prescription.Repository.PrescriptionRepository;

import java.util.List;

import static Util.RepositoryGetter.getPrescriptionRepository;
import static Util.RepositoryGetter.initPrescriptionRepository;

public class PrescriptionActions {

    public static Prescription createNewPrescription(String doctorId, String patientId, String medicationName, String dosage, String status){
        return new Prescription(getPrescriptionRepository().generateId(), doctorId, patientId, medicationName, dosage, status);
    }

    public static Prescription getPrescriptionById(String id){
        return getPrescriptionRepository().read(id);
    }

    public static Prescription createNewPendingPrescription(String doctorId, String patientId, String medicationName, String dosage, String appointmentId){
        return new Prescription(getPrescriptionRepository().generateId(), doctorId, patientId, medicationName, dosage, "pending", appointmentId);
    }

    public static List<Prescription> getAllPrescriptions() {
        return getPrescriptionRepository().getAll();
    }

    public static List<Prescription> getAllPendingPrescriptions() {
        return getPrescriptionRepository().getByFilter(prescription -> prescription.getStatus().equals("pending"));
    }

    public static List<Prescription> getAllDispensedPrescriptions() {
        return getPrescriptionRepository().getByFilter(prescription -> prescription.getStatus().equals("dispensed"));
    }

    public static Prescription addPrescription(Prescription prescription){
        PrescriptionRepository prescriptionRepository = initPrescriptionRepository();
        return prescriptionRepository.createPrescription(prescription);
    }
}
