package Pharmacist.View;

import Inventory.Model.Inventory;
import Pharmacist.Controller.PharmacistActions;
import static Util.SafeScanner.getValidatedIntInput;
import java.util.List;
import java.util.Scanner;

public class PharmacistView {
    private Scanner scanner = new Scanner(System.in);
    private PharmacistActions pharmacistActions = new PharmacistActions();

    public void displayMenu() {
        int choice;

        do {
            System.out.println("\n=== Pharmacist Menu ===");
            System.out.println("1. View Appointment Outcome Record"); //view PrescriptionDetails
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Submit Restock Request");
            System.out.println("0. Exit");

            choice = getValidatedIntInput(scanner, "Enter your choice: ", 0, 4);

            switch (choice) {
                case 1:
                    viewPrescriptionDetails();
                    break;
                case 2:
                    updatePrescriptionStatus();
                    break;
                case 3:
                    viewMedicationInventory();
                    break;
                case 4:
                    submitRestockRequest();
                    break;
                case 0:
                    System.out.println("Exiting the Pharmacist Menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        } while (choice != 0);

        scanner.close();
    }

    // View prescription details by patientId
    public void viewPrescriptionDetails() {
        System.out.print("Enter the patient ID to view their appointment outcome record: ");
        String patientId = scanner.nextLine();
        pharmacistActions.viewPrescriptionDetails(patientId);
    }

    // Update prescription
    public void updatePrescriptionStatus() {
        System.out.print("Enter the prescription ID to update: ");
        String prescriptionId = scanner.nextLine();
        System.out.print("Enter the new status (e.g., pending, dispensed): ");
        String newStatus = scanner.nextLine();
        pharmacistActions.updatePrescriptionStatus(prescriptionId, newStatus);
    }

    // View medication inventory
    public void viewMedicationInventory() {
        List<Inventory> inventoryList = pharmacistActions.viewInventory();
        if (inventoryList.isEmpty()) {
            System.out.println("No medications found in inventory.");
        } else {
            for (Inventory item : inventoryList) {
                System.out.println(item);
            }
        }
    }

    // Submit a restock request 
    public void submitRestockRequest() {
        System.out.print("Enter the medication name to check and request restock: ");
        String medicationName = scanner.nextLine();
        pharmacistActions.requestRestock(medicationName);
    }
}
