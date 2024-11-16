package Administrator.View;

import Administrator.Controller.AdminActions;
import Administrator.Model.Admin;
import Appointment.Model.Appointment;
import Doctor.Model.Doctor;
import Inventory.Model.Inventory;
import Pharmacist.Model.Pharmacist;
import Staff.Model.Staff;
import static Util.RepositoryGetter.getAdminRepository;
import static Util.RepositoryGetter.getDoctorRepository;
import static Util.RepositoryGetter.getPharmacistRepository;
import static Util.SafeScanner.getValidatedIntInput;
import static Util.SafeScanner.getValidatedStringInput;
import java.util.List;
import java.util.Scanner;

public class AdminView {
    private Scanner scanner = new Scanner(System.in);

    public void displayMenu() {
        int choice;

        do {
            System.out.println("\n=== Administrator Menu ===");
            System.out.println("1. View and Manage Hospital Staff");
            System.out.println("2. View Appointments Details");
            System.out.println("3. View and Manage Medication Inventory");
            System.out.println("4. Approve Replenishment Requests");
            System.out.println("0. Exit");

            choice = getValidatedIntInput(scanner, "Enter your choice: ", 0, 4);

            switch (choice) {
                case 1 -> viewAndManageHospitalStaff();
                case 2 -> viewAppointmentsDetails();
                case 3 -> viewAndManageMedicationInventory();
                case 4 -> approveReplenishmentRequests();
                case 0 -> System.out.println("Exiting the Administrator Menu.");
                default -> System.out.println("Invalid choice. Please select a valid option.");
            }
        } while (choice != 0);

        scanner.close();
    }

    // View and manage hospital staff (Doctors, Pharmacists, etc.)
    public void viewAndManageHospitalStaff() {
        System.out.println("=== View and Manage Hospital Staff ===");
        List<Staff> staffList = AdminActions.getAllStaff(); 
        if (staffList.isEmpty()) {
            System.out.println("No hospital staff found.");
        } else {
            for (Staff staff : staffList) {
                System.out.println(staff);
            }
        }
        // Add or delete staff
        System.out.println("\n1. Add Staff");
        System.out.println("2. Delete Staff");
        System.out.println("3. Skip and Return to Menu");

        int choice = getValidatedIntInput(scanner, "Choose an option: ", 1, 3);
        switch (choice) {
            case 1 -> addStaff();
            case 2 -> deleteStaff();
            case 3 -> System.out.println("Returning to the previous menu...");
        }
    }

    // Add new staff member
    public void addStaff() {
        System.out.println("=== Add New Staff ===");
        String role = getValidatedStringInput(scanner, "Enter Role: ", List.of("Doctor", "Pharmacist", "Admin"));
        String name = getValidatedStringInput(scanner, "Enter Name: ", 20);
        String gender = getValidatedStringInput(scanner, "Enter Gender: ", List.of("Male", "Female"));
        int age = getValidatedIntInput(scanner, "Enter Age: ", 0,99);
        // String id = getValidatedStringInput(scanner, "Enter Staff ID: ", 4);

        switch (role) {
            case "Doctor" -> {
                String specialization = getValidatedStringInput(scanner, "Enter Specialization: ", 20);
                Doctor newDoctor = new Doctor(getDoctorRepository().generateId(), name, age, gender,specialization);
                Doctor addedDoctor = AdminActions.addDoctor(newDoctor);
                System.out.println("Added staff role: " + addedDoctor);
            }
            case "Pharmacist" -> {
                Pharmacist newPharmacist = new Pharmacist(getPharmacistRepository().generateId(), name, age, gender);
                Pharmacist addedPharmacist = AdminActions.addPharmacist(newPharmacist);
                System.out.println("Added staff role: " + addedPharmacist);
            }
            case "Admin" -> {
                Admin newAdmin = new Admin(getAdminRepository().generateId(), name, gender, age);
                Admin addedAdmin = AdminActions.addAdmin(newAdmin);
                System.out.println("Added staff role: " + addedAdmin);
            }

        }
    }

    // Delete a staff member based on role and ID
public void deleteStaff() {
    System.out.println("=== Delete Staff ===");
    String role = getValidatedStringInput(scanner, "Enter Role of staff to delete (Doctor, Pharmacist, Admin): ", List.of("Doctor", "Pharmacist", "Admin"));
    String staffId = getValidatedStringInput(scanner, "Enter Staff ID to delete: ", 10);  // Adjust max length if needed

    boolean success = false;

    switch (role) {
        case "Doctor" -> {
            success = AdminActions.deleteDoctor(staffId);
        }
        case "Pharmacist" -> {
            success = AdminActions.deletePharmacist(staffId);
        }
        case "Admin" -> {
            success = AdminActions.deleteAdmin(staffId);
        }
        default -> System.out.println("Invalid role provided.");
    }

    if (success) {
        System.out.println("Staff member with ID " + staffId + " has been deleted.");
    } else {
        System.out.println("Staff member with ID " + staffId + " not found or could not be deleted.");
    }
}


    // View appointments details
    public void viewAppointmentsDetails() {
        System.out.println("=== View Appointments Details ===");
        List<Appointment> appointments = AdminActions.getAllAppointments(); 
        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");
        } else {
            for (Appointment appointment : appointments) {
                System.out.println(appointment); 
            }
        }
    }

    // View and manage medication inventory 
    public void viewAndManageMedicationInventory() {
        System.out.println("=== View and Manage Medication Inventory ===");
        List<Inventory> inventoryList = AdminActions.getInventoryRepoInstance().getAll(); 
        if (inventoryList.isEmpty()) {
            System.out.println("No inventory items found.");
        } else {
            for (Inventory item : inventoryList) {
                System.out.println(item);
            }
        }
        // add functionality to update or remove items from inventory
        System.out.println("\n1. Update Stock");
        System.out.println("2. Add New Item");
        System.out.println("3. Done and Return to Menu");

        int choice = getValidatedIntInput(scanner, "Choose an option: ", 1, 3);
        switch (choice) {
            case 1 -> updateStock();
            case 2 -> addInventoryItem();
            case 3 -> System.out.println("Returning to the previous menu...");
        }
    }

    // Update stock of an inventory item
    public void updateStock() {
        System.out.println("=== Update Stock ===");
        int medicationId = getValidatedIntInput(scanner, "Enter Medication ID to update: ", 0, Integer.MAX_VALUE);
        int newQuantity = getValidatedIntInput(scanner, "Enter Quantity (valid integer): ", 0, Integer.MAX_VALUE);

        boolean success = AdminActions.updateStock(String.valueOf(medicationId), newQuantity);
        if (success) {
            System.out.println("Stock updated successfully.");
        } else {
            System.out.println("Failed to update stock. Medication ID may not exist.");
        }
    }

    // Add new inventory item
    public void addInventoryItem() {
        System.out.println("=== Add New Inventory Item ===");

        int medicationId = getValidatedIntInput(scanner, "Enter Medication ID: ", 0, Integer.MAX_VALUE);

        Inventory existingItem = AdminActions.getInventoryRepoInstance().getById(String.valueOf(medicationId));

        if (existingItem != null) {
            System.out.println("Item with this ID already exists: " + existingItem);
            String response = getValidatedStringInput(scanner, "Would you like to update the quantity? (yes/no): ", List.of("yes", "no"));

            if (response.equalsIgnoreCase("yes")) {
                int newQuantity = getValidatedIntInput(scanner, "Enter New Quantity: ", 0, Integer.MAX_VALUE);

                existingItem.setQuantity(newQuantity);
                boolean success = AdminActions.updateStock(String.valueOf(medicationId), newQuantity);
                if (success) {
                    System.out.println("Quantity updated successfully. New details: " + existingItem);
                } else {
                    System.out.println("Failed to update quantity.");
                }
            } else {
                System.out.println("No changes made to the existing inventory.");
            }
        } else {
            String medicationName = getValidatedStringInput(scanner, "Enter Medication Name: ", 20);
            int quantity = getValidatedIntInput(scanner, "Enter Quantity: ", 0, Integer.MAX_VALUE);
            int lowStockAlert = getValidatedIntInput(scanner, "Enter Low Stock Alert Level: ", 0, Integer.MAX_VALUE);
            boolean isAvailable = getValidatedStringInput(scanner, "Is Low Stock Alert? (true/false): ", List.of("true", "false")).equalsIgnoreCase("true");

            Inventory newItem = new Inventory(String.valueOf(medicationId), medicationName, quantity, lowStockAlert, isAvailable);
            boolean success = AdminActions.addNewInventoryItem(newItem);
            if (success) {
                System.out.println("New inventory item added successfully: " + newItem);
            } else {
                System.out.println("Failed to add new inventory item.");
            }
        }
    }

    // Approve replenishment requests
    public void approveReplenishmentRequests() {
        System.out.println("=== Approve Replenishment Requests ===");
        List<Inventory> lowStockItems = AdminActions.getInventoryRepoInstance().getLowStockItems(); 
        if (lowStockItems.isEmpty()) {
            System.out.println("No low stock items.");
        } else {
            boolean restockRequestedFound = false;
            for (Inventory item : lowStockItems) {
                if (item.isRestockRequested()) {
                    restockRequestedFound = true;
                    System.out.println("Item: " + item.getMedicationName() + " is requested to restock.");
                    String response = getValidatedStringInput(scanner, "Approve restock request? (yes/no): ", List.of("yes", "no"));
                    if (response.equalsIgnoreCase("yes")) {
                        int restockQuantity = getValidatedIntInput(scanner, "Enter restock quantity: ", 0, Integer.MAX_VALUE);
                        AdminActions.updateStock(item.getId(), restockQuantity);  
                        System.out.println("Restock request approved for: " + item.getMedicationName());
                    }
                }
            }
            if (!restockRequestedFound) {
                System.out.println("No items have been requested for a restock.");
            }
        }
    }
}
