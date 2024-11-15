package Pharmacist.View;

import Appointment.Controller.AppointmentController;
import Appointment.Model.Appointment;
import AppointmentOutcome.Model.AppointmentOutcome;
import Inventory.Model.Inventory;
import Pharmacist.Controller.PharmacistActions;
import static Util.SafeScanner.getValidatedIntInput;
import static Util.SafeScanner.getValidatedStringInput;

import java.util.List;
import java.util.Scanner;

import Prescription.Controller.PrescriptionActions;

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
        List<AppointmentOutcome> appointments = AppointmentController.getAppointmentOutcomeNotDispensed();
        for (AppointmentOutcome appointment : appointments) {
            appointment.prettyPrint();
        }
    }

    // Update prescription
    public void updatePrescriptionStatus() {
        String prescriptionId = getValidatedStringInput(scanner, "Enter the prescription ID to update: ", 10);
        if (PrescriptionActions.getPrescriptionById(prescriptionId) == null){
            System.out.println("Prescription does not exist.");
            return;
        }
        String newStatus = getValidatedStringInput(scanner, "Enter the new status (e.g., pending, dispensed): ", List.of("pending", "dispensed"));
        PharmacistActions.updatePrescriptionStatusById(prescriptionId, newStatus);
    }

    // View medication inventory
    public void viewMedicationInventory() {
        List<Inventory> inventoryList = PharmacistActions.viewInventory();
        if (inventoryList.isEmpty()) {
            System.out.println("No medications found in inventory.");
        } else {
            for (Inventory item : inventoryList) {
                item.prettyPrint();
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
