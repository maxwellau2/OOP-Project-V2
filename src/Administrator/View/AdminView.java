package Administrator.View;

import Administrator.Controller.AdminActions;
import Administrator.Model.Admin;
import Appointment.Model.Appointment;
import Doctor.Model.Doctor;
import Inventory.Controller.InventoryController;
import Inventory.Model.Inventory;
import Pharmacist.Model.Pharmacist;
import Pharmacist.Repository.PharmacistRepository;
import Staff.Controller.StaffActions;
import Staff.Model.Staff;
import Staff.Repository.StaffRepository;
import User.Controller.UserController;

import java.util.List;
import java.util.Scanner;

import static Util.RepositoryGetter.*;
import static Util.SafeScanner.getValidatedIntInput;
import static Util.SafeScanner.getValidatedStringInput;

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
        int choice = getValidatedIntInput(scanner, "Choose an option: ", 1, 2);
        switch (choice) {
            case 1 -> addStaff();
            case 2 -> deleteStaff();
        }
    }

    // Add new staff member
    public void addStaff() {
        System.out.println("=== Add New Staff ===");

        // Step 1: Ask for the role first
        String role = getValidatedStringInput(scanner, "Enter Role (doctor, pharmacist, admin): ", List.of("doctor", "pharmacist", "admin"));

        Staff newStaff = null;

        switch (role.toLowerCase()) {
            case "doctor" -> {
                String name = getValidatedStringInput(scanner, "Enter Name: ", 30);
                String gender = getValidatedStringInput(scanner, "Enter Gender (male, female): ", List.of("male", "female"));
                int age = getValidatedIntInput(scanner, "Enter Age: ", 18, 100);
                String specialization = getValidatedStringInput(scanner, "Enter Specialization: ", 50);

                // Create Doctor and convert to Staff
                Doctor newDoctor = getDoctorRepository().createDoctor(name,age,gender,specialization);
                newStaff = getStaffRepoInstance().createStaffFromDoctor(newDoctor);
            }
            case "pharmacist" -> {
                String name = getValidatedStringInput(scanner, "Enter Name: ", 30);
                String gender = getValidatedStringInput(scanner, "Enter Gender (male, female): ", List.of("male", "female"));
                int age = getValidatedIntInput(scanner, "Enter Age: ", 18, 100);

                // Create Pharmacist and convert to Staff
                Pharmacist newPharmacist = PharmacistRepository.createPharmacist(name, age, gender);
                newStaff = StaffRepository.getInstance("").createStaffFromPharmacist(newPharmacist);

            }
            case "admin" -> {
                String name = getValidatedStringInput(scanner, "Enter Name: ", 30);
                String gender = getValidatedStringInput(scanner, "Enter Gender (male, female): ", List.of("male", "female"));
                int age = getValidatedIntInput(scanner, "Enter Age: ", 18, 100);

                // Create Admin and convert to Staff
                Admin newAdmin = getAdminRepository().createAdmin(name, gender, age);
                newStaff = getStaffRepoInstance().createStaffFromAdmin(newAdmin);
                // create new user profile
            }
            default -> System.out.println("Invalid role. Please try again.");
        }

        // Step 2: Add the new Staff object to the Staff repository
        if (newStaff != null) {
            newStaff = getStaffRepoInstance().create(newStaff);
            UserController.createNewUser(newStaff.getId(), newStaff.getRole());

            System.out.println("Successfully added staff:");
            newStaff.prettyPrint(); // Assuming `prettyPrint` is implemented in the `Staff` class
        } else {
            System.out.println("Failed to add new staff. Please try again.");
        }
    }


    // Delete a staff member by ID
    public void deleteStaff() {
        System.out.println("=== Delete Staff ===");
        System.out.print("Enter Staff ID to delete: ");
        String staffId = scanner.nextLine();
        Staff staffToDelete = StaffActions.getStaffById(staffId);
        if (staffToDelete != null){
            System.out.println("Staff not found");
        }
        boolean success = getStaffRepoInstance().deleteStaffById(staffId);
        if (success) {
            System.out.println("Staff member with ID " + staffId + " has been deleted.");
        } else {
            System.out.println("Staff member with ID " + staffId + " not found.");
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
                appointment.prettyPrint();
            }
        }
    }


    // View and manage medication inventory 
    public void viewAndManageMedicationInventory() {
        System.out.println("=== View and Manage Medication Inventory ===");
        List<Inventory> inventoryList = getInventoryRepoInstance().getAll();
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
        int choice = getValidatedIntInput(scanner, "Choose an option: ", 1, 2);
        switch (choice) {
            case 1 -> updateStock();
            case 2 -> addInventoryItem();
        }
    }

    // Update stock of an inventory item
    public void updateStock() {
        System.out.println("=== Update Stock ===");
        System.out.print("Enter Medication ID to update: ");
        String medicationId = scanner.nextLine();
        System.out.print("Enter New Quantity: ");
        int newQuantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Inventory itemToUpdate = new Inventory(medicationId, "", newQuantity, 0, false);
        boolean success = AdminActions.updateStock(itemToUpdate); 
        if (success) {
            System.out.println("Stock updated successfully.");
        } else {
            System.out.println("Stock update failed.");
        }
    }

    // Add new inventory item
    public void addInventoryItem() {
        System.out.println("=== Add New Inventory Item ===");
        System.out.print("Enter Medication ID: ");
        String medicationId = scanner.nextLine();
        System.out.print("Enter Medication Name: ");
        String medicationName = scanner.nextLine();
        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();
        System.out.print("Enter Low Stock Alert Level: ");
        int lowStockAlert = scanner.nextInt();
        System.out.print("Is medication available? (true/false): ");
        boolean isAvailable = scanner.nextBoolean();
        scanner.nextLine(); // Consume newline

        Inventory newItem = new Inventory(medicationId, medicationName, quantity, lowStockAlert, isAvailable);
        boolean success = AdminActions.updateStock(newItem); 
        if (success) {
            System.out.println("New inventory item added successfully.");
        } else {
            System.out.println("Failed to add new item.");
        }
    }

    // Approve replenishment requests
    public void approveReplenishmentRequests() {
        System.out.println("=== Approve Replenishment Requests ===");
        List<Inventory> lowStockItems = InventoryController.getLowStockInventoryPending();
        if (lowStockItems.isEmpty()) {
            System.out.println("No replenishment requests pending approval.");
        } else {
            for (Inventory item : lowStockItems) {
                if (item.getQuantity() < item.getLowStockAlert()) {
                    System.out.println("Item: " + item.getMedicationName() + " is low on stock.");
                    System.out.print("Approve restock request? (yes/no): ");
                    String response = scanner.nextLine();
                    if (response.equalsIgnoreCase("yes")) {
                        AdminActions.updateStock(item);  // Static method call corrected
                        System.out.println("Restock request approved for: " + item.getMedicationName());
                    }
                }
            }
        }
    }
}
