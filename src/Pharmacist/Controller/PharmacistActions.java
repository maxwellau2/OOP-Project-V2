package Pharmacist.Controller;

import Inventory.Model.Inventory;
import Inventory.Repository.InventoryRepository;
import Pharmacist.Model.Pharmacist;
import Pharmacist.Repository.PharmacistRepository;
import Prescription.Model.Prescription;
import Prescription.Repository.PrescriptionRepository;
import User.Model.User;

import java.util.List;

import static Util.RepositoryGetter.*;


public class PharmacistActions {
    public PharmacistRepository getPharmacistrRepository() {
        return PharmacistRepository.getInstance("src/Data/Pharmacist_List.csv");
    }

        public static Pharmacist createPharmacistFromUser(User user) {
        Pharmacist pharmacist = getPharmacistRepository().read(user.getId());
        if (pharmacist == null) {
            System.out.println("Pharmacist not found");
            return null;
        }
        pharmacist.setUser(user);
        return pharmacist;
    }

    public static void viewPrescriptionDetails(String patientId) {
        PrescriptionRepository prescriptionRepo = PrescriptionRepository.getInstance("src/Data/Prescription_List.csv");
        List<Prescription> prescriptions = prescriptionRepo.getByPatientId(patientId);
        if (prescriptions.isEmpty()) {
            System.out.println("No prescriptions found for patient with ID: " + patientId);
        } else {
            for (Prescription prescription : prescriptions) {
                System.out.println(prescription);
            }
        }
    }
    public static boolean updatePrescriptionStatusById(String prescriptionId, String newStatus) {
        PrescriptionRepository prescriptionRepo = getPrescriptionRepository();
        Prescription prescription = prescriptionRepo.read(prescriptionId);
        if (prescription != null) {
            prescription.setStatus(newStatus);
            prescriptionRepo.update(prescription);
            System.out.println("Prescription status updated to: " + newStatus); //pending or dispensed
        } else {
            System.out.println("Prescription with ID " + prescriptionId + " not found.");
            return false;
        }
        if (!newStatus.equalsIgnoreCase("dispensed"))
            return true;
        // update the inventory
        // parse the dosage as integer, remove all characters
        String numericPart = prescription.getDosage().replaceAll("[^0-9]", "");
        int num = 0;
        if (!numericPart.isEmpty()){
            num = Integer.parseInt(numericPart);
        }
        getInventoryRepoInstance().decreaseQuantity(prescription.getMedicationName(), num);
        return true;
    }
    public static List<Inventory> getInventory(){
        InventoryRepository inventoryRepo = InventoryRepository.getInstance("src/Data/Medicine_List.csv");
        return inventoryRepo.getAll();
    }

    public static void requestRestock(String medicationName) {
        InventoryRepository inventoryRepo = getInventoryRepoInstance();
        Inventory item = inventoryRepo.getItemByName(medicationName);

        if (item != null && item.getQuantity() < item.getLowStockAlert()) {
            inventoryRepo.submitRestockRequest(item);  // Submit restock request
            System.out.println("Restock request submitted for medication: " + medicationName);
        } else {
            System.out.println("No restock needed or medication not found.");
        }
    }

    public static boolean deletePharmacistById(String id){
        return getPharmacistRepository().deleteById(id);
    }
}
