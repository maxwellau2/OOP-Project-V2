package Prescription.Controller;

import Prescription.Model.Prescription;
import Prescription.Repository.PrescriptionRepository;

import static Util.RepositoryGetter.initPrescriptionRepository;

public class PrescriptionActions {
    public static void main(String[] args) {
        viewAllPrescriptions();
        addPrescription(new Prescription("123", "D002", "P001", "xanax", "3mg", "dispensed"));
        addPrescription(new Prescription("D002", "P001", "mother", "69mg", "dispensed"));
        viewAllPrescriptions();
    }

    public static void viewAllPrescriptions() {
        PrescriptionRepository prescriptionRepository = initPrescriptionRepository();
        prescriptionRepository.display();
    }
    public static Prescription addPrescription(Prescription prescription){
        PrescriptionRepository prescriptionRepository = initPrescriptionRepository();
        return prescriptionRepository.createPrescription(prescription);
    }
}
