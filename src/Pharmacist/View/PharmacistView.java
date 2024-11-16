package Pharmacist.View;

import Appointment.Controller.AppointmentController;
import AppointmentOutcome.Model.AppointmentOutcome;
import Inventory.Controller.InventoryController;
import Inventory.Model.Inventory;
import Prescription.Controller.PrescriptionActions;
import Prescription.Model.Prescription;

import java.util.List;
import java.util.Scanner;

import static Util.SafeScanner.getValidatedIntInput;
import static Util.SafeScanner.getValidatedStringInput;

public class PharmacistView {
    private Scanner scanner = new Scanner(System.in);

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
        System.out.println("=== Update Prescription Status ===");

        // Get all prescriptions
        List<Prescription> prescriptions = PrescriptionActions.getAllPrescriptions();

        // Check if there are any prescriptions
        if (prescriptions.isEmpty()) {
            System.out.println("No prescriptions available to update.");
            return;
        }

        // Display all prescriptions with index
        System.out.println("Available Prescriptions:");
        for (int i = 0; i < prescriptions.size(); i++) {
            System.out.println((i + 1) + ". " + prescriptions.get(i).toString()); // Assumes `toString` is informative
        }

        // Ask for a valid index
        int index = getValidatedIntInput(scanner,
                "Enter the index of the prescription to update: ",
                1,
                prescriptions.size());

        // Get the selected prescription
        Prescription selectedPrescription = prescriptions.get(index - 1);

        // Ask for a new status
        String newStatus = getValidatedStringInput(scanner,
                "Enter the new status (e.g., pending, dispensed): ",
                List.of("pending", "dispensed"));

        // Update the prescription status
        Prescription updatedPrescription;
        selectedPrescription.setStatus(newStatus);
        if (newStatus.equalsIgnoreCase("dispensed")){
            updatedPrescription = PrescriptionActions.dispensePrescription(selectedPrescription);
        }
        else{
            updatedPrescription = PrescriptionActions.updatePrescription(selectedPrescription);
        }

        if (updatedPrescription != null) {
            System.out.println("Prescription status updated successfully.");
            updatedPrescription.prettyPrint(); // Assumes a `prettyPrint` method exists
        } else {
            System.out.println("Failed to update prescription status. Please try again.");
        }
    }


    // View medication inventory
    public void viewMedicationInventory() {
        List<Inventory> inventoryList = InventoryController.getAllInventory();
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
        List<Inventory> inventoryList = InventoryController.getAllInventory();
        if (inventoryList.isEmpty()) {
            System.out.println("No medications need restock request.");
            return;
        }

        System.out.println("Select a medication to restock:");
        for (int i = 0; i < inventoryList.size(); i++) {
            System.out.print((i + 1) + ". ");
            inventoryList.get(i).prettyPrint();
        }

        int choice = getValidatedIntInput(scanner, "Enter the number of the medication to restock: ", 1, inventoryList.size());

        // Get the selected inventory item
        Inventory selectedInventory = inventoryList.get(choice - 1);

        // Update the inventory stock request
        Inventory updatedInventory = InventoryController.updateInventoryStockRequest(selectedInventory);

        if (updatedInventory != null && updatedInventory.getRestockRequested().equalsIgnoreCase("pending")) {
            System.out.println("Restock request submitted successfully for: " + selectedInventory.getMedicationName());
        } else {
            System.out.println("Failed to submit restock request. Please try again.");
        }
    }

}
